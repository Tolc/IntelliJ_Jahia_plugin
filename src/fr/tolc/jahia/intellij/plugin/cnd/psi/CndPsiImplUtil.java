package fr.tolc.jahia.intellij.plugin.cnd.psi;

import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.*;

import com.google.common.collect.Lists;
import com.intellij.lang.ASTNode;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiReference;
import com.intellij.psi.impl.source.resolve.reference.ReferenceProvidersRegistry;
import fr.tolc.jahia.intellij.plugin.cnd.enums.AttributeEnum;
import fr.tolc.jahia.intellij.plugin.cnd.enums.OptionEnum;
import fr.tolc.jahia.intellij.plugin.cnd.enums.PropertyTypeEnum;
import fr.tolc.jahia.intellij.plugin.cnd.enums.PropertyTypeMaskEnum;
import fr.tolc.jahia.intellij.plugin.cnd.icons.CndIcons;
import fr.tolc.jahia.intellij.plugin.cnd.utils.CndUtil;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CndPsiImplUtil {

    //Namespace
    public static String getNamespaceName(CndNamespace element) {
        return element.getNamespaceIdentifier().getNamespaceName();
    }

    public static PsiElement setNamespaceName(CndNamespace element, String newName) {
        return element.getNamespaceIdentifier().setNamespaceName(newName);
    }

    public static String getNamespaceURI(CndNamespace element) {
        ASTNode uriNode = element.getNode().findChildByType(CndTypes.NAMESPACE_URI);
        if (uriNode != null) {
            return uriNode.getText();
        }
        return null;
    }

    @NotNull
    @Contract(pure = true)
    public static ItemPresentation getPresentation(final CndNamespace element) {
        return new ItemPresentation() {
            @NotNull
            @Override
            public String getPresentableText() {
                return element.getNamespaceName() + " = '" + element.getNamespaceURI() + "'";
            }

            @Nullable
            @Override
            public String getLocationString() {
                PsiFile containingFile = element.getContainingFile();
                return containingFile == null ? null : containingFile.getName();
            }

            @Nullable
            @Override
            public Icon getIcon(boolean unused) {
                return CndIcons.NAMESPACE;
            }
        };
    }

    public static String toString(CndNamespace element) {
        return element.getPresentation().getPresentableText();
    }


    //Namespace Identifier
    @Nullable
    public static String getNamespaceName(CndNamespaceIdentifier element) {
        ASTNode nameNode = element.getNode().findChildByType(CndTypes.NAMESPACE_NAME);
        if (nameNode != null) {
            return nameNode.getText();
        }
        return null;
    }

    public static PsiElement setNamespaceName(CndNamespaceIdentifier element, String newName) {
        ASTNode nameNode = element.getNode().findChildByType(CndTypes.NAMESPACE_NAME);
        if (nameNode != null) {
            CndNamespace namespace = CndElementFactory.createNamespace(element.getProject(), newName);
            ASTNode newNamespaceNode = namespace.getNamespaceIdentifier().getNode().findChildByType(CndTypes.NAMESPACE_NAME);
            element.getNode().replaceChild(nameNode, newNamespaceNode);
        }
        return element;
    }

    @Nullable
    public static PsiElement getNameIdentifier(CndNamespaceIdentifier element) {
        ASTNode namespaceNameNode = element.getNode().findChildByType(CndTypes.NAMESPACE_NAME);
        if (namespaceNameNode != null) {
            return namespaceNameNode.getPsi();
        } else {
            return null;
        }
    }

    public static PsiElement setName(CndNamespaceIdentifier element, String newName) {
        return setNamespaceName(element, newName);
    }

    public static String getName(CndNamespaceIdentifier element) {
        return getNamespaceName(element);
    }

    public static CndNamespace getNamespace(CndNamespaceIdentifier element) {
        return (CndNamespace) element.getParent();
    }

    public static ItemPresentation getPresentation(final CndNamespaceIdentifier element) {
        return element.getNamespace().getPresentation();
    }
    
    



    //NodeType
    public static String getNodeTypeName(CndNodeType element) {
        return element.getNodeTypeIdentifier().getNodeTypeName();
    }

    public static PsiElement setNodeTypeName(CndNodeType element, String newName) {
        return element.getNodeTypeIdentifier().setNodeTypeName(newName);
    }

    @Nullable
    public static String getNodeTypeNamespace(CndNodeType element) {
        ASTNode namespaceName = element.getNode().findChildByType(CndTypes.NAMESPACE_NAME);
        if (namespaceName != null) {
            return namespaceName.getText();
        }
        return null;
    }

    //TODO: use a proper cache mechanism...
    private static Map<CndNodeType, Set<CndProperty>> propertiesCache = new ConcurrentHashMap<>();
    private static Map<CndNodeType, Date> propertiesCacheDate = new ConcurrentHashMap<>();
    private static final long CACHE_DELAY = 10000;   //10s
    
    /**
     * Get nodetype properties recursively
     */
    @NotNull
    public static Set<CndProperty> getProperties(CndNodeType element) {
        Set<CndProperty> result = null;
        if (propertiesCacheDate.containsKey(element)) {
            Date date = propertiesCacheDate.get(element);
            if (date != null && new Date().getTime() - date.getTime() >= CACHE_DELAY) {
                //remove from cache
                propertiesCache.remove(element);
                propertiesCacheDate.remove(element);
            } else {
                result = propertiesCache.get(element);
            }
        } 
        if (result == null) {
            result = new LinkedHashSet<>();
            result.addAll(element.getPropertyList());
            for (CndNodeType ancestorNodeType : element.getAncestorsNodeTypes()) {
                result.addAll(getProperties(ancestorNodeType));
            }

            for (CndNodeType extensionNodeType : element.getExtensions()) {
                result.addAll(getProperties(extensionNodeType));
            }
            
            //Put in cache
            propertiesCache.put(element, result);
            propertiesCacheDate.put(element, new Date());
        }
        return result;
    }

    @NotNull
    public static Set<CndProperty> getOwnProperties(CndNodeType element) {
        return new LinkedHashSet<>(element.getPropertyList());
    }

    @Nullable
    public static CndProperty getProperty(CndNodeType element, String propertyName) {
        Set<CndProperty> properties = element.getProperties();
        for (CndProperty property : properties) {
            if (property.getPropertyName().equals(propertyName)) {
                return property;
            }
        }
        return null;
    }

    @Nullable
    public static CndProperty getOwnProperty(CndNodeType element, String propertyName) {
        Set<CndProperty> properties = element.getOwnProperties();
        for (CndProperty property : properties) {
            if (property.getPropertyName().equals(propertyName)) {
                return property;
            }
        }
        return null;
    }

    @NotNull
    public static Set<OptionEnum> getOptions(CndNodeType element) {
        Set<OptionEnum> result = new HashSet<OptionEnum>();
        for (CndNodeOption cndOption : element.getNodeOptionList()) {
            try {
                result.add(OptionEnum.fromValue(cndOption.getText()));
            } catch (IllegalArgumentException e) {
                //Nothing to do
            }
        }
        return result;
    }

    public static boolean isMixin(CndNodeType element) {
        return element.getOptions().contains(OptionEnum.MIXIN);
    }

    @NotNull
    public static Set<CndNodeType> getParentsNodeTypes(CndNodeType element) {
        Set<CndNodeType> result = new LinkedHashSet<CndNodeType>();
        if (element.getSuperTypes() != null) {
            List<CndSuperType> superTypes = Lists.reverse(element.getSuperTypes().getSuperTypeList());  //Reverse list because Jahia super types priority is from right to left
            for (CndSuperType superType : superTypes) {
                CndNodeType nodeType = CndUtil.findNodeType(element.getProject(), superType.getNodeTypeNamespace(), superType.getNodeTypeName());
                if (nodeType != null) {
                    result.add(nodeType);
                }
            }
        }
        return result;
    }

    @NotNull
    public static Set<CndNodeType> getAncestorsNodeTypes(CndNodeType element) {
        Set<CndNodeType> result = new LinkedHashSet<CndNodeType>();
        for (CndNodeType parentNodeType : element.getParentsNodeTypes()) {
            result.add(parentNodeType);
            result.addAll(getAncestorsNodeTypes(parentNodeType));
        }
        return result;
    }

    @NotNull
    public static Set<CndNodeType> getExtensions(CndNodeType element) {
        return CndUtil.findExtensionNodeTypes(element);
    }

    @NotNull
    @Contract(pure = true)
    public static ItemPresentation getPresentation(final CndNodeType element) {
        return new ItemPresentation() {
            @NotNull
            @Override
            public String getPresentableText() {
                return element.getNodeTypeNamespace() + ":" + element.getNodeTypeName();
            }

            @Nullable
            @Override
            public String getLocationString() {
                PsiFile containingFile = element.getContainingFile();
                return containingFile == null ? null : containingFile.getName();
            }

            @Nullable
            @Override
            public Icon getIcon(boolean unused) {
                return element.isMixin()? CndIcons.MIXIN : CndIcons.NODE_TYPE;
            }
        };
    }
    
    public static String toString(CndNodeType element) {
        return element.getPresentation().getPresentableText();
    }



    //NodeType Identifier
    @Nullable
    public static String getNodeTypeName(CndNodeTypeIdentifier element) {
        ASTNode nameNode = element.getNode().findChildByType(CndTypes.NODE_TYPE_NAME);
        if (nameNode != null) {
            return nameNode.getText();
        }
        return null;
    }

    public static PsiElement setNodeTypeName(CndNodeTypeIdentifier element, String newName) {
        ASTNode nameNode = element.getNode().findChildByType(CndTypes.NODE_TYPE_NAME);
        if (nameNode != null) {
            CndNodeType nodeType = CndElementFactory.createNodeType(element.getProject(), newName);
            ASTNode newNodeTypeNode = nodeType.getNodeTypeIdentifier().getNode().findChildByType(CndTypes.NODE_TYPE_NAME);
            element.getNode().replaceChild(nameNode, newNodeTypeNode);
        }
        return element;
    }

    @Nullable
    public static PsiElement getNameIdentifier(CndNodeTypeIdentifier element) {
        ASTNode nodeTypeNameNode = element.getNode().findChildByType(CndTypes.NODE_TYPE_NAME);
        if (nodeTypeNameNode != null) {
            return nodeTypeNameNode.getPsi();
        } else {
            return null;
        }
    }

    public static PsiElement setName(CndNodeTypeIdentifier element, String newName) {
        return setNodeTypeName(element, newName);
    }

    public static String getName(CndNodeTypeIdentifier element) {
        return getNodeTypeName(element);
    }
    
    @Contract(pure = true)
    public static CndNodeType getNodeType(CndNodeTypeIdentifier element) {
        return (CndNodeType) element.getParent();
    }

    public static ItemPresentation getPresentation(final CndNodeTypeIdentifier element) {
        return element.getNodeType().getPresentation();
    }
    
    
    


    //Property
    public static String getPropertyName(CndProperty element) {
        return element.getPropertyIdentifier().getPropertyName();
    }

    public static PsiElement setPropertyName(CndProperty element, String newName) {
        return element.getPropertyIdentifier().setPropertyName(newName);
    }

    @Nullable
    public static PropertyTypeEnum getType(CndProperty element) {
        ASTNode propertyType = element.getNode().findChildByType(CndTypes.PROPERTY_TYPE);
        if (propertyType != null) {
            try {
                return PropertyTypeEnum.fromValue(propertyType.getText());
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
        return null;
    }

    @Nullable
    public static PropertyTypeMaskEnum getTypeMask(CndProperty element) {
        ASTNode propertyMask = element.getNode().findChildByType(CndTypes.PROPERTY_MASK);
        if (propertyMask != null) {
            try {
                return PropertyTypeMaskEnum.fromValue(propertyMask.getText());
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
        return null;
    }

    @NotNull
    @Contract(pure = true)
    public static ItemPresentation getPresentation(final CndProperty element) {
        return new ItemPresentation() {
            @NotNull
            @Override
            public String getPresentableText() {
                return element.getNodeType().getPresentation().getPresentableText() + "  -  " + element.getPropertyName() + " (" + element.getType() + ")";
            }

            @Nullable
            @Override
            public String getLocationString() {
                PsiFile containingFile = element.getContainingFile();
                return containingFile == null ? null : containingFile.getName();
            }

            @Nullable
            @Override
            public Icon getIcon(boolean unused) {
                return CndIcons.PROPERTY;
            }
        };
    }
    
    public static String toString(CndProperty element) {
        return element.getPresentation().getPresentableText();
    }

    public static boolean hasAttribute(final CndProperty element, final AttributeEnum attribute) {
        CndPropertyAttributes propertyAttributes = element.getPropertyAttributes();
        if (propertyAttributes != null) {
            return AttributeEnum.textContainsAttribute(propertyAttributes.getText(), attribute);
        }
        return false;
    }

    public static boolean isMultiple(final CndProperty element) {
        return hasAttribute(element, AttributeEnum.MULTIPLE);
    }

    public static CndNodeType getNodeType(final CndProperty element) {
        return (CndNodeType) element.getParent();
    }




        //Property Identifier
    @Nullable
    public static String getPropertyName(CndPropertyIdentifier element) {
        ASTNode nameNode = element.getNode().findChildByType(CndTypes.PROPERTY_NAME);
        if (nameNode != null) {
            return nameNode.getText();
        }
        return null;
    }

    public static PsiElement setPropertyName(CndPropertyIdentifier element, String newName) {
        ASTNode nameNode = element.getNode().findChildByType(CndTypes.PROPERTY_NAME);
        if (nameNode != null) {
            CndProperty property = CndElementFactory.createProperty(element.getProject(), newName);
            ASTNode newPropertyNameNode = property.getPropertyIdentifier().getNode().findChildByType(CndTypes.PROPERTY_NAME);
            element.getNode().replaceChild(nameNode, newPropertyNameNode);
        }
        return element;
    }

    @Nullable
    public static PsiElement getNameIdentifier(CndPropertyIdentifier element) {
        ASTNode propertyNameNode = element.getNode().findChildByType(CndTypes.PROPERTY_NAME);
        if (propertyNameNode != null) {
            return propertyNameNode.getPsi();
        } else {
            return null;
        }
    }

    public static PsiElement setName(CndPropertyIdentifier element, String newName) {
        return setPropertyName(element, newName);
    }

    public static String getName(CndPropertyIdentifier element) {
        return getPropertyName(element);
    }

    @Contract(pure = true)
    public static CndProperty getProperty(CndPropertyIdentifier element) {
        return (CndProperty) element.getParent();
    }
    
    public static ItemPresentation getPresentation(final CndPropertyIdentifier element) {
        return element.getProperty().getPresentation();
    }


    //SubNode
    @Nullable
    public static String getSubNodeName(CndSubNode element) {
        ASTNode nameNode = element.getNode().findChildByType(CndTypes.NODE_NAME);
        if (nameNode != null) {
            return nameNode.getText();
        }
        return null;
    }
    

    //SuperType
    @NotNull
    public static PsiReference[] getReferences(CndSuperType element) {
        return ReferenceProvidersRegistry.getReferencesFromProviders(element);
    }

    @Nullable
    public static String getNodeTypeName(CndSuperType element) {
        ASTNode nodeTypeName = element.getNode().findChildByType(CndTypes.NODE_TYPE_NAME);
        if (nodeTypeName != null) {
            return nodeTypeName.getText();
        }
        return null;
    }

    @Nullable
    public static String getNodeTypeNamespace(CndSuperType element) {
        ASTNode namespaceName = element.getNode().findChildByType(CndTypes.NAMESPACE_NAME);
        if (namespaceName != null) {
            return namespaceName.getText();
        }
        return null;
    }
    

    //Extension
    @NotNull
    public static PsiReference[] getReferences(CndExtension element) {
        return ReferenceProvidersRegistry.getReferencesFromProviders(element);
    }

    @Nullable
    public static String getNodeTypeNamespace(CndExtension element) {
        ASTNode namespaceName = element.getNode().findChildByType(CndTypes.NAMESPACE_NAME);
        if (namespaceName != null) {
            return namespaceName.getText();
        }
        return null;
    }
    
    @Nullable
    public static String getNodeTypeName(CndExtension element) {
        ASTNode nameNode = element.getNode().findChildByType(CndTypes.NODE_TYPE_NAME);
        if (nameNode != null) {
            return nameNode.getText();
        }
        return null;
    }

    //SubNodeType
    @NotNull
    public static PsiReference[] getReferences(CndSubNodeType element) {
        return ReferenceProvidersRegistry.getReferencesFromProviders(element);
    }
    
    //SubNodeDefaultType
    @NotNull
    public static PsiReference[] getReferences(CndSubNodeDefaultType element) {
        return ReferenceProvidersRegistry.getReferencesFromProviders(element);
    }
}

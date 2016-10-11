package fr.tolc.jahia.intellij.plugin.cnd.psi.impl;

import javax.swing.*;

import com.intellij.lang.ASTNode;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiReference;
import com.intellij.psi.impl.source.resolve.reference.ReferenceProvidersRegistry;
import fr.tolc.jahia.intellij.plugin.cnd.CndIcons;
import fr.tolc.jahia.intellij.plugin.cnd.psi.*;
import org.jetbrains.annotations.Nullable;

public class CndPsiImplUtil {

    //Namespace
    public static String getNamespaceName(CndNamespace element) {
        ASTNode nameNode= element.getNode().findChildByType(CndTypes.NAMESPACE_NAME);
        if (nameNode != null) {
            return nameNode.getText();
        }
        return null;
    }

    public static PsiElement setNamespaceName(CndNamespace element, String newName) {
        ASTNode nameNode = element.getNode().findChildByType(CndTypes.NAMESPACE_NAME);
        if (nameNode != null) {
            CndNodeType namespace = CndElementFactory.createNamespace(element.getProject(), newName);
            ASTNode newNamespaceNode = namespace.getChildren()[1].getNode();
            element.getNode().replaceChild(nameNode, newNamespaceNode);
        }
        return element;
    }

    public static String getNamespaceURI(CndNamespace element) {
        ASTNode uriNode= element.getNode().findChildByType(CndTypes.NAMESPACE_URI);
        if (uriNode != null) {
            return uriNode.getText();
        }
        return null;
    }

    public static PsiElement getNameIdentifier(CndNamespace element) {
        ASTNode namespaceNameNode = element.getNode().findChildByType(CndTypes.NAMESPACE_NAME);
        if (namespaceNameNode != null) {
            return namespaceNameNode.getPsi();
        } else {
            return null;
        }
    }

    public static PsiElement setName(CndNamespace element, String newName) {
        return setNamespaceName(element, newName);
    }

    public static String getName(CndNamespace element) {
        return getNamespaceName(element);
    }

    public static ItemPresentation getPresentation(final CndNamespace element) {
        return new ItemPresentation() {
            @Nullable
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
                return CndIcons.FILE;
            }
        };
    }



    //NodeType
    public static String getNodeTypeName(CndNodeType element) {
        ASTNode nameNode= element.getNode().findChildByType(CndTypes.NODE_TYPE_NAME);
        if (nameNode != null) {
            return nameNode.getText();
        }
        return null;
    }

    public static PsiElement setNodeTypeName(CndNodeType element, String newName) {
        ASTNode nameNode = element.getNode().findChildByType(CndTypes.NODE_TYPE_NAME);
        if (nameNode != null) {
            ASTNode namespaceNode = element.getNode().findChildByType(CndTypes.NAMESPACE_NAME);
            String namespace = "dummyNameSpace";
            if (namespaceNode != null) {
                namespace = namespaceNode.getText();
            }
            CndNodeType nodeType = CndElementFactory.createNodeType(element.getProject(), newName, namespace);
            ASTNode newNodeTypeNode = nodeType.getChildren()[3].getNode();
            element.getNode().replaceChild(nameNode, newNodeTypeNode);
        }
        return element;
    }

    public static String getNodeTypeNamespace(CndNodeType element) {
        ASTNode namespaceName= element.getNode().findChildByType(CndTypes.NAMESPACE_NAME);
        if (namespaceName != null) {
            return namespaceName.getText();
        }
        return null;
    }

    public static PsiElement getNameIdentifier(CndNodeType element) {
        ASTNode nodeTypeNameNode = element.getNode().findChildByType(CndTypes.NODE_TYPE_NAME);
        if (nodeTypeNameNode != null) {
            return nodeTypeNameNode.getPsi();
        } else {
            return null;
        }
    }

    public static PsiElement setName(CndNodeType element, String newName) {
        return setNodeTypeName(element, newName);
    }

    public static String getName(CndNodeType element) {
        return getNodeTypeName(element);
    }

    public static ItemPresentation getPresentation(final CndNodeType element) {
        return new ItemPresentation() {
            @Nullable
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
                return CndIcons.FILE;
            }
        };
    }



    //SuperType
    public static PsiReference[] getReferences(CndSuperType element) {
        return ReferenceProvidersRegistry.getReferencesFromProviders(element);
    }

    //Extension
    public static PsiReference[] getReferences(CndExtension element) {
        return ReferenceProvidersRegistry.getReferencesFromProviders(element);
    }

    //SubNodeType
    public static PsiReference[] getReferences(CndSubNodeType element) {
        return ReferenceProvidersRegistry.getReferencesFromProviders(element);
    }
    //SubNodeDefaultType
    public static PsiReference[] getReferences(CndSubNodeDefaultType element) {
        return ReferenceProvidersRegistry.getReferencesFromProviders(element);
    }
}

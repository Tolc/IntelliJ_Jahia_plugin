package fr.tolc.jahia.intellij.plugin.cnd.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndElementFactory;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndNamespace;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndNodeType;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndTypes;

public class CndPsiImplUtil {

    //Namespace
    public static String getNamespaceName(CndNamespace element) {
        ASTNode nameNode= element.getNode().findChildByType(CndTypes.NAMESPACE_NAME);
        if (nameNode != null) {
            return nameNode.getText();
        }
        return null;
    }

    public static String getNamespaceURI(CndNamespace element) {
        ASTNode uriNode= element.getNode().findChildByType(CndTypes.NAMESPACE_URI);
        if (uriNode != null) {
            return uriNode.getText();
        }
        return null;
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
            ASTNode namespaceNode = element.getNode().findChildByType(CndTypes.NODE_TYPE_NAMESPACE);
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
        ASTNode namespaceName= element.getNode().findChildByType(CndTypes.NODE_TYPE_NAMESPACE);
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

//    public static ItemPresentation getPresentation(final SimpleProperty element) {
//        return new ItemPresentation() {
//            @Nullable
//            @Override
//            public String getPresentableText() {
//                return element.getKey();
//            }
//
//            @Nullable
//            @Override
//            public String getLocationString() {
//                PsiFile containingFile = element.getContainingFile();
//                return containingFile == null ? null : containingFile.getName();
//            }
//
//            @Nullable
//            @Override
//            public Icon getIcon(boolean unused) {
//                return SimpleIcons.FILE;
//            }
//        };
//    }
}

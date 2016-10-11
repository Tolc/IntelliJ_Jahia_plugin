package fr.tolc.jahia.intellij.plugin.cnd.references;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiLiteralExpression;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceProvider;
import com.intellij.util.ProcessingContext;
import fr.tolc.jahia.intellij.plugin.cnd.CndUtil;
import fr.tolc.jahia.intellij.plugin.cnd.model.NodeTypeModel;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndExtension;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndSubNodeDefaultType;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndSubNodeType;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndSuperType;
import org.jetbrains.annotations.NotNull;

public class CndReferenceProvider extends PsiReferenceProvider {

    @NotNull
    @Override
    public PsiReference[] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext context) {
        String nodetypeText = getNodeTypeText(element);
        
        if (nodetypeText != null) {
            NodeTypeModel nodeTypeModel = CndUtil.getNodeTypeModel(nodetypeText);

            if (nodeTypeModel != null) {
                int offset = getOffset(element);
                String namespace = nodeTypeModel.getNamespace();
                String nodeTypeName = nodeTypeModel.getNodeTypeName();

                return new PsiReference[] {
                        //Text range here is relative!!
                        new CndNamespaceReference(element, new TextRange(offset, namespace.length() + offset), namespace),
                        new CndNodeTypeReference(element, new TextRange(namespace.length() + offset + 1, nodetypeText.length() + offset), namespace, nodeTypeName)
                };
            }
        }
        return new PsiReference[0];
    }

    /**
     * Extract full node type text (i.e. namespace:nodeTypeName) from element, based on element class/type
     */
    private String getNodeTypeText(@NotNull PsiElement element) {
        if (element instanceof  PsiLiteralExpression) { //Java
            PsiLiteralExpression literalExpression = (PsiLiteralExpression) element;
            return literalExpression.getValue() instanceof String ? (String) literalExpression.getValue() : null;
        } else if (element instanceof CndSuperType) {   //Cnd super types
            CndSuperType superType = (CndSuperType) element;
            return  superType.getText();
        } else if (element instanceof CndExtension) {   //Cnd extends types
            CndExtension extension = (CndExtension) element;
            return  extension.getText();
        } else if (element instanceof CndSubNodeType) {   //Cnd subnode types
            CndSubNodeType subNodeType = (CndSubNodeType) element;
            return  subNodeType.getText();
        } else if (element instanceof CndSubNodeDefaultType) {   //Cnd subnode default type
            CndSubNodeDefaultType  subNodeDefaultType = (CndSubNodeDefaultType) element;
            return  subNodeDefaultType.getText();
        }
        return null;
    }
    
    private int getOffset(@NotNull PsiElement element) {
        if (element instanceof  PsiLiteralExpression) { //Java
            return 1;
        } else if (element instanceof CndSuperType) {   //Cnd super types
            return 0;
        } else if (element instanceof CndExtension) {   //Cnd extends types
            return 0;
        } else if (element instanceof CndSubNodeType) {   //Cnd subnode types
            return 0;
        } else if (element instanceof CndSubNodeDefaultType) {   //Cnd subnode default type
            return 0;
        }
        return 0;
    }
}

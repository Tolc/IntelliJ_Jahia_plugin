package fr.tolc.jahia.intellij.plugin.cnd.references;

import static fr.tolc.jahia.intellij.plugin.cnd.model.NodeTypeModel.nodeTypeGlobalRegex;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import com.intellij.lang.properties.psi.impl.PropertyKeyImpl;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiLiteralExpression;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceProvider;
import com.intellij.psi.xml.XmlToken;
import com.intellij.util.ProcessingContext;
import fr.tolc.jahia.intellij.plugin.cnd.model.NodeTypeModel;
import fr.tolc.jahia.intellij.plugin.cnd.model.PropertiesFileCndKeyModel;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndExtension;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndPropertyConstraint;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndSubNodeDefaultType;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndSubNodeType;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndSuperType;
import fr.tolc.jahia.intellij.plugin.cnd.references.types.CndNamespaceIdentifierReference;
import fr.tolc.jahia.intellij.plugin.cnd.references.types.CndNodeTypeIdentifierReference;
import fr.tolc.jahia.intellij.plugin.cnd.references.types.CndPropertyIdentifierReference;
import org.jetbrains.annotations.NotNull;

public class CndReferenceProvider extends PsiReferenceProvider {

    @NotNull
    @Override
    public PsiReference[] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext context) {
        if (element instanceof PropertyKeyImpl) {
            //Properties files
            String key = element.getText();

            PropertiesFileCndKeyModel cndKeyModel = null;
            try {
                cndKeyModel = new PropertiesFileCndKeyModel(key);
            } catch (IllegalArgumentException e) {
                //Nothing to do
            }
            
            if (cndKeyModel != null) {
                PsiReference[] psiReferences = new PsiReference[3];
                
                String namespace = cndKeyModel.getNamespace();
                String nodeTypeName = cndKeyModel.getNodeTypeName();
                CndNamespaceIdentifierReference cndNamespaceReference = new CndNamespaceIdentifierReference(element, new TextRange(0, namespace.length()), namespace);  //Text ranges here are relative!!
                CndNodeTypeIdentifierReference cndNodeTypeReference = new CndNodeTypeIdentifierReference(element, new TextRange(namespace.length() + 1, namespace.length() + 1 + nodeTypeName.length()), namespace, nodeTypeName);
                psiReferences[0] = cndNamespaceReference;
                psiReferences[1] = cndNodeTypeReference;
                
                if (cndKeyModel.isProperty() || cndKeyModel.isChoicelistElement() || cndKeyModel.isPropertyTooltip()) {
                    String propertyName = cndKeyModel.getPropertyName();
                    CndPropertyIdentifierReference propertyReference = new CndPropertyIdentifierReference(element, new TextRange(namespace.length() + 1 + nodeTypeName.length() + 1, namespace.length() + 1 + nodeTypeName.length() + 1 + propertyName.length()), namespace, nodeTypeName, propertyName, true);
                    psiReferences[2] = propertyReference;
                }
                return psiReferences;
            }
        } else {
            String nodetypeText = getNodeTypeText(element);
            if (nodetypeText != null) {
                List<PsiReference> psiReferences = new ArrayList<>();

                Matcher matcher = nodeTypeGlobalRegex.matcher(nodetypeText);
                while (matcher.find()) {
                    String group = matcher.group();

                    NodeTypeModel nodeTypeModel = null;
                    try {
                        nodeTypeModel = new NodeTypeModel(group);
                    } catch (IllegalArgumentException e) {
                        //Nothing to do
                    }

                    if (nodeTypeModel != null) {
                        int offset = getOffset(element) + matcher.start();
                        String namespace = nodeTypeModel.getNamespace();
                        String nodeTypeName = nodeTypeModel.getNodeTypeName();

                        //Text ranges here are relative!!
                        psiReferences.add(new CndNamespaceIdentifierReference(element, new TextRange(offset, namespace.length() + offset), namespace));
                        psiReferences.add(new CndNodeTypeIdentifierReference(element, new TextRange(namespace.length() + offset + 1, group.length() + offset), namespace, nodeTypeName));
                    }
                }

                PsiReference[] psiReferencesArray = new PsiReference[psiReferences.size()];
                return psiReferences.toArray(psiReferencesArray);
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
            return  element.getText();
        } else if (element instanceof CndExtension) {   //Cnd extends types
            return  element.getText();
        } else if (element instanceof CndSubNodeType) {   //Cnd subnode types
            return  element.getText();
        } else if (element instanceof CndSubNodeDefaultType) {   //Cnd subnode default type
            return  element.getText();
            //TODO: property constraint ref
//        } else if (element instanceof CndPropertyConstraint) {   //Cnd property weakreference constraint
//            return element.getText();
//        } else if (element instanceof XmlAttributeValue) {    //XML Attribute value
//            return ((XmlAttributeValue) element).getValue();
        } else if (element instanceof XmlToken) {           //XML Text
            return element.getText();
        }
        return null;
    }
    
    //TODO: remove?
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
        } else if (element instanceof CndPropertyConstraint) {   //Cnd property weakreference constraint
            return 0;
//        } else if (element instanceof XmlAttributeValue) {    //XML Attribute value
//            return 1;
        } else if (element instanceof XmlToken) {           //XML Text
            return 0;
        }
        return 0;
    }
}

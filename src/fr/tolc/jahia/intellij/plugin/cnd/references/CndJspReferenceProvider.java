package fr.tolc.jahia.intellij.plugin.cnd.references;

import static fr.tolc.jahia.intellij.plugin.cnd.model.NodeTypeModel.nodeTypeGlobalRegex;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceProvider;
import com.intellij.psi.jsp.el.ELElementTypes;
import com.intellij.psi.xml.XmlAttributeValue;
import com.intellij.psi.xml.XmlElementType;
import com.intellij.util.ProcessingContext;
import com.intellij.util.containers.HashSet;
import fr.tolc.jahia.intellij.plugin.cnd.model.NodeTypeModel;
import fr.tolc.jahia.intellij.plugin.cnd.references.types.CndNamespaceIdentifierReference;
import fr.tolc.jahia.intellij.plugin.cnd.references.types.CndNodeTypeIdentifierReference;
import fr.tolc.jahia.intellij.plugin.cnd.utils.PsiUtil;
import org.jetbrains.annotations.NotNull;

public class CndJspReferenceProvider extends PsiReferenceProvider {

    @NotNull
    @Override
    public PsiReference[] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext context) {
        if (element instanceof XmlAttributeValue) {
            Set<PsiElement> literalExpressions = PsiUtil.findDescendantsByType(element, ELElementTypes.EL_LITERAL_EXPRESSION);
            Set<PsiElement> attributeValueTokens = PsiUtil.findDescendantsByType(element, XmlElementType.XML_ATTRIBUTE_VALUE_TOKEN);

            Set<PsiElement> elements = new HashSet<PsiElement>();
            elements.addAll(literalExpressions);
            elements.addAll(attributeValueTokens);

            for (PsiElement psiElement : elements) {
                if (!ELElementTypes.EL_SLICE_EXPRESSION.equals(psiElement.getParent().getNode().getElementType())) {
                    String nodetypeText = psiElement.getText();

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
                                int offset = (psiElement.getTextRange().getStartOffset() - element.getTextRange().getStartOffset()) + matcher.start();
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
            }
        }
        return new PsiReference[0];
    }
}

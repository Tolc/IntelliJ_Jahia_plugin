package fr.tolc.jahia.intellij.plugin.cnd.references;

import static fr.tolc.jahia.intellij.plugin.cnd.model.PropertyModel.propertyGetRegex;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceProvider;
import com.intellij.psi.jsp.el.ELElementTypes;
import com.intellij.psi.jsp.el.ELLiteralExpression;
import com.intellij.psi.jsp.el.ELVariable;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlAttributeValue;
import com.intellij.psi.xml.XmlElementType;
import com.intellij.psi.xml.XmlTag;
import com.intellij.util.ProcessingContext;
import fr.tolc.jahia.intellij.plugin.cnd.utils.JspUtil;
import fr.tolc.jahia.intellij.plugin.cnd.utils.PsiUtil;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

public class CndJspReferenceProvider extends PsiReferenceProvider {

    @NotNull
    @Override
    public PsiReference[] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext context) {
        List<PsiReference> psiReferences = new ArrayList<>();

        if (element instanceof XmlAttributeValue) {
            if (JspUtil.isJcrNodePropertyName((XmlAttributeValue) element)) {
                //<jcr:nodeProperty var="lol" node="${currentNode}" name="lol"/>
                XmlAttributeValue attributeValue = (XmlAttributeValue) element;

                String propertyName = attributeValue.getValue();
                XmlAttribute nodeAttr = ((XmlTag) attributeValue.getParent().getParent()).getAttribute(JspUtil.TAG_ATTRIBUTE_NODE);

                if (nodeAttr != null) {
                    String nodeVar = (nodeAttr.getValue() != null) ? nodeAttr.getValue().replaceAll("\\$\\{|}", "") : "";

                    ReferenceProviderUtil.createPropertyReferences(element, psiReferences, nodeVar, propertyName, 1);
                    
                    PsiReference[] psiReferencesArray = new PsiReference[psiReferences.size()];
                    return psiReferences.toArray(psiReferencesArray);
                }
            } else {
                Set<PsiElement> elements = PsiUtil.findDescendantsByType(element, ELElementTypes.EL_LITERAL_EXPRESSION, XmlElementType.XML_ATTRIBUTE_VALUE_TOKEN);

                if (!elements.isEmpty()) {
                    for (PsiElement psiElement : elements) {
                        PsiElement parent = psiElement.getParent();
                        IElementType parentElementType = parent.getNode().getElementType();
                        if (!ELElementTypes.EL_SLICE_EXPRESSION.equals(parentElementType) && !ELElementTypes.EL_SELECT_EXPRESSION.equals(parentElementType)) {
                            String nodetypeText = psiElement.getText();

                            int offsetShift = (psiElement.getTextRange().getStartOffset() - element.getTextRange().getStartOffset());
                            ReferenceProviderUtil.createNodeTypeReferences(element, psiReferences, nodetypeText, offsetShift);
                        }
                    }
                    PsiReference[] psiReferencesArray = new PsiReference[psiReferences.size()];
                    return psiReferences.toArray(psiReferencesArray);
                }
            }
        } else if (element instanceof ELLiteralExpression || element instanceof ELVariable) {
            //currentNode.properties['desc'].string and/or currentNode.properties.desc
            PsiElement parent = element.getParent();
            IElementType parentElementType = parent.getNode().getElementType();

            if (ELElementTypes.EL_SLICE_EXPRESSION.equals(parentElementType) || ELElementTypes.EL_SELECT_EXPRESSION.equals(parentElementType)) {
                String value = parent.getText();

                Matcher matcher = propertyGetRegex.matcher(value);
                while (matcher.find()) {
                    String nodeVar = StringUtils.isNotBlank(matcher.group(1)) ? matcher.group(1) : matcher.group(3);
                    String propertyName = StringUtils.isNotBlank(matcher.group(2)) ? matcher.group(2) : matcher.group(4);

                    int startOffset = element instanceof ELLiteralExpression ? 1 : 0;
                    int endOffset = element instanceof ELLiteralExpression ? 1 : 0;

                    if (element.getText().substring(startOffset, element.getText().length() - endOffset).equals(propertyName)) {
                        ReferenceProviderUtil.createPropertyReferences(element, psiReferences, nodeVar, propertyName, startOffset);
                    }
                }
            }
            PsiReference[] psiReferencesArray = new PsiReference[psiReferences.size()];
            return psiReferences.toArray(psiReferencesArray);
        }
        return new PsiReference[0];
    }
}
    

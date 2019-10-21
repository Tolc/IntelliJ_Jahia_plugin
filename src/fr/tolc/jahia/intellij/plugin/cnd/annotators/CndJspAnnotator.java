package fr.tolc.jahia.intellij.plugin.cnd.annotators;

import static fr.tolc.jahia.intellij.plugin.cnd.model.PropertyModel.propertyGetRegex;

import java.util.Set;
import java.util.regex.Matcher;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.psi.PsiElement;
import com.intellij.psi.impl.source.jsp.jspXml.JspOuterLanguageElement;
import com.intellij.psi.jsp.el.ELElementTypes;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlAttributeValue;
import com.intellij.psi.xml.XmlElementType;
import com.intellij.psi.xml.XmlTag;
import fr.tolc.jahia.intellij.plugin.cnd.utils.JspUtil;
import fr.tolc.jahia.intellij.plugin.cnd.utils.PsiUtil;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

public class CndJspAnnotator implements Annotator {


    @Override
    public void annotate(@NotNull final PsiElement element, @NotNull AnnotationHolder holder) {
        if (XmlElementType.XML_ATTRIBUTE_VALUE.equals(element.getNode().getElementType())) {
            if (JspUtil.isJcrNodePropertyName((XmlAttributeValue) element)) {
                //<jcr:nodeProperty var="lol" node="${currentNode}" name="lol"/>
                XmlAttributeValue attributeValue = (XmlAttributeValue) element;

                String propertyName = attributeValue.getValue();
                XmlAttribute nodeAttr = ((XmlTag) attributeValue.getParent().getParent()).getAttribute(JspUtil.TAG_ATTRIBUTE_NODE);

                if (nodeAttr != null) {
                    String nodeVar = (nodeAttr.getValue() != null) ? nodeAttr.getValue().replaceAll("\\$\\{|}", "") : "";

                    int offset = attributeValue.getTextRange().getStartOffset() + 1;

                    AnnotatorUtil.createPropertyAnnotations(element, holder, nodeVar, propertyName, offset);
                }
                
                return;
            }
        }

        if (element instanceof JspOuterLanguageElement) {
            String value = element.getText();

            Matcher matcher = propertyGetRegex.matcher(value);
            while (matcher.find()) {
                String nodeVar = StringUtils.isNotBlank(matcher.group(1)) ? matcher.group(1) : matcher.group(3);
                String propertyName = StringUtils.isNotBlank(matcher.group(2)) ? matcher.group(2) : matcher.group(4);

                int offset = element.getTextRange().getStartOffset() + ((matcher.start(2) > -1) ? matcher.start(2) : matcher.start(4));

                AnnotatorUtil.createPropertyAnnotations(element, holder, nodeVar, propertyName, offset);
            }
        }

        Set<PsiElement> literalExpressions = PsiUtil.findFirstDescendantsByType(element, ELElementTypes.EL_LITERAL_EXPRESSION);
        for (PsiElement literalExpression : literalExpressions) {
            if (!ELElementTypes.EL_SLICE_EXPRESSION.equals(literalExpression.getParent().getNode().getElementType())) {
                String value = literalExpression.getText();
                AnnotatorUtil.createNodeTypeAnnotations(literalExpression, holder, value);
            }
        }
    
    }
}

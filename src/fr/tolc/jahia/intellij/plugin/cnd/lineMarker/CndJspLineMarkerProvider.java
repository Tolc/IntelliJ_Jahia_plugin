package fr.tolc.jahia.intellij.plugin.cnd.lineMarker;

import static fr.tolc.jahia.intellij.plugin.cnd.model.PropertyModel.propertyGetRegex;

import java.util.Collection;
import java.util.Set;
import java.util.regex.Matcher;

import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.jsp.ELElementType;
import com.intellij.psi.jsp.el.ELElementTypes;
import com.intellij.psi.xml.XmlAttributeValue;
import com.intellij.psi.xml.XmlToken;
import fr.tolc.jahia.intellij.plugin.cnd.utils.PsiUtil;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

public class CndJspLineMarkerProvider extends RelatedItemLineMarkerProvider {

    @Override
    protected void collectNavigationMarkers(@NotNull PsiElement element, Collection<? super RelatedItemLineMarkerInfo> result) {
        if (ELElementType.JSP_EL_HOLDER.equals(element.getNode().getElementType())) {

            Set<PsiElement> elExpressions = PsiUtil.findFirstDescendantsByType(element, ELElementTypes.EL_SELECT_EXPRESSION, ELElementTypes.EL_SLICE_EXPRESSION);
            for (PsiElement elExpression : elExpressions) {
                String value = elExpression.getText();

                Matcher matcher = propertyGetRegex.matcher(value);
                while (matcher.find()) {
                    String nodeVar = StringUtils.isNotBlank(matcher.group(1)) ? matcher.group(1) : matcher.group(3);
                    String propertyName = StringUtils.isNotBlank(matcher.group(2)) ? matcher.group(2) : matcher.group(4);

                    LineMarkerUtil.createPropertyLineMarkers(elExpression, result, nodeVar, propertyName);
                }
            }
        }
        
            
        if (element instanceof XmlAttributeValue || element instanceof XmlToken) {
            if (!ELElementTypes.EL_SLICE_EXPRESSION.equals(element.getParent().getNode().getElementType())) {
                String value = element.getText();

                LineMarkerUtil.createNodeTypeLineMarkers(element, result, value);
            }
        }
    }
}

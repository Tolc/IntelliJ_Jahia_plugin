package fr.tolc.jahia.plugin.annotators;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.openapi.editor.HighlighterColors;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiLiteralExpression;
import fr.tolc.jahia.constants.CndConstants;
import fr.tolc.jahia.language.cnd.CndSyntaxHighlighter;
import fr.tolc.jahia.language.cnd.CndUtil;
import fr.tolc.jahia.language.cnd.psi.CndNodetype;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.regex.Matcher;


public class JavaAnnotator extends AbstractAnnotator {

    @Override
    public void annotate(@NotNull PsiElement element, @NotNull AnnotationHolder holder) {

        // Ensure the PSI Element is an expression
        if (!(element instanceof PsiLiteralExpression literalExpression)) {
            return;
        }

        // Ensure the PSI element contains a string that contains separator
        String text = literalExpression.getValue() instanceof String ? (String) literalExpression.getValue() : null;
        if (text == null || !text.contains(CndConstants.NS_SEP)) {
            return;
        }

        Matcher matcher = CndConstants.nodetypePattern.matcher(text);
        while (matcher.find()) {
            List<CndNodetype> nodetypes = CndUtil.findNodetypes(element.getProject(), matcher.group());
            if (!nodetypes.isEmpty()) {
                int start = matcher.start();
                String ns = matcher.group(1);
                String nt = matcher.group(2);

                TextRange nsRange = TextRange.from(element.getTextRange().getStartOffset() + start + 1, ns.length());
                TextRange sepRange = TextRange.from(nsRange.getEndOffset(), CndConstants.NS_SEP.length());
                TextRange ntRange = TextRange.from(sepRange.getEndOffset(), nt.length());

                // highlight namespace, separator, and nodetype
                holder.newSilentAnnotation(HighlightSeverity.INFORMATION).range(nsRange).textAttributes(CndSyntaxHighlighter.NAMESPACE).create();
                holder.newSilentAnnotation(HighlightSeverity.INFORMATION).range(sepRange).textAttributes(HighlighterColors.TEXT).create();
                holder.newSilentAnnotation(HighlightSeverity.INFORMATION).range(ntRange).textAttributes(CndSyntaxHighlighter.NODETYPE).create();
            }
        }
    }
}

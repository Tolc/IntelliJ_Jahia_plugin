package fr.tolc.jahia.plugin.annotators;

import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.openapi.editor.HighlighterColors;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import fr.tolc.jahia.constants.CndConstants;
import fr.tolc.jahia.constants.enums.AttributeEnum;
import fr.tolc.jahia.constants.enums.OptionEnum;
import fr.tolc.jahia.language.cnd.CndSyntaxHighlighter;
import fr.tolc.jahia.language.cnd.CndUtil;
import fr.tolc.jahia.language.cnd.psi.CndNamespace;
import fr.tolc.jahia.language.cnd.psi.CndNodetypeIdentifier;
import fr.tolc.jahia.language.cnd.psi.CndOptionValue;
import fr.tolc.jahia.language.cnd.psi.CndSubnode;
import fr.tolc.jahia.language.cnd.psi.CndSubnodeAttribute;
import fr.tolc.jahia.language.cnd.psi.CndSubnodeDefault;
import fr.tolc.jahia.language.cnd.psi.CndSubnodeType;
import fr.tolc.jahia.language.cnd.psi.CndSupertype;
import fr.tolc.jahia.plugin.messages.CndBundle;
import fr.tolc.jahia.plugin.quickfixes.CndAddSubnodeDefaultValueQuickFix;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.regex.Matcher;


public class CndAnnotator extends AbstractAnnotator {
    private static final Logger logger = LoggerFactory.getLogger(CndAnnotator.class);

    @Override
    public void annotate(@NotNull PsiElement element, @NotNull AnnotationHolder holder) {
        handleNodetypes(element, holder);
        handleSubnodes(element, holder);
    }

    private static void handleNodetypes(@NotNull PsiElement element, @NotNull AnnotationHolder holder) {
        if (element instanceof CndNodetypeIdentifier || element instanceof CndSupertype || element instanceof CndSubnodeType || element instanceof CndSubnodeDefault
                || (element instanceof CndOptionValue optionValue && OptionEnum.EXTENDS == optionValue.getOptionType())) {
            String text = element.getText();
            if (text == null || StringUtils.isBlank(text)) {
                holder.newAnnotation(HighlightSeverity.ERROR, CndBundle.message("annotations.cnd.error.nodetype.blank"))
                        .highlightType(ProblemHighlightType.ERROR)
                        .create();
            } else {
                String trimmed = text.stripLeading();
                int leadingSpaces = text.length() - trimmed.length();
                trimmed = text.trim();
                if (trimmed.length() != text.length()) {
                    holder.newAnnotation(HighlightSeverity.WEAK_WARNING, CndBundle.message("annotations.cnd.warning.nodetype.spaces"))
                            .highlightType(ProblemHighlightType.WEAK_WARNING)
                            .create();
                }

                Matcher matcher = CndConstants.nodetypePattern.matcher(trimmed);
                if (!matcher.matches()) {
                    holder.newAnnotation(HighlightSeverity.ERROR, CndBundle.message("annotations.cnd.error.nodetype.format"))
                            .highlightType(ProblemHighlightType.ERROR)
                            .create();
                } else {
                    int start = matcher.start();
                    String ns = matcher.group(1);
                    String nt = matcher.group(2);

                    TextRange nsRange = TextRange.from(element.getTextRange().getStartOffset() + start + leadingSpaces, ns.length());
                    TextRange sepRange = TextRange.from(nsRange.getEndOffset(), CndConstants.NS_SEP.length());
                    TextRange ntRange = TextRange.from(sepRange.getEndOffset(), nt.length());

                    // highlight namespace, separator, and nodetype
                    holder.newSilentAnnotation(HighlightSeverity.INFORMATION).range(nsRange).textAttributes(CndSyntaxHighlighter.NAMESPACE).create();
                    holder.newSilentAnnotation(HighlightSeverity.INFORMATION).range(sepRange).textAttributes(HighlighterColors.TEXT).create();
                    holder.newSilentAnnotation(HighlightSeverity.INFORMATION).range(ntRange).textAttributes(CndSyntaxHighlighter.NODETYPE).create();

                    List<CndNamespace> namespaces = CndUtil.findNamespaces(element.getProject(), ns);
                    if (namespaces.isEmpty()) {
                        holder.newAnnotation(HighlightSeverity.WARNING, CndBundle.message("annotations.cnd.warning.namespace.unresolved", ns))
                                .range(nsRange).highlightType(ProblemHighlightType.LIKE_UNKNOWN_SYMBOL)
                                // ** Tutorial step 19. - Add a quick fix for the string containing possible properties
//                                .withFix(new SimpleCreatePropertyQuickFix(key))
                                .create();
                    }
                }
            }
        }
    }

    private static void handleSubnodes(@NotNull PsiElement element, @NotNull AnnotationHolder holder) {
        if (element instanceof CndSubnodeAttribute) {
            String text = element.getText();
            if (AttributeEnum.AUTOCREATED == AttributeEnum.fromValueForSubNode(text)) {
                CndSubnode subnode = (CndSubnode) element.getParent();
                if (subnode.getSubnodeDefault() == null) {
                    holder.newAnnotation(HighlightSeverity.WARNING, CndBundle.message("annotations.cnd.warning.subnode.autocreated"))
                            .highlightType(ProblemHighlightType.WARNING)
                            .withFix(new CndAddSubnodeDefaultValueQuickFix(subnode))
                            .create();
                }
            }
        }
    }

}

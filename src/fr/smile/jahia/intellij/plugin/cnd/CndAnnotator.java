package fr.smile.jahia.intellij.plugin.cnd;

import com.intellij.lang.annotation.Annotation;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiLiteralExpression;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CndAnnotator implements Annotator {

    private static final Pattern nodeTypeRegex = Pattern.compile("^[A-Za-z]+" + ":" + "[A-Za-z0-9]+$");

    @Override
    public void annotate(@NotNull final PsiElement element, @NotNull AnnotationHolder holder) {
        if (element instanceof PsiLiteralExpression) {
            PsiLiteralExpression literalExpression = (PsiLiteralExpression) element;
            String value = literalExpression.getValue() instanceof String ? (String)literalExpression.getValue() : null;

            if (value != null && value.contains(":")) {
                Matcher matcher = nodeTypeRegex.matcher(value);
                if (matcher.matches()) {
                    String[] splitValue = value.split(":");
                    String namespace = splitValue[0];
                    String nodeTypeName = splitValue[1];
                    Project project = element.getProject();
                    int offset = element.getTextRange().getStartOffset() + 1; //because of starting "
                    TextRange namespaceRange = new TextRange(offset, offset + namespace.length());
                    TextRange colonRange = new TextRange(offset + namespace.length(), offset + namespace.length() + 1);
                    TextRange nodeTypeNameRange = new TextRange(offset + namespace.length() + 1, element.getTextRange().getEndOffset() - 1); //because of closing "

                    //Color ":"
                    Annotation colonAnnotation = holder.createInfoAnnotation(colonRange, null);
                    colonAnnotation.setTextAttributes(DefaultLanguageHighlighterColors.LINE_COMMENT);

                    if (CndUtil.findNamespace(project, namespace) != null) {
                        Annotation namespaceAnnotation = holder.createInfoAnnotation(namespaceRange, null);
                        namespaceAnnotation.setTextAttributes(CndSyntaxHighlighter.NAMESPACE);

                        if (CndUtil.findNodeType(project, namespace, nodeTypeName) != null) {
                            Annotation nodeTypeNameAnnotation = holder.createInfoAnnotation(nodeTypeNameRange, null);
                            nodeTypeNameAnnotation.setTextAttributes(CndSyntaxHighlighter.NODE_TYPE);
                        } else {
                            holder.createErrorAnnotation(nodeTypeNameRange, "Unresolved CND node type");
                        }
                    } else {
                        holder.createErrorAnnotation(namespaceRange, "Unresolved CND namespace");
                    }
                }
            }
        }
    }
}

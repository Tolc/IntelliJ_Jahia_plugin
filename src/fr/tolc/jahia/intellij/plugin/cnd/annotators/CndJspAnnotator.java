package fr.tolc.jahia.intellij.plugin.cnd.annotators;

import java.util.regex.Pattern;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.psi.PsiElement;
import com.intellij.psi.jsp.JspTokenType;
import org.jetbrains.annotations.NotNull;

public class CndJspAnnotator implements Annotator {

    private static final Pattern nodeTypeRegex = Pattern.compile("^[A-Za-z]+" + ":" + "[A-Za-z0-9]+$");

    @Override
    public void annotate(@NotNull final PsiElement element, @NotNull AnnotationHolder holder) {
        if (JspTokenType.XML_ATTRIBUTE_VALUE_TOKEN.equals(element.getNode().getElementType())) {
            String value = element.getText();

//            if (value != null && value.contains(":")) {
//                Matcher matcher = nodeTypeRegex.matcher(value);
//                if (matcher.matches()) {
//                    String[] splitValue = value.split(":");
//                    String namespace = splitValue[0];
//                    String nodeTypeName = splitValue[1];
//                    Project project = element.getProject();
//                    int offset = element.getTextRange().getStartOffset();
//                    TextRange namespaceRange = new TextRange(offset, offset + namespace.length());
//                    TextRange colonRange = new TextRange(offset + namespace.length(), offset + namespace.length() + 1);
//                    TextRange nodeTypeNameRange = new TextRange(offset + namespace.length() + 1, element.getTextRange().getEndOffset());
//
//                    //Color ":"
//                    Annotation colonAnnotation = holder.createInfoAnnotation(colonRange, null);
//                    colonAnnotation.setTextAttributes(DefaultLanguageHighlighterColors.LINE_COMMENT);
//
//                    if (CndUtil.findNamespace(project, namespace) != null) {
//                        Annotation namespaceAnnotation = holder.createInfoAnnotation(namespaceRange, null);
//                        namespaceAnnotation.setTextAttributes(CndSyntaxHighlighter.NAMESPACE);
//
//                        if (CndUtil.findNodeType(project, namespace, nodeTypeName) != null) {
//                            Annotation nodeTypeNameAnnotation = holder.createInfoAnnotation(nodeTypeNameRange, null);
//                            nodeTypeNameAnnotation.setTextAttributes(CndSyntaxHighlighter.NODE_TYPE);
//                        } else {
//                            holder.createErrorAnnotation(nodeTypeNameRange, "Unresolved CND node type").registerFix(new CreateNodeTypeQuickFix(namespace, nodeTypeName));
//                        }
//                    } else {
//                        holder.createErrorAnnotation(namespaceRange, "Unresolved CND namespace");
//                    }
//                }
//            }
        }
    }
}

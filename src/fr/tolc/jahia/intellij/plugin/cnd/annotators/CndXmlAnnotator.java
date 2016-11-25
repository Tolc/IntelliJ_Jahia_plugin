package fr.tolc.jahia.intellij.plugin.cnd.annotators;

import com.intellij.lang.annotation.Annotation;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.xml.XmlAttributeValue;
import fr.tolc.jahia.intellij.plugin.cnd.CndSyntaxHighlighter;
import fr.tolc.jahia.intellij.plugin.cnd.utils.CndUtil;
import fr.tolc.jahia.intellij.plugin.cnd.model.NodeTypeModel;
import fr.tolc.jahia.intellij.plugin.cnd.quickfixes.CreateNodeTypeQuickFix;
import org.jetbrains.annotations.NotNull;

public class CndXmlAnnotator implements Annotator {

    @Override
    public void annotate(@NotNull final PsiElement element, @NotNull AnnotationHolder holder) {
        if (element instanceof XmlAttributeValue) {
            String value = ((XmlAttributeValue) element).getValue();
            NodeTypeModel nodeTypeModel = null;
            try {
                nodeTypeModel = new NodeTypeModel(value);
            } catch (IllegalArgumentException e) {
                //Nothing to do
            }

            if (nodeTypeModel != null) {
                String namespace = nodeTypeModel.getNamespace();
                String nodeTypeName = nodeTypeModel.getNodeTypeName();

                Project project = element.getProject();
                int offset = element.getTextRange().getStartOffset() + 1;   //because of starting "
                TextRange namespaceRange = new TextRange(offset, offset + namespace.length());
                TextRange colonRange = new TextRange(offset + namespace.length(), offset + namespace.length() + 1);
                TextRange nodeTypeNameRange = new TextRange(offset + namespace.length() + 1, element.getTextRange().getEndOffset() - 1); //because of ending "

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
                        holder.createErrorAnnotation(nodeTypeNameRange, "Unresolved CND node type").registerFix(new CreateNodeTypeQuickFix(namespace, nodeTypeName));
                    }
//                } else {
//                    holder.createErrorAnnotation(namespaceRange, "Unresolved CND namespace");
                }
            }
        }
    }
}

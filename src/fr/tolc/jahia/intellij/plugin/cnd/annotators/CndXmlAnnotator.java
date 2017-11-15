package fr.tolc.jahia.intellij.plugin.cnd.annotators;

import com.intellij.lang.annotation.Annotation;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.xml.XmlElementType;
import fr.tolc.jahia.intellij.plugin.cnd.CndSyntaxHighlighter;
import fr.tolc.jahia.intellij.plugin.cnd.model.NodeTypeModel;
import fr.tolc.jahia.intellij.plugin.cnd.utils.CndUtil;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;

import static fr.tolc.jahia.intellij.plugin.cnd.model.NodeTypeModel.nodeTypeGlobalRegex;

public class CndXmlAnnotator implements Annotator {


    @Override
    public void annotate(@NotNull final PsiElement element, @NotNull AnnotationHolder holder) {
        if (element.getNode() != null && XmlElementType.XML_ATTRIBUTE_VALUE_TOKEN.equals(element.getNode().getElementType())) {
            String value = element.getText();

            Matcher matcher = nodeTypeGlobalRegex.matcher(value);
            while (matcher.find()) {
                String group = matcher.group();

                NodeTypeModel nodeTypeModel = null;
                try {
                    nodeTypeModel = new NodeTypeModel(group);
                } catch (IllegalArgumentException e) {
                    //Nothing to do
                }

                if (nodeTypeModel != null) {
                    String namespace = nodeTypeModel.getNamespace();
                    String nodeTypeName = nodeTypeModel.getNodeTypeName();

//                    if (CndUtil.findNamespace(project, namespace) != null) {
                    Project project = element.getProject();
                    if (CndUtil.findNodeType(project, namespace, nodeTypeName) != null) {
                        int offset = element.getTextRange().getStartOffset() + matcher.start();
                        TextRange namespaceRange = new TextRange(offset, offset + namespace.length());
                        TextRange colonRange = new TextRange(offset + namespace.length(), offset + namespace.length() + 1);
                        TextRange nodeTypeNameRange = new TextRange(offset + namespace.length() + 1, offset + group.length());

                        //Color ":"
                        Annotation colonAnnotation = holder.createInfoAnnotation(colonRange, null);
                        colonAnnotation.setTextAttributes(DefaultLanguageHighlighterColors.LINE_COMMENT);

                        Annotation namespaceAnnotation = holder.createInfoAnnotation(namespaceRange, null);
                        namespaceAnnotation.setTextAttributes(CndSyntaxHighlighter.NAMESPACE);

                        Annotation nodeTypeNameAnnotation = holder.createInfoAnnotation(nodeTypeNameRange, null);
                        nodeTypeNameAnnotation.setTextAttributes(CndSyntaxHighlighter.NODE_TYPE);
//                        } else {
//                            holder.createWarningAnnotation(nodeTypeNameRange, "Unresolved CND node type").registerFix(new CreateNodeTypeQuickFix(namespace, nodeTypeName));
//                        }
//                } else {
//                    holder.createErrorAnnotation(namespaceRange, "Unresolved CND namespace");
                    }
                }
            }
        }
    }
}

package fr.tolc.jahia.intellij.plugin.cnd.annotators;

import com.intellij.lang.annotation.Annotation;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.lang.properties.psi.impl.PropertyKeyImpl;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import fr.tolc.jahia.intellij.plugin.cnd.CndSyntaxHighlighter;
import fr.tolc.jahia.intellij.plugin.cnd.CndUtil;
import fr.tolc.jahia.intellij.plugin.cnd.enums.PropertyTypeEnum;
import fr.tolc.jahia.intellij.plugin.cnd.enums.PropertyTypeMaskEnum;
import fr.tolc.jahia.intellij.plugin.cnd.model.NodeTypeModel;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndNodeType;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndProperty;
import fr.tolc.jahia.intellij.plugin.cnd.quickfixes.CreateNodeTypeQuickFix;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

public class CndPropertiesAnnotator implements Annotator {

    @Override
    public void annotate(@NotNull final PsiElement element, @NotNull AnnotationHolder holder) {
        if (element instanceof PropertyKeyImpl) {
            PropertyKeyImpl propertyKey = (PropertyKeyImpl) element;
            
            //TODO: refactor
            
            String key = element.getText();
            String nodeTypePart;
            String propertyPart = null;

            if (key.contains(".")) {
                int pointIndex = key.indexOf('.');
                nodeTypePart = key.substring(0, pointIndex);
                propertyPart = key.substring(pointIndex + 1);
            } else {
                nodeTypePart = key;
            }

            NodeTypeModel nodeTypeModel = CndUtil.getNodeTypeModel(nodeTypePart);

            if (nodeTypeModel != null) {
                String namespace = nodeTypeModel.getNamespace();
                String nodeTypeName = nodeTypeModel.getNodeTypeName();

                Project project = element.getProject();
                int offset = element.getTextRange().getStartOffset();
                TextRange namespaceRange = new TextRange(offset, offset + namespace.length());
                TextRange colonRange = new TextRange(offset + namespace.length(), offset + namespace.length() + 1);
                TextRange nodeTypeNameRange = new TextRange(offset + namespace.length() + 1, offset + namespace.length() + 1 + nodeTypeName.length());

                //Color ":"
                Annotation colonAnnotation = holder.createInfoAnnotation(colonRange, null);
                colonAnnotation.setTextAttributes(DefaultLanguageHighlighterColors.LINE_COMMENT);

                if (CndUtil.findNamespace(project, namespace) != null) {
                    Annotation namespaceAnnotation = holder.createInfoAnnotation(namespaceRange, null);
                    namespaceAnnotation.setTextAttributes(CndSyntaxHighlighter.NAMESPACE);

                    CndNodeType nodeType = CndUtil.findNodeType(project, namespace, nodeTypeName);
                    if (nodeType != null) {
                        Annotation nodeTypeNameAnnotation = holder.createInfoAnnotation(nodeTypeNameRange, null);
                        nodeTypeNameAnnotation.setTextAttributes(CndSyntaxHighlighter.NODE_TYPE);

                        if (StringUtils.isNotBlank(propertyPart)) {
                            String listPart = null;
                            if (propertyPart.contains(".")) {
                                int pointIndex = propertyPart.indexOf('.');
                                listPart = propertyPart.substring(pointIndex + 1);
                                propertyPart = propertyPart.substring(0, pointIndex);
                            }


                            TextRange propertyRange = new TextRange(offset + namespace.length() + 1 + nodeTypeName.length() + 1,
                                                                    offset + namespace.length() + 1 + nodeTypeName.length() + 1 + propertyPart.length());
                            CndProperty property = nodeType.getProperty(propertyPart);
                            if (property != null) {
                                Annotation propertyAnnotation = holder.createInfoAnnotation(propertyRange, null);
                                propertyAnnotation.setTextAttributes(CndSyntaxHighlighter.STRING);

                                if (StringUtils.isNotBlank(listPart)) {
                                    TextRange listRange = new TextRange(offset + namespace.length() + 1 + nodeTypeName.length() + 1 + propertyPart.length() + 1,
                                            offset + namespace.length() + 1 + nodeTypeName.length() + 1 + propertyPart.length() + 1 + listPart.length());

                                    if (PropertyTypeEnum.STRING.equals(property.getType())) {
                                        if (PropertyTypeMaskEnum.CHOICELIST.equals(property.getTypeMask())) {
                                            Annotation listAnnotation = holder.createInfoAnnotation(listRange, null);
                                            listAnnotation.setTextAttributes(DefaultLanguageHighlighterColors.LINE_COMMENT);
                                        } else {
                                            holder.createErrorAnnotation(listRange, "Property should be declared as a \"STRING, CHOICELIST\"");
                                        }
                                    } else {
                                        holder.createErrorAnnotation(listRange, "Property type should be declared as a \"STRING\"");
                                    }
                                }
                            } else {
                                holder.createErrorAnnotation(propertyRange, "Unresolved property");
                            }
                        }
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

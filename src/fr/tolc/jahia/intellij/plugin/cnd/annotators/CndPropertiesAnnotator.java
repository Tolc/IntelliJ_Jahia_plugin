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
import fr.tolc.jahia.intellij.plugin.cnd.utils.CndUtil;
import fr.tolc.jahia.intellij.plugin.cnd.enums.PropertyTypeEnum;
import fr.tolc.jahia.intellij.plugin.cnd.enums.PropertyTypeMaskEnum;
import fr.tolc.jahia.intellij.plugin.cnd.model.PropertiesFileCndKeyModel;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndNodeType;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndProperty;
import fr.tolc.jahia.intellij.plugin.cnd.quickfixes.CreateNodeTypeQuickFix;
import org.jetbrains.annotations.NotNull;

public class CndPropertiesAnnotator implements Annotator {

    @Override
    public void annotate(@NotNull final PsiElement element, @NotNull AnnotationHolder holder) {
        if (element instanceof PropertyKeyImpl) {
            String key = element.getText();

            PropertiesFileCndKeyModel cndKeyModel = null;
            try {
                cndKeyModel = new PropertiesFileCndKeyModel(key);
            } catch (IllegalArgumentException e) {
                //Nothing to do
            }

            if (cndKeyModel != null) {
                String namespace = cndKeyModel.getNamespace();
                String nodeTypeName = cndKeyModel.getNodeTypeName();

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

                        if (cndKeyModel.isNodeTypeDescription()) {
                            TextRange underscoreRange = new TextRange(offset + namespace.length() + 1 + nodeTypeName.length(),
                                                                      offset + namespace.length() + 1 + nodeTypeName.length() + 1);
                            Annotation underscoreAnnotation = holder.createInfoAnnotation(underscoreRange, null);
                            underscoreAnnotation.setTextAttributes(DefaultLanguageHighlighterColors.LINE_COMMENT);

                            TextRange descRange = new TextRange(offset + namespace.length() + 1 + nodeTypeName.length() + 1,
                                                                offset + namespace.length() + 1 + nodeTypeName.length() + 1 + "description".length());
                            Annotation descAnnotation = holder.createInfoAnnotation(descRange, null);
                            descAnnotation.setTextAttributes(CndSyntaxHighlighter.ATTRIBUTE);

                        } else if (cndKeyModel.isProperty() || cndKeyModel.isChoicelistElement() || cndKeyModel.isPropertyTooltip()) {
                            String propertyName = cndKeyModel.getPropertyName();
                            TextRange propertyRange = new TextRange(offset + namespace.length() + 1 + nodeTypeName.length() + 1,
                                                                    offset + namespace.length() + 1 + nodeTypeName.length() + 1 + propertyName.length());

                            CndProperty property = nodeType.getProperty(propertyName);
                            if (property != null) {
                                Annotation propertyAnnotation = holder.createInfoAnnotation(propertyRange, null);
                                propertyAnnotation.setTextAttributes(CndSyntaxHighlighter.STRING);

                                if (cndKeyModel.isChoicelistElement()) {
                                    String choicelistElement = cndKeyModel.getChoicelistElement();
                                    TextRange listRange = new TextRange(offset + namespace.length() + 1 + nodeTypeName.length() + 1 + propertyName.length() + 1,
                                                                        offset + namespace.length() + 1 + nodeTypeName.length() + 1 + propertyName.length() + 1 + choicelistElement.length());

                                    TextRange pointRange = new TextRange(offset + namespace.length() + 1 + nodeTypeName.length() + 1 + propertyName.length(),
                                            offset + namespace.length() + 1 + nodeTypeName.length() + 1 + propertyName.length() + 1);
                                    Annotation pointAnnotation = holder.createInfoAnnotation(pointRange, null);
                                    pointAnnotation.setTextAttributes(DefaultLanguageHighlighterColors.LINE_COMMENT);

                                    if (PropertyTypeEnum.STRING.equals(property.getType())) {
                                        if (PropertyTypeMaskEnum.CHOICELIST.equals(property.getTypeMask())) {
                                            Annotation listAnnotation = holder.createInfoAnnotation(listRange, null);
                                            listAnnotation.setTextAttributes(CndSyntaxHighlighter.STRING);
                                        } else {
                                            holder.createErrorAnnotation(listRange, "Property should be declared as a \"STRING, CHOICELIST\"");
                                        }
                                    } else {
                                        holder.createErrorAnnotation(listRange, "Property type should be declared as a \"STRING\"");
                                    }
                                } else if (cndKeyModel.isPropertyTooltip()) {
                                    TextRange tooltipRange = new TextRange(offset + namespace.length() + 1 + nodeTypeName.length() + 1 + propertyName.length() + 1,
                                                                           offset + namespace.length() + 1 + nodeTypeName.length() + 1 + propertyName.length() + 1 + "ui.tooltip".length());
                                    Annotation tooltipAnnotation = holder.createInfoAnnotation(tooltipRange, null);
                                    tooltipAnnotation.setTextAttributes(CndSyntaxHighlighter.ATTRIBUTE);
                                }
                            } else {
                                holder.createErrorAnnotation(propertyRange, "Unresolved property for node type " + namespace + ":" + nodeTypeName);
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

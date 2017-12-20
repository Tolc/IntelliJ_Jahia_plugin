package fr.tolc.jahia.intellij.plugin.cnd.annotators;

import static fr.tolc.jahia.intellij.plugin.cnd.model.NodeTypeModel.nodeTypeGlobalRegex;
import static fr.tolc.jahia.intellij.plugin.cnd.model.PropertyModel.CURRENT_NODE;
import static fr.tolc.jahia.intellij.plugin.cnd.model.PropertyModel.propertyGetRegex;

import java.util.Set;
import java.util.regex.Matcher;

import com.intellij.lang.annotation.Annotation;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.jsp.el.ELElementTypes;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlAttributeValue;
import com.intellij.psi.xml.XmlElementType;
import com.intellij.psi.xml.XmlTag;
import fr.tolc.jahia.intellij.plugin.cnd.CndSyntaxHighlighter;
import fr.tolc.jahia.intellij.plugin.cnd.model.NodeTypeModel;
import fr.tolc.jahia.intellij.plugin.cnd.model.ViewModel;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndNodeType;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndProperty;
import fr.tolc.jahia.intellij.plugin.cnd.utils.CndProjectFilesUtil;
import fr.tolc.jahia.intellij.plugin.cnd.utils.CndUtil;
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
                    TextRange propertyRange = new TextRange(offset, offset + propertyName.length());

                    if (CURRENT_NODE.equals(nodeVar)) {
                        ViewModel viewModel = CndProjectFilesUtil.getViewModelFromPotentialViewFile(element.getContainingFile().getVirtualFile());
                        if (viewModel != null) {
                            CndNodeType nodeType = CndUtil.findNodeType(element.getProject(), viewModel.getNodeType());
                            if (nodeType != null) {
                                CndProperty property = nodeType.getProperty(propertyName);
                                if (property != null) {
                                    Annotation propertyAnnotation = holder.createInfoAnnotation(propertyRange, null);
                                    propertyAnnotation.setTextAttributes(DefaultLanguageHighlighterColors.NUMBER);
                                } else {
                                    Set<CndProperty> possibleProperties = CndUtil.findProperties(element.getProject(), propertyName);
                                    if (!possibleProperties.isEmpty()) {
                                        Annotation propertyAnnotation = holder.createWarningAnnotation(propertyRange,
                                                "Property '" + propertyName + "' not found for node type '" + viewModel.getNodeType().toString() + "'.\r\nAre you sure it exists?");
                                        propertyAnnotation.setTextAttributes(DefaultLanguageHighlighterColors.NUMBER);
                                    }
                                }
                            } else {
                                Annotation propertyAnnotation = holder.createWarningAnnotation(propertyRange,
                                        "Node type '" + viewModel.getNodeType().toString() + "' not found.\r\nAre you sure it exists?");
                                propertyAnnotation.setTextAttributes(DefaultLanguageHighlighterColors.NUMBER);
                            }
                        }
                    } else {
                        //Get all properties with same name from project
                        Set<CndProperty> possibleProperties = CndUtil.findProperties(element.getProject(), propertyName);
                        if (!possibleProperties.isEmpty()) {
                            Annotation propertyAnnotation = holder.createInfoAnnotation(propertyRange, null);
                            propertyAnnotation.setTextAttributes(DefaultLanguageHighlighterColors.NUMBER);
                        } else {
                            Annotation propertyAnnotation = holder.createErrorAnnotation(propertyRange,
                                    "Property '" + propertyName + "' not found in project or Jahia modules.\r\nAre you sure it exists?");
                            propertyAnnotation.setTextAttributes(DefaultLanguageHighlighterColors.NUMBER);
                        }
                    }
                }
            } else {

                Set<PsiElement> elExpressions = PsiUtil.findFirstDescendantsByType(element, ELElementTypes.EL_SELECT_EXPRESSION, ELElementTypes.EL_SLICE_EXPRESSION);
                for (PsiElement elExpression : elExpressions) {
                    String value = elExpression.getText();

                    Matcher matcher = propertyGetRegex.matcher(value);
                    while (matcher.find()) {
                        String nodeVar = StringUtils.isNotBlank(matcher.group(1)) ? matcher.group(1) : matcher.group(3);
                        String propertyName = StringUtils.isNotBlank(matcher.group(2)) ? matcher.group(2) : matcher.group(4);

                        int offset = elExpression.getTextRange().getStartOffset() + ((matcher.start(2) > -1) ? matcher.start(2) : matcher.start(4));
                        TextRange propertyRange = new TextRange(offset, offset + propertyName.length());

                        if (CURRENT_NODE.equals(nodeVar)) {
                            ViewModel viewModel = CndProjectFilesUtil.getViewModelFromPotentialViewFile(element.getContainingFile().getVirtualFile());
                            if (viewModel != null) {
                                CndNodeType nodeType = CndUtil.findNodeType(element.getProject(), viewModel.getNodeType());
                                if (nodeType != null) {
                                    CndProperty property = nodeType.getProperty(propertyName);
                                    if (property != null) {
                                        Annotation propertyAnnotation = holder.createInfoAnnotation(propertyRange, null);
                                        propertyAnnotation.setTextAttributes(DefaultLanguageHighlighterColors.NUMBER);
                                    } else {
                                        Set<CndProperty> possibleProperties = CndUtil.findProperties(element.getProject(), propertyName);
                                        if (!possibleProperties.isEmpty()) {
                                            Annotation propertyAnnotation = holder.createWarningAnnotation(propertyRange,
                                                    "Property '" + propertyName + "' not found for node type '" + viewModel.getNodeType().toString() + "'.\r\nAre you sure it exists?");
                                            propertyAnnotation.setTextAttributes(DefaultLanguageHighlighterColors.NUMBER);
                                        }
                                    }
                                } else {
                                    Annotation propertyAnnotation = holder.createWarningAnnotation(propertyRange,
                                            "Node type '" + viewModel.getNodeType().toString() + "' not found.\r\nAre you sure it exists?");
                                    propertyAnnotation.setTextAttributes(DefaultLanguageHighlighterColors.NUMBER);
                                }
                            }
                        } else {
                            //Get all properties with same name from project
                            Set<CndProperty> possibleProperties = CndUtil.findProperties(element.getProject(), propertyName);
                            if (!possibleProperties.isEmpty()) {
                                Annotation propertyAnnotation = holder.createInfoAnnotation(propertyRange, null);
                                propertyAnnotation.setTextAttributes(DefaultLanguageHighlighterColors.NUMBER);
                            } else {
                                Annotation propertyAnnotation = holder.createErrorAnnotation(propertyRange,
                                        "Property '" + propertyName + "' not found in project or Jahia modules.\r\nAre you sure it exists?");
                                propertyAnnotation.setTextAttributes(DefaultLanguageHighlighterColors.NUMBER);
                            }
                        }
                    }
                }
            }
                
            Set<PsiElement> literalExpressions = PsiUtil.findFirstDescendantsByType(element, ELElementTypes.EL_LITERAL_EXPRESSION);
            for (PsiElement literalExpression : literalExpressions) {
                if (!ELElementTypes.EL_SLICE_EXPRESSION.equals(literalExpression.getParent().getNode().getElementType())) {
                    String value = literalExpression.getText();

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
                            Project project = literalExpression.getProject();
                            if (CndUtil.findNodeType(project, namespace, nodeTypeName) != null) {
                                int offset = literalExpression.getTextRange().getStartOffset() + matcher.start();
                                TextRange namespaceRange = new TextRange(offset, offset + namespace.length());
                                TextRange colonRange = new TextRange(offset + namespace.length(), offset + namespace.length() + 1);
                                TextRange nodeTypeNameRange = new TextRange(offset + namespace.length() + 1, offset + group.length()); //because of ending "

                                //Color ":"
                                Annotation colonAnnotation = holder.createInfoAnnotation(colonRange, null);
                                colonAnnotation.setTextAttributes(DefaultLanguageHighlighterColors.LINE_COMMENT);

                                Annotation namespaceAnnotation = holder.createInfoAnnotation(namespaceRange, null);
                                namespaceAnnotation.setTextAttributes(CndSyntaxHighlighter.NAMESPACE);

                                Annotation nodeTypeNameAnnotation = holder.createInfoAnnotation(nodeTypeNameRange, null);
                                nodeTypeNameAnnotation.setTextAttributes(CndSyntaxHighlighter.NODE_TYPE);
//                                    } else {
//                                        holder.createWarningAnnotation(nodeTypeNameRange, "Unresolved CND node type").registerFix(new CreateNodeTypeQuickFix(namespace, nodeTypeName));
//                                    }
//                            } else {
//                                holder.createErrorAnnotation(namespaceRange, "Unresolved CND namespace");
                            }
                        }
                    }
                }
            }
        }
    }
}

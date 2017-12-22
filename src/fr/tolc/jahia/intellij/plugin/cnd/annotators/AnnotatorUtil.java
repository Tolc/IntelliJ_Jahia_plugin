package fr.tolc.jahia.intellij.plugin.cnd.annotators;

import static fr.tolc.jahia.intellij.plugin.cnd.model.NodeTypeModel.nodeTypeGlobalRegex;
import static fr.tolc.jahia.intellij.plugin.cnd.model.PropertyModel.CURRENT_NODE;

import java.util.Set;
import java.util.regex.Matcher;

import com.intellij.lang.annotation.Annotation;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import fr.tolc.jahia.intellij.plugin.cnd.CndSyntaxHighlighter;
import fr.tolc.jahia.intellij.plugin.cnd.model.NodeTypeModel;
import fr.tolc.jahia.intellij.plugin.cnd.model.ViewModel;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndNodeType;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndProperty;
import fr.tolc.jahia.intellij.plugin.cnd.utils.CndProjectFilesUtil;
import fr.tolc.jahia.intellij.plugin.cnd.utils.CndUtil;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

public class AnnotatorUtil {
    
    private AnnotatorUtil() {}
    
    public static void createPropertyAnnotations(@NotNull final PsiElement element, @NotNull AnnotationHolder holder, String nodeVar, String propertyName, int offset) {
        TextRange propertyRange = new TextRange(offset, offset + propertyName.length());
        if (CURRENT_NODE.equals(nodeVar)) {
            ViewModel viewModel = CndProjectFilesUtil.getViewModelFromPotentialViewFile(element.getContainingFile().getOriginalFile().getVirtualFile());
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
                                    "Property '" + propertyName + "' not found for node type '" + viewModel.getNodeType().toString() + "'. Are you sure it exists?");
                            propertyAnnotation.setTextAttributes(DefaultLanguageHighlighterColors.NUMBER);
                        } else {
                            Annotation propertyAnnotation = holder.createErrorAnnotation(propertyRange,
                                    "Property '" + propertyName + "' not found for node type '" + viewModel.getNodeType().toString() + "'. Are you sure it exists?");
                            propertyAnnotation.setTextAttributes(DefaultLanguageHighlighterColors.NUMBER);
                        }
                    }
                } else {
                    Annotation propertyAnnotation = holder.createErrorAnnotation(propertyRange,
                            "Node type '" + viewModel.getNodeType().toString() + "' not found. Are you sure it exists?");
                    propertyAnnotation.setTextAttributes(DefaultLanguageHighlighterColors.NUMBER);
                }
            }
        } else {
            //Warn about cache
            Annotation cacheAnnotation = holder.createWarningAnnotation(element.getTextRange(),
                    "You should not be accessing properties from another node. It is not cache efficient. Use a <template:module node=\"" + nodeVar + "\"> instead.");
            //TODO: quick fix
            
            
            //Get all properties with same name from project
            Set<CndProperty> possibleProperties = CndUtil.findProperties(element.getProject(), propertyName);
            if (!possibleProperties.isEmpty()) {
                Annotation propertyAnnotation = holder.createInfoAnnotation(propertyRange, null);
                propertyAnnotation.setTextAttributes(DefaultLanguageHighlighterColors.NUMBER);
            } else {
                Annotation propertyAnnotation = holder.createErrorAnnotation(propertyRange,
                        "Property '" + propertyName + "' not found in project or Jahia modules. Are you sure it exists?");
                propertyAnnotation.setTextAttributes(DefaultLanguageHighlighterColors.NUMBER);
            }
        }
    }

    public static void createNodeTypeAnnotations(@NotNull final PsiElement element, @NotNull AnnotationHolder holder, String text) {
        createNodeTypeAnnotations(element, holder, text, 0);
    }
    
    public static void createNodeTypeAnnotations(@NotNull final PsiElement element, @NotNull AnnotationHolder holder, String text, int offsetShift) {
        if (StringUtils.isNotBlank(text)) {
            Matcher matcher = nodeTypeGlobalRegex.matcher(text);
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
                        int offset = element.getTextRange().getStartOffset() + matcher.start() + offsetShift;
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

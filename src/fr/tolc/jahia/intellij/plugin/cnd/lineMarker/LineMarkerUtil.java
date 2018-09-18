package fr.tolc.jahia.intellij.plugin.cnd.lineMarker;

import static fr.tolc.jahia.intellij.plugin.cnd.model.NodeTypeModel.nodeTypeGlobalRegex;
import static fr.tolc.jahia.intellij.plugin.cnd.model.PropertyModel.CURRENT_NODE;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;

import javax.swing.*;

import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo;
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import fr.tolc.jahia.intellij.plugin.cnd.icons.CndIcons;
import fr.tolc.jahia.intellij.plugin.cnd.model.NodeTypeModel;
import fr.tolc.jahia.intellij.plugin.cnd.model.ViewModel;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndNodeType;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndProperty;
import fr.tolc.jahia.intellij.plugin.cnd.utils.CndProjectFilesUtil;
import fr.tolc.jahia.intellij.plugin.cnd.utils.CndUtil;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

public class LineMarkerUtil {
    
    private LineMarkerUtil() {}
    
    public static void createPropertyLineMarkers(@NotNull PsiElement element, Collection<? super RelatedItemLineMarkerInfo> result, String nodeVar, String propertyName) {
        Set<CndProperty> possibleProperties = new LinkedHashSet<>();
        if (CURRENT_NODE.equals(nodeVar.trim())) {
            ViewModel viewModel = CndProjectFilesUtil.getViewModelFromPotentialViewFile(element.getContainingFile().getOriginalFile().getVirtualFile());
            if (viewModel != null) {
                CndNodeType nodeType = CndUtil.findNodeType(element.getProject(), viewModel.getNodeType());
                if (nodeType != null) {
                    CndProperty property = nodeType.getProperty(propertyName);
                    if (property != null) {
                        possibleProperties.add(property);
                    } else {
                        possibleProperties.addAll(CndUtil.findProperties(element.getProject(), propertyName));
                    }
                }
            }
        } else {
            //Get all properties with same name from project
            possibleProperties.addAll(CndUtil.findProperties(element.getProject(), propertyName));
        }

        NavigationGutterIconBuilder<PsiElement> builder = NavigationGutterIconBuilder.create(CndIcons.PROPERTY);
        if (!possibleProperties.isEmpty()) {
            if (possibleProperties.size() > 1) {
                builder
                        .setTargets(possibleProperties)
                        .setTooltipText("Several possible properties [" + propertyName + "]");
            } else {
                for (CndProperty possibleProperty : possibleProperties) {
                    builder
                            .setTarget(possibleProperty)
                            .setTooltipText("Navigate to property [" + propertyName + "] of node type [" + possibleProperty.getNodeType().toString() + "]");
                }
            }
        } else {
            builder.setTarget(element.getContainingFile()).setTooltipText("Property [" + propertyName + "] of node [" + nodeVar.trim() + "]");
        }
        result.add(builder.createLineMarkerInfo(element));
    }
    
    public static void createNodeTypeLineMarkers(@NotNull PsiElement element, Collection<? super RelatedItemLineMarkerInfo> result, String text) {
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

                    Project project = element.getProject();
                    CndNodeType nodeType = CndUtil.findNodeType(project, namespace, nodeTypeName);
                    if (nodeType != null) {
                        Icon icon;
                        if (nodeType.isMixin()) {
                            icon = CndIcons.MIXIN;
                        } else {
                            icon = CndIcons.NODE_TYPE;
                        }
                        NavigationGutterIconBuilder<PsiElement> builder =
                                NavigationGutterIconBuilder.create(icon).
                                        setTarget(nodeType).
                                        setTooltipText("Navigate to node type [" + nodeTypeModel.toString() + "] definition");
                        result.add(builder.createLineMarkerInfo(element));
                    }
                }
            }
        }
    }
}

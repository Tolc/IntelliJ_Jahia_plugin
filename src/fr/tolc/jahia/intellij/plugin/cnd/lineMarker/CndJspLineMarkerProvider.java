package fr.tolc.jahia.intellij.plugin.cnd.lineMarker;

import static fr.tolc.jahia.intellij.plugin.cnd.model.NodeTypeModel.nodeTypeGlobalRegex;

import java.util.Collection;
import java.util.regex.Matcher;

import javax.swing.*;

import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider;
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.jsp.el.ELElementTypes;
import com.intellij.psi.xml.XmlAttributeValue;
import com.intellij.psi.xml.XmlToken;
import fr.tolc.jahia.intellij.plugin.cnd.icons.CndIcons;
import fr.tolc.jahia.intellij.plugin.cnd.model.NodeTypeModel;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndNodeType;
import fr.tolc.jahia.intellij.plugin.cnd.utils.CndUtil;
import org.jetbrains.annotations.NotNull;

public class CndJspLineMarkerProvider extends RelatedItemLineMarkerProvider {

    @Override
    protected void collectNavigationMarkers(@NotNull PsiElement element, Collection<? super RelatedItemLineMarkerInfo> result) {
        if (element instanceof XmlAttributeValue || element instanceof XmlToken) {
            if (!ELElementTypes.EL_SLICE_EXPRESSION.equals(element.getParent().getNode().getElementType())) {
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
}

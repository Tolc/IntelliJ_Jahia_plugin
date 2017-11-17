package fr.tolc.jahia.intellij.plugin.cnd.lineMarker;

import static fr.tolc.jahia.intellij.plugin.cnd.model.NodeTypeModel.nodeTypeGlobalRegex;

import java.util.Collection;
import java.util.regex.Matcher;

import javax.swing.Icon;

import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider;
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiLiteralExpression;
import fr.tolc.jahia.intellij.plugin.cnd.icons.CndIcons;
import fr.tolc.jahia.intellij.plugin.cnd.model.NodeTypeModel;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndNodeType;
import fr.tolc.jahia.intellij.plugin.cnd.utils.CndUtil;
import org.jetbrains.annotations.NotNull;

public class CndJavaLineMarkerProvider extends RelatedItemLineMarkerProvider {

    @Override
    protected void collectNavigationMarkers(@NotNull PsiElement element, Collection<? super RelatedItemLineMarkerInfo> result) {
        if (element instanceof PsiLiteralExpression) {
            PsiLiteralExpression literalExpression = (PsiLiteralExpression) element;
            String value = literalExpression.getValue() instanceof String ? (String) literalExpression.getValue() : null;
            
            if (value != null) {
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

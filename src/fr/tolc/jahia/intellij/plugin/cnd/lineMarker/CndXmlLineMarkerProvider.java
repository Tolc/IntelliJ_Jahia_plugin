package fr.tolc.jahia.intellij.plugin.cnd.lineMarker;

import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider;
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.xml.XmlAttributeValue;
import fr.tolc.jahia.intellij.plugin.cnd.icons.CndIcons;
import fr.tolc.jahia.intellij.plugin.cnd.utils.CndUtil;
import fr.tolc.jahia.intellij.plugin.cnd.model.NodeTypeModel;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndNodeType;
import org.jetbrains.annotations.NotNull;

import javax.swing.Icon;
import java.util.Collection;

public class CndXmlLineMarkerProvider extends RelatedItemLineMarkerProvider {

    @Override
    protected void collectNavigationMarkers(@NotNull PsiElement element, Collection<? super RelatedItemLineMarkerInfo> result) {
        if (element instanceof XmlAttributeValue) {
            String value = ((XmlAttributeValue) element).getValue();
            NodeTypeModel nodeTypeModel = CndUtil.getNodeTypeModel(value);

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
                                    setTooltipText("Navigate to node type definition");
                    result.add(builder.createLineMarkerInfo(element));
                }
            }
        }
    }
}

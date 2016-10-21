package fr.tolc.jahia.intellij.plugin.cnd.lineMarker;

import java.util.Collection;

import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider;
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder;
import com.intellij.lang.properties.psi.impl.PropertyKeyImpl;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import fr.tolc.jahia.intellij.plugin.cnd.icons.CndIcons;
import fr.tolc.jahia.intellij.plugin.cnd.CndUtil;
import fr.tolc.jahia.intellij.plugin.cnd.model.PropertiesFileCndKeyModel;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndNodeType;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndProperty;
import org.jetbrains.annotations.NotNull;

public class CndPropertiesLineMarkerProvider extends RelatedItemLineMarkerProvider {

    @Override
    protected void collectNavigationMarkers(@NotNull PsiElement element, Collection<? super RelatedItemLineMarkerInfo> result) {
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
                CndNodeType nodeType = CndUtil.findNodeType(project, namespace, nodeTypeName);

                if (nodeType != null) {
                    if (cndKeyModel.isProperty() || cndKeyModel.isChoicelistElement() || cndKeyModel.isPropertyTooltip()) {
                        String propertyName = cndKeyModel.getPropertyName();

                        CndProperty cndProperty = nodeType.getProperty(propertyName);
                        if (cndProperty != null) {
                            NavigationGutterIconBuilder<PsiElement> builder =
                                    NavigationGutterIconBuilder.create(CndIcons.FILE).
                                            setTarget(cndProperty).
                                            setTooltipText("Navigate to node type property");
                            result.add(builder.createLineMarkerInfo(element));
                        }
                    } else {
                        NavigationGutterIconBuilder<PsiElement> builder =
                                NavigationGutterIconBuilder.create(CndIcons.FILE).
                                        setTarget(nodeType).
                                        setTooltipText("Navigate to node type");
                        result.add(builder.createLineMarkerInfo(element));
                    }
                }
            }
        }
    }
}

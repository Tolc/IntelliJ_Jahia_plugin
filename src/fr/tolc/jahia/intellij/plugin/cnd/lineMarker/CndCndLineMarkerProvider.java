package fr.tolc.jahia.intellij.plugin.cnd.lineMarker;

import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider;
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.util.IconLoader;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;
import fr.tolc.jahia.intellij.plugin.cnd.icons.CndIcons;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndNamespaceIdentifier;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndNodeType;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndNodeTypeIdentifier;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndProperty;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndPropertyIdentifier;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndTypes;
import fr.tolc.jahia.intellij.plugin.cnd.utils.CndProjectFilesUtil;
import fr.tolc.jahia.intellij.plugin.cnd.utils.CndTranslationUtil;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

import javax.swing.Icon;
import java.io.File;
import java.net.URL;
import java.util.Collection;
import java.util.Set;

public class CndCndLineMarkerProvider extends RelatedItemLineMarkerProvider {

    @Override
    protected void collectNavigationMarkers(@NotNull PsiElement element, @NotNull Collection<? super RelatedItemLineMarkerInfo<?>> result) {
        IElementType type = element.getNode().getElementType();
        //Should only create line marker for leaf elements

        if (type.equals(CndTypes.NAMESPACE_NAME) && element.getParent() instanceof CndNamespaceIdentifier cndNamespaceIdentifier) {
            NavigationGutterIconBuilder<PsiElement> builder = NavigationGutterIconBuilder.create(CndIcons.NAMESPACE)
                    .setTarget(null)
                    .setTooltipText("Namespace " + cndNamespaceIdentifier.getNamespace().getNamespaceName());
            result.add(builder.createLineMarkerInfo(element));

        } else  if (type.equals(CndTypes.NODE_TYPE_NAME) && element.getParent() instanceof CndNodeTypeIdentifier cndNodeTypeIdentifier) {
            CndNodeType cndNodeType = cndNodeTypeIdentifier.getNodeType();
            NavigationGutterIconBuilder<PsiElement> builder;

            //Custom icon
            String jahiaWorkFolderPath = CndProjectFilesUtil.getJahiaWorkFolderPath(element);
            String nodeTypeNamespace = cndNodeType.getNodeTypeNamespace();
            if (StringUtils.isNotBlank(jahiaWorkFolderPath) && nodeTypeNamespace != null) {
                String iconPath = jahiaWorkFolderPath + "/icons/" + CndTranslationUtil.convertNodeTypeIdentifierToPropertyName(nodeTypeNamespace, cndNodeType.getNodeTypeName()) + ".png";
                File iconFile = new File(iconPath);
                if (iconFile.exists()) {
                    try {
                        Icon icon = IconLoader.findIcon(new URL("file:/" + iconPath));
                        if (icon != null) {
                            builder = NavigationGutterIconBuilder.create(icon)
                                    .setTarget(null)
                                    .setTooltipText("Node type " + cndNodeType + " custom icon");
                            result.add(builder.createLineMarkerInfo(element));
                        }
                    } catch (Exception e) {
                        //Nothing to do
                    }
                }
            }

            if (cndNodeType.isMixin()) {
                 builder = NavigationGutterIconBuilder.create(CndIcons.MIXIN)
                        .setTarget(null)
                        .setTooltipText("Mixin " + cndNodeType);
            } else {
                builder = NavigationGutterIconBuilder.create(CndIcons.NODE_TYPE)
                        .setTarget(null)
                        .setTooltipText("Node type " + cndNodeType);
            }
            result.add(builder.createLineMarkerInfo(element));

        } else if (type.equals(CndTypes.PROPERTY_NAME) && element.getParent() instanceof CndPropertyIdentifier cndPropertyIdentifier) {
            CndProperty cndProperty = cndPropertyIdentifier.getProperty();
            CndNodeType currentNodeType = cndProperty.getNodeType();
            Set<CndProperty> propertiesWithName = currentNodeType.getPropertiesWithName(cndProperty.getPropertyName());
            for (CndProperty propertyWithName : propertiesWithName) {
                CndNodeType nodeType = propertyWithName.getNodeType();
                if (!nodeType.equals(currentNodeType)) {
                    NavigationGutterIconBuilder<PsiElement> builder = NavigationGutterIconBuilder.create(AllIcons.Gutter.OverridingMethod)
                            .setTarget(propertyWithName)
                            .setTooltipText("Property " + cndProperty.getPropertyName() + " overrides the one from " + nodeType);
                    result.add(builder.createLineMarkerInfo(element));
                    break;
                }
            }
        }
    }
}

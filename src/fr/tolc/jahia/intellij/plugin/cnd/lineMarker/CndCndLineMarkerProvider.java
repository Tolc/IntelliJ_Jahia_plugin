package fr.tolc.jahia.intellij.plugin.cnd.lineMarker;

import java.io.File;
import java.net.URL;
import java.util.Collection;

import javax.swing.*;

import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider;
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder;
import com.intellij.openapi.util.IconLoader;
import com.intellij.psi.PsiElement;
import fr.tolc.jahia.intellij.plugin.cnd.icons.CndIcons;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndNamespace;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndNodeType;
import fr.tolc.jahia.intellij.plugin.cnd.utils.CndProjectFilesUtil;
import fr.tolc.jahia.intellij.plugin.cnd.utils.CndTranslationUtil;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

public class CndCndLineMarkerProvider extends RelatedItemLineMarkerProvider {

    @Override
    protected void collectNavigationMarkers(@NotNull PsiElement element, Collection<? super RelatedItemLineMarkerInfo> result) {
        if (element instanceof CndNamespace) {
            CndNamespace cndNamespace = (CndNamespace) element;

            NavigationGutterIconBuilder<PsiElement> builder = NavigationGutterIconBuilder.create(CndIcons.NAMESPACE)
                    .setTarget(null)
                    .setTooltipText("Namespace " + cndNamespace.getNamespaceName());
            result.add(builder.createLineMarkerInfo(element));
        }
        if (element instanceof CndNodeType) {
            CndNodeType cndNodeType = (CndNodeType) element;

            NavigationGutterIconBuilder<PsiElement> builder;


            //Custom icon
            String jahiaWorkFolderPath = CndProjectFilesUtil.getJahiaWorkFolderPath(element);
            if (StringUtils.isNotBlank(jahiaWorkFolderPath)) {
                String iconPath = jahiaWorkFolderPath + "/icons/" + CndTranslationUtil.convertNodeTypeIdentifierToPropertyName(cndNodeType.getNodeTypeNamespace(), cndNodeType.getNodeTypeName()) + ".png";
                File iconFile = new File(iconPath);
                if (iconFile.exists()) {
                    try {
                        Icon icon = IconLoader.findIcon(new URL("file:/" + iconPath));
                        if (icon != null) {
                            builder = NavigationGutterIconBuilder.create(icon)
                                    .setTarget(null)
                                    .setTooltipText("Node type " + cndNodeType.getNodeTypeNamespace() + ":" + cndNodeType.getNodeTypeName() + " custom icon");
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
                        .setTooltipText("Mixin " + cndNodeType.getNodeTypeNamespace() + ":" + cndNodeType.getNodeTypeName());
            } else {
                builder = NavigationGutterIconBuilder.create(CndIcons.NODE_TYPE)
                        .setTarget(null)
                        .setTooltipText("Node type " + cndNodeType.getNodeTypeNamespace() + ":" + cndNodeType.getNodeTypeName());
            }
            result.add(builder.createLineMarkerInfo(element));
        }
    }
}

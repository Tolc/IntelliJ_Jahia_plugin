package fr.tolc.jahia.intellij.plugin.cnd.lineMarker;

import java.util.Collection;

import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider;
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder;
import com.intellij.psi.PsiElement;
import fr.tolc.jahia.intellij.plugin.cnd.icons.CndIcons;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndNodeType;
import org.jetbrains.annotations.NotNull;

public class CndCndLineMarkerProvider extends RelatedItemLineMarkerProvider {

    @Override
    protected void collectNavigationMarkers(@NotNull PsiElement element, Collection<? super RelatedItemLineMarkerInfo> result) {
        if (element instanceof CndNodeType) {
            CndNodeType cndNodeType = (CndNodeType) element;

            NavigationGutterIconBuilder<PsiElement> builder;
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

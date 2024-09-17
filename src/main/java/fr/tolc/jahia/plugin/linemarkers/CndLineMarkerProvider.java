package fr.tolc.jahia.plugin.linemarkers;

import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider;
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder;
import com.intellij.psi.PsiElement;
import fr.tolc.jahia.language.cnd.CndIcons;
import fr.tolc.jahia.language.cnd.psi.CndNamespaceIdentifier;
import fr.tolc.jahia.language.cnd.psi.CndNodetype;
import fr.tolc.jahia.plugin.messages.CndBundle;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class CndLineMarkerProvider extends RelatedItemLineMarkerProvider {

    @Override
    protected void collectNavigationMarkers(@NotNull PsiElement element, @NotNull Collection<? super RelatedItemLineMarkerInfo<?>> result) {
        //Should only create line marker for leaf elements

        if (element instanceof CndNamespaceIdentifier namespaceIdentifier) {
            NavigationGutterIconBuilder<PsiElement> builder = NavigationGutterIconBuilder.create(CndIcons.CND_NS)
                    .setTarget(null)
                    .setTooltipText(CndBundle.message("linemarkers.cnd.namespace", namespaceIdentifier.getText()));
            result.add(builder.createLineMarkerInfo(namespaceIdentifier.getNode().getFirstChildNode().getPsi()));
        } else if (element instanceof CndNodetype nodetype) {
            if (nodetype.getNodetypeIdentifier() != null) {
                boolean isMixin = nodetype.isMixin();
                NavigationGutterIconBuilder<PsiElement> builder = NavigationGutterIconBuilder.create(isMixin ? CndIcons.CND_MIX : CndIcons.CND_NT)
                        .setTarget(null)
                        .setTooltipText(CndBundle.message(isMixin ? "linemarkers.cnd.mixin" : "linemarkers.cnd.nodetype", nodetype.getIdentifier()));
                result.add(builder.createLineMarkerInfo(nodetype.getNodetypeIdentifier().getNode().getFirstChildNode().getPsi()));
            }
        }

    }
}

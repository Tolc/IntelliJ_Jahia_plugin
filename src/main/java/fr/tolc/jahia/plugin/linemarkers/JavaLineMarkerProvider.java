package fr.tolc.jahia.plugin.linemarkers;

import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider;
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiLiteralExpression;
import com.intellij.psi.impl.source.tree.java.PsiJavaTokenImpl;
import fr.tolc.jahia.constants.CndConstants;
import fr.tolc.jahia.language.cnd.CndIcons;
import fr.tolc.jahia.language.cnd.CndUtil;
import fr.tolc.jahia.language.cnd.psi.CndNodetype;
import fr.tolc.jahia.plugin.messages.CndBundle;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;

public class JavaLineMarkerProvider extends RelatedItemLineMarkerProvider {

    @Override
    protected void collectNavigationMarkers(@NotNull PsiElement element, @NotNull Collection<? super RelatedItemLineMarkerInfo<?>> result) {

        // This must be an element with a literal expression as a parent
        if (!(element instanceof PsiJavaTokenImpl) || !(element.getParent() instanceof PsiLiteralExpression literalExpression)) {
            return;
        }

        // Ensure the literal expression contains a string that contains separator
        String text = literalExpression.getValue() instanceof String ? (String) literalExpression.getValue() : null;
        if (text == null || !text.contains(CndConstants.NS_SEP)) {
            return;
        }

        Matcher matcher = CndConstants.nodetypePattern.matcher(text);
        while (matcher.find()) {
            String ntIdentifier = matcher.group();
            final List<CndNodetype> nts = CndUtil.findNodetypes(element.getProject(), ntIdentifier);
            if (!nts.isEmpty()) {
                final List<CndNodetype> nodetypes = new ArrayList<>();
                final List<CndNodetype> mixins = new ArrayList<>();
                nts.forEach(nt -> {
                    if (nt.isMixin()) {
                        mixins.add(nt);
                    } else {
                        nodetypes.add(nt);
                    }
                });

                if (!mixins.isEmpty()) {
                    NavigationGutterIconBuilder<PsiElement> builder = NavigationGutterIconBuilder.create(CndIcons.CND_MIX)
                            .setTargets(mixins)
                            .setTooltipText(CndBundle.message("linemarkers.java.mixin", ntIdentifier));
                    result.add(builder.createLineMarkerInfo(element));
                }
                if (!nodetypes.isEmpty()) {
                    NavigationGutterIconBuilder<PsiElement> builder = NavigationGutterIconBuilder.create(CndIcons.CND_NT)
                            .setTargets(nodetypes)
                            .setTooltipText(CndBundle.message("linemarkers.java.nodetype", ntIdentifier));
                    result.add(builder.createLineMarkerInfo(element));
                }
            }
        }
    }
}

package fr.tolc.jahia.intellij.plugin.cnd;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider;
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiLiteralExpression;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndNodeType;
import org.jetbrains.annotations.NotNull;

public class CndLineMarkerProvider extends RelatedItemLineMarkerProvider {

    private static final Pattern nodeTypeRegex = Pattern.compile("^[A-Za-z]+" + ":" + "[A-Za-z0-9]+$");
    
    @Override
    protected void collectNavigationMarkers(@NotNull PsiElement element, Collection<? super RelatedItemLineMarkerInfo> result) {
        if (element instanceof PsiLiteralExpression) {
            PsiLiteralExpression literalExpression = (PsiLiteralExpression) element;
            String value = literalExpression.getValue() instanceof String ? (String)literalExpression.getValue() : null;

            if (value != null && value.contains(":")) {
                Matcher matcher = nodeTypeRegex.matcher(value);
                if (matcher.matches()) {
                    String[] splitValue = value.split(":");
                    String namespace = splitValue[0];
                    String nodeTypeName = splitValue[1];

                    Project project = element.getProject();
                    CndNodeType nodeType = CndUtil.findNodeType(project, namespace, nodeTypeName);
                    if (nodeType != null) {
                        NavigationGutterIconBuilder<PsiElement> builder =
                                NavigationGutterIconBuilder.create(CndIcons.FILE).
                                        setTarget(nodeType).
                                        setTooltipText("Navigate to node type definition");
                        result.add(builder.createLineMarkerInfo(element));
                    }
                }
            }
        }
    }
}

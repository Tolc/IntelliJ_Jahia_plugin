package fr.tolc.jahia.intellij.plugin.cnd.references;

import java.util.ArrayList;
import java.util.List;

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementResolveResult;
import com.intellij.psi.PsiPolyVariantReference;
import com.intellij.psi.PsiReferenceBase;
import com.intellij.psi.ResolveResult;
import fr.tolc.jahia.intellij.plugin.cnd.CndIcons;
import fr.tolc.jahia.intellij.plugin.cnd.CndUtil;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndNodeType;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndProperty;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CndPropertyReference extends PsiReferenceBase<PsiElement> implements PsiPolyVariantReference {
    private String namespace;
    private String nodeType;
    private String propertyName;

    public CndPropertyReference(@NotNull PsiElement element, TextRange textRange, String namespace, String nodeType, String propertyName) {
        super(element, textRange);
        this.namespace = namespace;
        this.nodeType = nodeType;
        this.propertyName = propertyName;
    }

    @Nullable
    @Override
    public PsiElement resolve() {
        ResolveResult[] resolveResults = multiResolve(false);
        return resolveResults.length == 1 ? resolveResults[0].getElement() : null;
    }

    @NotNull
    @Override
    public Object[] getVariants() {
        Project project = myElement.getProject();
        CndNodeType cndNodeType = CndUtil.findNodeType(project, namespace, nodeType);
        List<LookupElement> variants = new ArrayList<LookupElement>();
        if (cndNodeType != null) {
            List<CndProperty> properties = cndNodeType.getPropertyList();
            for (final CndProperty property : properties) {
                if (StringUtils.isNotBlank(property.getPropertyName())) {
                    variants.add(LookupElementBuilder.create(property.getPropertyName().replace(':', '_')).withIcon(CndIcons.FILE).withTypeText(property.getContainingFile().getName()));
                }
            }
        }
        return variants.toArray();
    }

    @NotNull
    @Override
    public ResolveResult[] multiResolve(boolean incompleteCode) {
        Project project = myElement.getProject();
        List<CndProperty> properties = CndUtil.findProperties(project, namespace, nodeType, propertyName);
        List<ResolveResult> results = new ArrayList<ResolveResult>();
        for (CndProperty property : properties) {
            results.add(new PsiElementResolveResult(property));
        }
        return results.toArray(new ResolveResult[results.size()]);
    }
}

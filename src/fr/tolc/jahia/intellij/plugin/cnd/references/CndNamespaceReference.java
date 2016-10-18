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
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndNamespace;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CndNamespaceReference extends PsiReferenceBase<PsiElement> implements PsiPolyVariantReference {
    private String namespace;

    public CndNamespaceReference(@NotNull PsiElement element, TextRange textRange, String namespace) {
        super(element, textRange);
        this.namespace = namespace;
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
        List<CndNamespace> namespaces = CndUtil.findNamespaces(project);
        List<LookupElement> variants = new ArrayList<LookupElement>();
        for (final CndNamespace cndNamespace : namespaces) {
            if (StringUtils.isNotBlank(cndNamespace.getNamespaceName())) {
                variants.add(LookupElementBuilder.create(cndNamespace).withIcon(CndIcons.FILE).withTypeText(cndNamespace.getContainingFile().getName()));
            }
        }
        return variants.toArray();
    }

    @NotNull
    @Override
    public ResolveResult[] multiResolve(boolean incompleteCode) {
        Project project = myElement.getProject();
        List<CndNamespace> namespaces = CndUtil.findNamespaces(project, namespace);
        List<ResolveResult> results = new ArrayList<ResolveResult>();
        for (CndNamespace namespace : namespaces) {
            results.add(new PsiElementResolveResult(namespace));
        }
        return results.toArray(new ResolveResult[results.size()]);
    }
}

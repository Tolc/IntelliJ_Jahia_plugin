package fr.tolc.jahia.intellij.plugin.cnd;

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndNamespace;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndNodeType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CndNamespaceReference extends PsiReferenceBase<PsiElement> implements PsiPolyVariantReference {
    private String namespace;

    public CndNamespaceReference(@NotNull PsiElement element, TextRange textRange, String namespace) {
        super(element, textRange);
        this.namespace = namespace;
    }

    @Nullable
    @Override
    public PsiElement resolve() {
        Project project = myElement.getProject();
        return CndUtil.findNamespace(project, namespace);
    }

    @NotNull
    @Override
    public Object[] getVariants() {
        Project project = myElement.getProject();
        List<CndNamespace> namespaces = CndUtil.findNamespaces(project);
        List<LookupElement> variants = new ArrayList<LookupElement>();
        for (final CndNamespace namespace : namespaces) {
            if (namespace.getNamespaceName() != null && namespace.getNamespaceName().length() > 0) {
                variants.add(LookupElementBuilder.create(namespace).withIcon(CndIcons.FILE).withTypeText(namespace.getContainingFile().getName()));
            }
        }
        return variants.toArray();
    }

    @NotNull
    @Override
    public ResolveResult[] multiResolve(boolean incompleteCode) {
        List<ResolveResult> results = new ArrayList<ResolveResult>();
        PsiElement resolveResult = resolve();
        if (resolveResult != null) {
            results.add(new PsiElementResolveResult(resolveResult));
        }
        return results.toArray(new ResolveResult[results.size()]);
    }
}

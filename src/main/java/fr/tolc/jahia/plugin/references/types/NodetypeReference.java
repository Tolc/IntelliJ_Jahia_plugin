package fr.tolc.jahia.plugin.references.types;

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementResolveResult;
import com.intellij.psi.PsiPolyVariantReference;
import com.intellij.psi.PsiReferenceBase;
import com.intellij.psi.ResolveResult;
import fr.tolc.jahia.language.cnd.CndIcons;
import fr.tolc.jahia.language.cnd.CndUtil;
import fr.tolc.jahia.language.cnd.psi.CndNodetype;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class NodetypeReference extends PsiReferenceBase<PsiElement> implements PsiPolyVariantReference {

    private final String identifier;

    public NodetypeReference(@NotNull PsiElement element, TextRange rangeInElement) {
        super(element, rangeInElement);
        identifier = element.getText().substring(rangeInElement.getStartOffset(), rangeInElement.getEndOffset());
    }

    @Override
    public ResolveResult @NotNull [] multiResolve(boolean incompleteCode) {
        Project project = myElement.getProject();
        final List<CndNodetype> nodetypes = CndUtil.findNodetypes(project, identifier);
        List<ResolveResult> results = new ArrayList<>();
        for (CndNodetype nodetype : nodetypes) {
            results.add(new PsiElementResolveResult(nodetype));
        }
        return results.toArray(new ResolveResult[0]);
    }

    @Override
    public @Nullable PsiElement resolve() {
        ResolveResult[] resolveResults = multiResolve(false);
        return resolveResults.length == 1 ? resolveResults[0].getElement() : null;
    }

    @Override
    public Object @NotNull [] getVariants() {
        Project project = myElement.getProject();
        List<CndNodetype> nodetypes = CndUtil.findNodetypes(project);
        List<LookupElement> variants = new ArrayList<>();
        for (final CndNodetype nodetype : nodetypes) {
            if (StringUtils.isNotBlank(nodetype.getIdentifier())) {
                variants.add(LookupElementBuilder
                        .create(nodetype).withIcon(nodetype.isMixin() ? CndIcons.CND_MIX : CndIcons.CND_NT)
                        .withTypeText(nodetype.getContainingFile().getName())
                );
            }
        }
        return variants.toArray();
    }
}

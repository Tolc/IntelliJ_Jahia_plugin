package fr.tolc.jahia.plugin.references.types;

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.navigation.ItemPresentation;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementResolveResult;
import com.intellij.psi.PsiPolyVariantReference;
import com.intellij.psi.PsiReferenceBase;
import com.intellij.psi.ResolveResult;
import com.intellij.psi.impl.PsiElementBase;
import fr.tolc.jahia.language.cnd.psi.CndNodetype;
import fr.tolc.jahia.language.cnd.psi.CndProperty;
import fr.tolc.jahia.language.cnd.psi.CndSubnode;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PropertyOrSubnodeReference extends PsiReferenceBase<PsiElement> implements PsiPolyVariantReference {

    private final String name;
    private final CndNodetype nodetype;

    public PropertyOrSubnodeReference(@NotNull PsiElement element, TextRange rangeInElement, CndNodetype nodetype) {
        super(element, rangeInElement);
        this.name = element.getText().substring(rangeInElement.getStartOffset(), rangeInElement.getEndOffset());
        this.nodetype = nodetype;
    }

    @Override
    public ResolveResult @NotNull [] multiResolve(boolean incompleteCode) {
        //TODO: maybe search in supertypes properties and subnodes too?
        List<ResolveResult> results = new ArrayList<>();
        final CndProperty property = nodetype.getProperty(name);
        if (property != null) {
            results.add(new PsiElementResolveResult(property.getPropertyName()));
        }
        final CndSubnode subnode = nodetype.getSubnode(name);
        if (subnode != null) {
            results.add(new PsiElementResolveResult(subnode.getSubnodeName()));
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
        //TODO: maybe search in supertypes properties and subnodes too?
        List<LookupElement> variants = new ArrayList<>();
        List<CndProperty> properties = nodetype.getPropertyList();
        for (final CndProperty property : properties) {
            if (StringUtils.isNotBlank(property.getName())) {
                ItemPresentation presentation = ((PsiElementBase) property).getPresentation();
                variants.add(LookupElementBuilder
                        .create(property.getPropertyName())
                        .withTypeText(presentation.getLocationString())
                        .withIcon(presentation.getIcon(false))
                );
            }
        }
        List<CndSubnode> subnodes = nodetype.getSubnodeList();
        for (final CndSubnode subnode : subnodes) {
            if (StringUtils.isNotBlank(subnode.getName())) {
                ItemPresentation presentation = ((PsiElementBase) subnode).getPresentation();
                variants.add(LookupElementBuilder
                        .create(subnode.getSubnodeName())
                        .withTypeText(presentation.getLocationString())
                        .withIcon(presentation.getIcon(false))
                );
            }
        }
        return variants.toArray();
    }
}

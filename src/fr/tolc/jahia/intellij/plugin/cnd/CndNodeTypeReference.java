package fr.tolc.jahia.intellij.plugin.cnd;

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndNodeType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CndNodeTypeReference extends PsiReferenceBase<PsiElement> implements PsiPolyVariantReference {
    private String namespace;
    private String nodeType;

    public CndNodeTypeReference(@NotNull PsiElement element, TextRange textRange, String namespace, String nodeType) {
        super(element, textRange);
        this.namespace = namespace;
        this.nodeType = nodeType;
    }

    @Nullable
    @Override
    public PsiElement resolve() {
        Project project = myElement.getProject();
        return CndUtil.findNodeType(project, namespace, nodeType);
    }

    @NotNull
    @Override
    public Object[] getVariants() {
//        return EMPTY_ARRAY;
        Project project = myElement.getProject();
        List<CndNodeType> nodeTypes = CndUtil.findNodeTypes(project);
        List<LookupElement> variants = new ArrayList<LookupElement>();
        for (final CndNodeType nodeType : nodeTypes) {
            if (nodeType.getNodeTypeName() != null && nodeType.getNodeTypeName().length() > 0) {
                variants.add(LookupElementBuilder.create(nodeType).withIcon(CndIcons.FILE).withTypeText(nodeType.getContainingFile().getName()));
            }
        }
        return variants.toArray();
    }

    @NotNull
    @Override
    public ResolveResult[] multiResolve(boolean incompleteCode) {
        List<ResolveResult> results = new ArrayList<ResolveResult>();
        results.add(new PsiElementResolveResult(resolve()));
        return results.toArray(new ResolveResult[results.size()]);
    }
}

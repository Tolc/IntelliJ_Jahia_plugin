package fr.tolc.jahia.intellij.plugin.cnd.references;

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import fr.tolc.jahia.intellij.plugin.cnd.CndIcons;
import fr.tolc.jahia.intellij.plugin.cnd.CndUtil;
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
        ResolveResult[] resolveResults = multiResolve(false);
        return resolveResults.length == 1 ? resolveResults[0].getElement() : null;
    }

    @NotNull
    @Override
    public Object[] getVariants() {
        Project project = myElement.getProject();
        List<CndNodeType> nodeTypes = CndUtil.findNodeTypes(project, namespace);
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
        Project project = myElement.getProject();
        List<CndNodeType> nodeTypes = CndUtil.findNodeTypes(project, namespace, nodeType);
        List<ResolveResult> results = new ArrayList<ResolveResult>();
        for (CndNodeType nodeType : nodeTypes) {
            results.add(new PsiElementResolveResult(nodeType));
        }
        return results.toArray(new ResolveResult[results.size()]);
    }
}

package fr.tolc.jahia.intellij.plugin.cnd.references.types;

import java.util.ArrayList;
import java.util.List;

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementResolveResult;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiPolyVariantReference;
import com.intellij.psi.PsiReferenceBase;
import com.intellij.psi.ResolveResult;
import fr.tolc.jahia.intellij.plugin.cnd.icons.CndIcons;
import fr.tolc.jahia.intellij.plugin.cnd.model.ViewModel;
import fr.tolc.jahia.intellij.plugin.cnd.utils.CndProjectFilesUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ViewIncludeReference extends PsiReferenceBase<PsiElement> implements PsiPolyVariantReference {
    private ViewModel viewModel;

    public ViewIncludeReference(@NotNull PsiElement element, TextRange textRange, ViewModel viewModel) {
        super(element, textRange);
        this.viewModel = viewModel;
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
        List<ViewModel> nodeTypeViews = CndProjectFilesUtil.getNodeTypeViews(myElement, viewModel.getNodeType().getNamespace(), viewModel.getNodeType().getNodeTypeName());
        List<LookupElement> variants = new ArrayList<LookupElement>();
        for (ViewModel nodeTypeView : nodeTypeViews) {
            List<PsiFile> viewFiles = CndProjectFilesUtil.findViewFiles(project, nodeTypeView);
            if (!viewFiles.isEmpty()) {
                PsiFile psiViewFile = viewFiles.get(0);
                variants.add(LookupElementBuilder.create(psiViewFile)
                        .withTypeText(psiViewFile.getName())
                        .withIcon(nodeTypeView.isHidden()? CndIcons.VIEW_BIG_HIDDEN : CndIcons.VIEW_BIG));  //TODO: change icons to eye only
            }
        }
        return variants.toArray();
    }

    @NotNull
    @Override
    public ResolveResult[] multiResolve(boolean incompleteCode) {
        Project project = myElement.getProject();
        List<PsiFile> viewFiles = CndProjectFilesUtil.findViewFiles(project, viewModel);
        List<ResolveResult> results = new ArrayList<ResolveResult>();
        for (PsiFile viewFile : viewFiles) {
            results.add(new PsiElementResolveResult(viewFile));
        }
        return results.toArray(new ResolveResult[results.size()]);
    }
}

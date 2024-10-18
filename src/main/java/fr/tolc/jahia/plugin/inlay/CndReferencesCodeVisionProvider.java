package fr.tolc.jahia.plugin.inlay;

import com.intellij.codeInsight.codeVision.CodeVisionRelativeOrdering;
import com.intellij.codeInsight.hints.codeVision.ReferencesCodeVisionProvider;
import com.intellij.codeInspection.deadCode.UnusedDeclarationInspectionBase;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import fr.tolc.jahia.language.cnd.CndLanguage;
import fr.tolc.jahia.language.cnd.psi.CndNodetype;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.event.MouseEvent;
import java.util.List;

public class CndReferencesCodeVisionProvider extends ReferencesCodeVisionProvider {

    @Override
    public boolean acceptsFile(@NotNull PsiFile psiFile) {
        return psiFile.getLanguage() == CndLanguage.INSTANCE;
    }

    @Override
    public boolean acceptsElement(@NotNull PsiElement psiElement) {
        return psiElement instanceof CndNodetype;
    }

    @Override
    public @Nls @Nullable String getHint(@NotNull PsiElement psiElement, @NotNull PsiFile psiFile) {
        CodeVisionInfo visionInfo = getVisionInfo(psiElement, psiFile);
        return visionInfo != null ? visionInfo.getText() : null;
    }

    @Override
    public @Nullable CodeVisionInfo getVisionInfo(@NotNull PsiElement element, @NotNull PsiFile file) {
        CndNodetype nodetype = (CndNodetype) element;
        UnusedDeclarationInspectionBase inspection = UnusedDeclarationInspectionBase.findUnusedDeclarationInspection(nodetype.getNodetypeIdentifier());
        if (inspection.isEntryPoint(element)) {
            return null;
        }
        CndTelescope.UsagesHint usagesHint = CndTelescope.usagesHint(nodetype.getNodetypeIdentifier(), file);
        if (usagesHint != null) {
            return new CodeVisionInfo(usagesHint.getHint(), usagesHint.getCount(), false);
        }
        return null;
    }

    @Override
    public void handleClick(@NotNull Editor editor, @NotNull PsiElement element, @Nullable MouseEvent event) {
        CndNodetype nodetype = (CndNodetype) element;
        super.handleClick(editor, nodetype.getNodetypeIdentifier(), event);
    }

    @Override
    public @NotNull List<CodeVisionRelativeOrdering> getRelativeOrderings() {
//        return List.of(new CodeVisionRelativeOrdering.CodeVisionRelativeOrderingBefore("cnd.inheritors"));
        return List.of();
    }

    @Override
    public @NotNull String getId() {
        return "cnd.references";
    }

    @Override
    public @NotNull String getName() {
        return super.getName();
    }
}

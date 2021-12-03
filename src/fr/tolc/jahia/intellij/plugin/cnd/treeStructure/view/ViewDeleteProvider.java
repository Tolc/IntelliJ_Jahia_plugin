package fr.tolc.jahia.intellij.plugin.cnd.treeStructure.view;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.intellij.ide.DeleteProvider;
import com.intellij.ide.util.DeleteHandler;
import com.intellij.ide.util.treeView.AbstractTreeNode;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiUtilCore;
import com.intellij.util.containers.ContainerUtil;
import org.jetbrains.annotations.NotNull;

public class ViewDeleteProvider implements DeleteProvider {
    private final PsiElement[] elements;

    public ViewDeleteProvider(final Collection<AbstractTreeNode<?>> selected) {
        elements = collectViewPsiElements(selected);
    }

    public void deleteElement(@NotNull DataContext dataContext) {
        Project project = CommonDataKeys.PROJECT.getData(dataContext);
        DeleteHandler.deletePsiElement(elements, project);
    }

    public boolean canDeleteElement(@NotNull DataContext dataContext) {
        return DeleteHandler.shouldEnableDeleteAction(elements);
    }

    private static PsiElement[] collectViewPsiElements(Collection<AbstractTreeNode<?>> selected) {
        Set<PsiElement> result = new HashSet<>();
        for (AbstractTreeNode<?> node : selected) {
            if (node.getValue() instanceof View) {
                View view = (View) node.getValue();
                ContainerUtil.addAll(result, view.getViewFiles());
            } else if (node.getValue() instanceof PsiElement) {
                result.add((PsiElement) node.getValue());
            }
        }
        return PsiUtilCore.toPsiElementArray(result);
    }
}
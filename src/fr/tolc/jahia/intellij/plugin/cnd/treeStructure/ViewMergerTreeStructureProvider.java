package fr.tolc.jahia.intellij.plugin.cnd.treeStructure;

import com.intellij.ide.projectView.ProjectViewNode;
import com.intellij.ide.projectView.TreeStructureProvider;
import com.intellij.ide.projectView.ViewSettings;
import com.intellij.ide.projectView.impl.nodes.BasePsiNode;
import com.intellij.ide.util.treeView.AbstractTreeNode;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.fileTypes.StdFileTypes;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import fr.tolc.jahia.intellij.plugin.cnd.model.ViewModel;
import fr.tolc.jahia.intellij.plugin.cnd.utils.CndProjectFilesUtil;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class ViewMergerTreeStructureProvider implements TreeStructureProvider {
    private final Project project;

    public ViewMergerTreeStructureProvider(Project project) {
        this.project = project;
    }

    @NotNull
    public Collection<AbstractTreeNode> modify(@NotNull AbstractTreeNode parent, @NotNull Collection<AbstractTreeNode> children, ViewSettings settings) {
        if (parent.getValue() instanceof View) {
            return children;
        }


        Collection<AbstractTreeNode> result = new LinkedHashSet<AbstractTreeNode>(children);
        ProjectViewNode[] copy = children.toArray(new ProjectViewNode[children.size()]);
        List<String> alreadyDoneViews = new ArrayList<String>();

        for (ProjectViewNode element : copy) {
            if (element.getValue() instanceof PsiFile) {
                PsiFile file = (PsiFile) element.getValue();
                if (file.getFileType() != StdFileTypes.PROPERTIES) {
                    VirtualFile virtualFile = file.getVirtualFile();

                    ViewModel viewModel = CndProjectFilesUtil.getViewModelFromPotentialViewFile(file.getProject(), virtualFile);
                    if (viewModel != null && !alreadyDoneViews.contains(viewModel.getName())) {
                        List<PsiFile> views = CndProjectFilesUtil.findViewFiles(file.getProject(), viewModel);

                        Collection<BasePsiNode<? extends PsiElement>> viewNodes = findViewsIn(children, views);
                        if (!viewNodes.isEmpty()) {
                            Collection<PsiFile> viewFiles = convertToFiles(viewNodes);
                            Collection<BasePsiNode<? extends PsiElement>> subNodes = new ArrayList<>();
                            subNodes.add((BasePsiNode<? extends PsiElement>) element);
                            subNodes.addAll(viewNodes);
                            result.add(new ViewNode(project, new View(viewModel, viewFiles), settings, subNodes));
                            result.remove(element);
                            result.removeAll(viewNodes);

                            alreadyDoneViews.add(viewModel.getName());
                        }
                    }
                }
            }
        }

        return result;
    }

    public Object getData(Collection<AbstractTreeNode> selected, String dataId) {
        if (selected != null) {
            if (View.DATA_KEY.is(dataId)) {
                List<View> result = new ArrayList<>();
                for (AbstractTreeNode node : selected) {
                    if (node.getValue() instanceof View) {
                        result.add((View) node.getValue());
                    }
                }
                if (!result.isEmpty()) {
                    return result.toArray(new View[result.size()]);
                }
            } else if (PlatformDataKeys.DELETE_ELEMENT_PROVIDER.is(dataId)) {
                for (AbstractTreeNode node : selected) {
                    if (node.getValue() instanceof View) {
                        return new ViewDeleteProvider(selected);
                    }
                }
            }
        }
        return null;
    }

    private static Collection<PsiFile> convertToFiles(Collection<BasePsiNode<? extends PsiElement>> viewNodes) {
        ArrayList<PsiFile> psiFiles = new ArrayList<>();
        for (AbstractTreeNode treeNode : viewNodes) {
            psiFiles.add((PsiFile) treeNode.getValue());
        }
        return psiFiles;
    }

    private static Collection<BasePsiNode<? extends PsiElement>> findViewsIn(Collection<AbstractTreeNode> children, List<PsiFile> views) {
        if (children.isEmpty() || views.isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList<BasePsiNode<? extends PsiElement>> result = new ArrayList<>();
        HashSet<PsiFile> psiFiles = new HashSet<>(views);
        for (final AbstractTreeNode child : children) {
            if (child instanceof BasePsiNode) {
                BasePsiNode<? extends PsiElement> treeNode = (BasePsiNode<? extends PsiElement>) child;
                if (psiFiles.contains(treeNode.getValue())) {
                    result.add(treeNode);
                }
            }
        }
        return result;
    }
}

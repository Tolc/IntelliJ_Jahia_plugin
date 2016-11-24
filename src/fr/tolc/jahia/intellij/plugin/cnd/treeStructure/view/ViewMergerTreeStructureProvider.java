package fr.tolc.jahia.intellij.plugin.cnd.treeStructure.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

import com.intellij.ide.projectView.ProjectViewNode;
import com.intellij.ide.projectView.TreeStructureProvider;
import com.intellij.ide.projectView.ViewSettings;
import com.intellij.ide.projectView.impl.nodes.BasePsiNode;
import com.intellij.ide.util.treeView.AbstractTreeNode;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.fileTypes.StdFileTypes;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Computable;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import fr.tolc.jahia.intellij.plugin.cnd.model.ViewModel;
import fr.tolc.jahia.intellij.plugin.cnd.utils.CndProjectFilesUtil;
import org.jetbrains.annotations.NotNull;

public class ViewMergerTreeStructureProvider implements TreeStructureProvider, DumbAware {
    private final Project project;

    public ViewMergerTreeStructureProvider(Project project) {
        this.project = project;
    }

    @NotNull
    public Collection<AbstractTreeNode> modify(@NotNull AbstractTreeNode parent, @NotNull Collection<AbstractTreeNode> children, ViewSettings settings) {
        if (children.isEmpty() 
            || !(parent.getValue() instanceof PsiDirectory)
            || parent.getValue() instanceof View) {
            return children;
        }


        return ApplicationManager.getApplication().runReadAction(new Computable<Collection<AbstractTreeNode>>() {
            @Override
            public Collection<AbstractTreeNode> compute() {
                if (!CndProjectFilesUtil.isNodeTypeFolderChildFolder(((PsiDirectory) parent.getValue()).getVirtualFile())) {
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
//                                    Collection<BasePsiNode<? extends PsiElement>> subNodes = new ArrayList<>();
//                                    subNodes.add((BasePsiNode<? extends PsiElement>) element);
//                                    subNodes.addAll(viewNodes);
                                    result.add(new ViewNode(project, new View(viewModel, viewFiles), settings));
//                                    result.remove(element);
                                    result.removeAll(viewNodes);

                                    alreadyDoneViews.add(viewModel.getName());
                                }
                            }
                        }
                    }
                }

                //TODO: have default at the top of the view. Below sort is sorting right but it does not sort in projectView
                //        if (!alreadyDoneViews.isEmpty()) {
                //            Collection<AbstractTreeNode> sortedResult = new TreeSet<AbstractTreeNode>(new Comparator<AbstractTreeNode>() {
                //                @Override
                //                public int compare(AbstractTreeNode o1, AbstractTreeNode o2) {
                //                    if (o1 instanceof ViewNode && o2 instanceof ViewNode) {
                //                        View view1 = ((ViewNode) o1).getValue();
                //                        View view2 = ((ViewNode) o2).getValue();
                //                        if ("default".equals(view1.getName())) {
                //                            return -1;
                //                        }
                //                        if ("default".equals(view2.getName())) {
                //                            return 1;
                //                        }
                //                        return view1.getName().compareTo(view2.getName());
                //                    } else if (o1 instanceof ViewNode) {
                //                        return -1;
                //                    } else if (o2 instanceof ViewNode) {
                //                        return 1;
                //                    }
                //                    return 0;
                //                }
                //            });
                //            sortedResult.addAll(result);
                //            result = sortedResult;
                //        }

                return result;
            }
        });
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

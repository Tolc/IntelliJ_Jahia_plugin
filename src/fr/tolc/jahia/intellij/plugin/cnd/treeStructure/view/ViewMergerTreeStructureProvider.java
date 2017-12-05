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
import fr.tolc.jahia.intellij.plugin.cnd.model.NodeTypeModel;
import fr.tolc.jahia.intellij.plugin.cnd.model.ViewModel;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndNodeType;
import fr.tolc.jahia.intellij.plugin.cnd.utils.CndProjectFilesUtil;
import fr.tolc.jahia.intellij.plugin.cnd.utils.CndUtil;
import org.jetbrains.annotations.NotNull;

public class ViewMergerTreeStructureProvider implements TreeStructureProvider, DumbAware {
    private final Project project;

    public ViewMergerTreeStructureProvider(Project project) {
        this.project = project;
    }

    @NotNull
    public Collection<AbstractTreeNode> modify(@NotNull AbstractTreeNode parent, @NotNull Collection<AbstractTreeNode> children, ViewSettings settings) {
        if (!children.isEmpty() && parent.getValue() instanceof PsiDirectory) {

            //Views global virtual folder
            VirtualFile parentFile = ((PsiDirectory) parent.getValue()).getVirtualFile();
            String jahiaWorkFolderPath = CndProjectFilesUtil.getJahiaWorkFolderPath(CndProjectFilesUtil.getModuleForFile(project, parentFile));
            if (parentFile.getPath().equals(jahiaWorkFolderPath)) {
                return ApplicationManager.getApplication().runReadAction(new Computable<Collection<AbstractTreeNode>>() {
                    @Override
                    public Collection<AbstractTreeNode> compute() {
                        Collection<AbstractTreeNode> result = new LinkedHashSet<>(children);
                        ProjectViewNode[] copy = children.toArray(new ProjectViewNode[children.size()]);

                        List<PsiDirectory> nodeTypeFolders = new ArrayList<>();
                        for (ProjectViewNode element : copy) {
                            if (element.getValue() instanceof PsiDirectory) {
                                PsiDirectory psiDirectory = (PsiDirectory) element.getValue();
                                NodeTypeModel nodeTypeModel = null;
                                try {
                                    nodeTypeModel = new NodeTypeModel(psiDirectory.getName(), true);
                                } catch (IllegalArgumentException e) {
                                    //Nothing to do
                                }
                                if (nodeTypeModel == null) {
                                    //Try with parent directory (because of IntelliJ's weird way of merging directories into one if only one subdirectory)
                                    PsiDirectory parentDir = psiDirectory.getParent();
                                    if (parentDir != null && parentDir.getChildren().length == 1 && !parentDir.equals(parent.getValue())) {
                                        try {
                                            nodeTypeModel = new NodeTypeModel(parentDir.getName(), true);
                                        } catch (IllegalArgumentException e) {
                                            //Nothing to do
                                        }
                                    }
                                }

                                if (nodeTypeModel != null) {
                                    CndNodeType cndNodeType = CndUtil.findNodeType(project, nodeTypeModel);
                                    if (cndNodeType != null) {
                                        nodeTypeFolders.add(psiDirectory);
                                        result.remove(element);
                                    }
                                }
                            }
                        }
                        
                        result.add(new ViewsFolderNode(project, new ViewsFolder((PsiDirectory) parent.getValue(), nodeTypeFolders), settings));
                        return result;
                    }
                });
            }

            //View individual virtual folders
            return ApplicationManager.getApplication().runReadAction(new Computable<Collection<AbstractTreeNode>>() {
                @Override
                public Collection<AbstractTreeNode> compute() {
                    if (!CndProjectFilesUtil.isNodeTypeFolderChildFolder(((PsiDirectory) parent.getValue()).getVirtualFile())) {
                        return children;
                    }

                    Collection<AbstractTreeNode> result = new LinkedHashSet<>(children);
                    ProjectViewNode[] copy = children.toArray(new ProjectViewNode[children.size()]);
                    List<String> alreadyDoneViews = new ArrayList<String>();

                    for (ProjectViewNode element : copy) {
                        if (element.getValue() instanceof PsiFile) {
                            PsiFile file = (PsiFile) element.getValue();
                            if (file.getFileType() != StdFileTypes.PROPERTIES) {
                                VirtualFile virtualFile = file.getVirtualFile();

                                ViewModel viewModel = CndProjectFilesUtil.getViewModelFromPotentialViewFile(virtualFile);
                                if (viewModel != null && !alreadyDoneViews.contains(viewModel.getName())) {
                                    List<PsiFile> views = CndProjectFilesUtil.findViewFiles(CndProjectFilesUtil.getModuleForFile(file.getProject(), virtualFile), viewModel);

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
        
        return children;
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
            } if (ViewsFolder.DATA_KEY.is(dataId)) {
                for (AbstractTreeNode node : selected) {
                    if (node.getValue() instanceof ViewsFolder) {
                        return node.getValue();
                    }
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

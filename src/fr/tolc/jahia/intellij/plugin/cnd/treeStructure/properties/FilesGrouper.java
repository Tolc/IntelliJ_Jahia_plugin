/**
 * Copyright (c) 2016 by Bank Lombard Odier & Co Ltd, Geneva, Switzerland. This software is subject
 * to copyright protection under the laws of Switzerland and other countries. ALL RIGHTS RESERVED.
 */
package fr.tolc.jahia.intellij.plugin.cnd.treeStructure.properties;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.intellij.ide.projectView.TreeStructureProvider;
import com.intellij.ide.projectView.ViewSettings;
import com.intellij.ide.projectView.impl.nodes.PsiDirectoryNode;
import com.intellij.ide.projectView.impl.nodes.PsiFileNode;
import com.intellij.ide.util.treeView.AbstractTreeNode;
import com.intellij.lang.properties.projectView.ResourceBundleDeleteProvider;
import com.intellij.lang.properties.projectView.ResourceBundleGrouper;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Computable;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import fr.tolc.jahia.intellij.plugin.cnd.model.ViewModel;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndNodeType;
import fr.tolc.jahia.intellij.plugin.cnd.utils.CndProjectFilesUtil;
import fr.tolc.jahia.intellij.plugin.cnd.utils.CndUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FilesGrouper implements TreeStructureProvider, DumbAware {
    private static final Logger LOG = Logger.getInstance(ResourceBundleGrouper.class);
    private final Project project;

    public FilesGrouper(Project project) {
        this.project = project;
    }

    @NotNull
    @Override
    public Collection<AbstractTreeNode> modify(@NotNull AbstractTreeNode parent, @NotNull Collection<AbstractTreeNode> children, ViewSettings settings) {
        if (children.isEmpty()
                || !(parent instanceof PsiDirectoryNode)
                || parent instanceof FilesGroupNode) {
            return children;
        }
        
        return ApplicationManager.getApplication().runReadAction(new Computable<Collection<AbstractTreeNode>>() {
            public Collection<AbstractTreeNode> compute() {
                if (!CndProjectFilesUtil.isNodeTypeFolderChildFolder(((PsiDirectory) parent.getValue()).getVirtualFile())) {
                    return children;
                }
                
                ArrayList<AbstractTreeNode> result = new ArrayList<AbstractTreeNode>();
                Map<ViewModel, List<PsiFile>> viewsMap = new HashMap<ViewModel, List<PsiFile>>();

                for (AbstractTreeNode child : children) {
                    if (child instanceof PsiFileNode) {

                        ViewModel viewModel = CndProjectFilesUtil.getViewModelFromPotentialViewFile(FilesGrouper.this.project, ((PsiFileNode) child).getVirtualFile());
                        if (viewModel != null) {

                            //trying to get view from map
                            ViewModel correspondingViewModel = null;
                            for (Map.Entry<ViewModel, List<PsiFile>> view : viewsMap.entrySet()) {
                                if (view.getKey().isSameView(viewModel)) {
                                    correspondingViewModel = view.getKey();
                                    break;
                                }
                            }

                            if (correspondingViewModel != null) {
                                //Add file to existing view file in map if already existing
                                viewsMap.get(correspondingViewModel).add(((PsiFileNode) child).getValue());
                            } else {
                                //Add view to the map if not already existing and nodetype exists
                                CndNodeType nodeType = CndUtil.findNodeType(FilesGrouper.this.project, viewModel.getNodeType());
                                if (nodeType != null) {
                                    ArrayList<PsiFile> viewFiles = new ArrayList<PsiFile>();
                                    viewFiles.add(((PsiFileNode) child).getValue());
                                    viewsMap.put(viewModel, viewFiles);
                                } else {
                                    result.add(child);
                                }
                            }
                        }
                    } else {
                        result.add(child);
                    }
                }

                for (Map.Entry<ViewModel, List<PsiFile>> view : viewsMap.entrySet()) {
                    result.add(new FilesGroupNode(FilesGrouper.this.project, new FilesGroup(view.getKey(), view.getValue()), settings));
                }

                return result;
            }
        });
    }

    @Nullable
    @Override
    public Object getData(Collection<AbstractTreeNode> selected, String dataName) {
        if(selected == null) {
            return null;
        } else {
            if(PlatformDataKeys.DELETE_ELEMENT_PROVIDER.is(dataName)) {
                Iterator selectedElements = selected.iterator();

                while(selectedElements.hasNext()) {
                    AbstractTreeNode selectedElement = (AbstractTreeNode)selectedElements.next();
                    Object node = selectedElement.getValue();
                    if(node instanceof FilesGroup) {
                        return new ResourceBundleDeleteProvider();
                    }
                }
            } else if(FilesGroup.ARRAY_DATA_KEY.is(dataName)) {
                ArrayList selectedElements1 = new ArrayList();
                Iterator selectedElement1 = selected.iterator();

                while(selectedElement1.hasNext()) {
                    AbstractTreeNode node1 = (AbstractTreeNode)selectedElement1.next();
                    Object value = node1.getValue();
                    if(value instanceof FilesGroup) {
                        selectedElements1.add((FilesGroup)value);
                    }
                }

                return selectedElements1.isEmpty()?null:selectedElements1.toArray(new FilesGroup[selectedElements1.size()]);
            }

            return null;
        }
    }
}

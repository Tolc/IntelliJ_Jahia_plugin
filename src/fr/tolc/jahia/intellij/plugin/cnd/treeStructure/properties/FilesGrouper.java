/**
 * Copyright (c) 2016 by Bank Lombard Odier & Co Ltd, Geneva, Switzerland. This software is subject
 * to copyright protection under the laws of Switzerland and other countries. ALL RIGHTS RESERVED.
 */
package fr.tolc.jahia.intellij.plugin.cnd.treeStructure.properties;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.intellij.ide.projectView.TreeStructureProvider;
import com.intellij.ide.projectView.ViewSettings;
import com.intellij.ide.util.treeView.AbstractTreeNode;
import com.intellij.lang.properties.projectView.ResourceBundleDeleteProvider;
import com.intellij.lang.properties.projectView.ResourceBundleGrouper;
import com.intellij.lang.properties.projectView.ResourceBundleNode;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Computable;
import com.intellij.psi.PsiFile;
import com.intellij.psi.jsp.JspFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FilesGrouper implements TreeStructureProvider, DumbAware {
    private static final Logger LOG = Logger.getInstance(ResourceBundleGrouper.class);
    private final Project myProject;

    public FilesGrouper(Project myProject) {
        this.myProject = myProject;
    }

    @NotNull
    @Override
    public Collection<AbstractTreeNode> modify(@NotNull AbstractTreeNode parent, @NotNull Collection<AbstractTreeNode> children, ViewSettings settings) {
        return (parent instanceof FilesGroupNode || parent instanceof ResourceBundleNode) ?children:(Collection) ApplicationManager.getApplication().runReadAction(new Computable() {
            public Collection<AbstractTreeNode> compute() {

                ArrayList result = new ArrayList();

                for (AbstractTreeNode child : children) {
                    if (child.getValue() instanceof JspFile) {
                        result.add(new FilesGroupNode(FilesGrouper.this.myProject, new FilesGroup((PsiFile) child.getValue()), settings));
                    } else {
                        result.add(child);
                    }
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

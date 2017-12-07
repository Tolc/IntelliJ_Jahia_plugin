package fr.tolc.jahia.intellij.plugin.cnd.treeStructure.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.*;

import com.intellij.ide.projectView.PresentationData;
import com.intellij.ide.projectView.ProjectViewNode;
import com.intellij.ide.projectView.ViewSettings;
import com.intellij.ide.projectView.impl.nodes.PsiFileNode;
import com.intellij.ide.util.treeView.AbstractTreeNode;
import com.intellij.ide.util.treeView.ValidateableNode;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import fr.tolc.jahia.intellij.plugin.cnd.icons.CndIcons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ViewNode extends ProjectViewNode<View> implements ValidateableNode, Comparable {

    public ViewNode(Project project, View value, ViewSettings viewSettings) {
        super(project, value, viewSettings);
        this.myName = value.getName();
    }

    @NotNull
    public Collection<AbstractTreeNode> getChildren() {
        List<PsiFile> viewFiles = this.getValue().getViewFiles();
        ArrayList<AbstractTreeNode> children = new ArrayList<AbstractTreeNode>();

        for (PsiFile viewFile : viewFiles) {
            PsiFileNode node = new PsiFileNode(this.myProject, viewFile.getContainingFile(), this.getSettings());
            children.add(node);
        }

        return children;
    }

    @Override
    public String getTestPresentation() {
        return "View:" + getValue().getName();
    }

    public boolean contains(@NotNull VirtualFile file) {
        if(!file.isValid()) {
            return false;
        } else {
            PsiFile psiFile = PsiManager.getInstance(this.getProject()).findFile(file);
            return psiFile != null && this.getValue().getViewFiles().contains(psiFile);
        }
    }

    @Override
    public VirtualFile getVirtualFile() {
        List<PsiFile> list = this.getValue().getViewFiles();
        return !list.isEmpty()? list.get(0).getVirtualFile() : null;
    }
    
    public void update(PresentationData presentation) {
        presentation.setPresentableText(this.getValue().getName());

        Icon icon;
        if (this.getValue().getViewModel().isHidden()) {
            icon = CndIcons.VIEW_BIG_HIDDEN;
        } else {
            icon = CndIcons.VIEW_BIG;
        }
        presentation.setIcon(icon);
    }

    @Override
    public boolean canNavigateToSource() {
        return true;
    }

    @Override
    public boolean canNavigate() {
        return true;
    }

    @Override
    public void navigate(boolean requestFocus) {
        OpenFileDescriptor descriptor = new OpenFileDescriptor(this.getProject(), this.getVirtualFile());
        FileEditorManager.getInstance(this.getProject()).openTextEditor(descriptor, requestFocus);
    }

    @Override
    public boolean isSortByFirstChild() {
        return false;
    }

    @Nullable
    @Override
    public Comparable getSortKey() {
        return this;
    }
    
    @Override
    public int compareTo(@NotNull Object o) {
        if (o instanceof ViewNode) {
            View view1 = this.getValue();
            View view2 = ((ViewNode) o).getValue();
            if ("default".equals(view1.getName())) {
                return -1;
            }
            if ("default".equals(view2.getName())) {
                return 1;
            }
            return view1.getName().compareTo(view2.getName());
        }
        return -1;
    }

    @Override
    public boolean isValid() {
        return this.getValue().getViewFiles().get(0).getContainingFile().isValid();
    }

    @Override
    public String getToolTip() {
//        return IdeBundle.message("tooltip.ui.designer.view");
        return "ViewNode Tooltip";
    }
}

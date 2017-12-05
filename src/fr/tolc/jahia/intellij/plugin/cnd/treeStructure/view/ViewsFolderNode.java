package fr.tolc.jahia.intellij.plugin.cnd.treeStructure.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.intellij.ide.projectView.PresentationData;
import com.intellij.ide.projectView.ProjectViewNode;
import com.intellij.ide.projectView.ViewSettings;
import com.intellij.ide.projectView.impl.nodes.PsiDirectoryNode;
import com.intellij.ide.util.treeView.AbstractTreeNode;
import com.intellij.ide.util.treeView.ValidateableNode;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import fr.tolc.jahia.intellij.plugin.cnd.icons.CndIcons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ViewsFolderNode extends ProjectViewNode<ViewsFolder> implements Comparable, ValidateableNode {

    public ViewsFolderNode(Project project, ViewsFolder value, ViewSettings viewSettings) {
        super(project, value, viewSettings);
        this.myName = value.getName();
    }

    @NotNull
    public Collection<AbstractTreeNode> getChildren() {
        List<PsiDirectory> nodeTypeFolders = this.getValue().getNodeTypeFolders();
        ArrayList<AbstractTreeNode> children = new ArrayList<AbstractTreeNode>();

        for (PsiDirectory directory : nodeTypeFolders) {
            PsiDirectoryNode node = new PsiDirectoryNode(this.myProject, directory, this.getSettings());
            children.add(node);
        }

        return children;
    }

    @Override
    public String getTestPresentation() {
        return getValue().getName();
    }

    public boolean contains(@NotNull VirtualFile file) {
        if(!file.isValid()) {
            return false;
        } else {
            for (PsiDirectory psiDirectory : this.getValue().getNodeTypeFolders()) {
                if (file.getCanonicalPath().contains(psiDirectory.getVirtualFile().getCanonicalPath())) {
                    return true;
                }
            }
            return false;
        }
    }

    @Override
    public VirtualFile getVirtualFile() {
        return this.getValue().getJahiaWorkFolder().getVirtualFile();
    }
    
    public void update(PresentationData presentation) {
        presentation.setPresentableText(this.getValue().getName());
        presentation.setIcon(CndIcons.JAHIA_LOGO_DXM);
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
        return true;
    }

    @Nullable
    @Override
    public Comparable getSortKey() {
        return this;
    }

    @Override
    public int compareTo(@NotNull Object o) {
        if (o instanceof ViewsFolderNode) {
            return 0;
        }
        return -1;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public String getToolTip() {
        return "ViewsFolderNode Tooltip";
    }
}

package fr.tolc.jahia.intellij.plugin.cnd.treeStructure.properties;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import com.intellij.ide.projectView.PresentationData;
import com.intellij.ide.projectView.ProjectViewNode;
import com.intellij.ide.projectView.ViewSettings;
import com.intellij.ide.projectView.impl.nodes.PsiFileNode;
import com.intellij.ide.projectView.impl.nodes.PsiFileNode.ExtensionSortKey;
import com.intellij.ide.util.treeView.AbstractTreeNode;
import com.intellij.ide.util.treeView.ValidateableNode;
import com.intellij.lang.properties.PropertiesBundle;
import com.intellij.lang.properties.PropertiesImplUtil;
import com.intellij.lang.properties.psi.PropertiesFile;
import com.intellij.openapi.fileTypes.StdFileTypes;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import fr.tolc.jahia.intellij.plugin.cnd.icons.CndIcons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FilesGroupNode extends ProjectViewNode<FilesGroup> implements ValidateableNode {
    public FilesGroupNode(Project project, FilesGroup resourceBundle, ViewSettings settings) {
        super(project, resourceBundle, settings);
    }

    @NotNull
    public Collection<AbstractTreeNode> getChildren() {
        List propertiesFiles = ((FilesGroup)this.getValue()).getFiles();
        ArrayList children = new ArrayList();
        Iterator var3 = propertiesFiles.iterator();

        while(var3.hasNext()) {
            PsiFile propertiesFile = (PsiFile)var3.next();
            PsiFileNode node = new PsiFileNode(this.myProject, propertiesFile.getContainingFile(), this.getSettings());
            children.add(node);
        }

        return children;
    }

    public boolean contains(@NotNull VirtualFile file) {
        if(!file.isValid()) {
            return false;
        } else {
            PsiFile psiFile = PsiManager.getInstance(this.getProject()).findFile(file);
            PropertiesFile propertiesFile = PropertiesImplUtil.getPropertiesFile(psiFile);
            return propertiesFile != null && ((FilesGroup)this.getValue()).getFiles().contains(propertiesFile);
        }
    }

    public VirtualFile getVirtualFile() {
        List list = ((FilesGroup)this.getValue()).getFiles();
        return !list.isEmpty()?((PsiFile)list.get(0)).getVirtualFile():null;
    }

    public void update(PresentationData presentation) {
        presentation.setIcon(CndIcons.VIEW_BIG);
        presentation.setPresentableText(PropertiesBundle.message("project.view.resource.bundle.tree.node.text", new Object[]{((FilesGroup)this.getValue()).getBaseName()}) + "lolilol");
    }

    public boolean canNavigateToSource() {
        return true;
    }

    public boolean canNavigate() {
        return true;
    }

//    public void navigate(boolean requestFocus) {
//        OpenFileDescriptor descriptor = new OpenFileDescriptor(this.getProject(), new ResourceBundleAsVirtualFile((PropertiesGroup)this.getValue()));
//        FileEditorManager.getInstance(this.getProject()).openTextEditor(descriptor, requestFocus);
//    }

    public boolean isSortByFirstChild() {
        return true;
    }

    public Comparable getTypeSortKey() {
        return new ExtensionSortKey(StdFileTypes.PROPERTIES.getDefaultExtension());
    }

//    public boolean validate() {
//        if(!super.validate()) {
//            return false;
//        } else {
//            ResourceBundle newBundle = ((PropertiesGroup)this.getValue()).getDefaultPropertiesFile().getResourceBundle();
//            PropertiesGroup currentBundle = (PropertiesGroup)this.getValue();
//            return !Comparing.equal(newBundle, currentBundle)?false:!(currentBundle instanceof ResourceBundleImpl) || ((ResourceBundleImpl)currentBundle).isValid();
//        }
//    }

    public boolean isValid() {
        return ((FilesGroup)this.getValue()).getDefaultPropertiesFile().getContainingFile().isValid();
    }


    @Nullable
    private static PropertiesFile extractPropertiesFileFromNode(TreeNode node) {
        if(!(node instanceof DefaultMutableTreeNode)) {
            return null;
        } else {
            Object userObject = ((DefaultMutableTreeNode)node).getUserObject();
            if(!(userObject instanceof PsiFileNode)) {
                return null;
            } else {
                PsiFile file = (PsiFile)((PsiFileNode)userObject).getValue();
                PropertiesFile propertiesFile = PropertiesImplUtil.getPropertiesFile(file);
                return propertiesFile != null && file.getManager().isInProject(file) && file.isValid()?propertiesFile:null;
            }
        }
    }
}

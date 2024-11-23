package fr.tolc.jahia.plugin.projectView;

import com.intellij.ide.projectView.PresentationData;
import com.intellij.ide.projectView.ProjectViewNode;
import com.intellij.ide.projectView.ViewSettings;
import com.intellij.ide.projectView.impl.nodes.PsiDirectoryNode;
import com.intellij.ide.util.treeView.AbstractTreeNode;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import fr.tolc.jahia.language.cnd.CndIcons;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class NamespaceFolderNode extends ProjectViewNode<NamespaceFolder> {

    private final NamespaceFolder namespaceFolder;

    protected NamespaceFolderNode(Project project, @NotNull NamespaceFolder namespaceFolder, ViewSettings viewSettings) {
        super(project, namespaceFolder, viewSettings);
        this.myName = namespaceFolder.getName();
        this.namespaceFolder = namespaceFolder;
    }

    @Override
    public boolean contains(@NotNull VirtualFile file) {
        if (file.isValid()) {
            for (PsiDirectory psiDirectory : namespaceFolder.getNodetypeFolders()) {
                if (file.getPath().contains(psiDirectory.getVirtualFile().getPath())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public @NotNull Collection<? extends AbstractTreeNode<?>> getChildren() {
        ArrayList<AbstractTreeNode<?>> children = new ArrayList<>();

        List<PsiDirectory> nodeTypeFolders = namespaceFolder.getNodetypeFolders();
        for (PsiDirectory directory : nodeTypeFolders) {
            PsiDirectoryNode node = new PsiDirectoryNode(myProject, directory, this.getSettings());
            children.add(node);
        }

        return children;
    }

    @Override
    protected void update(@NotNull PresentationData presentation) {
        presentation.setPresentableText(namespaceFolder.getName());
        presentation.setIcon(CndIcons.CND_NS);
    }
}

package fr.tolc.jahia.plugin.projectView;

import com.intellij.ide.projectView.PresentationData;
import com.intellij.ide.projectView.ProjectViewNode;
import com.intellij.ide.projectView.ViewSettings;
import com.intellij.ide.util.treeView.AbstractTreeNode;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import fr.tolc.jahia.language.cnd.CndIcons;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class ViewsFolderNode extends ProjectViewNode<ViewsFolder> {

    private final ViewsFolder viewsFolder;

    protected ViewsFolderNode(Project project, @NotNull ViewsFolder viewsFolder, ViewSettings viewSettings) {
        super(project, viewsFolder, viewSettings);
        this.myName = viewsFolder.getName();
        this.viewsFolder = viewsFolder;
    }

    @Override
    public boolean contains(@NotNull VirtualFile file) {
        if (file.isValid()) {
            for (NamespaceFolderNode nsFolder : viewsFolder.getNamespaceFolders()) {
                if (nsFolder.contains(file)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public @NotNull Collection<? extends AbstractTreeNode<?>> getChildren() {
        return viewsFolder.getNamespaceFolders();
    }

    @Override
    protected void update(@NotNull PresentationData presentation) {
        presentation.setPresentableText(viewsFolder.getName());
        presentation.setIcon(CndIcons.JAHIA_DXM);
    }
}

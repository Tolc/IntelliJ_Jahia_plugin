package fr.tolc.jahia.intellij.plugin.cnd.treeStructure.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.intellij.openapi.actionSystem.DataKey;
import com.intellij.psi.PsiDirectory;
import org.jetbrains.annotations.NotNull;

public class ViewsFolder {
    public static final DataKey<ViewsFolder[]> DATA_KEY = DataKey.create("cnd.viewsFolder.array");

    private final PsiDirectory jahiaWorkFolder;
    private final List<PsiDirectory> nodeTypeFolders;

    public ViewsFolder(@NotNull PsiDirectory jahiaWorkFolder, @NotNull Collection<PsiDirectory> nodeTypeFolders) {
        this.jahiaWorkFolder = jahiaWorkFolder;
        this.nodeTypeFolders = new ArrayList<>(nodeTypeFolders);
    }

    @NotNull
    public String getName() {
        return "views";
    }

    public PsiDirectory getJahiaWorkFolder() {
        return jahiaWorkFolder;
    }

    @NotNull
    public List<PsiDirectory> getNodeTypeFolders() {
        return nodeTypeFolders;
    }

    @Override
    public boolean equals(Object object) {
        if(this == object) {
            return true;
        } else if (object instanceof ViewsFolder) {
            ViewsFolder viewsFolder = (ViewsFolder) object;
            return jahiaWorkFolder.equals(viewsFolder.getJahiaWorkFolder());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return jahiaWorkFolder.hashCode();
    }

    @Override
    public String toString() {
        return "Views virtual folder [" + jahiaWorkFolder.getVirtualFile().getCanonicalPath() + "/views]";
    }
}

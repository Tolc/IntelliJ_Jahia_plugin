package fr.tolc.jahia.plugin.projectView;

import com.intellij.openapi.actionSystem.DataKey;
import com.intellij.psi.PsiDirectory;
import fr.tolc.jahia.plugin.messages.CndBundle;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ViewsFolder {
    public static final DataKey<ViewsFolder[]> DATA_KEY = DataKey.create("cnd.viewsFolder.array");

    private final PsiDirectory jahiaWorkFolder;
    private final List<PsiDirectory> nodetypeFolders;

    public ViewsFolder(@NotNull PsiDirectory jahiaWorkFolder, @NotNull Collection<PsiDirectory> nodetypeFolders) {
        this.jahiaWorkFolder = jahiaWorkFolder;
        this.nodetypeFolders = new ArrayList<>(nodetypeFolders);
    }

    @NotNull
    public String getName() {
        return CndBundle.message("jahia.views.folder");
    }

    public PsiDirectory getJahiaWorkFolder() {
        return jahiaWorkFolder;
    }

    @NotNull
    public List<PsiDirectory> getNodetypeFolders() {
        return nodetypeFolders;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        } else if (object instanceof ViewsFolder viewsFolder) {
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

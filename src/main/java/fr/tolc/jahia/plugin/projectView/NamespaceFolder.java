package fr.tolc.jahia.plugin.projectView;

import com.intellij.openapi.actionSystem.DataKey;
import com.intellij.psi.PsiDirectory;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class NamespaceFolder {
    public static final DataKey<NamespaceFolder[]> DATA_KEY = DataKey.create("cnd.viewsFolder.namespacesFolders.array");

    private final PsiDirectory jahiaWorkFolder;
    private final String namespace;
    private final List<PsiDirectory> nodetypeFolders;

    public NamespaceFolder(@NotNull PsiDirectory jahiaWorkFolder, @NotNull String namespace, @NotNull Collection<PsiDirectory> nodetypeFolders) {
        this.jahiaWorkFolder = jahiaWorkFolder;
        this.namespace = namespace;
        this.nodetypeFolders = new ArrayList<>(nodetypeFolders);
    }

    @NotNull
    public String getName() {
        return namespace;
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
        } else if (object instanceof NamespaceFolder namespaceFolder) {
            return jahiaWorkFolder.equals(namespaceFolder.getJahiaWorkFolder()) && namespace.equals(namespaceFolder.namespace);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return jahiaWorkFolder.hashCode();
    }

    @Override
    public String toString() {
        return "Namespace virtual folder [" + namespace + "]";
    }
}

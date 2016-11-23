package fr.tolc.jahia.intellij.plugin.cnd.treeStructure.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.intellij.openapi.actionSystem.DataKey;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import fr.tolc.jahia.intellij.plugin.cnd.model.ViewModel;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

public class View {
    public static final DataKey<View[]> DATA_KEY = DataKey.create("cnd.view.array");

    private final List<PsiFile> viewFiles;
    private final ViewModel viewModel;

    public View(@NotNull ViewModel viewModel, @NotNull Collection<PsiFile> viewFiles) {
        this.viewModel = viewModel;
        this.viewFiles = new ArrayList<>(viewFiles);
        Collections.sort(this.viewFiles, (f1, f2) -> {
            return f1.getName().compareTo(f2.getName());
        });
    }

    @NotNull
    public String getName() {
        String name ="default";
        if (StringUtils.isNotBlank(viewModel.getName())) {
            name = viewModel.getName();
        }
        return name;
    }

    @NotNull
    public ViewModel getViewModel() {
        return viewModel;
    }

    @NotNull
    public List<PsiFile> getViewFiles() {
        return viewFiles;
    }

    public boolean containsFile(final VirtualFile vFile) {
        for (PsiFile psiFile : viewFiles) {
            final VirtualFile virtualFile = psiFile.getVirtualFile();
            if (virtualFile != null && virtualFile.equals(vFile)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof View) {
            View view = (View) object;
            return viewFiles.equals(view.viewFiles) && viewModel.equals(view.viewModel);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return viewFiles.hashCode() ^ viewModel.hashCode();
    }
    
    @Override
    public String toString() {
        return viewModel.getNodeType().getNamespace() + ":" + viewModel.getNodeType().getNodeTypeName() + "/" + viewModel.getType() + "." + viewModel.getName() + "." + viewModel.getLanguage();
    }
}

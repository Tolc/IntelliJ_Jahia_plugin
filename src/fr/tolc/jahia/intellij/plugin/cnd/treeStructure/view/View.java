package fr.tolc.jahia.intellij.plugin.cnd.treeStructure.view;

import java.util.Collection;
import java.util.HashSet;

import com.intellij.openapi.actionSystem.DataKey;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiUtilCore;
import fr.tolc.jahia.intellij.plugin.cnd.model.ViewModel;
import org.apache.commons.lang.StringUtils;

//public class View implements Navigatable {
public class View {
    public static final DataKey<View[]> DATA_KEY = DataKey.create("cnd.view.array");

    private final Collection<PsiFile> viewFiles;
    private final ViewModel viewModel;

//    public View(ViewModel viewModel) {
//        this.viewModel = viewModel;
//        viewFiles = CndProjectFilesUtil.findViewFiles(file, viewModel);
//    }

    public View(ViewModel viewModel, Collection<PsiFile> viewFiles) {
        this.viewModel = viewModel;
        this.viewFiles = new HashSet<>(viewFiles);
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

    public String getName() {
        String name ="default";
        if (StringUtils.isNotBlank(viewModel.getName())) {
            name = viewModel.getName();
        }
        return name;
    }

    public ViewModel getViewModel() {
        return viewModel;
    }

    public PsiFile[] getViewFiles() {
        return PsiUtilCore.toPsiFileArray(viewFiles);
    }

//    public void navigate(boolean requestFocus) {
//        for (PsiFile psiFile : viewFiles) {
//            if (psiFile != null && psiFile.canNavigate()) {
//                psiFile.navigate(requestFocus);
//            }
//        }
//    }
//
//    public boolean canNavigateToSource() {
//        for (PsiFile psiFile : viewFiles) {
//            if (psiFile != null && psiFile.canNavigateToSource()) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    public boolean canNavigate() {
//        for (PsiFile psiFile : viewFiles) {
//            if (psiFile != null && psiFile.canNavigate()) {
//                return true;
//            }
//        }
//        return false;
//    }

    public boolean isValid() {
        if (viewFiles.isEmpty()) {
            return false;
        }
        for (PsiFile psiFile : viewFiles) {
            if (!psiFile.isValid()) {
                return false;
            }
        }
        return viewModel.getName() != null;
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
    public String toString() {
        return viewModel.getNodeType().getNamespace() + ":" + viewModel.getNodeType().getNodeTypeName() + "/" + viewModel.getType() + "." + viewModel.getName() + "." + viewModel.getLanguage();
    }
}

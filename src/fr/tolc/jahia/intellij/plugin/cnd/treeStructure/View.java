package fr.tolc.jahia.intellij.plugin.cnd.treeStructure;

import java.util.Collection;
import java.util.HashSet;

import com.intellij.openapi.actionSystem.DataKey;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.pom.Navigatable;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiUtilCore;
import com.intellij.uiDesigner.binding.FormClassIndex;

public class View implements Navigatable {
  public static final DataKey<View[]> DATA_KEY = DataKey.create("view.array");
  
  private final Collection<PsiFile> myViewFiles;
  private final PsiClass myClassToBind;

  public View(PsiClass classToBind) {
    myClassToBind = classToBind;
    myViewFiles = ViewClassIndex.findViewsBoundToClass(classToBind.getProject(), classToBind);
  }

  public View(PsiClass classToBind, Collection<PsiFile> viewFiles) {
    myClassToBind = classToBind;
    myViewFiles = new HashSet<>(viewFiles);
  }

  @Override
  public boolean equals(Object object) {
    if (object instanceof View){
      View view = (View)object;
      return myViewFiles.equals(view.myViewFiles) && myClassToBind.equals(view.myClassToBind);
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    return myViewFiles.hashCode() ^ myClassToBind.hashCode();
  }

  public String getName() {
    return myClassToBind.getName();
  }

  public PsiClass getClassToBind() {
    return myClassToBind;
  }

  public PsiFile[] getViewFiles() {
    return PsiUtilCore.toPsiFileArray(myViewFiles);
  }

  public void navigate(boolean requestFocus) {
    for (PsiFile psiFile : myViewFiles) {
      if (psiFile != null && psiFile.canNavigate()) {
        psiFile.navigate(requestFocus);
      }
    }
  }

  public boolean canNavigateToSource() {
    for (PsiFile psiFile : myViewFiles) {
      if (psiFile != null && psiFile.canNavigateToSource()) {
        return true;
      }
    }
    return false;
  }

  public boolean canNavigate() {
    for (PsiFile psiFile : myViewFiles) {
      if (psiFile != null && psiFile.canNavigate()) {
        return true;
      }
    }
    return false;
  }

  public boolean isValid() {
    if (myViewFiles.isEmpty()) {
      return false;
    }
    for (PsiFile psiFile : myViewFiles) {
      if (!psiFile.isValid()) {
        return false;
      }
    }
    return myClassToBind.isValid();
  }

  public boolean containsFile(final VirtualFile vFile) {
    final PsiFile classFile = myClassToBind.getContainingFile();
    final VirtualFile classVFile = (classFile == null )? null : classFile.getVirtualFile();
    if (classVFile != null && classVFile.equals(vFile)) {
      return true;
    }
    for (PsiFile psiFile : myViewFiles) {
      final VirtualFile virtualFile = psiFile.getVirtualFile();
      if (virtualFile != null && virtualFile.equals(vFile)) {
        return true;
      }
    }
    return false;
  }
}

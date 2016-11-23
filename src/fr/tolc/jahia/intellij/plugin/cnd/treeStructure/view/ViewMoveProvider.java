package fr.tolc.jahia.intellij.plugin.cnd.treeStructure.view;

import java.util.Set;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.refactoring.move.MoveHandlerDelegate;
import com.intellij.refactoring.move.moveFilesOrDirectories.MoveFilesOrDirectoriesHandler;
import org.jetbrains.annotations.Nullable;

/**
 * @author yole
 */
public class ViewMoveProvider extends MoveHandlerDelegate {
  private static final Logger LOG = Logger.getInstance(ViewMoveProvider.class);

  @Override
  public boolean canMove(DataContext dataContext) {
    View[] views = View.DATA_KEY.getData(dataContext);
    return views != null && views.length > 0;
  }

  @Override
  public boolean isValidTarget(PsiElement psiElement, PsiElement[] sources) {
    return MoveFilesOrDirectoriesHandler.isValidTarget(psiElement);
  }

  public boolean canMove(PsiElement[] elements, @Nullable final PsiElement targetContainer) {
    return false;
  }

  @Override
  public void collectFilesOrDirsFromContext(DataContext dataContext, Set<PsiElement> filesOrDirs) {
    View[] views = View.DATA_KEY.getData(dataContext);
    LOG.assertTrue(views != null);
//    PsiClass[] classesToMove = new PsiClass[views.length];
    PsiFile[] filesToMove = new PsiFile[views.length];
    for(int i=0; i<views.length; i++) {
//      classesToMove [i] = views [i].getClassToBind();
//      if (classesToMove[i] != null) {
//        final PsiFile containingFile = classesToMove[i].getContainingFile();
//        if (containingFile != null) {
//          filesOrDirs.add(containingFile);
//        }
//      }
      filesToMove [i] = views [i].getViewFiles() [0];
      if (filesToMove[i] != null) {
        filesOrDirs.add(filesToMove[i]);
      }
    }
  }


//  @Override
//  public boolean isMoveRedundant(PsiElement source, PsiElement target) {
////    if (source instanceof PsiFile && source.getParent() == target) {
////      final VirtualFile virtualFile = ((PsiFile)source).getVirtualFile();
////      if (virtualFile != null && virtualFile.getFileType() instanceof GuiViewFileType) {
////        return true;
////      }
////    }
//    return super.isMoveRedundant(source, target);
//  }
}

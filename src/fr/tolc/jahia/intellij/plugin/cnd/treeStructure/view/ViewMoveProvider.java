package fr.tolc.jahia.intellij.plugin.cnd.treeStructure.view;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.PsiElement;
import com.intellij.refactoring.move.MoveHandlerDelegate;
import com.intellij.refactoring.move.moveFilesOrDirectories.MoveFilesOrDirectoriesHandler;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public class ViewMoveProvider extends MoveHandlerDelegate {
  private static final Logger logger = Logger.getInstance(ViewMoveProvider.class);

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
    logger.assertTrue(views != null);
    for (View view : views) {
      filesOrDirs.addAll(view.getViewFiles());
    }
  }

//  @Override
//  public boolean isMoveRedundant(PsiElement source, PsiElement target) {
//    if (source instanceof PsiFile && source.getParent() == target) {
//      final VirtualFile virtualFile = ((PsiFile)source).getVirtualFile();
//      if (virtualFile != null && virtualFile.getFileType() instanceof GuiViewFileType) {
//        return true;
//      }
//    }
//    return super.isMoveRedundant(source, target);
//  }
}

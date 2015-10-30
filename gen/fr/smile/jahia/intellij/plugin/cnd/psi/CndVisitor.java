// This is a generated file. Not intended for manual editing.
package fr.smile.jahia.intellij.plugin.cnd.psi;

import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiElement;

public class CndVisitor extends PsiElementVisitor {

  public void visitExtend(@NotNull CndExtend o) {
    visitPsiElement(o);
  }

  public void visitInheritance(@NotNull CndInheritance o) {
    visitPsiElement(o);
  }

  public void visitInheritances(@NotNull CndInheritances o) {
    visitPsiElement(o);
  }

  public void visitNamespace(@NotNull CndNamespace o) {
    visitNamespaceElement(o);
  }

  public void visitNodeType(@NotNull CndNodeType o) {
    visitNodeTypeElement(o);
  }

  public void visitProperties(@NotNull CndProperties o) {
    visitPsiElement(o);
  }

  public void visitPropertyMinus(@NotNull CndPropertyMinus o) {
    visitPsiElement(o);
  }

  public void visitPropertyPlus(@NotNull CndPropertyPlus o) {
    visitPsiElement(o);
  }

  public void visitNamespaceElement(@NotNull CndNamespaceElement o) {
    visitPsiElement(o);
  }

  public void visitNodeTypeElement(@NotNull CndNodeTypeElement o) {
    visitPsiElement(o);
  }

  public void visitPsiElement(@NotNull PsiElement o) {
    visitElement(o);
  }

}

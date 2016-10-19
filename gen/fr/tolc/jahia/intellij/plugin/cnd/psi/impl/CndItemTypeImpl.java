// This is a generated file. Not intended for manual editing.
package fr.tolc.jahia.intellij.plugin.cnd.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndItemType;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndVisitor;
import org.jetbrains.annotations.NotNull;

public class CndItemTypeImpl extends ASTWrapperPsiElement implements CndItemType {

  public CndItemTypeImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull CndVisitor visitor) {
    visitor.visitItemType(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof CndVisitor) accept((CndVisitor)visitor);
    else super.accept(visitor);
  }

}

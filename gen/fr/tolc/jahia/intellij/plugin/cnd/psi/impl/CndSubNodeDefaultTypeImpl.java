// This is a generated file. Not intended for manual editing.
package fr.tolc.jahia.intellij.plugin.cnd.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static fr.tolc.jahia.intellij.plugin.cnd.psi.CndTypes.*;
import fr.tolc.jahia.intellij.plugin.cnd.psi.elements.impl.CndSubNodeDefaultTypeElementImpl;
import fr.tolc.jahia.intellij.plugin.cnd.psi.*;
import com.intellij.psi.PsiReference;

public class CndSubNodeDefaultTypeImpl extends CndSubNodeDefaultTypeElementImpl implements CndSubNodeDefaultType {

  public CndSubNodeDefaultTypeImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull CndVisitor visitor) {
    visitor.visitSubNodeDefaultType(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof CndVisitor) accept((CndVisitor)visitor);
    else super.accept(visitor);
  }

  @NotNull
  public PsiReference[] getReferences() {
    return CndPsiImplUtil.getReferences(this);
  }

}

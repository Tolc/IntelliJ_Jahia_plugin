// This is a generated file. Not intended for manual editing.
package fr.tolc.jahia.language.cnd.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static fr.tolc.jahia.language.cnd.psi.CndTypes.*;
import fr.tolc.jahia.language.cnd.psi.mixins.CndNodetypeMixin;
import fr.tolc.jahia.language.cnd.psi.*;

public class CndNodetypeImpl extends CndNodetypeMixin implements CndNodetype {

  public CndNodetypeImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull CndVisitor visitor) {
    visitor.visitNodetype(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof CndVisitor) accept((CndVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<CndProperty> getPropertyList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, CndProperty.class);
  }

  @Override
  @NotNull
  public List<CndSubnode> getSubnodeList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, CndSubnode.class);
  }

}

// This is a generated file. Not intended for manual editing.
package fr.tolc.jahia.intellij.plugin.cnd.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static fr.tolc.jahia.intellij.plugin.cnd.psi.CndTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import fr.tolc.jahia.intellij.plugin.cnd.psi.*;

public class CndPropertyImpl extends ASTWrapperPsiElement implements CndProperty {

  public CndPropertyImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof CndVisitor) ((CndVisitor)visitor).visitProperty(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public CndPropertyAttributes getPropertyAttributes() {
    return findChildByClass(CndPropertyAttributes.class);
  }

  @Override
  @Nullable
  public CndPropertyConstraint getPropertyConstraint() {
    return findChildByClass(CndPropertyConstraint.class);
  }

  @Override
  @Nullable
  public CndPropertyDefault getPropertyDefault() {
    return findChildByClass(CndPropertyDefault.class);
  }

}

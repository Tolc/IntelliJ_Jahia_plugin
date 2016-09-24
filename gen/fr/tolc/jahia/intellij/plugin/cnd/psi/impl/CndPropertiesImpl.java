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

public class CndPropertiesImpl extends ASTWrapperPsiElement implements CndProperties {

  public CndPropertiesImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof CndVisitor) ((CndVisitor)visitor).visitProperties(this);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<CndPropertyMinus> getPropertyMinusList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, CndPropertyMinus.class);
  }

  @Override
  @NotNull
  public List<CndPropertyPlus> getPropertyPlusList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, CndPropertyPlus.class);
  }

}

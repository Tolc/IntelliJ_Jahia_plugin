// This is a generated file. Not intended for manual editing.
package fr.tolc.jahia.intellij.plugin.cnd.psi.impl;

import java.util.List;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndExtend;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndExtendNodeType;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndVisitor;
import org.jetbrains.annotations.NotNull;

public class CndExtendImpl extends ASTWrapperPsiElement implements CndExtend {

  public CndExtendImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull CndVisitor visitor) {
    visitor.visitExtend(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof CndVisitor) accept((CndVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<CndExtendNodeType> getExtendNodeTypeList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, CndExtendNodeType.class);
  }

}

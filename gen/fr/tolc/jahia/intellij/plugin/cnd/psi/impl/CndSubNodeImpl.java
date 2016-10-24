// This is a generated file. Not intended for manual editing.
package fr.tolc.jahia.intellij.plugin.cnd.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static fr.tolc.jahia.intellij.plugin.cnd.psi.CndTypes.*;
import fr.tolc.jahia.intellij.plugin.cnd.psi.elements.impl.CndSubNodeElementImpl;
import fr.tolc.jahia.intellij.plugin.cnd.psi.*;

public class CndSubNodeImpl extends CndSubNodeElementImpl implements CndSubNode {

  public CndSubNodeImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull CndVisitor visitor) {
    visitor.visitSubNode(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof CndVisitor) accept((CndVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public CndSubNodeAttributes getSubNodeAttributes() {
    return findChildByClass(CndSubNodeAttributes.class);
  }

  @Override
  @Nullable
  public CndSubNodeDefaultType getSubNodeDefaultType() {
    return findChildByClass(CndSubNodeDefaultType.class);
  }

  @Override
  @NotNull
  public List<CndSubNodeType> getSubNodeTypeList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, CndSubNodeType.class);
  }

  public String getSubNodeName() {
    return CndPsiImplUtil.getSubNodeName(this);
  }

}

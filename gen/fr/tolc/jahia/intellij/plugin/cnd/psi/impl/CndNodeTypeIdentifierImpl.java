// This is a generated file. Not intended for manual editing.
package fr.tolc.jahia.intellij.plugin.cnd.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static fr.tolc.jahia.intellij.plugin.cnd.psi.CndTypes.*;
import fr.tolc.jahia.intellij.plugin.cnd.psi.identifiers.impl.CndNodeTypeIdentifierElementImpl;
import fr.tolc.jahia.intellij.plugin.cnd.psi.*;
import com.intellij.navigation.ItemPresentation;

public class CndNodeTypeIdentifierImpl extends CndNodeTypeIdentifierElementImpl implements CndNodeTypeIdentifier {

  public CndNodeTypeIdentifierImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull CndVisitor visitor) {
    visitor.visitNodeTypeIdentifier(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof CndVisitor) accept((CndVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public String getNodeTypeName() {
    return CndPsiImplUtil.getNodeTypeName(this);
  }

  @Override
  public PsiElement setNodeTypeName(String newName) {
    return CndPsiImplUtil.setNodeTypeName(this, newName);
  }

  @Override
  @Nullable
  public PsiElement getNameIdentifier() {
    return CndPsiImplUtil.getNameIdentifier(this);
  }

  @Override
  public ItemPresentation getPresentation() {
    return CndPsiImplUtil.getPresentation(this);
  }

  @Override
  public String getName() {
    return CndPsiImplUtil.getName(this);
  }

  @Override
  public PsiElement setName(String newName) {
    return CndPsiImplUtil.setName(this, newName);
  }

  @Override
  public CndNodeType getNodeType() {
    return CndPsiImplUtil.getNodeType(this);
  }

}

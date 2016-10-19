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

  public CndNodeTypeIdentifierImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull CndVisitor visitor) {
    visitor.visitNodeTypeIdentifier(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof CndVisitor) accept((CndVisitor)visitor);
    else super.accept(visitor);
  }

  public String getNodeTypeName() {
    return CndPsiImplUtil.getNodeTypeName(this);
  }

  public PsiElement setNodeTypeName(String newName) {
    return CndPsiImplUtil.setNodeTypeName(this, newName);
  }

  public PsiElement getNameIdentifier() {
    return CndPsiImplUtil.getNameIdentifier(this);
  }

  public ItemPresentation getPresentation() {
    return CndPsiImplUtil.getPresentation(this);
  }

  public String getName() {
    return CndPsiImplUtil.getName(this);
  }

  public PsiElement setName(String newName) {
    return CndPsiImplUtil.setName(this, newName);
  }

  public CndNodeType getNodeType() {
    return CndPsiImplUtil.getNodeType(this);
  }

}

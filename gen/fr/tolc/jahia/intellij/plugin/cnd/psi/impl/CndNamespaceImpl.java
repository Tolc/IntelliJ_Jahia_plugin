// This is a generated file. Not intended for manual editing.
package fr.tolc.jahia.intellij.plugin.cnd.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static fr.tolc.jahia.intellij.plugin.cnd.psi.CndTypes.*;
import fr.tolc.jahia.intellij.plugin.cnd.psi.elements.impl.CndNamespaceElementImpl;
import fr.tolc.jahia.intellij.plugin.cnd.psi.*;
import com.intellij.navigation.ItemPresentation;

public class CndNamespaceImpl extends CndNamespaceElementImpl implements CndNamespace {

  public CndNamespaceImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull CndVisitor visitor) {
    visitor.visitNamespace(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof CndVisitor) accept((CndVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public CndNamespaceIdentifier getNamespaceIdentifier() {
    return findNotNullChildByClass(CndNamespaceIdentifier.class);
  }

  public String getNamespaceName() {
    return CndPsiImplUtil.getNamespaceName(this);
  }

  public PsiElement setNamespaceName(String newName) {
    return CndPsiImplUtil.setNamespaceName(this, newName);
  }

  public String getNamespaceURI() {
    return CndPsiImplUtil.getNamespaceURI(this);
  }

  public ItemPresentation getPresentation() {
    return CndPsiImplUtil.getPresentation(this);
  }

}

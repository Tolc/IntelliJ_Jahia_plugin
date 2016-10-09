// This is a generated file. Not intended for manual editing.
package fr.tolc.jahia.intellij.plugin.cnd.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static fr.tolc.jahia.intellij.plugin.cnd.psi.CndTypes.*;
import fr.tolc.jahia.intellij.plugin.cnd.psi.*;
import com.intellij.navigation.ItemPresentation;

public class CndNamespaceImpl extends CndNamespaceElementImpl implements CndNamespace {

  public CndNamespaceImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof CndVisitor) ((CndVisitor)visitor).visitNamespace(this);
    else super.accept(visitor);
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

}

// This is a generated file. Not intended for manual editing.
package fr.tolc.jahia.intellij.plugin.cnd.psi.impl;

import java.util.List;

import fr.tolc.jahia.intellij.plugin.cnd.psi.CndVisitor;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static fr.tolc.jahia.intellij.plugin.cnd.psi.CndTypes.*;
import fr.tolc.jahia.intellij.plugin.cnd.psi.*;

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

  public String getNamespaceURI() {
    return CndPsiImplUtil.getNamespaceURI(this);
  }

}

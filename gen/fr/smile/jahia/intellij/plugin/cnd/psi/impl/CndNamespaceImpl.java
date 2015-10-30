// This is a generated file. Not intended for manual editing.
package fr.smile.jahia.intellij.plugin.cnd.psi.impl;

import java.util.List;

import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static fr.smile.jahia.intellij.plugin.cnd.psi.CndTypes.*;
import fr.smile.jahia.intellij.plugin.cnd.psi.*;

public class CndNamespaceImpl extends CndNamespaceElementImpl implements CndNamespace {

  public CndNamespaceImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof CndVisitor) ((CndVisitor)visitor).visitNamespace(this);
    else super.accept(visitor);
  }

    @Nullable
    @Override
    public PsiElement getNameIdentifier() {
        return null;
    }

    @Override
    public PsiElement setName(@NonNls @NotNull String name) throws IncorrectOperationException {
        return null;
    }
}

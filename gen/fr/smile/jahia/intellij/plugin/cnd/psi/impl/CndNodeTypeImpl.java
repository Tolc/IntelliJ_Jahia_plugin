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

public class CndNodeTypeImpl extends CndNodeTypeElementImpl implements CndNodeType {

  public CndNodeTypeImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof CndVisitor) ((CndVisitor)visitor).visitNodeType(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public CndExtend getExtend() {
    return findChildByClass(CndExtend.class);
  }

  @Override
  @Nullable
  public CndInheritances getInheritances() {
    return findChildByClass(CndInheritances.class);
  }

  @Override
  @Nullable
  public CndProperties getProperties() {
    return findChildByClass(CndProperties.class);
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

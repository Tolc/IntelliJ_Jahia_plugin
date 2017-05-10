// This is a generated file. Not intended for manual editing.
package fr.tolc.jahia.intellij.plugin.cnd.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static fr.tolc.jahia.intellij.plugin.cnd.psi.CndTypes.*;
import fr.tolc.jahia.intellij.plugin.cnd.psi.elements.impl.CndPropertyElementImpl;
import fr.tolc.jahia.intellij.plugin.cnd.psi.*;
import com.intellij.navigation.ItemPresentation;
import fr.tolc.jahia.intellij.plugin.cnd.enums.AttributeEnum;
import fr.tolc.jahia.intellij.plugin.cnd.enums.PropertyTypeEnum;
import fr.tolc.jahia.intellij.plugin.cnd.enums.PropertyTypeMaskEnum;

public class CndPropertyImpl extends CndPropertyElementImpl implements CndProperty {

  public CndPropertyImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull CndVisitor visitor) {
    visitor.visitProperty(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof CndVisitor) accept((CndVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public CndPropertyAttributes getPropertyAttributes() {
    return findChildByClass(CndPropertyAttributes.class);
  }

  @Override
  @Nullable
  public CndPropertyConstraint getPropertyConstraint() {
    return findChildByClass(CndPropertyConstraint.class);
  }

  @Override
  @Nullable
  public CndPropertyDefault getPropertyDefault() {
    return findChildByClass(CndPropertyDefault.class);
  }

  @Override
  @NotNull
  public CndPropertyIdentifier getPropertyIdentifier() {
    return findNotNullChildByClass(CndPropertyIdentifier.class);
  }

  @Override
  @NotNull
  public List<CndPropertyTypeMaskOption> getPropertyTypeMaskOptionList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, CndPropertyTypeMaskOption.class);
  }

  public String getPropertyName() {
    return CndPsiImplUtil.getPropertyName(this);
  }

  public PsiElement setPropertyName(String newName) {
    return CndPsiImplUtil.setPropertyName(this, newName);
  }

  public PropertyTypeEnum getType() {
    return CndPsiImplUtil.getType(this);
  }

  public PropertyTypeMaskEnum getTypeMask() {
    return CndPsiImplUtil.getTypeMask(this);
  }

  public ItemPresentation getPresentation() {
    return CndPsiImplUtil.getPresentation(this);
  }

  public boolean hasAttribute(AttributeEnum attribute) {
    return CndPsiImplUtil.hasAttribute(this, attribute);
  }

  public boolean isMultiple() {
    return CndPsiImplUtil.isMultiple(this);
  }

}

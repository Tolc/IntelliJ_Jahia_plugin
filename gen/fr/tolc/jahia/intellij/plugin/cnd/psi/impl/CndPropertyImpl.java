// This is a generated file. Not intended for manual editing.
package fr.tolc.jahia.intellij.plugin.cnd.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import fr.tolc.jahia.intellij.plugin.cnd.enums.AttributeEnum;
import fr.tolc.jahia.intellij.plugin.cnd.enums.PropertyTypeEnum;
import fr.tolc.jahia.intellij.plugin.cnd.enums.PropertyTypeMaskEnum;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndNodeType;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndProperty;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndPropertyAttributes;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndPropertyConstraint;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndPropertyDefault;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndPropertyIdentifier;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndPropertyTypeMaskOption;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndPsiImplUtil;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndVisitor;
import fr.tolc.jahia.intellij.plugin.cnd.psi.elements.impl.CndPropertyElementImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CndPropertyImpl extends CndPropertyElementImpl implements CndProperty {

  public CndPropertyImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull CndVisitor visitor) {
    visitor.visitProperty(this);
  }

  @Override
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

  @Override
  public String getPropertyName() {
    return CndPsiImplUtil.getPropertyName(this);
  }

  @Override
  public PsiElement setPropertyName(String newName) {
    return CndPsiImplUtil.setPropertyName(this, newName);
  }

  @Override
  @Nullable
  public PropertyTypeEnum getType() {
    return CndPsiImplUtil.getType(this);
  }

  @Override
  @Nullable
  public PropertyTypeMaskEnum getTypeMask() {
    return CndPsiImplUtil.getTypeMask(this);
  }

  @Override
  @NotNull
  public ItemPresentation getPresentation() {
    return CndPsiImplUtil.getPresentation(this);
  }

  @Override
  public String toString() {
    return CndPsiImplUtil.toString(this);
  }

  @Override
  public boolean hasAttribute(AttributeEnum attribute) {
    return CndPsiImplUtil.hasAttribute(this, attribute);
  }

  @Override
  public boolean isMultiple() {
    return CndPsiImplUtil.isMultiple(this);
  }

  @Override
  public boolean isHidden() {
    return CndPsiImplUtil.isHidden(this);
  }

  @Override
  public boolean isProtected() {
    return CndPsiImplUtil.isProtected(this);
  }

  @Override
  public boolean isMandatory() {
    return CndPsiImplUtil.isMandatory(this);
  }

  @Override
  public boolean isInternationalized() {
    return CndPsiImplUtil.isInternationalized(this);
  }

  @Override
  public boolean isSearchable() {
    return CndPsiImplUtil.isSearchable(this);
  }

  @Override
  public CndNodeType getNodeType() {
    return CndPsiImplUtil.getNodeType(this);
  }

}

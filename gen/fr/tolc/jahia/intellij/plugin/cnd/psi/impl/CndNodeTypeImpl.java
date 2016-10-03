// This is a generated file. Not intended for manual editing.
package fr.tolc.jahia.intellij.plugin.cnd.psi.impl;

import java.util.List;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndExtensions;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndItemType;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndNodeType;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndOptions;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndProperty;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndSubNode;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndSuperTypes;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CndNodeTypeImpl extends CndNodeTypeElementImpl implements CndNodeType {

  public CndNodeTypeImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull CndVisitor visitor) {
    visitor.visitNodeType(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof CndVisitor) accept((CndVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public CndExtensions getExtensions() {
    return findChildByClass(CndExtensions.class);
  }

  @Override
  @Nullable
  public CndItemType getItemType() {
    return findChildByClass(CndItemType.class);
  }

  @Override
  @Nullable
  public CndOptions getOptions() {
    return findChildByClass(CndOptions.class);
  }

  @Override
  @NotNull
  public List<CndProperty> getPropertyList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, CndProperty.class);
  }

  @Override
  @NotNull
  public List<CndSubNode> getSubNodeList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, CndSubNode.class);
  }

  @Override
  @Nullable
  public CndSuperTypes getSuperTypes() {
    return findChildByClass(CndSuperTypes.class);
  }

  public String getNodeTypeName() {
    return CndPsiImplUtil.getNodeTypeName(this);
  }

  public PsiElement setNodeTypeName(String newName) {
    return CndPsiImplUtil.setNodeTypeName(this, newName);
  }

  public String getNodeTypeNamespace() {
    return CndPsiImplUtil.getNodeTypeNamespace(this);
  }

  public PsiElement getNameIdentifier() {
    return CndPsiImplUtil.getNameIdentifier(this);
  }

  public String getName() {
    return CndPsiImplUtil.getName(this);
  }

  public PsiElement setName(String newName) {
    return CndPsiImplUtil.setName(this, newName);
  }

}

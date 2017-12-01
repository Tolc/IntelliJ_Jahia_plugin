// This is a generated file. Not intended for manual editing.
package fr.tolc.jahia.intellij.plugin.cnd.psi.impl;

import java.util.List;
import java.util.Set;

import com.intellij.lang.ASTNode;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import fr.tolc.jahia.intellij.plugin.cnd.enums.OptionEnum;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndExtensions;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndItemType;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndNodeOption;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndNodeType;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndNodeTypeIdentifier;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndProperty;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndPsiImplUtil;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndSubNode;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndSuperTypes;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndVisitor;
import fr.tolc.jahia.intellij.plugin.cnd.psi.elements.impl.CndNodeTypeElementImpl;
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
  @NotNull
  public List<CndExtensions> getExtensionsList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, CndExtensions.class);
  }

  @Override
  @Nullable
  public CndItemType getItemType() {
    return findChildByClass(CndItemType.class);
  }

  @Override
  @NotNull
  public List<CndNodeOption> getNodeOptionList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, CndNodeOption.class);
  }

  @Override
  @NotNull
  public CndNodeTypeIdentifier getNodeTypeIdentifier() {
    return findNotNullChildByClass(CndNodeTypeIdentifier.class);
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

  public ItemPresentation getPresentation() {
    return CndPsiImplUtil.getPresentation(this);
  }

  @NotNull
  public Set<CndProperty> getProperties() {
    return CndPsiImplUtil.getProperties(this);
  }

  @NotNull
  public Set<CndProperty> getOwnProperties() {
    return CndPsiImplUtil.getOwnProperties(this);
  }

  @Nullable
  public CndProperty getProperty(String propertyName) {
    return CndPsiImplUtil.getProperty(this, propertyName);
  }

  @Nullable
  public CndProperty getOwnProperty(String propertyName) {
    return CndPsiImplUtil.getOwnProperty(this, propertyName);
  }

  @NotNull
  public Set<OptionEnum> getOptions() {
    return CndPsiImplUtil.getOptions(this);
  }

  public boolean isMixin() {
    return CndPsiImplUtil.isMixin(this);
  }

  @NotNull
  public Set<CndNodeType> getParentsNodeTypes() {
    return CndPsiImplUtil.getParentsNodeTypes(this);
  }

  @NotNull
  public Set<CndNodeType> getAncestorsNodeTypes() {
    return CndPsiImplUtil.getAncestorsNodeTypes(this);
  }

}

// This is a generated file. Not intended for manual editing.
package fr.tolc.jahia.intellij.plugin.cnd.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiReference;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndExtension;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndPsiImplUtil;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndVisitor;
import fr.tolc.jahia.intellij.plugin.cnd.psi.elements.impl.CndExtensionElementImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CndExtensionImpl extends CndExtensionElementImpl implements CndExtension {

  public CndExtensionImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull CndVisitor visitor) {
    visitor.visitExtension(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof CndVisitor) accept((CndVisitor)visitor);
    else super.accept(visitor);
  }

  @NotNull
  public PsiReference[] getReferences() {
    return CndPsiImplUtil.getReferences(this);
  }

  @Nullable
  public String getNodeTypeNamespace() {
    return CndPsiImplUtil.getNodeTypeNamespace(this);
  }

  @Nullable
  public String getNodeTypeName() {
    return CndPsiImplUtil.getNodeTypeName(this);
  }

}

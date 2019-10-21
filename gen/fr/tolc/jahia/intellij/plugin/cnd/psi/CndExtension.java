// This is a generated file. Not intended for manual editing.
package fr.tolc.jahia.intellij.plugin.cnd.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import fr.tolc.jahia.intellij.plugin.cnd.psi.elements.CndExtensionElement;
import com.intellij.psi.PsiReference;

public interface CndExtension extends CndExtensionElement {

  @NotNull
  PsiReference[] getReferences();

  @Nullable
  String getNodeTypeNamespace();

  @Nullable
  String getNodeTypeName();

}

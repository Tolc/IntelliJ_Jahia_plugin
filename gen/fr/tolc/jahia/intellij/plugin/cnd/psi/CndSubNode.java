// This is a generated file. Not intended for manual editing.
package fr.tolc.jahia.intellij.plugin.cnd.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import fr.tolc.jahia.intellij.plugin.cnd.psi.elements.CndSubNodeElement;

public interface CndSubNode extends CndSubNodeElement {

  @Nullable
  CndSubNodeAttributes getSubNodeAttributes();

  @Nullable
  CndSubNodeDefaultType getSubNodeDefaultType();

  @NotNull
  List<CndSubNodeType> getSubNodeTypeList();

  @Nullable
  String getSubNodeName();

}

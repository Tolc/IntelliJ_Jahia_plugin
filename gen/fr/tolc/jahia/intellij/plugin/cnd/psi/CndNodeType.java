// This is a generated file. Not intended for manual editing.
package fr.tolc.jahia.intellij.plugin.cnd.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface CndNodeType extends CndNodeTypeElement {

  @Nullable
  CndExtend getExtend();

  @Nullable
  CndInheritances getInheritances();

  @Nullable
  CndItemType getItemType();

  @Nullable
  CndProperties getProperties();

  String getNodeTypeName();

  PsiElement setNodeTypeName(String newName);

  String getNodeTypeNamespace();

  PsiElement getNameIdentifier();

  String getName();

  PsiElement setName(String newName);

}

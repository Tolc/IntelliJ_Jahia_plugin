// This is a generated file. Not intended for manual editing.
package fr.tolc.jahia.intellij.plugin.cnd.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface CndNodeType extends CndNodeTypeElement {

  @Nullable
  CndExtensions getExtensions();

  @Nullable
  CndItemType getItemType();

  @Nullable
  CndOptions getOptions();

  @Nullable
  CndProperty getProperty();

  @Nullable
  CndSubNode getSubNode();

  @Nullable
  CndSuperTypes getSuperTypes();

  String getNodeTypeName();

  PsiElement setNodeTypeName(String newName);

  String getNodeTypeNamespace();

  PsiElement getNameIdentifier();

  String getName();

  PsiElement setName(String newName);

}

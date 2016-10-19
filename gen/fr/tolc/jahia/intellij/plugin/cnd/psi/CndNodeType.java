// This is a generated file. Not intended for manual editing.
package fr.tolc.jahia.intellij.plugin.cnd.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import fr.tolc.jahia.intellij.plugin.cnd.psi.elements.CndNodeTypeElement;
import com.intellij.navigation.ItemPresentation;

public interface CndNodeType extends CndNodeTypeElement {

  @NotNull
  List<CndExtensions> getExtensionsList();

  @Nullable
  CndItemType getItemType();

  @NotNull
  CndNodeTypeIdentifier getNodeTypeIdentifier();

  @Nullable
  CndOptions getOptions();

  @NotNull
  List<CndProperty> getPropertyList();

  @NotNull
  List<CndSubNode> getSubNodeList();

  @Nullable
  CndSuperTypes getSuperTypes();

  String getNodeTypeName();

  PsiElement setNodeTypeName(String newName);

  String getNodeTypeNamespace();

  ItemPresentation getPresentation();

  CndProperty getProperty(String propertyName);

}

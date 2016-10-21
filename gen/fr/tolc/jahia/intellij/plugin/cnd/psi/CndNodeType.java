// This is a generated file. Not intended for manual editing.
package fr.tolc.jahia.intellij.plugin.cnd.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import fr.tolc.jahia.intellij.plugin.cnd.psi.elements.CndNodeTypeElement;
import com.intellij.navigation.ItemPresentation;
import java.util.Set;
import fr.tolc.jahia.intellij.plugin.cnd.enums.OptionEnum;

public interface CndNodeType extends CndNodeTypeElement {

  @NotNull
  List<CndExtensions> getExtensionsList();

  @Nullable
  CndItemType getItemType();

  @NotNull
  List<CndNodeOption> getNodeOptionList();

  @NotNull
  CndNodeTypeIdentifier getNodeTypeIdentifier();

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

  Set<OptionEnum> getOptions();

  boolean isMixin();

}

// This is a generated file. Not intended for manual editing.
package fr.tolc.jahia.intellij.plugin.cnd.psi;

import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiElement;
import fr.tolc.jahia.intellij.plugin.cnd.enums.OptionEnum;
import fr.tolc.jahia.intellij.plugin.cnd.psi.elements.CndNodeTypeElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;

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

  @Nullable
  String getNodeTypeNamespace();

  @NotNull
  ItemPresentation getPresentation();

  String toString();

  boolean equals(Object o);

  int hashCode();

  @NotNull
  Set<CndProperty> getProperties();

  @NotNull
  Set<CndProperty> getPropertiesWithName(String propertyName);

  @NotNull
  Set<CndProperty> getOwnProperties();

  @Nullable
  CndProperty getProperty(String propertyName);

  @Nullable
  CndProperty getOwnProperty(String propertyName);

  @NotNull
  Set<OptionEnum> getOptions();

  boolean isMixin();

  @NotNull
  Set<CndNodeType> getParentsNodeTypes();

  @NotNull
  Set<CndNodeType> getAncestorsNodeTypes();

  @NotNull
  Set<CndNodeType> getExtensions();

}

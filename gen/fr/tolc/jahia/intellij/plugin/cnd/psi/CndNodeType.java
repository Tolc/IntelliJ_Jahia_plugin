// This is a generated file. Not intended for manual editing.
package fr.tolc.jahia.intellij.plugin.cnd.psi;

import java.util.List;
import java.util.Set;

import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiElement;
import fr.tolc.jahia.intellij.plugin.cnd.enums.OptionEnum;
import fr.tolc.jahia.intellij.plugin.cnd.psi.elements.CndNodeTypeElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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

  @NotNull
  Set<CndProperty> getProperties();

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

}

// This is a generated file. Not intended for manual editing.
package fr.tolc.jahia.intellij.plugin.cnd.psi;

import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiElement;
import fr.tolc.jahia.intellij.plugin.cnd.enums.AttributeEnum;
import fr.tolc.jahia.intellij.plugin.cnd.enums.PropertyTypeEnum;
import fr.tolc.jahia.intellij.plugin.cnd.enums.PropertyTypeMaskEnum;
import fr.tolc.jahia.intellij.plugin.cnd.psi.elements.CndPropertyElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface CndProperty extends CndPropertyElement {

  @Nullable
  CndPropertyAttributes getPropertyAttributes();

  @Nullable
  CndPropertyConstraint getPropertyConstraint();

  @Nullable
  CndPropertyDefault getPropertyDefault();

  @NotNull
  CndPropertyIdentifier getPropertyIdentifier();

  @NotNull
  List<CndPropertyTypeMaskOption> getPropertyTypeMaskOptionList();

  String getPropertyName();

  PsiElement setPropertyName(String newName);

  @Nullable
  PropertyTypeEnum getType();

  @Nullable
  PropertyTypeMaskEnum getTypeMask();

  @NotNull
  ItemPresentation getPresentation();

  boolean hasAttribute(AttributeEnum attribute);

  boolean isMultiple();

  boolean isHidden();

  boolean isProtected();

  boolean isMandatory();

  boolean isInternationalized();

  boolean isSearchable();

  CndNodeType getNodeType();

}

// This is a generated file. Not intended for manual editing.
package fr.tolc.jahia.intellij.plugin.cnd.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import fr.tolc.jahia.intellij.plugin.cnd.psi.elements.CndPropertyElement;
import com.intellij.navigation.ItemPresentation;
import fr.tolc.jahia.intellij.plugin.cnd.enums.PropertyTypeEnum;
import fr.tolc.jahia.intellij.plugin.cnd.enums.PropertyTypeMaskEnum;

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

  PropertyTypeEnum getType();

  PropertyTypeMaskEnum getTypeMask();

  ItemPresentation getPresentation();

}

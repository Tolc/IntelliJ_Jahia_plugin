// This is a generated file. Not intended for manual editing.
package fr.tolc.jahia.intellij.plugin.cnd.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import fr.tolc.jahia.intellij.plugin.cnd.psi.identifiers.CndPropertyIdentifierElement;
import com.intellij.navigation.ItemPresentation;

public interface CndPropertyIdentifier extends CndPropertyIdentifierElement {

  @Nullable
  String getPropertyName();

  PsiElement setPropertyName(String newName);

  @Nullable
  PsiElement getNameIdentifier();

  ItemPresentation getPresentation();

  String getName();

  PsiElement setName(String newName);

  CndProperty getProperty();

}

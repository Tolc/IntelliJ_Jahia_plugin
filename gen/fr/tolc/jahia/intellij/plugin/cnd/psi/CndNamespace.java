// This is a generated file. Not intended for manual editing.
package fr.tolc.jahia.intellij.plugin.cnd.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import fr.tolc.jahia.intellij.plugin.cnd.psi.elements.CndNamespaceElement;
import com.intellij.navigation.ItemPresentation;

public interface CndNamespace extends CndNamespaceElement {

  @NotNull
  CndNamespaceIdentifier getNamespaceIdentifier();

  String getNamespaceName();

  PsiElement setNamespaceName(String newName);

  String getNamespaceURI();

  @NotNull
  ItemPresentation getPresentation();

}

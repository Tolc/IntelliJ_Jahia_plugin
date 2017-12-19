// This is a generated file. Not intended for manual editing.
package fr.tolc.jahia.intellij.plugin.cnd.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import fr.tolc.jahia.intellij.plugin.cnd.psi.identifiers.CndNamespaceIdentifierElement;
import com.intellij.navigation.ItemPresentation;

public interface CndNamespaceIdentifier extends CndNamespaceIdentifierElement {

  @Nullable
  String getNamespaceName();

  PsiElement setNamespaceName(String newName);

  @Nullable
  PsiElement getNameIdentifier();

  ItemPresentation getPresentation();

  //WARNING: toString(...) is skipped
  //matching toString(CndNamespaceIdentifier, ...)
  //methods are not found in CndPsiImplUtil

  String getName();

  PsiElement setName(String newName);

  CndNamespace getNamespace();

}

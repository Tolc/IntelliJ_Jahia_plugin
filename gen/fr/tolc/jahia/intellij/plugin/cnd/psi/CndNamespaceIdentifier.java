// This is a generated file. Not intended for manual editing.
package fr.tolc.jahia.intellij.plugin.cnd.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import fr.tolc.jahia.intellij.plugin.cnd.psi.identifiers.CndNamespaceIdentifierElement;
import com.intellij.navigation.ItemPresentation;

public interface CndNamespaceIdentifier extends CndNamespaceIdentifierElement {

  String getNamespaceName();

  PsiElement setNamespaceName(String newName);

  PsiElement getNameIdentifier();

  ItemPresentation getPresentation();

  String getName();

  PsiElement setName(String newName);

  CndNamespace getNamespace();

}

// This is a generated file. Not intended for manual editing.
package fr.tolc.jahia.intellij.plugin.cnd.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import fr.tolc.jahia.intellij.plugin.cnd.psi.identifiers.CndNodeTypeIdentifierElement;
import com.intellij.navigation.ItemPresentation;

public interface CndNodeTypeIdentifier extends CndNodeTypeIdentifierElement {

  String getNodeTypeName();

  PsiElement setNodeTypeName(String newName);

  PsiElement getNameIdentifier();

  ItemPresentation getPresentation();

  String getName();

  PsiElement setName(String newName);

  CndNodeType getNodeType();

}

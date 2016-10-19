// This is a generated file. Not intended for manual editing.
package fr.tolc.jahia.intellij.plugin.cnd.psi;

import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiElement;
import fr.tolc.jahia.intellij.plugin.cnd.psi.identifiers.CndPropertyIdentifierElement;

public class CndVisitor extends PsiElementVisitor {

  public void visitExtension(@NotNull CndExtension o) {
    visitExtensionElement(o);
  }

  public void visitExtensions(@NotNull CndExtensions o) {
    visitPsiElement(o);
  }

  public void visitItemType(@NotNull CndItemType o) {
    visitPsiElement(o);
  }

  public void visitNamespace(@NotNull CndNamespace o) {
    visitNamespaceElement(o);
  }

  public void visitNodeType(@NotNull CndNodeType o) {
    visitNodeTypeElement(o);
  }

  public void visitOptions(@NotNull CndOptions o) {
    visitPsiElement(o);
  }

  public void visitProperty(@NotNull CndProperty o) {
    visitPropertyElement(o);
  }

  public void visitPropertyAttributes(@NotNull CndPropertyAttributes o) {
    visitPsiElement(o);
  }

  public void visitPropertyConstraint(@NotNull CndPropertyConstraint o) {
    visitPsiElement(o);
  }

  public void visitPropertyDefault(@NotNull CndPropertyDefault o) {
    visitPsiElement(o);
  }

  public void visitPropertyIdentifier(@NotNull CndPropertyIdentifier o) {
    visitPropertyIdentifierElement(o);
  }

  public void visitSubNode(@NotNull CndSubNode o) {
    visitPsiElement(o);
  }

  public void visitSubNodeAttributes(@NotNull CndSubNodeAttributes o) {
    visitPsiElement(o);
  }

  public void visitSubNodeDefaultType(@NotNull CndSubNodeDefaultType o) {
    visitSubNodeDefaultTypeElement(o);
  }

  public void visitSubNodeType(@NotNull CndSubNodeType o) {
    visitSubNodeTypeElement(o);
  }

  public void visitSuperType(@NotNull CndSuperType o) {
    visitSuperTypeElement(o);
  }

  public void visitSuperTypes(@NotNull CndSuperTypes o) {
    visitPsiElement(o);
  }

  public void visitExtensionElement(@NotNull CndExtensionElement o) {
    visitPsiElement(o);
  }

  public void visitNamespaceElement(@NotNull CndNamespaceElement o) {
    visitPsiElement(o);
  }

  public void visitNodeTypeElement(@NotNull CndNodeTypeElement o) {
    visitPsiElement(o);
  }

  public void visitPropertyElement(@NotNull CndPropertyElement o) {
    visitPsiElement(o);
  }

  public void visitPropertyIdentifierElement(@NotNull CndPropertyIdentifierElement o) {
    visitPsiElement(o);
  }

  public void visitSubNodeDefaultTypeElement(@NotNull CndSubNodeDefaultTypeElement o) {
    visitPsiElement(o);
  }

  public void visitSubNodeTypeElement(@NotNull CndSubNodeTypeElement o) {
    visitPsiElement(o);
  }

  public void visitSuperTypeElement(@NotNull CndSuperTypeElement o) {
    visitPsiElement(o);
  }

  public void visitPsiElement(@NotNull PsiElement o) {
    visitElement(o);
  }

}

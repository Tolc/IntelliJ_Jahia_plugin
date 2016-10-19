package fr.tolc.jahia.intellij.plugin.cnd.psi.identifiers.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import fr.tolc.jahia.intellij.plugin.cnd.psi.identifiers.CndNamespaceIdentifierElement;
import org.jetbrains.annotations.NotNull;

public abstract class CndNamespaceIdentifierElementImpl extends ASTWrapperPsiElement implements CndNamespaceIdentifierElement {
    public CndNamespaceIdentifierElementImpl(@NotNull ASTNode node) {
        super(node);
    }
}
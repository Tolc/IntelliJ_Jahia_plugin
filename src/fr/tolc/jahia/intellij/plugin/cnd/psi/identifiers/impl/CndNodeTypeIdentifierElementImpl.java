package fr.tolc.jahia.intellij.plugin.cnd.psi.identifiers.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import fr.tolc.jahia.intellij.plugin.cnd.psi.identifiers.CndNodeTypeIdentifierElement;
import org.jetbrains.annotations.NotNull;

public abstract class CndNodeTypeIdentifierElementImpl extends ASTWrapperPsiElement implements CndNodeTypeIdentifierElement {
    public CndNodeTypeIdentifierElementImpl(@NotNull ASTNode node) {
        super(node);
    }
}
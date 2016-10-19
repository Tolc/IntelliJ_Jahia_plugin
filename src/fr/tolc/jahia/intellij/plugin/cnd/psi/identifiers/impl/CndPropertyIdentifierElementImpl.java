package fr.tolc.jahia.intellij.plugin.cnd.psi.identifiers.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import fr.tolc.jahia.intellij.plugin.cnd.psi.identifiers.CndPropertyIdentifierElement;
import org.jetbrains.annotations.NotNull;

public abstract class CndPropertyIdentifierElementImpl extends ASTWrapperPsiElement implements CndPropertyIdentifierElement {
    public CndPropertyIdentifierElementImpl(@NotNull ASTNode node) {
        super(node);
    }
}
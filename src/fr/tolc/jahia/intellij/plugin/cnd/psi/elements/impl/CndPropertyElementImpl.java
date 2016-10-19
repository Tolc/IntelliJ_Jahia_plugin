package fr.tolc.jahia.intellij.plugin.cnd.psi.elements.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import fr.tolc.jahia.intellij.plugin.cnd.psi.elements.CndPropertyElement;
import org.jetbrains.annotations.NotNull;

public abstract class CndPropertyElementImpl extends ASTWrapperPsiElement implements CndPropertyElement {
    public CndPropertyElementImpl(@NotNull ASTNode node) {
        super(node);
    }
}
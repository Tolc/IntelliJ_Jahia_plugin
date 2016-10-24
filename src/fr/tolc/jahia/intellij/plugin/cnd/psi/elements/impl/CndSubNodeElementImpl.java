package fr.tolc.jahia.intellij.plugin.cnd.psi.elements.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import fr.tolc.jahia.intellij.plugin.cnd.psi.elements.CndSubNodeElement;
import org.jetbrains.annotations.NotNull;

public abstract class CndSubNodeElementImpl extends ASTWrapperPsiElement implements CndSubNodeElement {
    public CndSubNodeElementImpl(@NotNull ASTNode node) {
        super(node);
    }
}
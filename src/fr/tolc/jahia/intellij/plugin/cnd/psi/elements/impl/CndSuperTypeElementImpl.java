package fr.tolc.jahia.intellij.plugin.cnd.psi.elements.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import fr.tolc.jahia.intellij.plugin.cnd.psi.elements.CndSuperTypeElement;
import org.jetbrains.annotations.NotNull;

public abstract class CndSuperTypeElementImpl extends ASTWrapperPsiElement implements CndSuperTypeElement {
    public CndSuperTypeElementImpl(@NotNull ASTNode node) {
        super(node);
    }
}
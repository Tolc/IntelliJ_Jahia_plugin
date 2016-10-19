package fr.tolc.jahia.intellij.plugin.cnd.psi.elements.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import fr.tolc.jahia.intellij.plugin.cnd.psi.elements.CndSubNodeDefaultTypeElement;
import org.jetbrains.annotations.NotNull;

public abstract class CndSubNodeDefaultTypeElementImpl extends ASTWrapperPsiElement implements CndSubNodeDefaultTypeElement {
    public CndSubNodeDefaultTypeElementImpl(@NotNull ASTNode node) {
        super(node);
    }
}
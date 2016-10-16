package fr.tolc.jahia.intellij.plugin.cnd.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndNodeTypeElement;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndPropertyElement;
import org.jetbrains.annotations.NotNull;

public abstract class CndPropertyElementImpl extends ASTWrapperPsiElement implements CndPropertyElement {
    public CndPropertyElementImpl(@NotNull ASTNode node) {
        super(node);
    }
}
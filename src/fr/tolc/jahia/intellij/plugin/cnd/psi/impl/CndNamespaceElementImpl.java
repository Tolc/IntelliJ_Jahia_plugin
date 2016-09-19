package fr.tolc.jahia.intellij.plugin.cnd.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndNamespaceElement;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndNodeTypeElement;
import org.jetbrains.annotations.NotNull;

public abstract class CndNamespaceElementImpl extends ASTWrapperPsiElement implements CndNamespaceElement {
    public CndNamespaceElementImpl(@NotNull ASTNode node) {
        super(node);
    }
}
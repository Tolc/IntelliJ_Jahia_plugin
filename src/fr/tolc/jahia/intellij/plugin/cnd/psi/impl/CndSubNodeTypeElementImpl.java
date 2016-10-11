package fr.tolc.jahia.intellij.plugin.cnd.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndSubNodeTypeElement;
import org.jetbrains.annotations.NotNull;

public abstract class CndSubNodeTypeElementImpl extends ASTWrapperPsiElement implements CndSubNodeTypeElement {
    public CndSubNodeTypeElementImpl(@NotNull ASTNode node) {
        super(node);
    }
}
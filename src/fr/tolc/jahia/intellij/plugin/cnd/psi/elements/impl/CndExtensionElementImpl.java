package fr.tolc.jahia.intellij.plugin.cnd.psi.elements.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import fr.tolc.jahia.intellij.plugin.cnd.psi.elements.CndExtensionElement;
import org.jetbrains.annotations.NotNull;

public abstract class CndExtensionElementImpl extends ASTWrapperPsiElement implements CndExtensionElement {
    public CndExtensionElementImpl(@NotNull ASTNode node) {
        super(node);
    }
}
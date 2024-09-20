package fr.tolc.jahia.language.cnd.psi.mixins;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.NlsSafe;
import com.intellij.psi.PsiElement;
import com.intellij.util.IncorrectOperationException;
import fr.tolc.jahia.language.cnd.CndElementFactory;
import fr.tolc.jahia.language.cnd.psi.CndNamespace;
import fr.tolc.jahia.language.cnd.psi.CndNamespaceIdentifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class CndNamespaceIdentifierMixin extends ASTWrapperPsiElement implements CndNamespaceIdentifier {

    public CndNamespaceIdentifierMixin(@NotNull ASTNode node) {
        super(node);
    }

    @Override
    public @Nullable PsiElement getNameIdentifier() {
        return this.getNode().getFirstChildNode().getPsi();
    }

    @Override
    public String getName() {
        return this.getText().trim();
    }

    @Override
    public PsiElement setName(@NlsSafe @NotNull String name) throws IncorrectOperationException {
        ASTNode nameNode = this.getNode().getFirstChildNode();
        if (nameNode != null) {
            CndNamespace fakeNamespace = CndElementFactory.createNamespace(this.getProject(), name);
            ASTNode newNameNode = fakeNamespace.getNamespaceIdentifier().getNode().getFirstChildNode();
            this.getNode().replaceChild(nameNode, newNameNode);
        }
        return this;
    }

}

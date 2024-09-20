package fr.tolc.jahia.language.cnd.psi.mixins;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.navigation.ItemPresentation;
import com.intellij.openapi.util.NlsSafe;
import com.intellij.psi.PsiElement;
import com.intellij.util.IncorrectOperationException;
import fr.tolc.jahia.language.cnd.CndIcons;
import fr.tolc.jahia.language.cnd.psi.CndNamespace;
import fr.tolc.jahia.language.cnd.psi.CndTypes;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.Icon;

public abstract class CndNamespaceMixin extends ASTWrapperPsiElement implements CndNamespace {

    public CndNamespaceMixin(@NotNull ASTNode node) {
        super(node);
    }

    @Override
    public @Nullable PsiElement getNameIdentifier() {
        return getNamespaceIdentifier() != null ? getNamespaceIdentifier().getNameIdentifier() : null;
    }

    @Override
    public @NotNull String getName() {
        return getNamespaceIdentifier() != null ? getNamespaceIdentifier().getName() : "Unknown";
    }

    @Override
    public PsiElement setName(@NlsSafe @NotNull String name) throws IncorrectOperationException {
        if (getNamespaceIdentifier() != null) {
            getNamespaceIdentifier().setName(name);
        }
        return this;
    }

    @Override
    public @NotNull String getIdentifier() {
        return getName();
    }

    @Override
    public @NotNull String getUri() {
        PsiElement uriEl = findChildByType(CndTypes.NS_URI);
        if (uriEl != null) {
            return uriEl.getText();
        }
        return StringUtils.EMPTY;
    }

    @Override
    public ItemPresentation getPresentation() {
        return new ItemPresentation() {
            @Override
            public @NlsSafe @NotNull String getPresentableText() {
                return getName();
            }

            @Override
            public @NlsSafe @Nullable String getLocationString() {
                return getUri();
            }

            @Override
            public @NotNull Icon getIcon(boolean unused) {
                return CndIcons.CND_NS;
            }
        };
    }
}

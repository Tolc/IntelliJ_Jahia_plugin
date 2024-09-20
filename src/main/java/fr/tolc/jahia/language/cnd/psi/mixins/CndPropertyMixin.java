package fr.tolc.jahia.language.cnd.psi.mixins;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.navigation.ItemPresentation;
import com.intellij.openapi.util.NlsSafe;
import com.intellij.psi.PsiElement;
import com.intellij.util.IncorrectOperationException;
import fr.tolc.jahia.language.cnd.CndIcons;
import fr.tolc.jahia.language.cnd.psi.CndProperty;
import fr.tolc.jahia.language.cnd.psi.CndPropertyType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.Icon;

public abstract class CndPropertyMixin extends ASTWrapperPsiElement implements CndProperty {

    public CndPropertyMixin(@NotNull ASTNode node) {
        super(node);
    }

    @Override
    public @Nullable PsiElement getNameIdentifier() {
        return getPropertyName() != null ? getPropertyName().getNameIdentifier() : null;
    }

    @Override
    public @NotNull String getName() {
        return getPropertyName() != null ? getPropertyName().getName() : "Unknown";
    }

    @Override
    public PsiElement setName(@NlsSafe @NotNull String name) throws IncorrectOperationException {
        if (getPropertyName() != null) {
            getPropertyName().setName(name);
        }
        return this;
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
                CndPropertyType type = getPropertyType();
                return type != null ? type.getText() : "undefined";
            }

            @Override
            public @NotNull Icon getIcon(boolean unused) {
                return CndIcons.CND_PROP;
            }
        };
    }
}

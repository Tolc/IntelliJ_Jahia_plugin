package fr.tolc.jahia.language.cnd.psi.mixins;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.navigation.ItemPresentation;
import com.intellij.openapi.util.NlsSafe;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.util.IncorrectOperationException;
import fr.tolc.jahia.constants.enums.OptionEnum;
import fr.tolc.jahia.language.cnd.CndIcons;
import fr.tolc.jahia.language.cnd.psi.CndNodetype;
import fr.tolc.jahia.language.cnd.psi.CndOption;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.Icon;

public abstract class CndNodetypeMixin extends ASTWrapperPsiElement implements CndNodetype {

    public CndNodetypeMixin(@NotNull ASTNode node) {
        super(node);
    }

    @Override
    public @Nullable PsiElement getNameIdentifier() {
        return getNodetypeIdentifier() != null ? getNodetypeIdentifier().getNameIdentifier() : null;
    }

    @Override
    public String getName() {
        return getNodetypeIdentifier() != null ? getNodetypeIdentifier().getName() : "Unknown";
    }

    @Override
    public PsiElement setName(@NlsSafe @NotNull String name) throws IncorrectOperationException {
        if (getNodetypeIdentifier() != null) {
            getNodetypeIdentifier().setName(name);
        }
        return this;
    }

    @Override
    public String getIdentifier() {
        return getName();
    }

    @Override
    public boolean isMixin() {
        for (CndOption cndOption : this.getOptionList()) {
            if (OptionEnum.MIXIN == OptionEnum.fromValue(cndOption.getOptionName().getText())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public ItemPresentation getPresentation() {
        return new ItemPresentation() {
            @Override
            public @NlsSafe @Nullable String getPresentableText() {
                return getName();
            }

            @Override
            public @NlsSafe @Nullable String getLocationString() {
                PsiFile containingFile = getContainingFile();
                return containingFile == null ? null : containingFile.getName();
            }

            @Override
            public @Nullable Icon getIcon(boolean unused) {
                return isMixin() ? CndIcons.CND_MIX : CndIcons.CND_NT;
            }
        };
    }
}

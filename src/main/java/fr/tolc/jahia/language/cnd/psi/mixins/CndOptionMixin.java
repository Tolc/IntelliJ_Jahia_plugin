package fr.tolc.jahia.language.cnd.psi.mixins;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.util.PsiTreeUtil;
import fr.tolc.jahia.constants.enums.OptionEnum;
import fr.tolc.jahia.language.cnd.psi.CndNodetype;
import fr.tolc.jahia.language.cnd.psi.CndOption;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class CndOptionMixin extends ASTWrapperPsiElement implements CndOption {

    public CndOptionMixin(@NotNull ASTNode node) {
        super(node);
    }

    @Override
    public @Nullable OptionEnum getOptionType() {
        return OptionEnum.fromValue(getOptionName().getText());
    }

    @Override
    public @Nullable CndOption getPreviousOption() {
        return PsiTreeUtil.getPrevSiblingOfType(this, CndOption.class);
    }

    @Override
    public boolean isValueForPreviousOption() {
        CndOption previousOption = getPreviousOption();
        if (previousOption != null) {
            OptionEnum previousOptionType = previousOption.getOptionType();
            if (previousOptionType != null) {
                return previousOptionType.hasValueWithoutSeparator();
            }
        }
        return false;
    }

    @Override
    public @NotNull CndNodetype getNodetype() {
        return (CndNodetype) getParent();
    }
}

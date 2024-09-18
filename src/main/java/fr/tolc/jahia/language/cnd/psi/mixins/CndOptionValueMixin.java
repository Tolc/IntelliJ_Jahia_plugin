package fr.tolc.jahia.language.cnd.psi.mixins;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiReference;
import fr.tolc.jahia.constants.enums.OptionEnum;
import fr.tolc.jahia.language.cnd.psi.CndOption;
import fr.tolc.jahia.language.cnd.psi.CndOptionValue;
import fr.tolc.jahia.language.cnd.psi.CndReferencialElement;
import org.jetbrains.annotations.NotNull;

public abstract class CndOptionValueMixin extends CndReferencialElement implements CndOptionValue {

    public CndOptionValueMixin(@NotNull ASTNode node) {
        super(node);
    }

    @Override
    public OptionEnum getOptionType() {
        return ((CndOption) getParent()).getOptionType();
    }

    @Override
    public PsiReference @NotNull [] getReferences() {
        if (OptionEnum.EXTENDS == getOptionType()) {
            return super.getReferences();
        }
        return PsiReference.EMPTY_ARRAY;
    }
}

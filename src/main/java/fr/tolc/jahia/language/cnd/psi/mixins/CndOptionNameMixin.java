package fr.tolc.jahia.language.cnd.psi.mixins;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiReference;
import fr.tolc.jahia.language.cnd.psi.CndOption;
import fr.tolc.jahia.language.cnd.psi.CndOptionName;
import fr.tolc.jahia.language.cnd.psi.CndReferencialElement;
import org.jetbrains.annotations.NotNull;

public abstract class CndOptionNameMixin extends CndReferencialElement implements CndOptionName {

    public CndOptionNameMixin(@NotNull ASTNode node) {
        super(node);
    }

    @Override
    public @NotNull CndOption getOption() {
        return (CndOption) getParent();
    }

    @Override
    public PsiReference @NotNull [] getReferences() {
        CndOption option = getOption();
        if (option.isValueForPreviousOption() && option.getPreviousOption().getOptionType().isValueReference()) {
            return super.getReferences();
        }
        return PsiReference.EMPTY_ARRAY;
    }
}

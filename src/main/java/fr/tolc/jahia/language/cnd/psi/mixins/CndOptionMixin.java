package fr.tolc.jahia.language.cnd.psi.mixins;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import fr.tolc.jahia.constants.enums.OptionEnum;
import fr.tolc.jahia.language.cnd.psi.CndOption;
import org.jetbrains.annotations.NotNull;

public abstract class CndOptionMixin extends ASTWrapperPsiElement implements CndOption {

    public CndOptionMixin(@NotNull ASTNode node) {
        super(node);
    }

    @Override
    public OptionEnum getOptionType() {
        return OptionEnum.fromValue(getFirstChild().getText());
    }
}

package fr.tolc.jahia.language.cnd.psi.interfaces;

import com.intellij.psi.PsiElement;
import fr.tolc.jahia.constants.enums.OptionEnum;
import fr.tolc.jahia.language.cnd.psi.CndNodetype;
import fr.tolc.jahia.language.cnd.psi.CndOption;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface CndOptionInterface extends PsiElement {

    @Nullable OptionEnum getOptionType();

    @Nullable CndOption getPreviousOption();

    boolean isValueForPreviousOption();

    @NotNull CndNodetype getNodetype();
}

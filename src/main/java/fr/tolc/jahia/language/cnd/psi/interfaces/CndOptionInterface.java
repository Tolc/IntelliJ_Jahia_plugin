package fr.tolc.jahia.language.cnd.psi.interfaces;

import com.intellij.psi.PsiElement;
import fr.tolc.jahia.constants.enums.OptionEnum;

public interface CndOptionInterface extends PsiElement {

    OptionEnum getOptionType();
}

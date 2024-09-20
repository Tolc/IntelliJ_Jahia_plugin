package fr.tolc.jahia.language.cnd.psi.interfaces;

import com.intellij.psi.PsiElement;
import fr.tolc.jahia.language.cnd.psi.CndOption;
import org.jetbrains.annotations.NotNull;

public interface CndOptionNameInterface extends PsiElement {

    @NotNull CndOption getOption();
}

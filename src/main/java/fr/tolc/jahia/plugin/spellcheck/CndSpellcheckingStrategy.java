package fr.tolc.jahia.plugin.spellcheck;

import com.intellij.psi.PsiComment;
import com.intellij.psi.PsiElement;
import com.intellij.spellchecker.tokenizer.SpellcheckingStrategy;
import com.intellij.spellchecker.tokenizer.Tokenizer;
import fr.tolc.jahia.language.cnd.psi.CndNodetypeIdentifier;
import fr.tolc.jahia.language.cnd.psi.CndPropertyName;
import fr.tolc.jahia.language.cnd.psi.CndSubnodeName;
import org.jetbrains.annotations.NotNull;

public class CndSpellcheckingStrategy extends SpellcheckingStrategy {

    @Override
    public @NotNull Tokenizer<?> getTokenizer(PsiElement element) {
        if (element instanceof PsiComment) {
            return new CndCommentTokenizer();
        }
        if (element instanceof CndNodetypeIdentifier) {
            return new CndNodetypeTokenizer();
        }
        if (element instanceof CndPropertyName || element instanceof CndSubnodeName) {
            return new CndPropertySubnodeTokenizer();
        }

        return EMPTY_TOKENIZER;
    }
}

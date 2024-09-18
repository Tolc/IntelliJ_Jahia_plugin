package fr.tolc.jahia.plugin.spellcheck;

import com.intellij.openapi.util.TextRange;
import com.intellij.spellchecker.inspections.IdentifierSplitter;
import com.intellij.spellchecker.tokenizer.TokenConsumer;
import com.intellij.spellchecker.tokenizer.Tokenizer;
import fr.tolc.jahia.language.cnd.psi.CndNodetypeIdentifier;
import org.jetbrains.annotations.NotNull;

public class CndNodetypeTokenizer extends Tokenizer<CndNodetypeIdentifier> {

    @Override
    public void tokenize(@NotNull CndNodetypeIdentifier element, @NotNull TokenConsumer consumer) {
        String text = element.getText();
        int startIndex = text.contains(":") ? text.indexOf(":") + 1 : 0;
        consumer.consumeToken(element, text, true, 0,
                TextRange.create(startIndex, element.getTextLength()),
                IdentifierSplitter.getInstance());
    }
}

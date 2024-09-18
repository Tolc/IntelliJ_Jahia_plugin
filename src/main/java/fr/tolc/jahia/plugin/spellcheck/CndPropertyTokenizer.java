package fr.tolc.jahia.plugin.spellcheck;

import com.intellij.openapi.util.TextRange;
import com.intellij.spellchecker.inspections.IdentifierSplitter;
import com.intellij.spellchecker.tokenizer.TokenConsumer;
import com.intellij.spellchecker.tokenizer.Tokenizer;
import fr.tolc.jahia.language.cnd.psi.CndPropertyName;
import org.jetbrains.annotations.NotNull;

public class CndPropertyTokenizer extends Tokenizer<CndPropertyName> {

    @Override
    public void tokenize(@NotNull CndPropertyName element, @NotNull TokenConsumer consumer) {
        String text = element.getText();
        int startIndex = text.contains(":") ? text.indexOf(":") + 1 : 0;
        consumer.consumeToken(element, element.getText(), true, 0,
                TextRange.create(startIndex, element.getTextLength()),
                IdentifierSplitter.getInstance());
    }
}

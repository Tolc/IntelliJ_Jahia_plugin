package fr.tolc.jahia.plugin.spellcheck;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiComment;
import com.intellij.spellchecker.inspections.CommentSplitter;
import com.intellij.spellchecker.tokenizer.TokenConsumer;
import com.intellij.spellchecker.tokenizer.Tokenizer;
import org.jetbrains.annotations.NotNull;

public class CndCommentTokenizer extends Tokenizer<PsiComment> {

    @Override
    public void tokenize(@NotNull PsiComment element, @NotNull TokenConsumer consumer) {
        // Exclude the start of the comment with its / characters from spell checking
        int startIndex = 0;
        for (char c : element.textToCharArray()) {
            if (c == '/' || c == '*' || Character.isWhitespace(c)) {
                startIndex++;
            } else {
                break;
            }
        }
        consumer.consumeToken(element, element.getText(), false, 0,
                TextRange.create(startIndex, element.getTextLength()),
                CommentSplitter.getInstance());
    }
}

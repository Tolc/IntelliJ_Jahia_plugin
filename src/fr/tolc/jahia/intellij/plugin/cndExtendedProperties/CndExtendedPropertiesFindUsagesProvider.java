package fr.tolc.jahia.intellij.plugin.cndExtendedProperties;

import com.intellij.lang.cacheBuilder.WordOccurrence;
import com.intellij.lang.cacheBuilder.WordsScanner;
import com.intellij.lang.properties.findUsages.PropertiesFindUsagesProvider;
import com.intellij.lang.properties.parsing.PropertiesTokenTypes;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.tree.IElementType;
import com.intellij.util.Processor;
import fr.tolc.jahia.intellij.plugin.cnd.CndLexerAdapter;
import org.jetbrains.annotations.Nullable;

public class CndExtendedPropertiesFindUsagesProvider extends PropertiesFindUsagesProvider {
    @Nullable
    @Override
    public WordsScanner getWordsScanner() {
        return new WordsScanner() {
            @Override
            public void processWords(CharSequence fileText, Processor<WordOccurrence> processor) {
                Lexer cndLexer = new CndLexerAdapter();
                cndLexer.start(fileText);

                IElementType type;
                while ((type = cndLexer.getTokenType()) != null) {
                    if (PropertiesTokenTypes.KEY_CHARACTERS.equals(type)) { 
                        //TODO: maybe process occurrences in string literals and comments?
                        int tokenStart = cndLexer.getTokenStart();
                        for (TextRange wordRange : StringUtil.getWordIndicesIn(cndLexer.getTokenText())) {
                            int start = tokenStart + wordRange.getStartOffset();
                            int end = tokenStart + wordRange.getEndOffset();
                            processor.process(new WordOccurrence(fileText, start, end, WordOccurrence.Kind.CODE));
                        }
                    }
                    cndLexer.advance();
                }
            }
        };
    }
}

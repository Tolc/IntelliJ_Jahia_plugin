package fr.tolc.jahia.intellij.plugin.cndExtendedProperties.indexer;

import com.intellij.lang.properties.parsing.PropertiesLexer;
import com.intellij.lexer.Lexer;
import com.intellij.psi.impl.cache.impl.OccurrenceConsumer;
import com.intellij.psi.impl.cache.impl.id.LexerBasedIdIndexer;

/**
 * Created by Tolc on 06/11/2016.
 */
public class PropertiesIdIndexerCustom extends LexerBasedIdIndexer {

    @Override
    public Lexer createLexer(OccurrenceConsumer consumer) {
        return createIndexingLexer(consumer);
    }

    static Lexer createIndexingLexer(OccurrenceConsumer consumer) {
        return new PropertiesFilterLexerCustom(new PropertiesLexer(), consumer);
    }
}

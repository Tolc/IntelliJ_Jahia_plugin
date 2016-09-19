package fr.tolc.jahia.intellij.plugin.cnd;

import com.intellij.lexer.FlexAdapter;

import java.io.Reader;

public class CndLexerAdapter extends FlexAdapter {
    public CndLexerAdapter() {
        super(new CndLexer((Reader) null));
    }
}

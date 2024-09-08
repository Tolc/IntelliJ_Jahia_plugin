package fr.tolc.jahia.language.cnd;

import com.intellij.lexer.FlexAdapter;

public class CndLexerAdapter extends FlexAdapter {

    public CndLexerAdapter() {
        super(new CndLexer(null));
    }
}

package fr.tolc.jahia.plugin.formatter;

import com.intellij.codeInsight.editorActions.SimpleTokenSetQuoteHandler;
import com.intellij.psi.TokenType;
import fr.tolc.jahia.language.cnd.psi.CndTypes;

//TODO: make it work
public class CndQuoteHandler extends SimpleTokenSetQuoteHandler {

    public CndQuoteHandler() {
        super(CndTypes.NS_URI_QUOTE, TokenType.BAD_CHARACTER);
    }

}

package fr.tolc.jahia.intellij.plugin.cnd;

import com.intellij.codeInsight.editorActions.SimpleTokenSetQuoteHandler;
import com.intellij.psi.TokenType;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndTypes;

public class CndQuoteHandler extends SimpleTokenSetQuoteHandler {

    public CndQuoteHandler() {
        super(CndTypes.SINGLE_QUOTE, TokenType.BAD_CHARACTER);
    }
}
//TODO: see https://github.com/holgerbrandl/r4intellij/blob/5333ac85796d0e8eda7916e7742b088abe259033/src/com/r4intellij/editor/highlighting/RQuoteHandler.java
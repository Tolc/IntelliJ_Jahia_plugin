package fr.smile.jahia.intellij.plugin.cnd;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.HighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import fr.smile.jahia.intellij.plugin.cnd.psi.CndTypes;
import org.jetbrains.annotations.NotNull;

import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;

public class CndSyntaxHighlighter extends SyntaxHighlighterBase {
    public static final TextAttributesKey COMMENT = createTextAttributesKey("CND_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT);
    public static final TextAttributesKey NAMESPACE = createTextAttributesKey("CND_NAMESPACE", DefaultLanguageHighlighterColors.CONSTANT);
    public static final TextAttributesKey NODE_TYPE = createTextAttributesKey("CND_NODE_TYPE", DefaultLanguageHighlighterColors.KEYWORD);
    public static final TextAttributesKey KEYWORD = createTextAttributesKey("CND_KEYWORD", DefaultLanguageHighlighterColors.KEYWORD);
    public static final TextAttributesKey STRING = createTextAttributesKey("CND_STRING", DefaultLanguageHighlighterColors.STRING);
    public static final TextAttributesKey BAD_CHARACTER = createTextAttributesKey("CND_BAD_CHARACTER", HighlighterColors.BAD_CHARACTER);

    private static final TextAttributesKey[] COMMENT_KEYS = new TextAttributesKey[]{COMMENT};
    private static final TextAttributesKey[] NAMESPACE_KEYS = new TextAttributesKey[]{NAMESPACE};
    private static final TextAttributesKey[] NODE_TYPE_KEYS = new TextAttributesKey[]{NODE_TYPE};
    private static final TextAttributesKey[] KEYWORD_KEYS = new TextAttributesKey[]{KEYWORD};
    private static final TextAttributesKey[] STRING_KEYS = new TextAttributesKey[]{STRING};
    private static final TextAttributesKey[] BAD_CHAR_KEYS = new TextAttributesKey[]{BAD_CHARACTER};
    private static final TextAttributesKey[] EMPTY_KEYS = new TextAttributesKey[0];

//    public static final TextAttributesKey SEPARATOR = createTextAttributesKey("SIMPLE_SEPARATOR", DefaultLanguageHighlighterColors.OPERATION_SIGN);


    @NotNull
    @Override
    public Lexer getHighlightingLexer() {
        return new CndLexerAdapter();
    }

    @NotNull
    @Override
    public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
        if (tokenType.equals(CndTypes.COMMENT)) {
            return COMMENT_KEYS;
        } else if (tokenType.equals(CndTypes.NAMESPACE_NAME)
                || tokenType.equals(CndTypes.NAMESPACE_URI)
                || tokenType.equals(CndTypes.NODE_TYPE_NAMESPACE)
                || tokenType.equals(CndTypes.NODE_TYPE_INHERITANCE_NAMESPACE)) {
            return NAMESPACE_KEYS;
        } else if (tokenType.equals(CndTypes.NODE_TYPE_NAME)
                || tokenType.equals(CndTypes.NODE_TYPE_INHERITANCE_TYPE_NAME)) {
            return NODE_TYPE_KEYS;
        } else if (tokenType.equals(CndTypes.NODE_TYPE_MIXIN)
                || tokenType.equals(CndTypes.NODE_TYPE_ORDERABLE)
                || tokenType.equals(CndTypes.NODE_TYPE_ABSTRACT)
                || tokenType.equals(CndTypes.PROPERTY_ATTRIBUTES)
                || tokenType.equals(CndTypes.EXTEND_ITEM_START)
                || tokenType.equals(CndTypes.EXTEND_OPENING)) {
            return KEYWORD_KEYS;
        } else if (tokenType.equals(CndTypes.PROPERTY_NAME)
                || tokenType.equals(CndTypes.PROPERTY_CONSTRAINT)
                || tokenType.equals(CndTypes.EXTEND_ITEM_TYPE)) {
            return STRING_KEYS;
        } else if (tokenType.equals(TokenType.BAD_CHARACTER)) {
            return BAD_CHAR_KEYS;
        } else {
            return EMPTY_KEYS;
        }
    }
}
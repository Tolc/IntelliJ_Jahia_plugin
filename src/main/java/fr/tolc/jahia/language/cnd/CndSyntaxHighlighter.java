package fr.tolc.jahia.language.cnd;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.HighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import fr.tolc.jahia.language.cnd.psi.CndTypes;
import org.jetbrains.annotations.NotNull;

public class CndSyntaxHighlighter extends SyntaxHighlighterBase {

    public static final TextAttributesKey COMMENT = TextAttributesKey.createTextAttributesKey("CND_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT);
    public static final TextAttributesKey NAMESPACE = TextAttributesKey.createTextAttributesKey("CND_NAMESPACE", DefaultLanguageHighlighterColors.CONSTANT);
    public static final TextAttributesKey NODETYPE = TextAttributesKey.createTextAttributesKey("CND_NODETYPE", DefaultLanguageHighlighterColors.NUMBER);
    public static final TextAttributesKey KEYWORD = TextAttributesKey.createTextAttributesKey("CND_KEYWORD", DefaultLanguageHighlighterColors.KEYWORD);
    public static final TextAttributesKey ATTRIBUTE = TextAttributesKey.createTextAttributesKey("CND_ATTRIBUTE", DefaultLanguageHighlighterColors.METADATA);
    public static final TextAttributesKey TYPE = TextAttributesKey.createTextAttributesKey("CND_TYPE", DefaultLanguageHighlighterColors.FUNCTION_DECLARATION);
    public static final TextAttributesKey SEPARATOR = TextAttributesKey.createTextAttributesKey("CND_SEPARATOR", DefaultLanguageHighlighterColors.TEMPLATE_LANGUAGE_COLOR);
    public static final TextAttributesKey BAD_CHARACTER = TextAttributesKey.createTextAttributesKey("CND_BAD_CHARACTER", HighlighterColors.BAD_CHARACTER);

    public static final TextAttributesKey CND_PROPERTY = TextAttributesKey.createTextAttributesKey("CND_PROPERTY", DefaultLanguageHighlighterColors.STRING);
    public static final TextAttributesKey CND_PROPERTY_HIDDEN = TextAttributesKey.createTextAttributesKey("CND_PROPERTY_HIDDEN", CND_PROPERTY);
    public static final TextAttributesKey CND_PROPERTY_MANDATORY = TextAttributesKey.createTextAttributesKey("CND_PROPERTY_MANDATORY", CND_PROPERTY);
    public static final TextAttributesKey CND_PROPERTY_PROTECTED = TextAttributesKey.createTextAttributesKey("CND_PROPERTY_PROTECTED", CND_PROPERTY);
    public static final TextAttributesKey CND_PROPERTY_MULTIPLE = TextAttributesKey.createTextAttributesKey("CND_PROPERTY_MULTIPLE", CND_PROPERTY);
    public static final TextAttributesKey CND_PROPERTY_INTERNATIONALIZED = TextAttributesKey.createTextAttributesKey("CND_PROPERTY_INTERNATIONALIZED", CND_PROPERTY);
    public static final TextAttributesKey CND_PROPERTY_NOT_SEARCHABLE = TextAttributesKey.createTextAttributesKey("CND_PROPERTY_NOT_SEARCHABLE", CND_PROPERTY);
    public static final TextAttributesKey CND_SUBNODE = TextAttributesKey.createTextAttributesKey("CND_SUBNODE", CND_PROPERTY);

    private static final TextAttributesKey[] CND_SUBNODE_KEYS = new TextAttributesKey[]{CND_SUBNODE};
    private static final TextAttributesKey[] CND_PROPERTY_KEYS = new TextAttributesKey[]{CND_PROPERTY};
    private static final TextAttributesKey[] COMMENT_KEYS = new TextAttributesKey[]{COMMENT};
    private static final TextAttributesKey[] NAMESPACE_KEYS = new TextAttributesKey[]{NAMESPACE};
    private static final TextAttributesKey[] NODETYPE_KEYS = new TextAttributesKey[]{NODETYPE};
    private static final TextAttributesKey[] KEYWORD_KEYS = new TextAttributesKey[]{KEYWORD};
    private static final TextAttributesKey[] ATTRIBUTE_KEYS = new TextAttributesKey[]{ATTRIBUTE};
    private static final TextAttributesKey[] TYPE_KEYS = new TextAttributesKey[]{TYPE};
    private static final TextAttributesKey[] SEPARATOR_KEYS = new TextAttributesKey[]{SEPARATOR};
    private static final TextAttributesKey[] SEPARATOR_HIGHLIGHT_KEYS = new TextAttributesKey[]{SEPARATOR, KEYWORD};
    private static final TextAttributesKey[] BAD_CHAR_KEYS = new TextAttributesKey[]{BAD_CHARACTER};
    private static final TextAttributesKey[] EMPTY_KEYS = new TextAttributesKey[0];

    @Override
    public @NotNull Lexer getHighlightingLexer() {
        return new CndLexerAdapter();
    }

    @Override
    public TextAttributesKey @NotNull [] getTokenHighlights(IElementType tokenType) {
        if (tokenType.equals(CndTypes.COMMENT)) {
            return COMMENT_KEYS;
        }
        if (tokenType.equals(CndTypes.NS_NAME) || tokenType.equals(CndTypes.NS_URI)) {
            return NAMESPACE_KEYS;
        }
        if (tokenType.equals(CndTypes.NT_NAME) || tokenType.equals(CndTypes.ST_NAME) || tokenType.equals(CndTypes.SUB_TYPE) || tokenType.equals(CndTypes.SUB_DEFAULT)) {
            return NODETYPE_KEYS;
        }
        if (tokenType.equals(CndTypes.OPT)) {
            return KEYWORD_KEYS;
        }
        if (tokenType.equals(CndTypes.PROP_ATTR) || tokenType.equals(CndTypes.SUB_ATTR)) {
            return ATTRIBUTE_KEYS;
        }
        if (tokenType.equals(CndTypes.PROP_NAME)) {
            return CND_PROPERTY_KEYS;
        }
        if (tokenType.equals(CndTypes.SUB_NAME)) {
            return CND_SUBNODE_KEYS;
        }
        if (tokenType.equals(CndTypes.OPT_VALUE) || tokenType.equals(CndTypes.PROP_TYPE) || tokenType.equals(CndTypes.PROP_TYPE_MASK) || tokenType.equals(CndTypes.PROP_TYPE_MASK_OPT)) {
            return TYPE_KEYS;
        }
        if (tokenType.equals(CndTypes.PROP_CONST_START) || tokenType.equals(CndTypes.PROP_DEFAULT_EQUAL) || tokenType.equals(CndTypes.SUB_DEFAULT_EQUAL)) {
            return SEPARATOR_HIGHLIGHT_KEYS;
        }
        if (tokenType.equals(CndTypes.NS_EQUAL) || tokenType.equals(CndTypes.OPT_EQUAL) || tokenType.equals(CndTypes.PROP_TYPE_MASK_OPT_EQUAL) || tokenType.equals(CndTypes.ST_START)) {
            return SEPARATOR_KEYS;
        }
        if (tokenType.equals(TokenType.BAD_CHARACTER)) {
            return BAD_CHAR_KEYS;
        }
        return EMPTY_KEYS;
    }
}

package fr.tolc.jahia.language.cnd;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import fr.tolc.jahia.language.cnd.psi.CndTypes;
import com.intellij.psi.TokenType;

%%

%class CndLexer
%implements FlexLexer
%unicode
%function advance
%type IElementType
%eof{  return;
%eof}

CRLF=\r|\n|\r\n
WHITE_SPACE=[\ \t\f]
ANY_SPACE=\s
NO_SPACE=[^\s]
//NO_LB=[^\r\n]
COMMENT="//"[^\r\n]*
COMMENT_BLOCK="/*"~"*/"
QUOTE_SIMPLE='
QUOTE_DOUBLE=\"
EQUAL=\=
//COLON=:
ANGLE_BRACKET_LEFT=<
ANGLE_BRACKET_RIGHT=>
BRACKET_LEFT=\[
BRACKET_RIGHT=]
COMMA=,
MINUS=-
PLUS=\+
PARENTHESIS_LEFT=\(
PARENTHESIS_RIGHT=\)

%state NS, NS_SEP, NS_URI
%state NT, NT_AFTER
%state ST, ST_AFTER
%state OPT, OPT_VALUE, OPT_VALUE_AFTER
%state PROP, PROP_AFTER, PROP_TYPE, PROP_TYPE_MASK, PROP_TYPE_MASK_OPT, PROP_TYPE_MASK_OPT_VALUE, PROP_TYPE_MASK_OPT_AFTER, PROP_TYPE_AFTER, PROP_DEFAULT, PROP_DEFAULT_VALUE, PROP_DEFAULT_VALUE_AFTER, PROP_ATTR, PROP_CONST, PROP_CONST_VALUE
%state SUB, SUB_AFTER, SUB_TYPE, SUB_TYPE_AFTER, SUB_DEFAULT, SUB_DEFAULT_VALUE, SUB_DEFAULT_VALUE_AFTER, SUB_ATTR


%%

//Namespaces "<tnt = 'http://www.thomas-coquel.fr/jahia/nt/1.0'>"
<YYINITIAL> {ANGLE_BRACKET_LEFT}                            { yybegin(NS); return CndTypes.NS_START; }
<NS> [^\s=]+                                                { yybegin(NS_SEP); return CndTypes.NS_NAME; }
<NS_SEP> {
    {EQUAL}                                                 { return CndTypes.NS_EQUAL; }
    {QUOTE_SIMPLE}                                          { yybegin(NS_URI); return CndTypes.NS_URI_QUOTE; }
}
<NS_URI> {
    [^'>]+                                                  { return CndTypes.NS_URI; }
    {QUOTE_SIMPLE}                                          { return CndTypes.NS_URI_QUOTE; }
    {ANGLE_BRACKET_RIGHT}                                   { yybegin(YYINITIAL); return CndTypes.NS_END; }
}

//Node type declaration "[tnt:test]"
<YYINITIAL> {BRACKET_LEFT}                                  { yybegin(NT); return CndTypes.NT_START; }
<NT> {
    [^\]]+                                                  { return CndTypes.NT_NAME; }
    {BRACKET_RIGHT}                                         { yybegin(NT_AFTER); return CndTypes.NT_END; }
}


//Node type super types "> jnt:content, mix:component"
<NT_AFTER> {
    {ANGLE_BRACKET_RIGHT}                                   { yybegin(ST); return CndTypes.ST_START; }
    {ANY_SPACE}*[^\s>\[/+-]                                 { yybegin(OPT); yypushback(1); return TokenType.WHITE_SPACE; }
}
<ST> {
    [^\s,]+                                                 { yybegin(ST_AFTER); return CndTypes.ST_NAME; }
}
<ST_AFTER> {
    {COMMA}                                                 { yybegin(ST); return CndTypes.ST_SEP; }
    {ANY_SPACE}*[^\s,\[/+-]                                 { yybegin(OPT); yypushback(1); return TokenType.WHITE_SPACE; }
}


//Node type options "mixin", "orderable", "abstract", "extends = jmix:droppableContent", or "itemtype = layout"
<OPT> {
    [^\s=]+                                                 { return CndTypes.OPT; }
    {EQUAL}                                                 { yybegin(OPT_VALUE); return CndTypes.OPT_EQUAL; }
    {ANY_SPACE}+[^\s\[/+-]                                  { yypushback(1); return TokenType.WHITE_SPACE; }
}
<OPT_VALUE> {
    [^\s,]+                                                 { yybegin(OPT_VALUE_AFTER); return CndTypes.OPT_VALUE; }
}
<OPT_VALUE_AFTER> {
    {COMMA}                                                 { yybegin(OPT_VALUE); return CndTypes.OPT_VALUE_SEP; }
    {ANY_SPACE}*[^\s,\[/+-]                                 { yybegin(OPT); yypushback(1); return TokenType.WHITE_SPACE; }
}


//Property -
<YYINITIAL> {MINUS}                                         { yybegin(PROP); return CndTypes.PROP_START; }
<PROP> [^\s(=<]+                                            { yybegin(PROP_AFTER); return CndTypes.PROP_NAME; }
<PROP_AFTER> {
    {ANY_SPACE}*{PARENTHESIS_LEFT}                          { yybegin(PROP_TYPE); yypushback(1); return TokenType.WHITE_SPACE; }
    {ANY_SPACE}*{EQUAL}                                     { yybegin(PROP_DEFAULT); yypushback(1); return TokenType.WHITE_SPACE; }
    {ANY_SPACE}*[^\s(=<\[/+-]                               { yybegin(PROP_ATTR); yypushback(1); return TokenType.WHITE_SPACE; }
    {ANY_SPACE}*{ANGLE_BRACKET_LEFT}                        { yybegin(PROP_CONST); yypushback(1); return TokenType.WHITE_SPACE; }
}
<PROP_TYPE> {
    {PARENTHESIS_LEFT}                                      { return CndTypes.PROP_TYPE_START; }
    [^(),]+                                                 { return CndTypes.PROP_TYPE; }
    {COMMA}                                                 { yybegin(PROP_TYPE_MASK); return CndTypes.PROP_TYPE_MASK_START; }
    {PARENTHESIS_RIGHT}                                     { yybegin(PROP_TYPE_AFTER); return CndTypes.PROP_TYPE_END; }
}
<PROP_TYPE_MASK> {
    [^\[)]+                                                 { return CndTypes.PROP_TYPE_MASK; }
    {BRACKET_LEFT}                                          { yybegin(PROP_TYPE_MASK_OPT); return CndTypes.PROP_TYPE_MASK_OPTS_START; }
    {PARENTHESIS_RIGHT}                                     { yybegin(PROP_TYPE_AFTER); return CndTypes.PROP_TYPE_END; }
}
<PROP_TYPE_MASK_OPT> {
    [^\s\]=,]+                                              { return CndTypes.PROP_TYPE_MASK_OPT; }
    {EQUAL}                                                 { yybegin(PROP_TYPE_MASK_OPT_VALUE); return CndTypes.PROP_TYPE_MASK_OPT_EQUAL; }
    {COMMA}                                                 { return CndTypes.PROP_TYPE_MASK_OPT_SEP; }
    {BRACKET_RIGHT}                                         { yybegin(PROP_TYPE_MASK_OPT_AFTER); return CndTypes.PROP_TYPE_MASK_OPTS_END; }
}
<PROP_TYPE_MASK_OPT_VALUE> {
	{QUOTE_SIMPLE}[^']*{QUOTE_SIMPLE} | [^\s\],]+		    { return CndTypes.PROP_TYPE_MASK_OPT_VALUE; }
    {COMMA}                                                 { yybegin(PROP_TYPE_MASK_OPT); return CndTypes.PROP_TYPE_MASK_OPT_SEP; }
    {BRACKET_RIGHT}                                         { yybegin(PROP_TYPE_MASK_OPT_AFTER); return CndTypes.PROP_TYPE_MASK_OPTS_END; }
}
<PROP_TYPE_MASK_OPT_AFTER> {
    {PARENTHESIS_RIGHT}                                     { yybegin(PROP_TYPE_AFTER); return CndTypes.PROP_TYPE_END; }
}
<PROP_TYPE_AFTER> {
    {ANY_SPACE}*{EQUAL}                                     { yybegin(PROP_DEFAULT); yypushback(1); return TokenType.WHITE_SPACE; }
    {ANY_SPACE}*[^\s(=<\[/+-]                               { yybegin(PROP_ATTR); yypushback(1); return TokenType.WHITE_SPACE; }
    {ANY_SPACE}*{ANGLE_BRACKET_LEFT}                        { yybegin(PROP_CONST); yypushback(1); return TokenType.WHITE_SPACE; }
}
<PROP_DEFAULT> {EQUAL}                                      { yybegin(PROP_DEFAULT_VALUE); return CndTypes.PROP_DEFAULT_EQUAL; }
<PROP_DEFAULT_VALUE>
    {NO_SPACE}+({ANY_SPACE}*","{ANY_SPACE}*{NO_SPACE}+)*
    | {QUOTE_SIMPLE}[^']*{QUOTE_SIMPLE}({ANY_SPACE}*","{ANY_SPACE}*{QUOTE_SIMPLE}[^']*{QUOTE_SIMPLE})*
    | {QUOTE_DOUBLE}[^\"]*{QUOTE_DOUBLE}({ANY_SPACE}*","{ANY_SPACE}*{QUOTE_DOUBLE}[^\"]*{QUOTE_DOUBLE})*                                     { yybegin(PROP_DEFAULT_VALUE_AFTER); return CndTypes.PROP_DEFAULT; }
<PROP_DEFAULT_VALUE_AFTER> {
    {ANY_SPACE}*[^\s<\[/+-]                                 { yybegin(PROP_ATTR); yypushback(1); return TokenType.WHITE_SPACE; }
    {ANY_SPACE}*{ANGLE_BRACKET_LEFT}                        { yybegin(PROP_CONST); yypushback(1); return TokenType.WHITE_SPACE; }
}
<PROP_ATTR> {
    [^\s=]+({WHITE_SPACE}*(={WHITE_SPACE}*{NO_SPACE}+|{QUOTE_SIMPLE}[^']*{QUOTE_SIMPLE}|{QUOTE_DOUBLE}[^\"]*{QUOTE_DOUBLE}))?                { return CndTypes.PROP_ATTR; }
    {ANY_SPACE}*{ANGLE_BRACKET_LEFT}                        { yybegin(PROP_CONST); yypushback(1); return TokenType.WHITE_SPACE; }
}
<PROP_CONST> {ANGLE_BRACKET_LEFT}                           { yybegin(PROP_CONST_VALUE); return CndTypes.PROP_CONST_START; }
<PROP_CONST_VALUE>
    {NO_SPACE}+({ANY_SPACE}*","{ANY_SPACE}*{NO_SPACE}+)*
    | {QUOTE_SIMPLE}[^']*{QUOTE_SIMPLE}({ANY_SPACE}*","{ANY_SPACE}*{QUOTE_SIMPLE}[^']*{QUOTE_SIMPLE})*
    | {QUOTE_DOUBLE}[^\"]*{QUOTE_DOUBLE}({ANY_SPACE}*","{ANY_SPACE}*{QUOTE_DOUBLE}[^\"]*{QUOTE_DOUBLE})*                                     { return CndTypes.PROP_CONST; }


//Subnode +
<YYINITIAL> {PLUS}                                          { yybegin(SUB); return CndTypes.SUB_START; }
<SUB> [^\s(=]+                                              { yybegin(SUB_AFTER); return CndTypes.SUB_NAME; }
<SUB_AFTER> {
    {ANY_SPACE}*{PARENTHESIS_LEFT}                          { yybegin(SUB_TYPE); yypushback(1); return TokenType.WHITE_SPACE; }
    {ANY_SPACE}*{EQUAL}                                     { yybegin(SUB_DEFAULT); yypushback(1); return TokenType.WHITE_SPACE; }
    {ANY_SPACE}*[^\s(=\[/+-]                                { yybegin(SUB_ATTR); yypushback(1); return TokenType.WHITE_SPACE; }
}
<SUB_TYPE> {
    {PARENTHESIS_LEFT}                                      { return CndTypes.SUB_TYPES_START; }
    [^(),]+                                                 { return CndTypes.SUB_TYPE; }
    {COMMA}                                                 { return CndTypes.SUB_TYPE_SEP; }
    {PARENTHESIS_RIGHT}                                     { yybegin(SUB_TYPE_AFTER); return CndTypes.SUB_TYPES_END; }
}
<SUB_TYPE_AFTER> {
    {ANY_SPACE}*{EQUAL}                                     { yybegin(SUB_DEFAULT); yypushback(1); return TokenType.WHITE_SPACE; }
    {ANY_SPACE}*[^\s=\[/+-]                                 { yybegin(SUB_ATTR); yypushback(1); return TokenType.WHITE_SPACE; }
}
<SUB_DEFAULT> {EQUAL}                                       { yybegin(SUB_DEFAULT_VALUE); return CndTypes.SUB_DEFAULT_EQUAL; }
<SUB_DEFAULT_VALUE> {NO_SPACE}+                             { yybegin(SUB_DEFAULT_VALUE_AFTER); return CndTypes.SUB_DEFAULT; }
<SUB_DEFAULT_VALUE_AFTER> {ANY_SPACE}*[^\s\[/+-]            { yybegin(SUB_ATTR); yypushback(1); return TokenType.WHITE_SPACE; }
<SUB_ATTR> [^\s=]+({WHITE_SPACE}*(={WHITE_SPACE}*{NO_SPACE}+|{QUOTE_SIMPLE}[^']*{QUOTE_SIMPLE}|{QUOTE_DOUBLE}[^\"]*{QUOTE_DOUBLE}))?         { return CndTypes.SUB_ATTR; }


{COMMENT}|{COMMENT_BLOCK}                                   { return CndTypes.COMMENT; }

{WHITE_SPACE}                                               { return TokenType.WHITE_SPACE; }

{CRLF}                                                      { yybegin(YYINITIAL); return CndTypes.CRLF; }

.                                                           { return TokenType.BAD_CHARACTER; }

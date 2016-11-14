package fr.tolc.jahia.intellij.plugin.cnd;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndTypes;
import com.intellij.psi.TokenType;

%%

%class CndLexer
%implements FlexLexer
%unicode
%caseless
%function advance
%type IElementType
%eof{  return;
%eof}

CRLF= \n|\r|\r\n
WHITE_SPACE=[\ \t\f]
//FIRST_VALUE_CHARACTER=[^ \n\r\f\\] | "\\"{CRLF} | "\\".
//VALUE_CHARACTER=[^\n\r\f\\] | "\\"{CRLF} | "\\".
//END_OF_LINE_COMMENT=("#"|"!")[^\r\n]*
//SEPARATOR=[:=]
//KEY_CHARACTER=[^:=\ \n\r\t\f\\] | "\\ "

COMMENT="//"[^\r\n]*
COMMENT_BLOCK="/*"~"*/"
//~ = upto

CHARS=[:jletter:][:jletterdigit:]*
ATTRIBUTE=[^\r\n\ =+\-\[]([^\r\n\ ]+({WHITE_SPACE}*"="{WHITE_SPACE}*)?[^\r\n\ ]+)?

%state NAMESPACE, NAMESPACE_URI, NODETYPE_NAMESPACE, NODETYPE, NODETYPE_DONE, SUPER_TYPE_NAMESPACE, SUPER_TYPE_NAME, AFTER_SUPER_TYPE_NAME, OPTIONS
%state EXTENDS, EXTEND_NAMESPACE, EXTEND, ITEMTYPE
%state PROPERTY, PROPERTY_TYPE, PROPERTY_MASK_OPTION_NAME, PROPERTY_MASK, PROPERTY_MASK_OPTION, PROPERTY_DEFAULT, PROPERTY_DEFAULT_VALUE, PROPERTY_ATTRIBUTES, PROPERTY_CONSTRAINT, PROPERTY_CONSTRAINT_NEWLINE
%state NODE, NODE_NAMESPACE, NODE_NODETYPE, NODE_DEFAULT, NODE_DEFAULT_VALUE_NAMESPACE, NODE_DEFAULT_VALUE, NODE_ATTRIBUTES


%%

<YYINITIAL> {COMMENT_BLOCK}				{ return CndTypes.COMMENT; }

//Namespaces "<tnt = 'http://www.thomas-coquel.fr/jahia/nt/1.0'>"
<YYINITIAL> "<" 									{ yybegin(NAMESPACE); return CndTypes.LEFT_ANGLE_BRACKET; }
<NAMESPACE> {
	{CHARS}											{ return CndTypes.NAMESPACE_NAME; }
    "="												{ return CndTypes.EQUAL; }
    "'"												{ yybegin(NAMESPACE_URI); return CndTypes.SINGLE_QUOTE; }
}
<NAMESPACE_URI>	{
	[^'>]+											{ return CndTypes.NAMESPACE_URI; }
	"'"												{ return CndTypes.SINGLE_QUOTE; }
	">" 											{ yybegin(YYINITIAL); return CndTypes.RIGHT_ANGLE_BRACKET; }
}





//Node type declaration "[tnt:test]"
<YYINITIAL> "["									{ yybegin(NODETYPE_NAMESPACE); return CndTypes.LEFT_BRACKET; }
<NODETYPE_NAMESPACE> {CHARS}					{ yybegin(NODETYPE); return CndTypes.NAMESPACE_NAME; }
<NODETYPE> {
	":"                                         { return CndTypes.COLON; }
	{CHARS}                       				{ return CndTypes.NODE_TYPE_NAME; }
	"]"							                {  yybegin(NODETYPE_DONE); return CndTypes.RIGHT_BRACKET; }
}
<NODETYPE_DONE> {
	[:jletter:]+								{ yybegin(OPTIONS); return CndTypes.OPTION; }
	">"											{ yybegin(SUPER_TYPE_NAMESPACE); return CndTypes.RIGHT_ONLY_ANGLE_BRACKET; }
}


//Node type super types "> jnt:content, smix:lmcuComponent"
<SUPER_TYPE_NAMESPACE> {
	{CHARS}										{ return CndTypes.NAMESPACE_NAME; }
	":"											{ yybegin(SUPER_TYPE_NAME); return CndTypes.COLON; }
}
<SUPER_TYPE_NAME> {
	{CHARS}									    { yybegin(AFTER_SUPER_TYPE_NAME); return CndTypes.NODE_TYPE_NAME; }
    {CRLF}									    { yybegin(YYINITIAL); yypushback(yylength()); return CndTypes.NODE_TYPE_NAME; }     //For completion purposes
}
<AFTER_SUPER_TYPE_NAME> {
	","											{ yybegin(SUPER_TYPE_NAMESPACE); return CndTypes.COMMA; }
    [:jletter:]+								{ yybegin(OPTIONS); return CndTypes.OPTION; }
}

//Node type options "mixin", "orderable", "abstract", ... at the end of line or on a new line
<OPTIONS> {
	[:jletter:]+								{ return CndTypes.OPTION; }
}


//Options, Extends or Itemtype at the start of a line
<YYINITIAL> {
	[:jletter:]+								{ 
													if ("extends".equalsIgnoreCase(yytext().toString())) { yybegin(EXTENDS); return CndTypes.EXTENDS; } 
												  	else if ("itemtype".equalsIgnoreCase(yytext().toString())) { yybegin(ITEMTYPE); return CndTypes.ITEMTYPE; } 
    											  	else { yybegin(OPTIONS); return CndTypes.OPTION; } 
												}
}



//Extends
//<YYINITIAL> "extends"                           	{ yybegin(EXTENDS); return CndTypes.EXTENDS; }
<EXTENDS> "="										{ yybegin(EXTEND_NAMESPACE); return CndTypes.EQUAL; }
<EXTEND_NAMESPACE> {CHARS}							{ yybegin(EXTEND); return CndTypes.NAMESPACE_NAME; }
<EXTEND> {
	":"												{ return CndTypes.COLON; }
	{CHARS}											{ return CndTypes.NODE_TYPE_NAME; }
	","												{ yybegin(EXTEND_NAMESPACE); return CndTypes.COMMA; }
}

//Item type
//<YYINITIAL> "itemtype"                          				{ yybegin(ITEMTYPE); return CndTypes.ITEMTYPE; }
<ITEMTYPE> {
	"="                           								{ return CndTypes.EQUAL; }
	[^\r\n\ =]+                                                { return CndTypes.ITEMTYPE_TYPE; }
}





//Node type property -
<YYINITIAL> "-"									{ yybegin(PROPERTY); return CndTypes.MINUS; }
<PROPERTY> {
	[:jletter:]([:jletterdigit:]|":"|".")* | "*"		{ return CndTypes.PROPERTY_NAME; }
	"("											{ yybegin(PROPERTY_TYPE); return CndTypes.LEFT_PARENTHESIS; }
}
<PROPERTY_TYPE> {
	[:jletter:]+								{ return CndTypes.PROPERTY_TYPE; }
	","											{ yybegin(PROPERTY_MASK); return CndTypes.COMMA; }
	")"											{ yybegin(PROPERTY_DEFAULT); return CndTypes.RIGHT_PARENTHESIS; }
}

<PROPERTY_MASK> {
	[:jletter:]+								{ return CndTypes.PROPERTY_MASK; }
	"["											{ yybegin(PROPERTY_MASK_OPTION_NAME); return CndTypes.LEFT_BRACKET; }
	")"											{ yybegin(PROPERTY_DEFAULT); return CndTypes.RIGHT_PARENTHESIS; }
}
<PROPERTY_MASK_OPTION_NAME> [^\r\n\ \]=)',]+		{ yybegin(PROPERTY_MASK_OPTION); return CndTypes.PROPERTY_MASK_OPTION; }
<PROPERTY_MASK_OPTION> {
	"="											{ return CndTypes.EQUAL; }
	"'"[^\r\n\]']+"'" | [^\r\n\ \]=)',]+		{ return CndTypes.PROPERTY_MASK_OPTION_VALUE; }
	"]"											{ return CndTypes.RIGHT_BRACKET; }
	","											{ yybegin(PROPERTY_MASK_OPTION_NAME); return CndTypes.COMMA; }
	")"											{ yybegin(PROPERTY_DEFAULT); return CndTypes.RIGHT_PARENTHESIS; }
}

<PROPERTY_DEFAULT> {
	{CRLF}*{WHITE_SPACE}*"="					{ yybegin(PROPERTY_DEFAULT_VALUE); return CndTypes.EQUAL; }
	{CRLF}+{WHITE_SPACE}*"<"					{ yybegin(PROPERTY_CONSTRAINT_NEWLINE); return CndTypes.LEFT_ONLY_ANGLE_BRACKET; }
	"<"											{ yybegin(PROPERTY_CONSTRAINT); return CndTypes.LEFT_ONLY_ANGLE_BRACKET; }
	{ATTRIBUTE}									{ yybegin(PROPERTY_ATTRIBUTES); return CndTypes.PROPERTY_ATTRIBUTE; }
	{CRLF}+{WHITE_SPACE}*{ATTRIBUTE}			{ yypushback(yytext().toString().replaceAll("\\r", "").replaceAll("\\n", "").trim().length()); yybegin(PROPERTY_ATTRIBUTES); return TokenType.WHITE_SPACE; }
}
<PROPERTY_DEFAULT_VALUE> {
	[^\r\n\ ]+ | "'"[^\r\n]+"'"					{ yybegin(PROPERTY_ATTRIBUTES); return CndTypes.PROPERTY_DEFAULT_VALUE; }
}

<PROPERTY_ATTRIBUTES> {
	{CRLF}+{WHITE_SPACE}*"<"					{ yybegin(PROPERTY_CONSTRAINT_NEWLINE); return CndTypes.LEFT_ONLY_ANGLE_BRACKET; }
	"<"											{ yybegin(PROPERTY_CONSTRAINT); return CndTypes.LEFT_ONLY_ANGLE_BRACKET; }
	{ATTRIBUTE}									{ return CndTypes.PROPERTY_ATTRIBUTE; }
	{CRLF}+{WHITE_SPACE}*{ATTRIBUTE}			{ yypushback(yytext().toString().replaceAll("\\r", "").replaceAll("\\n", "").trim().length()); return TokenType.WHITE_SPACE; }
}

<PROPERTY_CONSTRAINT> [^\r\n\ \t\f][^\r\n]+[^\r\n\ \t\f]					{ return CndTypes.PROPERTY_CONSTRAINT_VALUE; }
<PROPERTY_CONSTRAINT_NEWLINE> "'"[^\r\n]+"'" ({WHITE_SPACE}*","{WHITE_SPACE}*{CRLF}*{WHITE_SPACE}*"'"[^\r\n']+"'")*			{ return CndTypes.PROPERTY_CONSTRAINT_VALUE; }






//Node type nodes +
<YYINITIAL> "+"										{ yybegin(NODE); return CndTypes.PLUS; }
<NODE> {
	[:jletter:]([:jletterdigit:]|:)* | "*"			{ return CndTypes.NODE_NAME; }
	"("												{ yybegin(NODE_NAMESPACE); return CndTypes.LEFT_PARENTHESIS; }
}
<NODE_NAMESPACE> {CHARS}							{ yybegin(NODE_NODETYPE); return CndTypes.NAMESPACE_NAME; }
<NODE_NODETYPE> {
	":"                                             { return CndTypes.COLON; }
	{CHARS}                   						{ return CndTypes.NODE_TYPE_NAME; }
	","							                    { yybegin(NODE_NAMESPACE); return CndTypes.COMMA; }
	")"							                    { yybegin(NODE_DEFAULT); return CndTypes.RIGHT_PARENTHESIS; }
}

<NODE_DEFAULT> {
	{CRLF}*{WHITE_SPACE}*"="						{ yybegin(NODE_DEFAULT_VALUE_NAMESPACE); return CndTypes.EQUAL; }
	{ATTRIBUTE}     								{ yybegin(NODE_ATTRIBUTES); return CndTypes.NODE_ATTRIBUTE; }
	{CRLF}+{WHITE_SPACE}*{ATTRIBUTE}				{ yypushback(yytext().toString().replaceAll("\\r", "").replaceAll("\\n", "").trim().length()); yybegin(NODE_ATTRIBUTES); return TokenType.WHITE_SPACE; }
}
<NODE_DEFAULT_VALUE_NAMESPACE> {CHARS}				{ yybegin(NODE_DEFAULT_VALUE); return CndTypes.NAMESPACE_NAME; }
<NODE_DEFAULT_VALUE> {
	":"                                             { return CndTypes.COLON; }
	{CHARS}                    						{ yybegin(NODE_ATTRIBUTES); return CndTypes.NODE_TYPE_NAME; }
}

<NODE_ATTRIBUTES> {
	{ATTRIBUTE}  									{ return CndTypes.NODE_ATTRIBUTE; }
	{CRLF}+{WHITE_SPACE}*{ATTRIBUTE}				{ yypushback(yytext().toString().replaceAll("\\r", "").replaceAll("\\n", "").trim().length()); return TokenType.WHITE_SPACE; }	
}




{COMMENT}						{ return CndTypes.COMMENT; }

{WHITE_SPACE}+                  { return TokenType.WHITE_SPACE; }

{CRLF}                          { yybegin(YYINITIAL); return CndTypes.CRLF; }

.                               { return TokenType.BAD_CHARACTER; }
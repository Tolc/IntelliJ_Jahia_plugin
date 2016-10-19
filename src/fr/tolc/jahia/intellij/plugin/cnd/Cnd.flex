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
OPTIONS="mixin"|"abstract"|"orderable"|"noquery"
//PROPERTY_ATTRIBUTES="mandatory"|"protected"|"primary"|"i18n"|"internationalized"|"sortable"|"hidden"|"multiple"|"nofulltext"|"indexed="("'")?("no"|"tokenized"|"untokenized")("'")?|"analyzer='keyword'"|"autocreated"|("boost"|"scoreboost")"="[:digit:]+("."[:digit:]+){0,1}|"onconflict="("sum"|"latest"|"oldest"|"min"|"max"|"ignore")|"facetable"|"hierarchical"|"noqueryorder"|"itemtype = "("content"|"metadata"|"layout"|"options"|"codeEditor")|("copy"|"version"|"initialize"|"compute"|"ignore"|"abort")|"queryops '" (("<"|"<="|"<>"|"="|">"|">="|"like")(","){0,1})+ "'"
NODE_ATTRIBUTES="mandatory"|"autocreated"|("copy"|"version"|"initialize"|"compute"|"ignore"|"abort")|"multiple"|"protected"|"sns"

//See https://www.jahia.com/fr/communaute/etendre/techwiki/content-editing-uis/input-masks

%state NAMESPACE, NODETYPE_NAMESPACE, NODETYPE, SUPER_TYPES_NAMESPACE, SUPER_TYPES, OPTIONS
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
    "'"												{ return CndTypes.SINGLE_QUOTE; }
    "http"(s){0,1}":\/\/"[A-Za-z0-9.\/\-_]+			{ return CndTypes.NAMESPACE_URI; }
	">" 											{ yybegin(YYINITIAL); return CndTypes.RIGHT_ANGLE_BRACKET; }
}





//Node type declaration "[tnt:test]"
<YYINITIAL> "["									{ yybegin(NODETYPE_NAMESPACE); return CndTypes.LEFT_BRACKET; }
<NODETYPE_NAMESPACE> {CHARS}					{ yybegin(NODETYPE); return CndTypes.NAMESPACE_NAME; }
<NODETYPE> {
	{OPTIONS}									{ yybegin(OPTIONS); return CndTypes.OPTION; }
	":"                                         { return CndTypes.COLON; }
	{CHARS}                       				{ return CndTypes.NODE_TYPE_NAME; }
	"]"							                { return CndTypes.RIGHT_BRACKET; }
	">"							                { yybegin(SUPER_TYPES_NAMESPACE); return CndTypes.RIGHT_ONLY_ANGLE_BRACKET; }
}

//Node type super types "> jnt:content, smix:lmcuComponent"
<SUPER_TYPES_NAMESPACE> {
	{OPTIONS}									{ yybegin(OPTIONS); return CndTypes.OPTION; }
	{CHARS}										{ yybegin(SUPER_TYPES); return CndTypes.NAMESPACE_NAME; }
}
<SUPER_TYPES> {
	{OPTIONS}									{ yybegin(OPTIONS); return CndTypes.OPTION; }
	":"											{ return CndTypes.COLON; }
	{CHARS}										{ return CndTypes.NODE_TYPE_NAME; }
	","											{ yybegin(SUPER_TYPES_NAMESPACE); return CndTypes.COMMA; }
}

//Node type options "mixin", "orderable", "abstract", ... at the end of line or on a new line
<OPTIONS> {
	{OPTIONS}									{ return CndTypes.OPTION; }
}
<YYINITIAL> {
	{OPTIONS}									{ yybegin(OPTIONS); return CndTypes.OPTION; }
}





//Extends
<YYINITIAL> "extends"                           	{ yybegin(EXTENDS); return CndTypes.EXTENDS; }
<EXTENDS> "="										{ yybegin(EXTEND_NAMESPACE); return CndTypes.EQUAL; }
<EXTEND_NAMESPACE> {CHARS}							{ yybegin(EXTEND); return CndTypes.NAMESPACE_NAME; }
<EXTEND> {
	":"												{ return CndTypes.COLON; }
	{CHARS}											{ return CndTypes.NODE_TYPE_NAME; }
	","												{ yybegin(EXTEND_NAMESPACE); return CndTypes.COMMA; }
}

//Item type
<YYINITIAL> "itemtype"                          				{ yybegin(ITEMTYPE); return CndTypes.ITEMTYPE; }
<ITEMTYPE> {
	"="                           								{ return CndTypes.EQUAL; }
//	"default"|"options"|"layout"|"metadata"|"content"|"classification"|"permissions"|"listOrdering"|"contributeMode"|"propertiesView"           { return CndTypes.ITEMTYPE_TYPE; }
	[^\r\n\ ]+                                                { return CndTypes.ITEMTYPE_TYPE; }
}





//Node type property -
<YYINITIAL> "-"									{ yybegin(PROPERTY); return CndTypes.MINUS; }
<PROPERTY> {
	[:jletter:]([:jletterdigit:]|":"|".")* | "*"		{ return CndTypes.PROPERTY_NAME; }
	"("											{ yybegin(PROPERTY_TYPE); return CndTypes.LEFT_PARENTHESIS; }
}
<PROPERTY_TYPE> {
//	"string"|"long"|"double"|"decimal"|"path"|"uri"|"boolean"|"date"|"binary"|"weakreference"|"name"|"reference"|"UNDEFINED"		{ return CndTypes.PROPERTY_TYPE; }
	[:jletter:]+								{ return CndTypes.PROPERTY_TYPE; }
	","											{ yybegin(PROPERTY_MASK); return CndTypes.COMMA; }
	")"											{ yybegin(PROPERTY_DEFAULT); return CndTypes.RIGHT_PARENTHESIS; }
}

<PROPERTY_MASK> {
//	"text"|"richtext"|"textarea"|"choicelist"|"datetimepicker"|"datepicker"|"picker"|"color"|"category"|"checkbox"|"fileupload"|"tag"|"file"		{ return CndTypes.PROPERTY_MASKS; }
	[:jletter:]+								{ return CndTypes.PROPERTY_MASK; }
	"["											{ yybegin(PROPERTY_MASK_OPTION_NAME); return CndTypes.LEFT_BRACKET; }
	")"											{ yybegin(PROPERTY_DEFAULT); return CndTypes.RIGHT_PARENTHESIS; }
}
<PROPERTY_MASK_OPTION_NAME> "resourceBundle"|"country"|"templates"|"templatesNode"|"users"|"nodetypes"|"subnodetypes"|"nodes"|"menus"|"script"|"flag"|"sortableFieldnames"|"moduleImage"|"linkerProps"|"workflow"|"workflowTypes"|"sort"|"componenttypes"|"autoSelectParent"|"type"|"image"|"dependentProperties"|"mime"|"renderModes"|"permissions"|"autocomplete"|"separator"|"folder"|"root"		{ yybegin(PROPERTY_MASK_OPTION); return CndTypes.PROPERTY_MASK_OPTION; }
<PROPERTY_MASK_OPTION> {
	"="											{ return CndTypes.EQUAL; }
	"'"[^\r\n\]']+"'" | [^\r\n\ \]=)',]+		{ return CndTypes.PROPERTY_MASK_OPTION_VALUE; }
	"]"											{ return CndTypes.RIGHT_BRACKET; }
	","											{ yybegin(PROPERTY_MASK_OPTION_NAME); return CndTypes.COMMA; }
	")"											{ yybegin(PROPERTY_DEFAULT); return CndTypes.RIGHT_PARENTHESIS; }
}

<PROPERTY_DEFAULT> {
//	{PROPERTY_ATTRIBUTES}						{ yybegin(PROPERTY_ATTRIBUTES); return CndTypes.PROPERTY_ATTRIBUTE; }
	"="											{ yybegin(PROPERTY_DEFAULT_VALUE); return CndTypes.EQUAL; }
	{CRLF}+{WHITE_SPACE}*"<"					{ yybegin(PROPERTY_CONSTRAINT_NEWLINE); return CndTypes.LEFT_ONLY_ANGLE_BRACKET; }
	"<"											{ yybegin(PROPERTY_CONSTRAINT); return CndTypes.LEFT_ONLY_ANGLE_BRACKET; }
	[^\r\n\ =][^\r\n\ ]+({WHITE_SPACE}*"="{WHITE_SPACE}*)?[^\r\n\ ]+									{ yybegin(PROPERTY_ATTRIBUTES); return CndTypes.PROPERTY_ATTRIBUTE; }
}
<PROPERTY_DEFAULT_VALUE> {
	[^\r\n\ ]+ | "'"[^\r\n]+"'"					{ yybegin(PROPERTY_ATTRIBUTES); return CndTypes.PROPERTY_DEFAULT_VALUE; }
}

<PROPERTY_ATTRIBUTES> {
//	{PROPERTY_ATTRIBUTES}						{ return CndTypes.PROPERTY_ATTRIBUTE; }
	{CRLF}+{WHITE_SPACE}*"<"					{ yybegin(PROPERTY_CONSTRAINT_NEWLINE); return CndTypes.LEFT_ONLY_ANGLE_BRACKET; }
	"<"											{ yybegin(PROPERTY_CONSTRAINT); return CndTypes.LEFT_ONLY_ANGLE_BRACKET; }
	[^\r\n\ =][^\r\n\ ]+({WHITE_SPACE}*"="{WHITE_SPACE}*)?[^\r\n\ ]+									{ return CndTypes.PROPERTY_ATTRIBUTE; }
}

<PROPERTY_CONSTRAINT> [^\r\n]+					{ return CndTypes.PROPERTY_CONSTRAINT_VALUE; }
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
	"="												{ yybegin(NODE_DEFAULT_VALUE_NAMESPACE); return CndTypes.EQUAL; }
	{NODE_ATTRIBUTES}								{ yybegin(NODE_ATTRIBUTES); return CndTypes.NODE_ATTRIBUTE; }
}
<NODE_DEFAULT_VALUE_NAMESPACE> {CHARS}				{ yybegin(NODE_DEFAULT_VALUE); return CndTypes.NAMESPACE_NAME; }
<NODE_DEFAULT_VALUE> {
	":"                                             { return CndTypes.COLON; }
	{CHARS}                    						{ yybegin(NODE_ATTRIBUTES); return CndTypes.NODE_TYPE_NAME; }
}

<NODE_ATTRIBUTES> {NODE_ATTRIBUTES}					{ return CndTypes.NODE_ATTRIBUTE; }




{COMMENT}						{ return CndTypes.COMMENT; }

{WHITE_SPACE}+                  { return TokenType.WHITE_SPACE; }

{CRLF}                          { yybegin(YYINITIAL); return CndTypes.CRLF; }

.                               { return TokenType.BAD_CHARACTER; }
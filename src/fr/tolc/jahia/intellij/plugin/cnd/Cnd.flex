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
COMMENT_BLOCK="/*" ~"*/"
//~ = upto

NAMESPACE_START="<"
NAMESPACE_CHARS=[:jletter:][:jletterdigit:]*
NAMESPACE_SEPARATOR="="
NAMESPACE_URI_BEGIN="'"
NAMESPACE_URI="http"(s){0,1}":\/\/"[A-Za-z0-9.\/\-_]+
NAMESPACE_URI_END="'"
NAMESPACE_END=">"

NODE_TYPE_DECLARATION_START="["
NODE_TYPE_DECLARATION_SEPARATOR=":"
NODE_TYPE_CHARS=[:jletter:][:jletterdigit:]*
NODE_TYPE_DECLARATION_END="]"

NODE_TYPE_SUPER_TYPES_START=">"
NODE_TYPE_SUPER_TYPES_ANOTHER=","

NODE_TYPE_OPTIONS_MIXIN="mixin"
NODE_TYPE_OPTIONS_ORDERABLE="orderable"
NODE_TYPE_OPTIONS_ABSTRACT="abstract"



PROPERTY_NAME_CHARS=[:jletter:]([:jletterdigit:]|:)*
PROPERTY_TYPE_START="("
PROPERTY_TYPE_END=")"
PROPERTY_DEFAULT="="


PROPERTY_MINUS_START="-"


PROPERTY_PLUS_START="+"
PROPERTY_PLUS_NAME_CHARS={PROPERTY_NAME_CHARS}|"*"
PROPERTY_PLUS_DEFAULT_VALUE={NAMESPACE_CHARS}{NODE_TYPE_DECLARATION_SEPARATOR}{NODE_TYPE_CHARS}
PROPERTY_ATTRIBUTE="mandatory"|"protected"|"primary"|"i18n"|"sortable"|"hidden"|"multiple"|"nofulltext"|"indexed="("no"|"'untokenized'")|"analyzer='keyword'"|"autocreated"|"boost="[:digit:]"."[:digit:]|"onconflict=sum"|"facetable"
PROPERTY_PLUS_ATTRIBUTE="autocreated"|"version"
PROPERTY_CONSTRAINT_START="<"
PROPERTY_CONSTRAINT=("'"[^\r\n]+"',"{WHITE_SPACE}*)*"'"[^\r\n]+"'"|([:jletterdigit:]+","{WHITE_SPACE}*)*[:jletterdigit:]+|{NAMESPACE_CHARS}{NODE_TYPE_DECLARATION_SEPARATOR}{NODE_TYPE_CHARS}

EXTEND_START="extends"{WHITE_SPACE}*"="
EXTEND_ANOTHER=","
EXTEND_ITEM_TYPE_START="itemtype"{WHITE_SPACE}*"="
EXTEND_ITEM_TYPE_VALUE="default"|"options"|"layout"|"metadata"|"content"


PROPERTY_TYPE_PRECISION_COMMA={WHITE_SPACE}*","{WHITE_SPACE}*

PROPERTY_TYPE_BINARY="binary"
PROPERTY_TYPE_LONG="long"
PROPERTY_DEFAULT_LONG=[:digit:]+
PROPERTY_TYPE_DOUBLE="double"
PROPERTY_DEFAULT_DOUBLE=[:digit:]+"."[:digit:]+
PROPERTY_TYPE_BOOLEAN="boolean"
PROPERTY_DEFAULT_BOOLEAN="true"|"false"
PROPERTY_TYPE_DATE="date"
PROPERTY_DEFAULT_DATE="now()"|"'"[:digit:]{4}"-"(0[1-9]|1[0-2])"-"(0[1-9]|[1-2][0-9]|3[0-1])"T"([0-1][0-9]|2[0-3])":"([0-5][0-9])":"([0-5][0-9])"."([0-9][0-9][0-9])"+"([0-1][0-9]|2[0-3])":"([0-5][0-9])"'"

PROPERTY_TYPE_STRING="string"
PROPERTY_TYPE_STRING_PRECISION_TEXT="text"|"richtext"|"textarea"
PROPERTY_TYPE_STRING_TEXT={PROPERTY_TYPE_STRING} ({PROPERTY_TYPE_PRECISION_COMMA} {PROPERTY_TYPE_STRING_PRECISION_TEXT}){0,1}
PROPERTY_DEFAULT_STRING_TEXT="'"[^\r\n]*"'"
PROPERTY_TYPE_STRING_PRECISION_CHOICELIST="choicelist"("["("resourceBundle"|("country"(",flag"){0,1}))"]"){0,1}
PROPERTY_TYPE_STRING_CHOICELIST={PROPERTY_TYPE_STRING} {PROPERTY_TYPE_PRECISION_COMMA} {PROPERTY_TYPE_STRING_PRECISION_CHOICELIST}
PROPERTY_DEFAULT_STRING_CHOICELIST=[^\r\n\s]+

PROPERTY_TYPE_WEAKREFERENCE="weakreference"
PROPERTY_TYPE_WEAKREFERENCE_PRECISION_PICKER="picker[type='"({PROPERTY_TYPE_WEAKREFERENCE_PRECISION_PICKER_TYPE})"'"(",mime='"[-\w+]+"/"([-\w+]+|"*")"'"){0,1}"]"
PROPERTY_TYPE_WEAKREFERENCE_PRECISION_PICKER_TYPE="image"|"file"|"page"|"category"|"folder"|"contentfolder"|"portlet"|"editorial"|"editoriallink"|"site"|"user"|"usergroup"
PROPERTY_TYPE_WEAKREFERENCE_PRECISION_CATEGORY="category"("[autoSelectParent="("true"|"false")"]"){0,1}
PROPERTY_TYPE_WEAKREFERENCE_PRECISION_FILE="file"("[folder]"){0,1}
PROPERTY_TYPE_WEAKREFERENCE_FINAL={PROPERTY_TYPE_WEAKREFERENCE} ({PROPERTY_TYPE_PRECISION_COMMA} ({PROPERTY_TYPE_WEAKREFERENCE_PRECISION_PICKER} | {PROPERTY_TYPE_WEAKREFERENCE_PRECISION_CATEGORY} | {PROPERTY_TYPE_WEAKREFERENCE_PRECISION_FILE})){0,1}


//See https://www.jahia.com/fr/communaute/etendre/techwiki/content-editing-uis/input-masks

%state NAMESPACE_BEGIN
%state NAMESPACE_NAME_DONE
%state NAMESPACE_EQUAL_DONE
%state NAMESPACE_URI_DONE
%state NAMESPACES_OVER
%state NODE_TYPE_DECLARATION_BEGIN
%state NODE_TYPE_DECLARATION_NAMESPACE_DONE
%state NODE_TYPE_DECLARATION_COLON_DONE
%state NODE_TYPE_DECLARATION_TYPE_DONE
%state NODE_TYPE_INHERITANCE
%state NODE_TYPE_INHERITANCE_BEGIN
%state NODE_TYPE_INHERITANCE_NAMESPACE_DONE
%state NODE_TYPE_INHERITANCE_COLON_DONE
%state NODE_TYPE_INHERITANCE_TYPE_DONE
%state PROPERTY_MINUS_BEGIN
%state PROPERTY_NAME_DONE
%state PROPERTY_TYPE_BEGIN
%state PROPERTY_TYPE_DONE
%state PROPERTY_TYPE_DONE_BINARY, PROPERTY_ADDENDUM_BINARY
%state PROPERTY_TYPE_DONE_LONG, PROPERTY_ADDENDUM_LONG,PROPERTY_DEFAULT_BEGIN_LONG
%state PROPERTY_TYPE_DONE_DOUBLE, PROPERTY_ADDENDUM_DOUBLE,PROPERTY_DEFAULT_BEGIN_DOUBLE
%state PROPERTY_TYPE_DONE_BOOLEAN, PROPERTY_ADDENDUM_BOOLEAN,PROPERTY_DEFAULT_BEGIN_BOOLEAN
%state PROPERTY_TYPE_DONE_DATE, PROPERTY_ADDENDUM_DATE,PROPERTY_DEFAULT_BEGIN_DATE
%state PROPERTY_TYPE_DONE_STRING_TEXT, PROPERTY_ADDENDUM_STRING_TEXT,PROPERTY_DEFAULT_BEGIN_STRING_TEXT
%state PROPERTY_TYPE_DONE_STRING_CHOICELIST, PROPERTY_ADDENDUM_STRING_CHOICELIST,PROPERTY_DEFAULT_BEGIN_STRING_CHOICELIST
%state PROPERTY_TYPE_DONE_WEAKREFERENCE, PROPERTY_ADDENDUM_WEAKREFERENCE
%state PROPERTY_ADDENDUM
%state PROPERTY_ADDENDUM_ATTR
%state PROPERTY_DEFAULT_BEGIN
%state PROPERTY_CONSTRAINT_BEGIN
%state PROPERTY_PLUS_BEGIN
%state PROPERTY_PLUS_NAME_DONE
%state PROPERTY_PLUS_TYPE_BEGIN
%state PROPERTY_PLUS_NAMESPACE_DONE
%state PROPERTY_PLUS_COLON_DONE
%state PROPERTY_PLUS_TYPE_DONE
%state PROPERTY_PLUS_ADDENDUM
%state PROPERTY_PLUS_DEFAULT_BEGIN
%state EXTEND_BEGIN
%state EXTEND_NAMESPACE_DONE
%state EXTEND_COLON_DONE
%state EXTEND_DONE
%state EXTEND_ITEM_BEGIN



%%

<YYINITIAL> {COMMENT_BLOCK}				{ return CndTypes.COMMENT; }

//Namespaces "<snt = 'http://www.thomas-coquel.fr/jahia/nt/1.0'>"
<YYINITIAL> {NAMESPACE_START}                   { yybegin(NAMESPACE_BEGIN); return CndTypes.NAMESPACE_OPENING; }
<NAMESPACE_BEGIN> {NAMESPACE_CHARS}             { yybegin(NAMESPACE_NAME_DONE); return CndTypes.NAMESPACE_NAME; }
<NAMESPACE_NAME_DONE> {NAMESPACE_SEPARATOR}     { yybegin(NAMESPACE_EQUAL_DONE); return CndTypes.NAMESPACE_EQUAL; }
<NAMESPACE_EQUAL_DONE> {NAMESPACE_URI}          { yybegin(NAMESPACE_URI_DONE); return CndTypes.NAMESPACE_URI; }
<NAMESPACE_URI_DONE> {NAMESPACE_END}            { yybegin(YYINITIAL); return CndTypes.NAMESPACE_CLOSING; }


//Node type declaration "[snt:test]"
<YYINITIAL> {NODE_TYPE_DECLARATION_START}                                   { yybegin(NODE_TYPE_DECLARATION_BEGIN); return CndTypes.NODE_TYPE_DECLARATION_OPENING; }
<NODE_TYPE_DECLARATION_BEGIN> {NAMESPACE_CHARS}                             { yybegin(NODE_TYPE_DECLARATION_NAMESPACE_DONE); return CndTypes.NODE_TYPE_NAMESPACE; }
<NODE_TYPE_DECLARATION_NAMESPACE_DONE> {NODE_TYPE_DECLARATION_SEPARATOR}    { yybegin(NODE_TYPE_DECLARATION_COLON_DONE); return CndTypes.NODE_TYPE_DECLARATION_COLON; }
<NODE_TYPE_DECLARATION_COLON_DONE> {NODE_TYPE_CHARS}                        { yybegin(NODE_TYPE_DECLARATION_TYPE_DONE); return CndTypes.NODE_TYPE_NAME; }
<NODE_TYPE_DECLARATION_TYPE_DONE> {NODE_TYPE_DECLARATION_END}               { yybegin(NODE_TYPE_INHERITANCE); return CndTypes.NODE_TYPE_DECLARATION_CLOSING; }

//Node type inheritence "> jnt:content, smix:lmcuComponent"
<NODE_TYPE_INHERITANCE> {NODE_TYPE_INHERITANCE_START}                       		{ yybegin(NODE_TYPE_INHERITANCE_BEGIN); return CndTypes.NODE_TYPE_INHERITANCE_OPENING; }
<NODE_TYPE_INHERITANCE_BEGIN> {NAMESPACE_CHARS}                             		{ yybegin(NODE_TYPE_INHERITANCE_NAMESPACE_DONE); return CndTypes.NODE_TYPE_INHERITANCE_NAMESPACE; }
<NODE_TYPE_INHERITANCE_NAMESPACE_DONE> {NODE_TYPE_DECLARATION_SEPARATOR}    		{ yybegin(NODE_TYPE_INHERITANCE_COLON_DONE); return CndTypes.NODE_TYPE_INHERITANCE_COLON; }
<NODE_TYPE_INHERITANCE_COLON_DONE> {NODE_TYPE_CHARS}                        		{ yybegin(NODE_TYPE_INHERITANCE_TYPE_DONE); return CndTypes.NODE_TYPE_INHERITANCE_TYPE_NAME; }
<NODE_TYPE_INHERITANCE_TYPE_DONE> {WHITE_SPACE}* {NODE_TYPE_INHERITANCE_ANOTHER}    { yybegin(NODE_TYPE_INHERITANCE_BEGIN); return CndTypes.NODE_TYPE_INHERITANCE_TYPE_COMMA; }
<NODE_TYPE_INHERITANCE_TYPE_DONE> {WHITE_SPACE}+                                    { yybegin(NODE_TYPE_INHERITANCE); }

//Node type inheritance "mixin", "orderable" or "abstract" at the end of line
<NODE_TYPE_INHERITANCE> {NODE_TYPE_INHERITANCE_MIXIN}                       { yybegin(NODE_TYPE_INHERITANCE); return CndTypes.NODE_TYPE_MIXIN; }
<NODE_TYPE_INHERITANCE> {NODE_TYPE_INHERITANCE_ORDERABLE}                   { yybegin(NODE_TYPE_INHERITANCE); return CndTypes.NODE_TYPE_ORDERABLE; }
<NODE_TYPE_INHERITANCE> {NODE_TYPE_INHERITANCE_ABSTRACT}                    { yybegin(NODE_TYPE_INHERITANCE); return CndTypes.NODE_TYPE_ABSTRACT; }

<YYINITIAL> {NODE_TYPE_INHERITANCE_ORDERABLE} 		{ yybegin(YYINITIAL); return CndTypes.NODE_TYPE_ORDERABLE; }

//Node type properties minus
<YYINITIAL> {PROPERTY_MINUS_START}                         { yybegin(PROPERTY_MINUS_BEGIN); return CndTypes.PROPERTY_MINUS_OPENING; }
<PROPERTY_MINUS_BEGIN> {PROPERTY_NAME_CHARS}               { yybegin(PROPERTY_NAME_DONE); return CndTypes.PROPERTY_NAME; }
<PROPERTY_NAME_DONE> {PROPERTY_TYPE_START}                 { yybegin(PROPERTY_TYPE_BEGIN); return CndTypes.PROPERTY_TYPE_OPENING; }
    
    <PROPERTY_TYPE_BEGIN> {PROPERTY_TYPE_BINARY}                      { yybegin(PROPERTY_TYPE_DONE_BINARY); return CndTypes.PROPERTY_TYPE_BINARY; }
	<PROPERTY_TYPE_DONE_BINARY> {PROPERTY_TYPE_END}                   { yybegin(PROPERTY_ADDENDUM_ATTR); return CndTypes.PROPERTY_TYPE_CLOSING; }
    
    <PROPERTY_TYPE_BEGIN> {PROPERTY_TYPE_LONG}                      { yybegin(PROPERTY_TYPE_DONE_LONG); return CndTypes.PROPERTY_TYPE_LONG; }
    <PROPERTY_TYPE_DONE_LONG> {PROPERTY_TYPE_END}                   { yybegin(PROPERTY_ADDENDUM_LONG); return CndTypes.PROPERTY_TYPE_CLOSING; }
    <PROPERTY_ADDENDUM_LONG> {WHITE_SPACE}* {PROPERTY_DEFAULT}                     { yybegin(PROPERTY_DEFAULT_BEGIN_LONG); return CndTypes.PROPERTY_DEFAULT_OPENING; }
    <PROPERTY_DEFAULT_BEGIN_LONG> {PROPERTY_DEFAULT_LONG}           { yybegin(PROPERTY_ADDENDUM_ATTR); return CndTypes.PROPERTY_DEFAULT_LONG; }
    <PROPERTY_ADDENDUM_LONG> {WHITE_SPACE}+                  		{ yybegin(PROPERTY_ADDENDUM_ATTR); }
    
    <PROPERTY_TYPE_BEGIN> {PROPERTY_TYPE_DOUBLE}                      { yybegin(PROPERTY_TYPE_DONE_DOUBLE); return CndTypes.PROPERTY_TYPE_DOUBLE; }
	<PROPERTY_TYPE_DONE_DOUBLE> {PROPERTY_TYPE_END}                   { yybegin(PROPERTY_ADDENDUM_DOUBLE); return CndTypes.PROPERTY_TYPE_CLOSING; }
	<PROPERTY_ADDENDUM_DOUBLE> {WHITE_SPACE}* {PROPERTY_DEFAULT}                     { yybegin(PROPERTY_DEFAULT_BEGIN_DOUBLE); return CndTypes.PROPERTY_DEFAULT_OPENING; }
	<PROPERTY_DEFAULT_BEGIN_DOUBLE> {PROPERTY_DEFAULT_DOUBLE}         { yybegin(PROPERTY_ADDENDUM_ATTR); return CndTypes.PROPERTY_DEFAULT_DOUBLE; }
	<PROPERTY_ADDENDUM_DOUBLE> {WHITE_SPACE}+                  		  { yybegin(PROPERTY_ADDENDUM_ATTR); }
	
	<PROPERTY_TYPE_BEGIN> {PROPERTY_TYPE_BOOLEAN}                      { yybegin(PROPERTY_TYPE_DONE_BOOLEAN); return CndTypes.PROPERTY_TYPE_BOOLEAN; }
	<PROPERTY_TYPE_DONE_BOOLEAN> {PROPERTY_TYPE_END}                   { yybegin(PROPERTY_ADDENDUM_BOOLEAN); return CndTypes.PROPERTY_TYPE_CLOSING; }
	<PROPERTY_ADDENDUM_BOOLEAN> {WHITE_SPACE}* {PROPERTY_DEFAULT}                     { yybegin(PROPERTY_DEFAULT_BEGIN_BOOLEAN); return CndTypes.PROPERTY_DEFAULT_OPENING; }
	<PROPERTY_DEFAULT_BEGIN_BOOLEAN> {PROPERTY_DEFAULT_BOOLEAN}        { yybegin(PROPERTY_ADDENDUM_ATTR); return CndTypes.PROPERTY_DEFAULT_BOOLEAN; }
	<PROPERTY_ADDENDUM_BOOLEAN> {WHITE_SPACE}+                  	   { yybegin(PROPERTY_ADDENDUM_ATTR); }
	
	<PROPERTY_TYPE_BEGIN> {PROPERTY_TYPE_DATE}                      { yybegin(PROPERTY_TYPE_DONE_DATE); return CndTypes.PROPERTY_TYPE_DATE; }
	<PROPERTY_TYPE_DONE_DATE> {PROPERTY_TYPE_END}                   { yybegin(PROPERTY_ADDENDUM_DATE); return CndTypes.PROPERTY_TYPE_CLOSING; }
	<PROPERTY_ADDENDUM_DATE> {WHITE_SPACE}* {PROPERTY_DEFAULT}                     { yybegin(PROPERTY_DEFAULT_BEGIN_DATE); return CndTypes.PROPERTY_DEFAULT_OPENING; }
	<PROPERTY_DEFAULT_BEGIN_DATE> {PROPERTY_DEFAULT_DATE}           { yybegin(PROPERTY_ADDENDUM_ATTR); return CndTypes.PROPERTY_DEFAULT_DATE; }
	<PROPERTY_ADDENDUM_DATE> {WHITE_SPACE}+                  		{ yybegin(PROPERTY_ADDENDUM_ATTR); }
	
	<PROPERTY_TYPE_BEGIN> {PROPERTY_TYPE_STRING_TEXT}                      { yybegin(PROPERTY_TYPE_DONE_STRING_TEXT); return CndTypes.PROPERTY_TYPE_STRING_TEXT; }
	<PROPERTY_TYPE_DONE_STRING_TEXT> {PROPERTY_TYPE_END}                   { yybegin(PROPERTY_ADDENDUM_STRING_TEXT); return CndTypes.PROPERTY_TYPE_CLOSING; }
	<PROPERTY_ADDENDUM_STRING_TEXT> {WHITE_SPACE}* {PROPERTY_DEFAULT}                     { yybegin(PROPERTY_DEFAULT_BEGIN_STRING_TEXT); return CndTypes.PROPERTY_DEFAULT_OPENING; }
	<PROPERTY_DEFAULT_BEGIN_STRING_TEXT> {PROPERTY_DEFAULT_STRING_TEXT}    { yybegin(PROPERTY_ADDENDUM_ATTR); return CndTypes.PROPERTY_DEFAULT_STRING_TEXT; }
	<PROPERTY_ADDENDUM_STRING_TEXT> {WHITE_SPACE}+                  	   { yybegin(PROPERTY_ADDENDUM_ATTR); }
	
	<PROPERTY_TYPE_BEGIN> {PROPERTY_TYPE_STRING_CHOICELIST}                      	  { yybegin(PROPERTY_TYPE_DONE_STRING_CHOICELIST); return CndTypes.PROPERTY_TYPE_STRING_CHOICELIST; }
	<PROPERTY_TYPE_DONE_STRING_CHOICELIST> {PROPERTY_TYPE_END}                  	  { yybegin(PROPERTY_ADDENDUM_STRING_CHOICELIST); return CndTypes.PROPERTY_TYPE_CLOSING; }
	<PROPERTY_ADDENDUM_STRING_CHOICELIST> {WHITE_SPACE}* {PROPERTY_DEFAULT}               		      { yybegin(PROPERTY_DEFAULT_BEGIN_STRING_CHOICELIST); return CndTypes.PROPERTY_DEFAULT_OPENING; }
	<PROPERTY_DEFAULT_BEGIN_STRING_CHOICELIST> {PROPERTY_DEFAULT_STRING_CHOICELIST}   { yybegin(PROPERTY_ADDENDUM_ATTR); return CndTypes.PROPERTY_DEFAULT_STRING_CHOICELIST; }
	<PROPERTY_ADDENDUM_STRING_CHOICELIST> {WHITE_SPACE}+                  			  { yybegin(PROPERTY_ADDENDUM_ATTR); }
	
	<PROPERTY_TYPE_BEGIN> {PROPERTY_TYPE_WEAKREFERENCE_FINAL}                      { yybegin(PROPERTY_TYPE_DONE_WEAKREFERENCE); return CndTypes.PROPERTY_TYPE_WEAKREFERENCE; }
	<PROPERTY_TYPE_DONE_WEAKREFERENCE> {PROPERTY_TYPE_END}                  	   { yybegin(PROPERTY_ADDENDUM_ATTR); return CndTypes.PROPERTY_TYPE_CLOSING; }
    
<PROPERTY_ADDENDUM_ATTR> {PROPERTY_ATTRIBUTE}              { yybegin(PROPERTY_ADDENDUM_ATTR); return CndTypes.PROPERTY_ATTRIBUTE; }
<PROPERTY_ADDENDUM_ATTR> {PROPERTY_CONSTRAINT_START}       { yybegin(PROPERTY_CONSTRAINT_BEGIN); return CndTypes.PROPERTY_CONSTRAINT_OPENING; }
<PROPERTY_CONSTRAINT_BEGIN> {PROPERTY_CONSTRAINT}          { yybegin(YYINITIAL); return CndTypes.PROPERTY_CONSTRAINT; }


//Node type properties plus
<YYINITIAL> {PROPERTY_PLUS_START}                                   { yybegin(PROPERTY_PLUS_BEGIN); return CndTypes.PROPERTY_PLUS_OPENING; }
<PROPERTY_PLUS_BEGIN> {PROPERTY_PLUS_NAME_CHARS}                    { yybegin(PROPERTY_PLUS_NAME_DONE); return CndTypes.PROPERTY_PLUS_NAME; }
<PROPERTY_PLUS_NAME_DONE> {PROPERTY_TYPE_START}                     { yybegin(PROPERTY_PLUS_TYPE_BEGIN); return CndTypes.PROPERTY_TYPE_OPENING; }
<PROPERTY_PLUS_TYPE_BEGIN> {NAMESPACE_CHARS}                        { yybegin(PROPERTY_PLUS_NAMESPACE_DONE); return CndTypes.NODE_TYPE_NAMESPACE; }
<PROPERTY_PLUS_NAMESPACE_DONE> {NODE_TYPE_DECLARATION_SEPARATOR}    { yybegin(PROPERTY_PLUS_COLON_DONE); return CndTypes.NODE_TYPE_DECLARATION_COLON; }
<PROPERTY_PLUS_COLON_DONE> {NODE_TYPE_CHARS}                        { yybegin(PROPERTY_PLUS_TYPE_DONE); return CndTypes.NODE_TYPE_NAME; }
<PROPERTY_PLUS_TYPE_DONE> {PROPERTY_TYPE_END}                       { yybegin(PROPERTY_PLUS_ADDENDUM); return CndTypes.PROPERTY_TYPE_CLOSING; }
<PROPERTY_PLUS_ADDENDUM> {PROPERTY_DEFAULT}                         { yybegin(PROPERTY_PLUS_DEFAULT_BEGIN); return CndTypes.PROPERTY_DEFAULT_OPENING; }
<PROPERTY_PLUS_DEFAULT_BEGIN> {PROPERTY_PLUS_DEFAULT_VALUE}         { yybegin(PROPERTY_PLUS_ADDENDUM); return CndTypes.PROPERTY_DEFAULT_VALUE; }
<PROPERTY_PLUS_ADDENDUM> {PROPERTY_PLUS_ATTRIBUTE}                  { yybegin(PROPERTY_PLUS_ADDENDUM); return CndTypes.PROPERTY_PLUS_ATTRIBUTE; }


//Node type extends
<YYINITIAL> {EXTEND_START}                                      { yybegin(EXTEND_BEGIN); return CndTypes.EXTEND_OPENING; }
<EXTEND_BEGIN> {NAMESPACE_CHARS}                                { yybegin(EXTEND_NAMESPACE_DONE); return CndTypes.NODE_TYPE_NAMESPACE; }
<EXTEND_NAMESPACE_DONE> {NODE_TYPE_DECLARATION_SEPARATOR}       { yybegin(EXTEND_COLON_DONE); return CndTypes.NODE_TYPE_DECLARATION_COLON; }
<EXTEND_COLON_DONE> {NODE_TYPE_CHARS}                           { yybegin(EXTEND_DONE); return CndTypes.NODE_TYPE_NAME; }
<EXTEND_DONE> {EXTEND_ANOTHER}                           		{ yybegin(EXTEND_BEGIN); return CndTypes.EXTEND_COMMA; }

//Item type
<YYINITIAL> {EXTEND_ITEM_TYPE_START}                            { yybegin(EXTEND_ITEM_BEGIN); return CndTypes.EXTEND_ITEM_START; }
<EXTEND_ITEM_BEGIN> {EXTEND_ITEM_TYPE_VALUE}                    { yybegin(YYINITIAL); return CndTypes.EXTEND_ITEM_TYPE; }

{COMMENT}						{ return CndTypes.COMMENT; }

{WHITE_SPACE}+                  { return TokenType.WHITE_SPACE; }

{CRLF}                          { yybegin(YYINITIAL); return CndTypes.CRLF; }

.                               { return TokenType.BAD_CHARACTER; }















<YYINITIAL> {COMMENT_BLOCK}				{ return CndTypes.COMMENT; }

//Namespaces "<tnt = 'http://www.thomas-coquel.fr/jahia/nt/1.0'>"
<YYINITIAL> "<" 						{ yybegin(NAMESPACE); return CndTypes.LEFT_ANGLE_BRACKET; }
<NAMESPACE> {
	[:jletter:][:jletterdigit:]*		{ return CndTypes.NAMESPACE_NAME; }
    "="									{ return CndTypes.EQUAL; }
    "'"									{ return CndTypes.SIMPLE_QUOTE; }
    {NAMESPACE_URI}						{ return CndTypes.NAMESPACE_URI; }
	">" 								{ yybegin(YYINITIAL); return CndTypes.RIGHT_ANGLE_BRACKET; }
}

//Node type declaration "[tnt:test]"
<YYINITIAL> "["											{ yybegin(NODETYPE_NAMESPACE); return CndTypes.LEFT_BRACKET; }
<NODETYPE_NAMESPACE> [:jletter:][:jletterdigit:]*		{ yybegin(NODETYPE); return CndTypes.NAMESPACE_NAME; }
<NODETYPE> {
	":"                                             	{ return CndTypes.COLON; }
	[:jletter:][:jletterdigit:]*                       	{ return CndTypes.NODE_TYPE_NAME; }
	"]"							                       	{ return CndTypes.RIGHT_BRACKET; }
	">"							                       	{ yybegin(SUPER_TYPES_NAMESPACE); return CndTypes.RIGHT_ANGLE_BRACKET; }
	{WHITE_SPACE}+                  					{ return TokenType.WHITE_SPACE; }
	.                               					{ yybegin(OPTIONS); }
}

//Node type super types "> jnt:content, smix:lmcuComponent"
<SUPER_TYPES_NAMESPACE> {
	[:jletter:][:jletterdigit:]*				{ yybegin(SUPER_TYPES); return CndTypes.NAMESPACE_NAME; }
	{WHITE_SPACE}+                  			{ return TokenType.WHITE_SPACE; }
	.                               			{ yybegin(OPTIONS); }
}
<SUPER_TYPES> {
	":"											{ return CndTypes.COLON; }
	[:jletter:][:jletterdigit:]*				{ return CndTypes.NODE_TYPE_NAME; }
	","											{ yybegin(SUPER_TYPES_NAMESPACE); return CndTypes.COMMA; }
	{WHITE_SPACE}+                  			{ return TokenType.WHITE_SPACE; }
	.                               			{ yybegin(OPTIONS); }
}

//Node type options "mixin", "orderable" or "abstract" at the end of line or on a new line
<OPTIONS> {
	"mixin"										{ return CndTypes.MIXIN; }
	"abstract"									{ return CndTypes.ABSTRACT; }
	"orderable"									{ return CndTypes.ORDERABLE; }
	{CRLF}                          			{ return CndTypes.CRLF; }
	{WHITE_SPACE}+                  			{ return TokenType.WHITE_SPACE; }
	.                               			{ yybegin(YYINITIAL); }
}






//Node type extends






//Node type itemtype





//Node type property -
<YYINITIAL> "-"									{ yybegin(PROPERTY); return CndTypes.MINUS; }
<PROPERTY> {
	[:jletter:]([:jletterdigit:]|:)*			{ return CndTypes.PROPERTY_NAME; }
	"("											{ return CndTypes.LEFT_PARENTHESIS; }
	"string"|"long"|"double"|"decimal"|"path"|"uri"|"boolean"|"date"|"binary"|"weakreference"|"name"|"reference"		{ return CndTypes.PROPERTY_TYPE; }
	","											{ return CndTypes.COMMA; }
	"text"|"richtext"|"textarea"|"choicelist"|"datetimepicker"|"datepicker"|"picker"|"color"|"category"|"checkbox"|"fileupload"		{ return CndTypes.PROPERTY_MASK; }
	"["											{ yybegin(PROPERTY_MASK_OPTION_NAME); return CndTypes.LEFT_BRACKET; }
	")"											{ yybegin(PROPERTY_DEFAULT); return CndTypes.RIGHT_PARENTHESIS; }
}

<PROPERTY_MASK_OPTION_NAME> "resourceBundle"|"country"|"templates"|"templatesNode"|"users"|"nodetypes"|"subnodetypes"|"nodes"|"menus"|"script"|"flag"|"sortableFieldnames"|"moduleImage"|"linkerProps"|"workflow"|"workflowTypes"|"sort"|"componenttypes"|"autoSelectParent"|"type"		{ yybegin(PROPERTY_MASK_OPTION); return CndTypes.PROPERTY_MASK_OPTION; }
<PROPERTY_MASK_OPTION> {
	"="											{ return CndTypes.EQUAL; }
	"'"[^\r\n\]']+"'" | [^\r\n\])',]+			{ return CndTypes.PROPERTY_MASK_OPTION_VALUE; }
	"]"											{ return CndTypes.RIGHT_BRACKET; }
	","											{ yybegin(PROPERTY_MASK_OPTION_NAME); return CndTypes.COMMA; }
	")"											{ yybegin(PROPERTY_DEFAULT); return CndTypes.RIGHT_PARENTHESIS; }
}

<PROPERTY_DEFAULT> {
	{WHITE_SPACE}+ "="							{ yybegin(PROPERTY_DEFAULT_VALUE); return CndTypes.EQUAL; }
	.											{ yybegin(PROPERTY_ATTRIBUTES); }
}
<PROPERTY_DEFAULT_VALUE> {
	[^\r\n\s]+									{ yybegin(PROPERTY_ATTRIBUTES); return CndTypes.PROPERTY_DEFAULT_VALUE; }
	{WHITE_SPACE}+                  			{ return TokenType.WHITE_SPACE; }
	.                               			{ yybegin(PROPERTY_ATTRIBUTES); }
}

<PROPERTY_ATTRIBUTES> {
	"mandatory"|"protected"|"primary"|"i18n"|"sortable"|"hidden"|"multiple"|"nofulltext"|"indexed="((')?"no"|"tokenized"|"untokenized"(')?)|"analyzer='keyword'"|"autocreated"|"boost="[:digit:]"."[:digit:]|"onconflict="("sum"|"latest"|"oldest"|"min"|"max")|"facetable"			{ return CndTypes.PROPERTY_ATTRIBUTE; }
	{WHITE_SPACE}+                  			{ return TokenType.WHITE_SPACE; }
	.                               			{ yybegin(PROPERTY_CONSTRAINT); }
}

<PROPERTY_CONSTRAINT> "<"						{ yybegin(PROPERTY_CONSTRAINT_VALUE); return CndTypes.LEFT_ANGLE_BRACKET; }
<PROPERTY_CONSTRAINT_VALUE> {
	[^\r\n\s\t\f]+								{ yybegin(YYINITIAL); return CndTypes.PROPERTY_CONSTRAINT; }
}






//Node type nodes +
<YYINITIAL> "+"										{ yybegin(NODE); return CndTypes.PLUS; }
<NODE> {
	[:jletter:]([:jletterdigit:]|:)* | "*"			{ return CndTypes.PROPERTY_NAME; }
	"("												{ yybegin(NODE_NAMESPACE); return CndTypes.LEFT_PARENTHESIS; }
}
<NODE_NAMESPACE> [:jletter:][:jletterdigit:]*		{ yybegin(NODE_NODETYPE); return CndTypes.NAMESPACE_NAME; }
<NODE_NODETYPE> {
	":"                                             { return CndTypes.COLON; }
	[:jletter:][:jletterdigit:]*                    { return CndTypes.NODE_TYPE_NAME; }
	")"							                    { yybegin(NODE_DEFAULT); return CndTypes.RIGHT_PARENTHESIS; }
}

<NODE_DEFAULT> {
	{WHITE_SPACE}+ "="							{ yybegin(NODE_DEFAULT_VALUE); return CndTypes.EQUAL; }
	.											{ yybegin(NODE_ATTRIBUTES); }
} //STOPPED HERE
<NODE_DEFAULT_VALUE> {
	[^\r\n\s]+									{ yybegin(PROPERTY_ATTRIBUTES); return CndTypes.PROPERTY_DEFAULT_VALUE; }
	{WHITE_SPACE}+                  			{ return TokenType.WHITE_SPACE; }
	.                               			{ yybegin(PROPERTY_ATTRIBUTES); }
}

<NODE_ATTRIBUTES> {
//mandatory autocreated version multiple
	"mandatory"|"autocreated"|"version"|"multiple"	{ return CndTypes.PROPERTY_ATTRIBUTE; }
	{WHITE_SPACE}+                  				{ return TokenType.WHITE_SPACE; }
	.                               				{ yybegin(PROPERTY_CONSTRAINT); }
}

<PROPERTY_CONSTRAINT> "<"						{ yybegin(PROPERTY_CONSTRAINT_VALUE); return CndTypes.LEFT_ANGLE_BRACKET; }
<PROPERTY_CONSTRAINT_VALUE> {
	[^\r\n\s\t\f]+									{ yybegin(YYINITIAL); return CndTypes.PROPERTY_CONSTRAINT; }
}




{COMMENT}						{ return CndTypes.COMMENT; }

{WHITE_SPACE}+                  { return TokenType.WHITE_SPACE; }

{CRLF}                          { yybegin(YYINITIAL); return CndTypes.CRLF; }

.                               { return TokenType.BAD_CHARACTER; }
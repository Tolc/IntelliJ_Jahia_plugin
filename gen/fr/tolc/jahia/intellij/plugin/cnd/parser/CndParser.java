// This is a generated file. Not intended for manual editing.
package fr.tolc.jahia.intellij.plugin.cnd.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import static fr.tolc.jahia.intellij.plugin.cnd.psi.CndTypes.*;
import static com.intellij.lang.parser.GeneratedParserUtilBase.*;
import com.intellij.psi.tree.IElementType;
import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.TokenSet;
import com.intellij.lang.PsiParser;
import com.intellij.lang.LightPsiParser;

@SuppressWarnings({"SimplifiableIfStatement", "UnusedAssignment"})
public class CndParser implements PsiParser, LightPsiParser {

  public ASTNode parse(IElementType t, PsiBuilder b) {
    parseLight(t, b);
    return b.getTreeBuilt();
  }

  public void parseLight(IElementType t, PsiBuilder b) {
    boolean r;
    b = adapt_builder_(t, b, this, null);
    Marker m = enter_section_(b, 0, _COLLAPSE_, null);
    if (t == EXTEND) {
      r = extend(b, 0);
    }
    else if (t == EXTEND_NODE_TYPE) {
      r = extendNodeType(b, 0);
    }
    else if (t == INHERITANCE) {
      r = inheritance(b, 0);
    }
    else if (t == INHERITANCES) {
      r = inheritances(b, 0);
    }
    else if (t == NAMESPACE) {
      r = namespace(b, 0);
    }
    else if (t == NODE_TYPE) {
      r = nodeType(b, 0);
    }
    else if (t == PROPERTIES) {
      r = properties(b, 0);
    }
    else if (t == PROPERTY_BINARY) {
      r = propertyBinary(b, 0);
    }
    else if (t == PROPERTY_BOOLEAN) {
      r = propertyBoolean(b, 0);
    }
    else if (t == PROPERTY_DATE) {
      r = propertyDate(b, 0);
    }
    else if (t == PROPERTY_DOUBLE) {
      r = propertyDouble(b, 0);
    }
    else if (t == PROPERTY_LONG) {
      r = propertyLong(b, 0);
    }
    else if (t == PROPERTY_MINUS) {
      r = propertyMinus(b, 0);
    }
    else if (t == PROPERTY_PLUS) {
      r = propertyPlus(b, 0);
    }
    else if (t == PROPERTY_STRING) {
      r = propertyString(b, 0);
    }
    else if (t == PROPERTY_STRING_CHOICELIST) {
      r = propertyStringChoicelist(b, 0);
    }
    else if (t == PROPERTY_STRING_TEXT) {
      r = propertyStringText(b, 0);
    }
    else if (t == PROPERTY_WEAKREFERENCE) {
      r = propertyWeakreference(b, 0);
    }
    else {
      r = parse_root_(t, b, 0);
    }
    exit_section_(b, 0, m, t, r, true, TRUE_CONDITION);
  }

  protected boolean parse_root_(IElementType t, PsiBuilder b, int l) {
    return cndFile(b, l + 1);
  }

  /* ********************************************************** */
  // namespace* (COMMENT|nodeType|CRLF)*
  static boolean cndFile(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "cndFile")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = cndFile_0(b, l + 1);
    r = r && cndFile_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // namespace*
  private static boolean cndFile_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "cndFile_0")) return false;
    int c = current_position_(b);
    while (true) {
      if (!namespace(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "cndFile_0", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // (COMMENT|nodeType|CRLF)*
  private static boolean cndFile_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "cndFile_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!cndFile_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "cndFile_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // COMMENT|nodeType|CRLF
  private static boolean cndFile_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "cndFile_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMENT);
    if (!r) r = nodeType(b, l + 1);
    if (!r) r = consumeToken(b, CRLF);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // EXTEND_OPENING extendNodeType (EXTEND_COMMA extendNodeType)* [EXTEND_ITEM_START EXTEND_ITEM_TYPE]
  public static boolean extend(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "extend")) return false;
    if (!nextTokenIs(b, EXTEND_OPENING)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, EXTEND_OPENING);
    r = r && extendNodeType(b, l + 1);
    r = r && extend_2(b, l + 1);
    r = r && extend_3(b, l + 1);
    exit_section_(b, m, EXTEND, r);
    return r;
  }

  // (EXTEND_COMMA extendNodeType)*
  private static boolean extend_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "extend_2")) return false;
    int c = current_position_(b);
    while (true) {
      if (!extend_2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "extend_2", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // EXTEND_COMMA extendNodeType
  private static boolean extend_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "extend_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, EXTEND_COMMA);
    r = r && extendNodeType(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // [EXTEND_ITEM_START EXTEND_ITEM_TYPE]
  private static boolean extend_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "extend_3")) return false;
    parseTokens(b, 0, EXTEND_ITEM_START, EXTEND_ITEM_TYPE);
    return true;
  }

  /* ********************************************************** */
  // NODE_TYPE_NAMESPACE NODE_TYPE_DECLARATION_COLON NODE_TYPE_NAME
  public static boolean extendNodeType(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "extendNodeType")) return false;
    if (!nextTokenIs(b, NODE_TYPE_NAMESPACE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, NODE_TYPE_NAMESPACE, NODE_TYPE_DECLARATION_COLON, NODE_TYPE_NAME);
    exit_section_(b, m, EXTEND_NODE_TYPE, r);
    return r;
  }

  /* ********************************************************** */
  // NODE_TYPE_INHERITANCE_NAMESPACE NODE_TYPE_INHERITANCE_COLON NODE_TYPE_INHERITANCE_TYPE_NAME
  public static boolean inheritance(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "inheritance")) return false;
    if (!nextTokenIs(b, NODE_TYPE_INHERITANCE_NAMESPACE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, NODE_TYPE_INHERITANCE_NAMESPACE, NODE_TYPE_INHERITANCE_COLON, NODE_TYPE_INHERITANCE_TYPE_NAME);
    exit_section_(b, m, INHERITANCE, r);
    return r;
  }

  /* ********************************************************** */
  // NODE_TYPE_INHERITANCE_OPENING inheritance (NODE_TYPE_INHERITANCE_TYPE_COMMA inheritance)* [NODE_TYPE_INHERITANCE_TYPE_COMMA]
  public static boolean inheritances(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "inheritances")) return false;
    if (!nextTokenIs(b, NODE_TYPE_INHERITANCE_OPENING)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, NODE_TYPE_INHERITANCE_OPENING);
    r = r && inheritance(b, l + 1);
    r = r && inheritances_2(b, l + 1);
    r = r && inheritances_3(b, l + 1);
    exit_section_(b, m, INHERITANCES, r);
    return r;
  }

  // (NODE_TYPE_INHERITANCE_TYPE_COMMA inheritance)*
  private static boolean inheritances_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "inheritances_2")) return false;
    int c = current_position_(b);
    while (true) {
      if (!inheritances_2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "inheritances_2", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // NODE_TYPE_INHERITANCE_TYPE_COMMA inheritance
  private static boolean inheritances_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "inheritances_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, NODE_TYPE_INHERITANCE_TYPE_COMMA);
    r = r && inheritance(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // [NODE_TYPE_INHERITANCE_TYPE_COMMA]
  private static boolean inheritances_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "inheritances_3")) return false;
    consumeToken(b, NODE_TYPE_INHERITANCE_TYPE_COMMA);
    return true;
  }

  /* ********************************************************** */
  // NAMESPACE_OPENING NAMESPACE_NAME NAMESPACE_EQUAL NAMESPACE_URI NAMESPACE_CLOSING CRLF
  public static boolean namespace(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "namespace")) return false;
    if (!nextTokenIs(b, NAMESPACE_OPENING)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, NAMESPACE_OPENING, NAMESPACE_NAME, NAMESPACE_EQUAL, NAMESPACE_URI, NAMESPACE_CLOSING, CRLF);
    exit_section_(b, m, NAMESPACE, r);
    return r;
  }

  /* ********************************************************** */
  // NODE_TYPE_DECLARATION_OPENING NODE_TYPE_NAMESPACE NODE_TYPE_DECLARATION_COLON NODE_TYPE_NAME NODE_TYPE_DECLARATION_CLOSING [inheritances] [NODE_TYPE_ORDERABLE|NODE_TYPE_MIXIN|NODE_TYPE_MIXIN WHITE_SPACE+ NODE_TYPE_ORDERABLE|NODE_TYPE_ORDERABLE WHITE_SPACE+ NODE_TYPE_MIXIN|NODE_TYPE_ABSTRACT] [CRLF extend] [CRLF properties]
  public static boolean nodeType(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "nodeType")) return false;
    if (!nextTokenIs(b, NODE_TYPE_DECLARATION_OPENING)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, NODE_TYPE_DECLARATION_OPENING, NODE_TYPE_NAMESPACE, NODE_TYPE_DECLARATION_COLON, NODE_TYPE_NAME, NODE_TYPE_DECLARATION_CLOSING);
    r = r && nodeType_5(b, l + 1);
    r = r && nodeType_6(b, l + 1);
    r = r && nodeType_7(b, l + 1);
    r = r && nodeType_8(b, l + 1);
    exit_section_(b, m, NODE_TYPE, r);
    return r;
  }

  // [inheritances]
  private static boolean nodeType_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "nodeType_5")) return false;
    inheritances(b, l + 1);
    return true;
  }

  // [NODE_TYPE_ORDERABLE|NODE_TYPE_MIXIN|NODE_TYPE_MIXIN WHITE_SPACE+ NODE_TYPE_ORDERABLE|NODE_TYPE_ORDERABLE WHITE_SPACE+ NODE_TYPE_MIXIN|NODE_TYPE_ABSTRACT]
  private static boolean nodeType_6(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "nodeType_6")) return false;
    nodeType_6_0(b, l + 1);
    return true;
  }

  // NODE_TYPE_ORDERABLE|NODE_TYPE_MIXIN|NODE_TYPE_MIXIN WHITE_SPACE+ NODE_TYPE_ORDERABLE|NODE_TYPE_ORDERABLE WHITE_SPACE+ NODE_TYPE_MIXIN|NODE_TYPE_ABSTRACT
  private static boolean nodeType_6_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "nodeType_6_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, NODE_TYPE_ORDERABLE);
    if (!r) r = consumeToken(b, NODE_TYPE_MIXIN);
    if (!r) r = nodeType_6_0_2(b, l + 1);
    if (!r) r = nodeType_6_0_3(b, l + 1);
    if (!r) r = consumeToken(b, NODE_TYPE_ABSTRACT);
    exit_section_(b, m, null, r);
    return r;
  }

  // NODE_TYPE_MIXIN WHITE_SPACE+ NODE_TYPE_ORDERABLE
  private static boolean nodeType_6_0_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "nodeType_6_0_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, NODE_TYPE_MIXIN);
    r = r && nodeType_6_0_2_1(b, l + 1);
    r = r && consumeToken(b, NODE_TYPE_ORDERABLE);
    exit_section_(b, m, null, r);
    return r;
  }

  // WHITE_SPACE+
  private static boolean nodeType_6_0_2_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "nodeType_6_0_2_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, WHITE_SPACE);
    int c = current_position_(b);
    while (r) {
      if (!consumeToken(b, WHITE_SPACE)) break;
      if (!empty_element_parsed_guard_(b, "nodeType_6_0_2_1", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, m, null, r);
    return r;
  }

  // NODE_TYPE_ORDERABLE WHITE_SPACE+ NODE_TYPE_MIXIN
  private static boolean nodeType_6_0_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "nodeType_6_0_3")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, NODE_TYPE_ORDERABLE);
    r = r && nodeType_6_0_3_1(b, l + 1);
    r = r && consumeToken(b, NODE_TYPE_MIXIN);
    exit_section_(b, m, null, r);
    return r;
  }

  // WHITE_SPACE+
  private static boolean nodeType_6_0_3_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "nodeType_6_0_3_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, WHITE_SPACE);
    int c = current_position_(b);
    while (r) {
      if (!consumeToken(b, WHITE_SPACE)) break;
      if (!empty_element_parsed_guard_(b, "nodeType_6_0_3_1", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, m, null, r);
    return r;
  }

  // [CRLF extend]
  private static boolean nodeType_7(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "nodeType_7")) return false;
    nodeType_7_0(b, l + 1);
    return true;
  }

  // CRLF extend
  private static boolean nodeType_7_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "nodeType_7_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, CRLF);
    r = r && extend(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // [CRLF properties]
  private static boolean nodeType_8(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "nodeType_8")) return false;
    nodeType_8_0(b, l + 1);
    return true;
  }

  // CRLF properties
  private static boolean nodeType_8_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "nodeType_8_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, CRLF);
    r = r && properties(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // (propertyMinus CRLF | propertyPlus CRLF | CRLF)*
  public static boolean properties(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "properties")) return false;
    Marker m = enter_section_(b, l, _NONE_, "<properties>");
    int c = current_position_(b);
    while (true) {
      if (!properties_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "properties", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, l, m, PROPERTIES, true, false, null);
    return true;
  }

  // propertyMinus CRLF | propertyPlus CRLF | CRLF
  private static boolean properties_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "properties_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = properties_0_0(b, l + 1);
    if (!r) r = properties_0_1(b, l + 1);
    if (!r) r = consumeToken(b, CRLF);
    exit_section_(b, m, null, r);
    return r;
  }

  // propertyMinus CRLF
  private static boolean properties_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "properties_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = propertyMinus(b, l + 1);
    r = r && consumeToken(b, CRLF);
    exit_section_(b, m, null, r);
    return r;
  }

  // propertyPlus CRLF
  private static boolean properties_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "properties_0_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = propertyPlus(b, l + 1);
    r = r && consumeToken(b, CRLF);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // PROPERTY_MINUS_OPENING PROPERTY_NAME PROPERTY_TYPE_OPENING PROPERTY_TYPE_BINARY PROPERTY_TYPE_CLOSING [(PROPERTY_ATTRIBUTE)*] [PROPERTY_CONSTRAINT_OPENING PROPERTY_CONSTRAINT]
  public static boolean propertyBinary(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "propertyBinary")) return false;
    if (!nextTokenIs(b, PROPERTY_MINUS_OPENING)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, PROPERTY_MINUS_OPENING, PROPERTY_NAME, PROPERTY_TYPE_OPENING, PROPERTY_TYPE_BINARY, PROPERTY_TYPE_CLOSING);
    r = r && propertyBinary_5(b, l + 1);
    r = r && propertyBinary_6(b, l + 1);
    exit_section_(b, m, PROPERTY_BINARY, r);
    return r;
  }

  // [(PROPERTY_ATTRIBUTE)*]
  private static boolean propertyBinary_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "propertyBinary_5")) return false;
    propertyBinary_5_0(b, l + 1);
    return true;
  }

  // (PROPERTY_ATTRIBUTE)*
  private static boolean propertyBinary_5_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "propertyBinary_5_0")) return false;
    int c = current_position_(b);
    while (true) {
      if (!consumeToken(b, PROPERTY_ATTRIBUTE)) break;
      if (!empty_element_parsed_guard_(b, "propertyBinary_5_0", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // [PROPERTY_CONSTRAINT_OPENING PROPERTY_CONSTRAINT]
  private static boolean propertyBinary_6(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "propertyBinary_6")) return false;
    parseTokens(b, 0, PROPERTY_CONSTRAINT_OPENING, PROPERTY_CONSTRAINT);
    return true;
  }

  /* ********************************************************** */
  // PROPERTY_MINUS_OPENING PROPERTY_NAME PROPERTY_TYPE_OPENING PROPERTY_TYPE_BOOLEAN PROPERTY_TYPE_CLOSING [PROPERTY_DEFAULT_OPENING PROPERTY_DEFAULT_BOOLEAN] [(PROPERTY_ATTRIBUTE)*] [PROPERTY_CONSTRAINT_OPENING PROPERTY_CONSTRAINT]
  public static boolean propertyBoolean(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "propertyBoolean")) return false;
    if (!nextTokenIs(b, PROPERTY_MINUS_OPENING)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, PROPERTY_MINUS_OPENING, PROPERTY_NAME, PROPERTY_TYPE_OPENING, PROPERTY_TYPE_BOOLEAN, PROPERTY_TYPE_CLOSING);
    r = r && propertyBoolean_5(b, l + 1);
    r = r && propertyBoolean_6(b, l + 1);
    r = r && propertyBoolean_7(b, l + 1);
    exit_section_(b, m, PROPERTY_BOOLEAN, r);
    return r;
  }

  // [PROPERTY_DEFAULT_OPENING PROPERTY_DEFAULT_BOOLEAN]
  private static boolean propertyBoolean_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "propertyBoolean_5")) return false;
    parseTokens(b, 0, PROPERTY_DEFAULT_OPENING, PROPERTY_DEFAULT_BOOLEAN);
    return true;
  }

  // [(PROPERTY_ATTRIBUTE)*]
  private static boolean propertyBoolean_6(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "propertyBoolean_6")) return false;
    propertyBoolean_6_0(b, l + 1);
    return true;
  }

  // (PROPERTY_ATTRIBUTE)*
  private static boolean propertyBoolean_6_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "propertyBoolean_6_0")) return false;
    int c = current_position_(b);
    while (true) {
      if (!consumeToken(b, PROPERTY_ATTRIBUTE)) break;
      if (!empty_element_parsed_guard_(b, "propertyBoolean_6_0", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // [PROPERTY_CONSTRAINT_OPENING PROPERTY_CONSTRAINT]
  private static boolean propertyBoolean_7(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "propertyBoolean_7")) return false;
    parseTokens(b, 0, PROPERTY_CONSTRAINT_OPENING, PROPERTY_CONSTRAINT);
    return true;
  }

  /* ********************************************************** */
  // PROPERTY_MINUS_OPENING PROPERTY_NAME PROPERTY_TYPE_OPENING PROPERTY_TYPE_DATE PROPERTY_TYPE_CLOSING [PROPERTY_DEFAULT_OPENING PROPERTY_DEFAULT_DATE] [(PROPERTY_ATTRIBUTE)*] [PROPERTY_CONSTRAINT_OPENING PROPERTY_CONSTRAINT]
  public static boolean propertyDate(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "propertyDate")) return false;
    if (!nextTokenIs(b, PROPERTY_MINUS_OPENING)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, PROPERTY_MINUS_OPENING, PROPERTY_NAME, PROPERTY_TYPE_OPENING, PROPERTY_TYPE_DATE, PROPERTY_TYPE_CLOSING);
    r = r && propertyDate_5(b, l + 1);
    r = r && propertyDate_6(b, l + 1);
    r = r && propertyDate_7(b, l + 1);
    exit_section_(b, m, PROPERTY_DATE, r);
    return r;
  }

  // [PROPERTY_DEFAULT_OPENING PROPERTY_DEFAULT_DATE]
  private static boolean propertyDate_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "propertyDate_5")) return false;
    parseTokens(b, 0, PROPERTY_DEFAULT_OPENING, PROPERTY_DEFAULT_DATE);
    return true;
  }

  // [(PROPERTY_ATTRIBUTE)*]
  private static boolean propertyDate_6(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "propertyDate_6")) return false;
    propertyDate_6_0(b, l + 1);
    return true;
  }

  // (PROPERTY_ATTRIBUTE)*
  private static boolean propertyDate_6_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "propertyDate_6_0")) return false;
    int c = current_position_(b);
    while (true) {
      if (!consumeToken(b, PROPERTY_ATTRIBUTE)) break;
      if (!empty_element_parsed_guard_(b, "propertyDate_6_0", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // [PROPERTY_CONSTRAINT_OPENING PROPERTY_CONSTRAINT]
  private static boolean propertyDate_7(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "propertyDate_7")) return false;
    parseTokens(b, 0, PROPERTY_CONSTRAINT_OPENING, PROPERTY_CONSTRAINT);
    return true;
  }

  /* ********************************************************** */
  // PROPERTY_MINUS_OPENING PROPERTY_NAME PROPERTY_TYPE_OPENING PROPERTY_TYPE_DOUBLE PROPERTY_TYPE_CLOSING [PROPERTY_DEFAULT_OPENING PROPERTY_DEFAULT_DOUBLE] [(PROPERTY_ATTRIBUTE)*] [PROPERTY_CONSTRAINT_OPENING PROPERTY_CONSTRAINT]
  public static boolean propertyDouble(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "propertyDouble")) return false;
    if (!nextTokenIs(b, PROPERTY_MINUS_OPENING)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, PROPERTY_MINUS_OPENING, PROPERTY_NAME, PROPERTY_TYPE_OPENING, PROPERTY_TYPE_DOUBLE, PROPERTY_TYPE_CLOSING);
    r = r && propertyDouble_5(b, l + 1);
    r = r && propertyDouble_6(b, l + 1);
    r = r && propertyDouble_7(b, l + 1);
    exit_section_(b, m, PROPERTY_DOUBLE, r);
    return r;
  }

  // [PROPERTY_DEFAULT_OPENING PROPERTY_DEFAULT_DOUBLE]
  private static boolean propertyDouble_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "propertyDouble_5")) return false;
    parseTokens(b, 0, PROPERTY_DEFAULT_OPENING, PROPERTY_DEFAULT_DOUBLE);
    return true;
  }

  // [(PROPERTY_ATTRIBUTE)*]
  private static boolean propertyDouble_6(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "propertyDouble_6")) return false;
    propertyDouble_6_0(b, l + 1);
    return true;
  }

  // (PROPERTY_ATTRIBUTE)*
  private static boolean propertyDouble_6_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "propertyDouble_6_0")) return false;
    int c = current_position_(b);
    while (true) {
      if (!consumeToken(b, PROPERTY_ATTRIBUTE)) break;
      if (!empty_element_parsed_guard_(b, "propertyDouble_6_0", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // [PROPERTY_CONSTRAINT_OPENING PROPERTY_CONSTRAINT]
  private static boolean propertyDouble_7(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "propertyDouble_7")) return false;
    parseTokens(b, 0, PROPERTY_CONSTRAINT_OPENING, PROPERTY_CONSTRAINT);
    return true;
  }

  /* ********************************************************** */
  // PROPERTY_MINUS_OPENING PROPERTY_NAME PROPERTY_TYPE_OPENING PROPERTY_TYPE_LONG PROPERTY_TYPE_CLOSING [PROPERTY_DEFAULT_OPENING PROPERTY_DEFAULT_LONG] [(PROPERTY_ATTRIBUTE)*] [PROPERTY_CONSTRAINT_OPENING PROPERTY_CONSTRAINT]
  public static boolean propertyLong(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "propertyLong")) return false;
    if (!nextTokenIs(b, PROPERTY_MINUS_OPENING)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, PROPERTY_MINUS_OPENING, PROPERTY_NAME, PROPERTY_TYPE_OPENING, PROPERTY_TYPE_LONG, PROPERTY_TYPE_CLOSING);
    r = r && propertyLong_5(b, l + 1);
    r = r && propertyLong_6(b, l + 1);
    r = r && propertyLong_7(b, l + 1);
    exit_section_(b, m, PROPERTY_LONG, r);
    return r;
  }

  // [PROPERTY_DEFAULT_OPENING PROPERTY_DEFAULT_LONG]
  private static boolean propertyLong_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "propertyLong_5")) return false;
    parseTokens(b, 0, PROPERTY_DEFAULT_OPENING, PROPERTY_DEFAULT_LONG);
    return true;
  }

  // [(PROPERTY_ATTRIBUTE)*]
  private static boolean propertyLong_6(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "propertyLong_6")) return false;
    propertyLong_6_0(b, l + 1);
    return true;
  }

  // (PROPERTY_ATTRIBUTE)*
  private static boolean propertyLong_6_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "propertyLong_6_0")) return false;
    int c = current_position_(b);
    while (true) {
      if (!consumeToken(b, PROPERTY_ATTRIBUTE)) break;
      if (!empty_element_parsed_guard_(b, "propertyLong_6_0", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // [PROPERTY_CONSTRAINT_OPENING PROPERTY_CONSTRAINT]
  private static boolean propertyLong_7(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "propertyLong_7")) return false;
    parseTokens(b, 0, PROPERTY_CONSTRAINT_OPENING, PROPERTY_CONSTRAINT);
    return true;
  }

  /* ********************************************************** */
  // propertyBinary | propertyLong | propertyDouble | propertyBoolean | propertyDate | propertyString | propertyWeakreference
  public static boolean propertyMinus(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "propertyMinus")) return false;
    if (!nextTokenIs(b, PROPERTY_MINUS_OPENING)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = propertyBinary(b, l + 1);
    if (!r) r = propertyLong(b, l + 1);
    if (!r) r = propertyDouble(b, l + 1);
    if (!r) r = propertyBoolean(b, l + 1);
    if (!r) r = propertyDate(b, l + 1);
    if (!r) r = propertyString(b, l + 1);
    if (!r) r = propertyWeakreference(b, l + 1);
    exit_section_(b, m, PROPERTY_MINUS, r);
    return r;
  }

  /* ********************************************************** */
  // PROPERTY_PLUS_OPENING PROPERTY_PLUS_NAME PROPERTY_TYPE_OPENING NODE_TYPE_NAMESPACE NODE_TYPE_DECLARATION_COLON NODE_TYPE_NAME PROPERTY_TYPE_CLOSING [PROPERTY_DEFAULT_OPENING PROPERTY_DEFAULT_VALUE] [(PROPERTY_PLUS_ATTRIBUTE)*]
  public static boolean propertyPlus(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "propertyPlus")) return false;
    if (!nextTokenIs(b, PROPERTY_PLUS_OPENING)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, PROPERTY_PLUS_OPENING, PROPERTY_PLUS_NAME, PROPERTY_TYPE_OPENING, NODE_TYPE_NAMESPACE, NODE_TYPE_DECLARATION_COLON, NODE_TYPE_NAME, PROPERTY_TYPE_CLOSING);
    r = r && propertyPlus_7(b, l + 1);
    r = r && propertyPlus_8(b, l + 1);
    exit_section_(b, m, PROPERTY_PLUS, r);
    return r;
  }

  // [PROPERTY_DEFAULT_OPENING PROPERTY_DEFAULT_VALUE]
  private static boolean propertyPlus_7(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "propertyPlus_7")) return false;
    parseTokens(b, 0, PROPERTY_DEFAULT_OPENING, PROPERTY_DEFAULT_VALUE);
    return true;
  }

  // [(PROPERTY_PLUS_ATTRIBUTE)*]
  private static boolean propertyPlus_8(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "propertyPlus_8")) return false;
    propertyPlus_8_0(b, l + 1);
    return true;
  }

  // (PROPERTY_PLUS_ATTRIBUTE)*
  private static boolean propertyPlus_8_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "propertyPlus_8_0")) return false;
    int c = current_position_(b);
    while (true) {
      if (!consumeToken(b, PROPERTY_PLUS_ATTRIBUTE)) break;
      if (!empty_element_parsed_guard_(b, "propertyPlus_8_0", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  /* ********************************************************** */
  // propertyStringText | propertyStringChoicelist
  public static boolean propertyString(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "propertyString")) return false;
    if (!nextTokenIs(b, PROPERTY_MINUS_OPENING)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = propertyStringText(b, l + 1);
    if (!r) r = propertyStringChoicelist(b, l + 1);
    exit_section_(b, m, PROPERTY_STRING, r);
    return r;
  }

  /* ********************************************************** */
  // PROPERTY_MINUS_OPENING PROPERTY_NAME PROPERTY_TYPE_OPENING PROPERTY_TYPE_STRING_CHOICELIST PROPERTY_TYPE_CLOSING [PROPERTY_DEFAULT_OPENING PROPERTY_DEFAULT_STRING_CHOICELIST] [(PROPERTY_ATTRIBUTE)*] [PROPERTY_CONSTRAINT_OPENING PROPERTY_CONSTRAINT]
  public static boolean propertyStringChoicelist(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "propertyStringChoicelist")) return false;
    if (!nextTokenIs(b, PROPERTY_MINUS_OPENING)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, PROPERTY_MINUS_OPENING, PROPERTY_NAME, PROPERTY_TYPE_OPENING, PROPERTY_TYPE_STRING_CHOICELIST, PROPERTY_TYPE_CLOSING);
    r = r && propertyStringChoicelist_5(b, l + 1);
    r = r && propertyStringChoicelist_6(b, l + 1);
    r = r && propertyStringChoicelist_7(b, l + 1);
    exit_section_(b, m, PROPERTY_STRING_CHOICELIST, r);
    return r;
  }

  // [PROPERTY_DEFAULT_OPENING PROPERTY_DEFAULT_STRING_CHOICELIST]
  private static boolean propertyStringChoicelist_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "propertyStringChoicelist_5")) return false;
    parseTokens(b, 0, PROPERTY_DEFAULT_OPENING, PROPERTY_DEFAULT_STRING_CHOICELIST);
    return true;
  }

  // [(PROPERTY_ATTRIBUTE)*]
  private static boolean propertyStringChoicelist_6(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "propertyStringChoicelist_6")) return false;
    propertyStringChoicelist_6_0(b, l + 1);
    return true;
  }

  // (PROPERTY_ATTRIBUTE)*
  private static boolean propertyStringChoicelist_6_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "propertyStringChoicelist_6_0")) return false;
    int c = current_position_(b);
    while (true) {
      if (!consumeToken(b, PROPERTY_ATTRIBUTE)) break;
      if (!empty_element_parsed_guard_(b, "propertyStringChoicelist_6_0", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // [PROPERTY_CONSTRAINT_OPENING PROPERTY_CONSTRAINT]
  private static boolean propertyStringChoicelist_7(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "propertyStringChoicelist_7")) return false;
    parseTokens(b, 0, PROPERTY_CONSTRAINT_OPENING, PROPERTY_CONSTRAINT);
    return true;
  }

  /* ********************************************************** */
  // PROPERTY_MINUS_OPENING PROPERTY_NAME PROPERTY_TYPE_OPENING PROPERTY_TYPE_STRING_TEXT PROPERTY_TYPE_CLOSING [PROPERTY_DEFAULT_OPENING PROPERTY_DEFAULT_STRING_TEXT] [(PROPERTY_ATTRIBUTE)*] [PROPERTY_CONSTRAINT_OPENING PROPERTY_CONSTRAINT]
  public static boolean propertyStringText(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "propertyStringText")) return false;
    if (!nextTokenIs(b, PROPERTY_MINUS_OPENING)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, PROPERTY_MINUS_OPENING, PROPERTY_NAME, PROPERTY_TYPE_OPENING, PROPERTY_TYPE_STRING_TEXT, PROPERTY_TYPE_CLOSING);
    r = r && propertyStringText_5(b, l + 1);
    r = r && propertyStringText_6(b, l + 1);
    r = r && propertyStringText_7(b, l + 1);
    exit_section_(b, m, PROPERTY_STRING_TEXT, r);
    return r;
  }

  // [PROPERTY_DEFAULT_OPENING PROPERTY_DEFAULT_STRING_TEXT]
  private static boolean propertyStringText_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "propertyStringText_5")) return false;
    parseTokens(b, 0, PROPERTY_DEFAULT_OPENING, PROPERTY_DEFAULT_STRING_TEXT);
    return true;
  }

  // [(PROPERTY_ATTRIBUTE)*]
  private static boolean propertyStringText_6(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "propertyStringText_6")) return false;
    propertyStringText_6_0(b, l + 1);
    return true;
  }

  // (PROPERTY_ATTRIBUTE)*
  private static boolean propertyStringText_6_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "propertyStringText_6_0")) return false;
    int c = current_position_(b);
    while (true) {
      if (!consumeToken(b, PROPERTY_ATTRIBUTE)) break;
      if (!empty_element_parsed_guard_(b, "propertyStringText_6_0", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // [PROPERTY_CONSTRAINT_OPENING PROPERTY_CONSTRAINT]
  private static boolean propertyStringText_7(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "propertyStringText_7")) return false;
    parseTokens(b, 0, PROPERTY_CONSTRAINT_OPENING, PROPERTY_CONSTRAINT);
    return true;
  }

  /* ********************************************************** */
  // PROPERTY_MINUS_OPENING PROPERTY_NAME PROPERTY_TYPE_OPENING PROPERTY_TYPE_WEAKREFERENCE PROPERTY_TYPE_CLOSING [(PROPERTY_ATTRIBUTE)*] [PROPERTY_CONSTRAINT_OPENING PROPERTY_CONSTRAINT]
  public static boolean propertyWeakreference(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "propertyWeakreference")) return false;
    if (!nextTokenIs(b, PROPERTY_MINUS_OPENING)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, PROPERTY_MINUS_OPENING, PROPERTY_NAME, PROPERTY_TYPE_OPENING, PROPERTY_TYPE_WEAKREFERENCE, PROPERTY_TYPE_CLOSING);
    r = r && propertyWeakreference_5(b, l + 1);
    r = r && propertyWeakreference_6(b, l + 1);
    exit_section_(b, m, PROPERTY_WEAKREFERENCE, r);
    return r;
  }

  // [(PROPERTY_ATTRIBUTE)*]
  private static boolean propertyWeakreference_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "propertyWeakreference_5")) return false;
    propertyWeakreference_5_0(b, l + 1);
    return true;
  }

  // (PROPERTY_ATTRIBUTE)*
  private static boolean propertyWeakreference_5_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "propertyWeakreference_5_0")) return false;
    int c = current_position_(b);
    while (true) {
      if (!consumeToken(b, PROPERTY_ATTRIBUTE)) break;
      if (!empty_element_parsed_guard_(b, "propertyWeakreference_5_0", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // [PROPERTY_CONSTRAINT_OPENING PROPERTY_CONSTRAINT]
  private static boolean propertyWeakreference_6(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "propertyWeakreference_6")) return false;
    parseTokens(b, 0, PROPERTY_CONSTRAINT_OPENING, PROPERTY_CONSTRAINT);
    return true;
  }

}

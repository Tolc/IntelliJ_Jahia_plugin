// This is a generated file. Not intended for manual editing.
package fr.tolc.jahia.intellij.plugin.cnd.parser;

import static com.intellij.lang.parser.GeneratedParserUtilBase.*;

import com.intellij.lang.ASTNode;
import com.intellij.lang.LightPsiParser;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import com.intellij.lang.PsiParser;
import com.intellij.lang.parser.GeneratedParserUtilBase;
import com.intellij.psi.tree.IElementType;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndTypes;

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
    if (t == CndTypes.EXTEND) {
      r = extend(b, 0);
    }
    else if (t == CndTypes.INHERITANCE) {
      r = inheritance(b, 0);
    }
    else if (t == CndTypes.INHERITANCES) {
      r = inheritances(b, 0);
    }
    else if (t == CndTypes.NAMESPACE) {
      r = namespace(b, 0);
    }
    else if (t == CndTypes.NODE_TYPE) {
      r = nodeType(b, 0);
    }
    else if (t == CndTypes.PROPERTIES) {
      r = properties(b, 0);
    }
    else if (t == CndTypes.PROPERTY_MINUS) {
      r = propertyMinus(b, 0);
    }
    else if (t == CndTypes.PROPERTY_PLUS) {
      r = propertyPlus(b, 0);
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
    r = GeneratedParserUtilBase.consumeToken(b, CndTypes.COMMENT);
    if (!r) r = nodeType(b, l + 1);
    if (!r) r = GeneratedParserUtilBase.consumeToken(b, CndTypes.CRLF);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // EXTEND_OPENING NODE_TYPE_NAMESPACE NODE_TYPE_DECLARATION_COLON NODE_TYPE_NAME [EXTEND_ITEM_START EXTEND_ITEM_TYPE]
  public static boolean extend(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "extend")) return false;
    if (!GeneratedParserUtilBase.nextTokenIs(b, CndTypes.EXTEND_OPENING)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, CndTypes.EXTEND_OPENING, CndTypes.NODE_TYPE_NAMESPACE, CndTypes.NODE_TYPE_DECLARATION_COLON, CndTypes.NODE_TYPE_NAME);
    r = r && extend_4(b, l + 1);
    exit_section_(b, m, CndTypes.EXTEND, r);
    return r;
  }

  // [EXTEND_ITEM_START EXTEND_ITEM_TYPE]
  private static boolean extend_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "extend_4")) return false;
    GeneratedParserUtilBase.parseTokens(b, 0, CndTypes.EXTEND_ITEM_START, CndTypes.EXTEND_ITEM_TYPE);
    return true;
  }

  /* ********************************************************** */
  // NODE_TYPE_INHERITANCE_NAMESPACE NODE_TYPE_INHERITANCE_COLON NODE_TYPE_INHERITANCE_TYPE_NAME
  public static boolean inheritance(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "inheritance")) return false;
    if (!GeneratedParserUtilBase.nextTokenIs(b, CndTypes.NODE_TYPE_INHERITANCE_NAMESPACE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, CndTypes.NODE_TYPE_INHERITANCE_NAMESPACE, CndTypes.NODE_TYPE_INHERITANCE_COLON, CndTypes.NODE_TYPE_INHERITANCE_TYPE_NAME);
    exit_section_(b, m, CndTypes.INHERITANCE, r);
    return r;
  }

  /* ********************************************************** */
  // NODE_TYPE_INHERITANCE_OPENING inheritance (NODE_TYPE_INHERITANCE_TYPE_COMMA inheritance)*
  public static boolean inheritances(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "inheritances")) return false;
    if (!GeneratedParserUtilBase.nextTokenIs(b, CndTypes.NODE_TYPE_INHERITANCE_OPENING)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = GeneratedParserUtilBase.consumeToken(b, CndTypes.NODE_TYPE_INHERITANCE_OPENING);
    r = r && inheritance(b, l + 1);
    r = r && inheritances_2(b, l + 1);
    exit_section_(b, m, CndTypes.INHERITANCES, r);
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
    r = GeneratedParserUtilBase.consumeToken(b, CndTypes.NODE_TYPE_INHERITANCE_TYPE_COMMA);
    r = r && inheritance(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // NAMESPACE_OPENING NAMESPACE_NAME NAMESPACE_EQUAL NAMESPACE_URI NAMESPACE_CLOSING
  public static boolean namespace(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "namespace")) return false;
    if (!GeneratedParserUtilBase.nextTokenIs(b, CndTypes.NAMESPACE_OPENING)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, CndTypes.NAMESPACE_OPENING, CndTypes.NAMESPACE_NAME, CndTypes.NAMESPACE_EQUAL, CndTypes.NAMESPACE_URI, CndTypes.NAMESPACE_CLOSING);
    exit_section_(b, m, CndTypes.NAMESPACE, r);
    return r;
  }

  /* ********************************************************** */
  // NODE_TYPE_DECLARATION_OPENING NODE_TYPE_NAMESPACE NODE_TYPE_DECLARATION_COLON NODE_TYPE_NAME NODE_TYPE_DECLARATION_CLOSING [inheritances] [NODE_TYPE_ORDERABLE|NODE_TYPE_MIXIN|NODE_TYPE_MIXIN WHITE_SPACE+ NODE_TYPE_ORDERABLE|NODE_TYPE_ORDERABLE WHITE_SPACE+ NODE_TYPE_MIXIN|NODE_TYPE_ABSTRACT] [extend] [properties]
  public static boolean nodeType(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "nodeType")) return false;
    if (!GeneratedParserUtilBase.nextTokenIs(b, CndTypes.NODE_TYPE_DECLARATION_OPENING)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, CndTypes.NODE_TYPE_DECLARATION_OPENING, CndTypes.NODE_TYPE_NAMESPACE, CndTypes.NODE_TYPE_DECLARATION_COLON, CndTypes.NODE_TYPE_NAME, CndTypes.NODE_TYPE_DECLARATION_CLOSING);
    r = r && nodeType_5(b, l + 1);
    r = r && nodeType_6(b, l + 1);
    r = r && nodeType_7(b, l + 1);
    r = r && nodeType_8(b, l + 1);
    exit_section_(b, m, CndTypes.NODE_TYPE, r);
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
    r = GeneratedParserUtilBase.consumeToken(b, CndTypes.NODE_TYPE_ORDERABLE);
    if (!r) r = GeneratedParserUtilBase.consumeToken(b, CndTypes.NODE_TYPE_MIXIN);
    if (!r) r = nodeType_6_0_2(b, l + 1);
    if (!r) r = nodeType_6_0_3(b, l + 1);
    if (!r) r = GeneratedParserUtilBase.consumeToken(b, CndTypes.NODE_TYPE_ABSTRACT);
    exit_section_(b, m, null, r);
    return r;
  }

  // NODE_TYPE_MIXIN WHITE_SPACE+ NODE_TYPE_ORDERABLE
  private static boolean nodeType_6_0_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "nodeType_6_0_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = GeneratedParserUtilBase.consumeToken(b, CndTypes.NODE_TYPE_MIXIN);
    r = r && nodeType_6_0_2_1(b, l + 1);
    r = r && GeneratedParserUtilBase.consumeToken(b, CndTypes.NODE_TYPE_ORDERABLE);
    exit_section_(b, m, null, r);
    return r;
  }

  // WHITE_SPACE+
  private static boolean nodeType_6_0_2_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "nodeType_6_0_2_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = GeneratedParserUtilBase.consumeToken(b, CndTypes.WHITE_SPACE);
    int c = current_position_(b);
    while (r) {
      if (!GeneratedParserUtilBase.consumeToken(b, CndTypes.WHITE_SPACE)) break;
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
    r = GeneratedParserUtilBase.consumeToken(b, CndTypes.NODE_TYPE_ORDERABLE);
    r = r && nodeType_6_0_3_1(b, l + 1);
    r = r && GeneratedParserUtilBase.consumeToken(b, CndTypes.NODE_TYPE_MIXIN);
    exit_section_(b, m, null, r);
    return r;
  }

  // WHITE_SPACE+
  private static boolean nodeType_6_0_3_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "nodeType_6_0_3_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = GeneratedParserUtilBase.consumeToken(b, CndTypes.WHITE_SPACE);
    int c = current_position_(b);
    while (r) {
      if (!GeneratedParserUtilBase.consumeToken(b, CndTypes.WHITE_SPACE)) break;
      if (!empty_element_parsed_guard_(b, "nodeType_6_0_3_1", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, m, null, r);
    return r;
  }

  // [extend]
  private static boolean nodeType_7(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "nodeType_7")) return false;
    extend(b, l + 1);
    return true;
  }

  // [properties]
  private static boolean nodeType_8(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "nodeType_8")) return false;
    properties(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // (propertyMinus|propertyPlus)*
  public static boolean properties(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "properties")) return false;
    Marker m = enter_section_(b, l, _NONE_, "<properties>");
    int c = current_position_(b);
    while (true) {
      if (!properties_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "properties", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, l, m, CndTypes.PROPERTIES, true, false, null);
    return true;
  }

  // propertyMinus|propertyPlus
  private static boolean properties_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "properties_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = propertyMinus(b, l + 1);
    if (!r) r = propertyPlus(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // PROPERTY_MINUS_OPENING PROPERTY_NAME PROPERTY_TYPE_OPENING PROPERTY_TYPE PROPERTY_TYPE_CLOSING [PROPERTY_DEFAULT_OPENING PROPERTY_DEFAULT_VALUE] [(PROPERTY_ATTRIBUTE)*] [PROPERTY_CONSTRAINT_OPENING PROPERTY_CONSTRAINT]
  public static boolean propertyMinus(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "propertyMinus")) return false;
    if (!GeneratedParserUtilBase.nextTokenIs(b, CndTypes.PROPERTY_MINUS_OPENING)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, CndTypes.PROPERTY_MINUS_OPENING, CndTypes.PROPERTY_NAME, CndTypes.PROPERTY_TYPE_OPENING, CndTypes.PROPERTY_TYPE, CndTypes.PROPERTY_TYPE_CLOSING);
    r = r && propertyMinus_5(b, l + 1);
    r = r && propertyMinus_6(b, l + 1);
    r = r && propertyMinus_7(b, l + 1);
    exit_section_(b, m, CndTypes.PROPERTY_MINUS, r);
    return r;
  }

  // [PROPERTY_DEFAULT_OPENING PROPERTY_DEFAULT_VALUE]
  private static boolean propertyMinus_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "propertyMinus_5")) return false;
    GeneratedParserUtilBase.parseTokens(b, 0, CndTypes.PROPERTY_DEFAULT_OPENING, CndTypes.PROPERTY_DEFAULT_VALUE);
    return true;
  }

  // [(PROPERTY_ATTRIBUTE)*]
  private static boolean propertyMinus_6(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "propertyMinus_6")) return false;
    propertyMinus_6_0(b, l + 1);
    return true;
  }

  // (PROPERTY_ATTRIBUTE)*
  private static boolean propertyMinus_6_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "propertyMinus_6_0")) return false;
    int c = current_position_(b);
    while (true) {
      if (!GeneratedParserUtilBase.consumeToken(b, CndTypes.PROPERTY_ATTRIBUTE)) break;
      if (!empty_element_parsed_guard_(b, "propertyMinus_6_0", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // [PROPERTY_CONSTRAINT_OPENING PROPERTY_CONSTRAINT]
  private static boolean propertyMinus_7(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "propertyMinus_7")) return false;
    GeneratedParserUtilBase.parseTokens(b, 0, CndTypes.PROPERTY_CONSTRAINT_OPENING, CndTypes.PROPERTY_CONSTRAINT);
    return true;
  }

  /* ********************************************************** */
  // PROPERTY_PLUS_OPENING PROPERTY_PLUS_NAME PROPERTY_TYPE_OPENING NODE_TYPE_NAMESPACE NODE_TYPE_DECLARATION_COLON NODE_TYPE_NAME PROPERTY_TYPE_CLOSING [PROPERTY_DEFAULT_OPENING PROPERTY_DEFAULT_VALUE] [(PROPERTY_PLUS_ATTRIBUTE)*]
  public static boolean propertyPlus(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "propertyPlus")) return false;
    if (!GeneratedParserUtilBase.nextTokenIs(b, CndTypes.PROPERTY_PLUS_OPENING)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, CndTypes.PROPERTY_PLUS_OPENING, CndTypes.PROPERTY_PLUS_NAME, CndTypes.PROPERTY_TYPE_OPENING, CndTypes.NODE_TYPE_NAMESPACE, CndTypes.NODE_TYPE_DECLARATION_COLON, CndTypes.NODE_TYPE_NAME, CndTypes.PROPERTY_TYPE_CLOSING);
    r = r && propertyPlus_7(b, l + 1);
    r = r && propertyPlus_8(b, l + 1);
    exit_section_(b, m, CndTypes.PROPERTY_PLUS, r);
    return r;
  }

  // [PROPERTY_DEFAULT_OPENING PROPERTY_DEFAULT_VALUE]
  private static boolean propertyPlus_7(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "propertyPlus_7")) return false;
    GeneratedParserUtilBase.parseTokens(b, 0, CndTypes.PROPERTY_DEFAULT_OPENING, CndTypes.PROPERTY_DEFAULT_VALUE);
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
      if (!GeneratedParserUtilBase.consumeToken(b, CndTypes.PROPERTY_PLUS_ATTRIBUTE)) break;
      if (!empty_element_parsed_guard_(b, "propertyPlus_8_0", c)) break;
      c = current_position_(b);
    }
    return true;
  }

}

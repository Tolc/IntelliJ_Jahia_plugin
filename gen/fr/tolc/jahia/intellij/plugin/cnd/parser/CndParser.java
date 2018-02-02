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
    if (t == EXTENSION) {
      r = extension(b, 0);
    }
    else if (t == EXTENSIONS) {
      r = extensions(b, 0);
    }
    else if (t == ITEM_TYPE) {
      r = itemType(b, 0);
    }
    else if (t == NAMESPACE) {
      r = namespace(b, 0);
    }
    else if (t == NAMESPACE_IDENTIFIER) {
      r = namespaceIdentifier(b, 0);
    }
    else if (t == NODE_OPTION) {
      r = nodeOption(b, 0);
    }
    else if (t == NODE_TYPE) {
      r = nodeType(b, 0);
    }
    else if (t == NODE_TYPE_IDENTIFIER) {
      r = nodeTypeIdentifier(b, 0);
    }
    else if (t == PROPERTY) {
      r = property(b, 0);
    }
    else if (t == PROPERTY_ATTRIBUTES) {
      r = propertyAttributes(b, 0);
    }
    else if (t == PROPERTY_CONSTRAINT) {
      r = propertyConstraint(b, 0);
    }
    else if (t == PROPERTY_DEFAULT) {
      r = propertyDefault(b, 0);
    }
    else if (t == PROPERTY_IDENTIFIER) {
      r = propertyIdentifier(b, 0);
    }
    else if (t == PROPERTY_TYPE_MASK_OPTION) {
      r = propertyTypeMaskOption(b, 0);
    }
    else if (t == SUB_NODE) {
      r = subNode(b, 0);
    }
    else if (t == SUB_NODE_ATTRIBUTES) {
      r = subNodeAttributes(b, 0);
    }
    else if (t == SUB_NODE_DEFAULT_TYPE) {
      r = subNodeDefaultType(b, 0);
    }
    else if (t == SUB_NODE_TYPE) {
      r = subNodeType(b, 0);
    }
    else if (t == SUPER_TYPE) {
      r = superType(b, 0);
    }
    else if (t == SUPER_TYPES) {
      r = superTypes(b, 0);
    }
    else {
      r = parse_root_(t, b, 0);
    }
    exit_section_(b, 0, m, t, r, true, TRUE_CONDITION);
  }

  protected boolean parse_root_(IElementType t, PsiBuilder b, int l) {
    return cnd(b, l + 1);
  }

  /* ********************************************************** */
  // (COMMENT|CRLF)* (namespace CRLF)* (namespace | (nodeType | COMMENT | CRLF)*)
  static boolean cnd(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "cnd")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = cnd_0(b, l + 1);
    r = r && cnd_1(b, l + 1);
    r = r && cnd_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (COMMENT|CRLF)*
  private static boolean cnd_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "cnd_0")) return false;
    int c = current_position_(b);
    while (true) {
      if (!cnd_0_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "cnd_0", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // COMMENT|CRLF
  private static boolean cnd_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "cnd_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMENT);
    if (!r) r = consumeToken(b, CRLF);
    exit_section_(b, m, null, r);
    return r;
  }

  // (namespace CRLF)*
  private static boolean cnd_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "cnd_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!cnd_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "cnd_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // namespace CRLF
  private static boolean cnd_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "cnd_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = namespace(b, l + 1);
    r = r && consumeToken(b, CRLF);
    exit_section_(b, m, null, r);
    return r;
  }

  // namespace | (nodeType | COMMENT | CRLF)*
  private static boolean cnd_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "cnd_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = namespace(b, l + 1);
    if (!r) r = cnd_2_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (nodeType | COMMENT | CRLF)*
  private static boolean cnd_2_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "cnd_2_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!cnd_2_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "cnd_2_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // nodeType | COMMENT | CRLF
  private static boolean cnd_2_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "cnd_2_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = nodeType(b, l + 1);
    if (!r) r = consumeToken(b, COMMENT);
    if (!r) r = consumeToken(b, CRLF);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // NAMESPACE_NAME COLON NODE_TYPE_NAME
  public static boolean extension(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "extension")) return false;
    if (!nextTokenIs(b, NAMESPACE_NAME)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, NAMESPACE_NAME, COLON, NODE_TYPE_NAME);
    exit_section_(b, m, EXTENSION, r);
    return r;
  }

  /* ********************************************************** */
  // EXTENDS EQUAL extension (COMMA extension)*
  public static boolean extensions(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "extensions")) return false;
    if (!nextTokenIs(b, EXTENDS)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, EXTENDS, EQUAL);
    r = r && extension(b, l + 1);
    r = r && extensions_3(b, l + 1);
    exit_section_(b, m, EXTENSIONS, r);
    return r;
  }

  // (COMMA extension)*
  private static boolean extensions_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "extensions_3")) return false;
    int c = current_position_(b);
    while (true) {
      if (!extensions_3_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "extensions_3", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // COMMA extension
  private static boolean extensions_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "extensions_3_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && extension(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // ITEMTYPE EQUAL ITEMTYPE_TYPE
  public static boolean itemType(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "itemType")) return false;
    if (!nextTokenIs(b, ITEMTYPE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, ITEMTYPE, EQUAL, ITEMTYPE_TYPE);
    exit_section_(b, m, ITEM_TYPE, r);
    return r;
  }

  /* ********************************************************** */
  // LEFT_ANGLE_BRACKET namespaceIdentifier EQUAL SINGLE_QUOTE NAMESPACE_URI SINGLE_QUOTE RIGHT_ANGLE_BRACKET
  public static boolean namespace(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "namespace")) return false;
    if (!nextTokenIs(b, LEFT_ANGLE_BRACKET)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LEFT_ANGLE_BRACKET);
    r = r && namespaceIdentifier(b, l + 1);
    r = r && consumeTokens(b, 0, EQUAL, SINGLE_QUOTE, NAMESPACE_URI, SINGLE_QUOTE, RIGHT_ANGLE_BRACKET);
    exit_section_(b, m, NAMESPACE, r);
    return r;
  }

  /* ********************************************************** */
  // NAMESPACE_NAME
  public static boolean namespaceIdentifier(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "namespaceIdentifier")) return false;
    if (!nextTokenIs(b, NAMESPACE_NAME)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, NAMESPACE_NAME);
    exit_section_(b, m, NAMESPACE_IDENTIFIER, r);
    return r;
  }

  /* ********************************************************** */
  // OPTION
  public static boolean nodeOption(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "nodeOption")) return false;
    if (!nextTokenIs(b, OPTION)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OPTION);
    exit_section_(b, m, NODE_OPTION, r);
    return r;
  }

  /* ********************************************************** */
  // LEFT_BRACKET NAMESPACE_NAME COLON nodeTypeIdentifier RIGHT_BRACKET [superTypes] [options] [COMMENT] [CRLF extensions] [COMMENT] [CRLF itemType] [COMMENT] [CRLF extensions] (CRLF+ COMMENT | CRLF+ property | CRLF+ subNode)* [CRLF]
  public static boolean nodeType(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "nodeType")) return false;
    if (!nextTokenIs(b, LEFT_BRACKET)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, LEFT_BRACKET, NAMESPACE_NAME, COLON);
    r = r && nodeTypeIdentifier(b, l + 1);
    r = r && consumeToken(b, RIGHT_BRACKET);
    r = r && nodeType_5(b, l + 1);
    r = r && nodeType_6(b, l + 1);
    r = r && nodeType_7(b, l + 1);
    r = r && nodeType_8(b, l + 1);
    r = r && nodeType_9(b, l + 1);
    r = r && nodeType_10(b, l + 1);
    r = r && nodeType_11(b, l + 1);
    r = r && nodeType_12(b, l + 1);
    r = r && nodeType_13(b, l + 1);
    r = r && nodeType_14(b, l + 1);
    exit_section_(b, m, NODE_TYPE, r);
    return r;
  }

  // [superTypes]
  private static boolean nodeType_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "nodeType_5")) return false;
    superTypes(b, l + 1);
    return true;
  }

  // [options]
  private static boolean nodeType_6(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "nodeType_6")) return false;
    options(b, l + 1);
    return true;
  }

  // [COMMENT]
  private static boolean nodeType_7(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "nodeType_7")) return false;
    consumeToken(b, COMMENT);
    return true;
  }

  // [CRLF extensions]
  private static boolean nodeType_8(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "nodeType_8")) return false;
    nodeType_8_0(b, l + 1);
    return true;
  }

  // CRLF extensions
  private static boolean nodeType_8_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "nodeType_8_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, CRLF);
    r = r && extensions(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // [COMMENT]
  private static boolean nodeType_9(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "nodeType_9")) return false;
    consumeToken(b, COMMENT);
    return true;
  }

  // [CRLF itemType]
  private static boolean nodeType_10(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "nodeType_10")) return false;
    nodeType_10_0(b, l + 1);
    return true;
  }

  // CRLF itemType
  private static boolean nodeType_10_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "nodeType_10_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, CRLF);
    r = r && itemType(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // [COMMENT]
  private static boolean nodeType_11(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "nodeType_11")) return false;
    consumeToken(b, COMMENT);
    return true;
  }

  // [CRLF extensions]
  private static boolean nodeType_12(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "nodeType_12")) return false;
    nodeType_12_0(b, l + 1);
    return true;
  }

  // CRLF extensions
  private static boolean nodeType_12_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "nodeType_12_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, CRLF);
    r = r && extensions(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (CRLF+ COMMENT | CRLF+ property | CRLF+ subNode)*
  private static boolean nodeType_13(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "nodeType_13")) return false;
    int c = current_position_(b);
    while (true) {
      if (!nodeType_13_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "nodeType_13", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // CRLF+ COMMENT | CRLF+ property | CRLF+ subNode
  private static boolean nodeType_13_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "nodeType_13_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = nodeType_13_0_0(b, l + 1);
    if (!r) r = nodeType_13_0_1(b, l + 1);
    if (!r) r = nodeType_13_0_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // CRLF+ COMMENT
  private static boolean nodeType_13_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "nodeType_13_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = nodeType_13_0_0_0(b, l + 1);
    r = r && consumeToken(b, COMMENT);
    exit_section_(b, m, null, r);
    return r;
  }

  // CRLF+
  private static boolean nodeType_13_0_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "nodeType_13_0_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, CRLF);
    int c = current_position_(b);
    while (r) {
      if (!consumeToken(b, CRLF)) break;
      if (!empty_element_parsed_guard_(b, "nodeType_13_0_0_0", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, m, null, r);
    return r;
  }

  // CRLF+ property
  private static boolean nodeType_13_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "nodeType_13_0_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = nodeType_13_0_1_0(b, l + 1);
    r = r && property(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // CRLF+
  private static boolean nodeType_13_0_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "nodeType_13_0_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, CRLF);
    int c = current_position_(b);
    while (r) {
      if (!consumeToken(b, CRLF)) break;
      if (!empty_element_parsed_guard_(b, "nodeType_13_0_1_0", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, m, null, r);
    return r;
  }

  // CRLF+ subNode
  private static boolean nodeType_13_0_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "nodeType_13_0_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = nodeType_13_0_2_0(b, l + 1);
    r = r && subNode(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // CRLF+
  private static boolean nodeType_13_0_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "nodeType_13_0_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, CRLF);
    int c = current_position_(b);
    while (r) {
      if (!consumeToken(b, CRLF)) break;
      if (!empty_element_parsed_guard_(b, "nodeType_13_0_2_0", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, m, null, r);
    return r;
  }

  // [CRLF]
  private static boolean nodeType_14(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "nodeType_14")) return false;
    consumeToken(b, CRLF);
    return true;
  }

  /* ********************************************************** */
  // NODE_TYPE_NAME
  public static boolean nodeTypeIdentifier(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "nodeTypeIdentifier")) return false;
    if (!nextTokenIs(b, NODE_TYPE_NAME)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, NODE_TYPE_NAME);
    exit_section_(b, m, NODE_TYPE_IDENTIFIER, r);
    return r;
  }

  /* ********************************************************** */
  // ([CRLF] nodeOption)+
  static boolean options(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "options")) return false;
    if (!nextTokenIs(b, "", CRLF, OPTION)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = options_0(b, l + 1);
    int c = current_position_(b);
    while (r) {
      if (!options_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "options", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, m, null, r);
    return r;
  }

  // [CRLF] nodeOption
  private static boolean options_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "options_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = options_0_0(b, l + 1);
    r = r && nodeOption(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // [CRLF]
  private static boolean options_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "options_0_0")) return false;
    consumeToken(b, CRLF);
    return true;
  }

  /* ********************************************************** */
  // MINUS propertyIdentifier [propertyType] [propertyDefault] [propertyAttributes] [propertyConstraint]
  public static boolean property(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "property")) return false;
    if (!nextTokenIs(b, MINUS)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, MINUS);
    r = r && propertyIdentifier(b, l + 1);
    r = r && property_2(b, l + 1);
    r = r && property_3(b, l + 1);
    r = r && property_4(b, l + 1);
    r = r && property_5(b, l + 1);
    exit_section_(b, m, PROPERTY, r);
    return r;
  }

  // [propertyType]
  private static boolean property_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "property_2")) return false;
    propertyType(b, l + 1);
    return true;
  }

  // [propertyDefault]
  private static boolean property_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "property_3")) return false;
    propertyDefault(b, l + 1);
    return true;
  }

  // [propertyAttributes]
  private static boolean property_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "property_4")) return false;
    propertyAttributes(b, l + 1);
    return true;
  }

  // [propertyConstraint]
  private static boolean property_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "property_5")) return false;
    propertyConstraint(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // PROPERTY_ATTRIBUTE+
  public static boolean propertyAttributes(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "propertyAttributes")) return false;
    if (!nextTokenIs(b, PROPERTY_ATTRIBUTE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PROPERTY_ATTRIBUTE);
    int c = current_position_(b);
    while (r) {
      if (!consumeToken(b, PROPERTY_ATTRIBUTE)) break;
      if (!empty_element_parsed_guard_(b, "propertyAttributes", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, m, PROPERTY_ATTRIBUTES, r);
    return r;
  }

  /* ********************************************************** */
  // LEFT_ONLY_ANGLE_BRACKET PROPERTY_CONSTRAINT_VALUE
  public static boolean propertyConstraint(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "propertyConstraint")) return false;
    if (!nextTokenIs(b, LEFT_ONLY_ANGLE_BRACKET)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, LEFT_ONLY_ANGLE_BRACKET, PROPERTY_CONSTRAINT_VALUE);
    exit_section_(b, m, PROPERTY_CONSTRAINT, r);
    return r;
  }

  /* ********************************************************** */
  // EQUAL PROPERTY_DEFAULT_VALUE
  public static boolean propertyDefault(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "propertyDefault")) return false;
    if (!nextTokenIs(b, EQUAL)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, EQUAL, PROPERTY_DEFAULT_VALUE);
    exit_section_(b, m, PROPERTY_DEFAULT, r);
    return r;
  }

  /* ********************************************************** */
  // PROPERTY_NAME
  public static boolean propertyIdentifier(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "propertyIdentifier")) return false;
    if (!nextTokenIs(b, PROPERTY_NAME)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PROPERTY_NAME);
    exit_section_(b, m, PROPERTY_IDENTIFIER, r);
    return r;
  }

  /* ********************************************************** */
  // LEFT_BRACKET propertyTypeMaskOption (COMMA propertyTypeMaskOption)* RIGHT_BRACKET
  static boolean propertyMaskOptions(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "propertyMaskOptions")) return false;
    if (!nextTokenIs(b, LEFT_BRACKET)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LEFT_BRACKET);
    r = r && propertyTypeMaskOption(b, l + 1);
    r = r && propertyMaskOptions_2(b, l + 1);
    r = r && consumeToken(b, RIGHT_BRACKET);
    exit_section_(b, m, null, r);
    return r;
  }

  // (COMMA propertyTypeMaskOption)*
  private static boolean propertyMaskOptions_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "propertyMaskOptions_2")) return false;
    int c = current_position_(b);
    while (true) {
      if (!propertyMaskOptions_2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "propertyMaskOptions_2", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // COMMA propertyTypeMaskOption
  private static boolean propertyMaskOptions_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "propertyMaskOptions_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && propertyTypeMaskOption(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // LEFT_PARENTHESIS PROPERTY_TYPE [COMMA PROPERTY_MASK [propertyMaskOptions]] RIGHT_PARENTHESIS
  static boolean propertyType(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "propertyType")) return false;
    if (!nextTokenIs(b, LEFT_PARENTHESIS)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, LEFT_PARENTHESIS, PROPERTY_TYPE);
    r = r && propertyType_2(b, l + 1);
    r = r && consumeToken(b, RIGHT_PARENTHESIS);
    exit_section_(b, m, null, r);
    return r;
  }

  // [COMMA PROPERTY_MASK [propertyMaskOptions]]
  private static boolean propertyType_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "propertyType_2")) return false;
    propertyType_2_0(b, l + 1);
    return true;
  }

  // COMMA PROPERTY_MASK [propertyMaskOptions]
  private static boolean propertyType_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "propertyType_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, COMMA, PROPERTY_MASK);
    r = r && propertyType_2_0_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // [propertyMaskOptions]
  private static boolean propertyType_2_0_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "propertyType_2_0_2")) return false;
    propertyMaskOptions(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // PROPERTY_MASK_OPTION [EQUAL PROPERTY_MASK_OPTION_VALUE]
  public static boolean propertyTypeMaskOption(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "propertyTypeMaskOption")) return false;
    if (!nextTokenIs(b, PROPERTY_MASK_OPTION)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PROPERTY_MASK_OPTION);
    r = r && propertyTypeMaskOption_1(b, l + 1);
    exit_section_(b, m, PROPERTY_TYPE_MASK_OPTION, r);
    return r;
  }

  // [EQUAL PROPERTY_MASK_OPTION_VALUE]
  private static boolean propertyTypeMaskOption_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "propertyTypeMaskOption_1")) return false;
    parseTokens(b, 0, EQUAL, PROPERTY_MASK_OPTION_VALUE);
    return true;
  }

  /* ********************************************************** */
  // PLUS NODE_NAME LEFT_PARENTHESIS subNodeTypes RIGHT_PARENTHESIS [subNodeDefault] [subNodeAttributes]
  public static boolean subNode(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "subNode")) return false;
    if (!nextTokenIs(b, PLUS)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, PLUS, NODE_NAME, LEFT_PARENTHESIS);
    r = r && subNodeTypes(b, l + 1);
    r = r && consumeToken(b, RIGHT_PARENTHESIS);
    r = r && subNode_5(b, l + 1);
    r = r && subNode_6(b, l + 1);
    exit_section_(b, m, SUB_NODE, r);
    return r;
  }

  // [subNodeDefault]
  private static boolean subNode_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "subNode_5")) return false;
    subNodeDefault(b, l + 1);
    return true;
  }

  // [subNodeAttributes]
  private static boolean subNode_6(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "subNode_6")) return false;
    subNodeAttributes(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // NODE_ATTRIBUTE+
  public static boolean subNodeAttributes(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "subNodeAttributes")) return false;
    if (!nextTokenIs(b, NODE_ATTRIBUTE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, NODE_ATTRIBUTE);
    int c = current_position_(b);
    while (r) {
      if (!consumeToken(b, NODE_ATTRIBUTE)) break;
      if (!empty_element_parsed_guard_(b, "subNodeAttributes", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, m, SUB_NODE_ATTRIBUTES, r);
    return r;
  }

  /* ********************************************************** */
  // EQUAL subNodeDefaultType
  static boolean subNodeDefault(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "subNodeDefault")) return false;
    if (!nextTokenIs(b, EQUAL)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, EQUAL);
    r = r && subNodeDefaultType(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // NAMESPACE_NAME COLON NODE_TYPE_NAME
  public static boolean subNodeDefaultType(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "subNodeDefaultType")) return false;
    if (!nextTokenIs(b, NAMESPACE_NAME)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, NAMESPACE_NAME, COLON, NODE_TYPE_NAME);
    exit_section_(b, m, SUB_NODE_DEFAULT_TYPE, r);
    return r;
  }

  /* ********************************************************** */
  // NAMESPACE_NAME COLON NODE_TYPE_NAME
  public static boolean subNodeType(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "subNodeType")) return false;
    if (!nextTokenIs(b, NAMESPACE_NAME)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, NAMESPACE_NAME, COLON, NODE_TYPE_NAME);
    exit_section_(b, m, SUB_NODE_TYPE, r);
    return r;
  }

  /* ********************************************************** */
  // subNodeType (COMMA subNodeType)*
  static boolean subNodeTypes(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "subNodeTypes")) return false;
    if (!nextTokenIs(b, NAMESPACE_NAME)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = subNodeType(b, l + 1);
    r = r && subNodeTypes_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (COMMA subNodeType)*
  private static boolean subNodeTypes_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "subNodeTypes_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!subNodeTypes_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "subNodeTypes_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // COMMA subNodeType
  private static boolean subNodeTypes_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "subNodeTypes_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && subNodeType(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // NAMESPACE_NAME COLON NODE_TYPE_NAME
  public static boolean superType(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "superType")) return false;
    if (!nextTokenIs(b, NAMESPACE_NAME)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, NAMESPACE_NAME, COLON, NODE_TYPE_NAME);
    exit_section_(b, m, SUPER_TYPE, r);
    return r;
  }

  /* ********************************************************** */
  // RIGHT_ONLY_ANGLE_BRACKET superType (COMMA superType)* [COMMA]
  public static boolean superTypes(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "superTypes")) return false;
    if (!nextTokenIs(b, RIGHT_ONLY_ANGLE_BRACKET)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, RIGHT_ONLY_ANGLE_BRACKET);
    r = r && superType(b, l + 1);
    r = r && superTypes_2(b, l + 1);
    r = r && superTypes_3(b, l + 1);
    exit_section_(b, m, SUPER_TYPES, r);
    return r;
  }

  // (COMMA superType)*
  private static boolean superTypes_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "superTypes_2")) return false;
    int c = current_position_(b);
    while (true) {
      if (!superTypes_2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "superTypes_2", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // COMMA superType
  private static boolean superTypes_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "superTypes_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && superType(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // [COMMA]
  private static boolean superTypes_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "superTypes_3")) return false;
    consumeToken(b, COMMA);
    return true;
  }

}

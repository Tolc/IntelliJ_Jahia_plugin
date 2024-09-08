// This is a generated file. Not intended for manual editing.
package fr.tolc.jahia.language.cnd.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import static fr.tolc.jahia.language.cnd.psi.CndTypes.*;
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
    r = parse_root_(t, b);
    exit_section_(b, 0, m, t, r, true, TRUE_CONDITION);
  }

  protected boolean parse_root_(IElementType t, PsiBuilder b) {
    return parse_root_(t, b, 0);
  }

  static boolean parse_root_(IElementType t, PsiBuilder b, int l) {
    return cndFile(b, l + 1);
  }

  /* ********************************************************** */
  // item_*
  static boolean cndFile(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "cndFile")) return false;
    while (true) {
      int c = current_position_(b);
      if (!item_(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "cndFile", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // COMMENT|CRLF|namespace|nodetype
  static boolean item_(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "item_")) return false;
    boolean r;
    r = consumeToken(b, COMMENT);
    if (!r) r = consumeToken(b, CRLF);
    if (!r) r = namespace(b, l + 1);
    if (!r) r = nodetype(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // NS_START NS_NAME NS_EQUAL NS_URI_QUOTE NS_URI NS_URI_QUOTE NS_END
  public static boolean namespace(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "namespace")) return false;
    if (!nextTokenIs(b, NS_START)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, NS_START, NS_NAME, NS_EQUAL, NS_URI_QUOTE, NS_URI, NS_URI_QUOTE, NS_END);
    exit_section_(b, m, NAMESPACE, r);
    return r;
  }

  /* ********************************************************** */
  // NT_START NT_NAME NT_END [supertypes_] option_* subitem_*
  public static boolean nodetype(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "nodetype")) return false;
    if (!nextTokenIs(b, NT_START)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, NT_START, NT_NAME, NT_END);
    r = r && nodetype_3(b, l + 1);
    r = r && nodetype_4(b, l + 1);
    r = r && nodetype_5(b, l + 1);
    exit_section_(b, m, NODETYPE, r);
    return r;
  }

  // [supertypes_]
  private static boolean nodetype_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "nodetype_3")) return false;
    supertypes_(b, l + 1);
    return true;
  }

  // option_*
  private static boolean nodetype_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "nodetype_4")) return false;
    while (true) {
      int c = current_position_(b);
      if (!option_(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "nodetype_4", c)) break;
    }
    return true;
  }

  // subitem_*
  private static boolean nodetype_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "nodetype_5")) return false;
    while (true) {
      int c = current_position_(b);
      if (!subitem_(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "nodetype_5", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // OPT [OPT_EQUAL option_values_]
  static boolean option_(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "option_")) return false;
    if (!nextTokenIs(b, OPT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OPT);
    r = r && option__1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // [OPT_EQUAL option_values_]
  private static boolean option__1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "option__1")) return false;
    option__1_0(b, l + 1);
    return true;
  }

  // OPT_EQUAL option_values_
  private static boolean option__1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "option__1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OPT_EQUAL);
    r = r && option_values_(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // OPT_VALUE
  static boolean option_value_(PsiBuilder b, int l) {
    return consumeToken(b, OPT_VALUE);
  }

  /* ********************************************************** */
  // option_value_ (OPT_VALUE_SEP option_value_)*
  static boolean option_values_(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "option_values_")) return false;
    if (!nextTokenIs(b, OPT_VALUE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = option_value_(b, l + 1);
    r = r && option_values__1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (OPT_VALUE_SEP option_value_)*
  private static boolean option_values__1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "option_values__1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!option_values__1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "option_values__1", c)) break;
    }
    return true;
  }

  // OPT_VALUE_SEP option_value_
  private static boolean option_values__1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "option_values__1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OPT_VALUE_SEP);
    r = r && option_value_(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // PROP_ATTR
  static boolean prop_attr_(PsiBuilder b, int l) {
    return consumeToken(b, PROP_ATTR);
  }

  /* ********************************************************** */
  // PROP_CONST_START PROP_CONST
  static boolean prop_constraint_(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "prop_constraint_")) return false;
    if (!nextTokenIs(b, PROP_CONST_START)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, PROP_CONST_START, PROP_CONST);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // PROP_DEFAULT_EQUAL PROP_DEFAULT
  static boolean prop_default_(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "prop_default_")) return false;
    if (!nextTokenIs(b, PROP_DEFAULT_EQUAL)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, PROP_DEFAULT_EQUAL, PROP_DEFAULT);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // PROP_TYPE_START PROP_TYPE [prop_type_mask_] PROP_TYPE_END
  static boolean prop_type_(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "prop_type_")) return false;
    if (!nextTokenIs(b, PROP_TYPE_START)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, PROP_TYPE_START, PROP_TYPE);
    r = r && prop_type__2(b, l + 1);
    r = r && consumeToken(b, PROP_TYPE_END);
    exit_section_(b, m, null, r);
    return r;
  }

  // [prop_type_mask_]
  private static boolean prop_type__2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "prop_type__2")) return false;
    prop_type_mask_(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // PROP_TYPE_MASK_START PROP_TYPE_MASK [prop_type_mask_options_]
  static boolean prop_type_mask_(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "prop_type_mask_")) return false;
    if (!nextTokenIs(b, PROP_TYPE_MASK_START)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, PROP_TYPE_MASK_START, PROP_TYPE_MASK);
    r = r && prop_type_mask__2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // [prop_type_mask_options_]
  private static boolean prop_type_mask__2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "prop_type_mask__2")) return false;
    prop_type_mask_options_(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // PROP_TYPE_MASK_OPT [PROP_TYPE_MASK_OPT_EQUAL PROP_TYPE_MASK_OPT_VALUE]
  static boolean prop_type_mask_option_(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "prop_type_mask_option_")) return false;
    if (!nextTokenIs(b, PROP_TYPE_MASK_OPT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PROP_TYPE_MASK_OPT);
    r = r && prop_type_mask_option__1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // [PROP_TYPE_MASK_OPT_EQUAL PROP_TYPE_MASK_OPT_VALUE]
  private static boolean prop_type_mask_option__1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "prop_type_mask_option__1")) return false;
    parseTokens(b, 0, PROP_TYPE_MASK_OPT_EQUAL, PROP_TYPE_MASK_OPT_VALUE);
    return true;
  }

  /* ********************************************************** */
  // PROP_TYPE_MASK_OPTS_START prop_type_mask_option_ (PROP_TYPE_MASK_OPT_SEP prop_type_mask_option_)* PROP_TYPE_MASK_OPTS_END
  static boolean prop_type_mask_options_(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "prop_type_mask_options_")) return false;
    if (!nextTokenIs(b, PROP_TYPE_MASK_OPTS_START)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PROP_TYPE_MASK_OPTS_START);
    r = r && prop_type_mask_option_(b, l + 1);
    r = r && prop_type_mask_options__2(b, l + 1);
    r = r && consumeToken(b, PROP_TYPE_MASK_OPTS_END);
    exit_section_(b, m, null, r);
    return r;
  }

  // (PROP_TYPE_MASK_OPT_SEP prop_type_mask_option_)*
  private static boolean prop_type_mask_options__2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "prop_type_mask_options__2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!prop_type_mask_options__2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "prop_type_mask_options__2", c)) break;
    }
    return true;
  }

  // PROP_TYPE_MASK_OPT_SEP prop_type_mask_option_
  private static boolean prop_type_mask_options__2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "prop_type_mask_options__2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PROP_TYPE_MASK_OPT_SEP);
    r = r && prop_type_mask_option_(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // PROP_START PROP_NAME [prop_type_] [prop_default_] prop_attr_* [prop_constraint_]
  public static boolean property(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "property")) return false;
    if (!nextTokenIs(b, PROP_START)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, PROP_START, PROP_NAME);
    r = r && property_2(b, l + 1);
    r = r && property_3(b, l + 1);
    r = r && property_4(b, l + 1);
    r = r && property_5(b, l + 1);
    exit_section_(b, m, PROPERTY, r);
    return r;
  }

  // [prop_type_]
  private static boolean property_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "property_2")) return false;
    prop_type_(b, l + 1);
    return true;
  }

  // [prop_default_]
  private static boolean property_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "property_3")) return false;
    prop_default_(b, l + 1);
    return true;
  }

  // prop_attr_*
  private static boolean property_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "property_4")) return false;
    while (true) {
      int c = current_position_(b);
      if (!prop_attr_(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "property_4", c)) break;
    }
    return true;
  }

  // [prop_constraint_]
  private static boolean property_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "property_5")) return false;
    prop_constraint_(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // CRLF+ (property|subnode)
  static boolean subitem_(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "subitem_")) return false;
    if (!nextTokenIs(b, CRLF)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = subitem__0(b, l + 1);
    r = r && subitem__1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // CRLF+
  private static boolean subitem__0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "subitem__0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, CRLF);
    while (r) {
      int c = current_position_(b);
      if (!consumeToken(b, CRLF)) break;
      if (!empty_element_parsed_guard_(b, "subitem__0", c)) break;
    }
    exit_section_(b, m, null, r);
    return r;
  }

  // property|subnode
  private static boolean subitem__1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "subitem__1")) return false;
    boolean r;
    r = property(b, l + 1);
    if (!r) r = subnode(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // SUB_START SUB_NAME [subnode_types_] [subnode_default_] subnode_attr_*
  public static boolean subnode(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "subnode")) return false;
    if (!nextTokenIs(b, SUB_START)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, SUB_START, SUB_NAME);
    r = r && subnode_2(b, l + 1);
    r = r && subnode_3(b, l + 1);
    r = r && subnode_4(b, l + 1);
    exit_section_(b, m, SUBNODE, r);
    return r;
  }

  // [subnode_types_]
  private static boolean subnode_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "subnode_2")) return false;
    subnode_types_(b, l + 1);
    return true;
  }

  // [subnode_default_]
  private static boolean subnode_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "subnode_3")) return false;
    subnode_default_(b, l + 1);
    return true;
  }

  // subnode_attr_*
  private static boolean subnode_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "subnode_4")) return false;
    while (true) {
      int c = current_position_(b);
      if (!subnode_attr_(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "subnode_4", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // SUB_ATTR
  static boolean subnode_attr_(PsiBuilder b, int l) {
    return consumeToken(b, SUB_ATTR);
  }

  /* ********************************************************** */
  // SUB_DEFAULT_EQUAL SUB_DEFAULT
  static boolean subnode_default_(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "subnode_default_")) return false;
    if (!nextTokenIs(b, SUB_DEFAULT_EQUAL)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, SUB_DEFAULT_EQUAL, SUB_DEFAULT);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // SUB_TYPE
  static boolean subnode_type_(PsiBuilder b, int l) {
    return consumeToken(b, SUB_TYPE);
  }

  /* ********************************************************** */
  // SUB_TYPES_START subnode_type_ (SUB_TYPE_SEP subnode_type_)* SUB_TYPES_END
  static boolean subnode_types_(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "subnode_types_")) return false;
    if (!nextTokenIs(b, SUB_TYPES_START)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, SUB_TYPES_START);
    r = r && subnode_type_(b, l + 1);
    r = r && subnode_types__2(b, l + 1);
    r = r && consumeToken(b, SUB_TYPES_END);
    exit_section_(b, m, null, r);
    return r;
  }

  // (SUB_TYPE_SEP subnode_type_)*
  private static boolean subnode_types__2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "subnode_types__2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!subnode_types__2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "subnode_types__2", c)) break;
    }
    return true;
  }

  // SUB_TYPE_SEP subnode_type_
  private static boolean subnode_types__2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "subnode_types__2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, SUB_TYPE_SEP);
    r = r && subnode_type_(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // ST_NAME
  static boolean supertype_(PsiBuilder b, int l) {
    return consumeToken(b, ST_NAME);
  }

  /* ********************************************************** */
  // ST_START supertype_ (ST_SEP supertype_)*
  static boolean supertypes_(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "supertypes_")) return false;
    if (!nextTokenIs(b, ST_START)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ST_START);
    r = r && supertype_(b, l + 1);
    r = r && supertypes__2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (ST_SEP supertype_)*
  private static boolean supertypes__2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "supertypes__2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!supertypes__2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "supertypes__2", c)) break;
    }
    return true;
  }

  // ST_SEP supertype_
  private static boolean supertypes__2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "supertypes__2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ST_SEP);
    r = r && supertype_(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

}

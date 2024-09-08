// This is a generated file. Not intended for manual editing.
package fr.tolc.jahia.language.cnd.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import fr.tolc.jahia.language.cnd.psi.impl.*;

public interface CndTypes {

  IElementType NAMESPACE = new CndElementType("NAMESPACE");
  IElementType NODETYPE = new CndElementType("NODETYPE");
  IElementType PROPERTY = new CndElementType("PROPERTY");
  IElementType SUBNODE = new CndElementType("SUBNODE");

  IElementType COMMENT = new CndTokenType("COMMENT");
  IElementType CRLF = new CndTokenType("CRLF");
  IElementType NS_END = new CndTokenType("NS_END");
  IElementType NS_EQUAL = new CndTokenType("NS_EQUAL");
  IElementType NS_NAME = new CndTokenType("NS_NAME");
  IElementType NS_START = new CndTokenType("NS_START");
  IElementType NS_URI = new CndTokenType("NS_URI");
  IElementType NS_URI_QUOTE = new CndTokenType("NS_URI_QUOTE");
  IElementType NT_END = new CndTokenType("NT_END");
  IElementType NT_NAME = new CndTokenType("NT_NAME");
  IElementType NT_START = new CndTokenType("NT_START");
  IElementType OPT = new CndTokenType("OPT");
  IElementType OPT_EQUAL = new CndTokenType("OPT_EQUAL");
  IElementType OPT_VALUE = new CndTokenType("OPT_VALUE");
  IElementType OPT_VALUE_SEP = new CndTokenType("OPT_VALUE_SEP");
  IElementType PROP_ATTR = new CndTokenType("PROP_ATTR");
  IElementType PROP_CONST = new CndTokenType("PROP_CONST");
  IElementType PROP_CONST_START = new CndTokenType("PROP_CONST_START");
  IElementType PROP_DEFAULT = new CndTokenType("PROP_DEFAULT");
  IElementType PROP_DEFAULT_EQUAL = new CndTokenType("PROP_DEFAULT_EQUAL");
  IElementType PROP_NAME = new CndTokenType("PROP_NAME");
  IElementType PROP_START = new CndTokenType("PROP_START");
  IElementType PROP_TYPE = new CndTokenType("PROP_TYPE");
  IElementType PROP_TYPE_END = new CndTokenType("PROP_TYPE_END");
  IElementType PROP_TYPE_MASK = new CndTokenType("PROP_TYPE_MASK");
  IElementType PROP_TYPE_MASK_OPT = new CndTokenType("PROP_TYPE_MASK_OPT");
  IElementType PROP_TYPE_MASK_OPTS_END = new CndTokenType("PROP_TYPE_MASK_OPTS_END");
  IElementType PROP_TYPE_MASK_OPTS_START = new CndTokenType("PROP_TYPE_MASK_OPTS_START");
  IElementType PROP_TYPE_MASK_OPT_EQUAL = new CndTokenType("PROP_TYPE_MASK_OPT_EQUAL");
  IElementType PROP_TYPE_MASK_OPT_SEP = new CndTokenType("PROP_TYPE_MASK_OPT_SEP");
  IElementType PROP_TYPE_MASK_OPT_VALUE = new CndTokenType("PROP_TYPE_MASK_OPT_VALUE");
  IElementType PROP_TYPE_MASK_START = new CndTokenType("PROP_TYPE_MASK_START");
  IElementType PROP_TYPE_START = new CndTokenType("PROP_TYPE_START");
  IElementType ST_NAME = new CndTokenType("ST_NAME");
  IElementType ST_SEP = new CndTokenType("ST_SEP");
  IElementType ST_START = new CndTokenType("ST_START");
  IElementType SUB_ATTR = new CndTokenType("SUB_ATTR");
  IElementType SUB_DEFAULT = new CndTokenType("SUB_DEFAULT");
  IElementType SUB_DEFAULT_EQUAL = new CndTokenType("SUB_DEFAULT_EQUAL");
  IElementType SUB_NAME = new CndTokenType("SUB_NAME");
  IElementType SUB_START = new CndTokenType("SUB_START");
  IElementType SUB_TYPE = new CndTokenType("SUB_TYPE");
  IElementType SUB_TYPES_END = new CndTokenType("SUB_TYPES_END");
  IElementType SUB_TYPES_START = new CndTokenType("SUB_TYPES_START");
  IElementType SUB_TYPE_SEP = new CndTokenType("SUB_TYPE_SEP");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
      if (type == NAMESPACE) {
        return new CndNamespaceImpl(node);
      }
      else if (type == NODETYPE) {
        return new CndNodetypeImpl(node);
      }
      else if (type == PROPERTY) {
        return new CndPropertyImpl(node);
      }
      else if (type == SUBNODE) {
        return new CndSubnodeImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}

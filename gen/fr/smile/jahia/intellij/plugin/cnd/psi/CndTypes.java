// This is a generated file. Not intended for manual editing.
package fr.smile.jahia.intellij.plugin.cnd.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import fr.smile.jahia.intellij.plugin.cnd.psi.impl.*;

public interface CndTypes {

  IElementType EXTEND = new CndElementType("EXTEND");
  IElementType INHERITANCE = new CndElementType("INHERITANCE");
  IElementType INHERITANCES = new CndElementType("INHERITANCES");
  IElementType NAMESPACE = new CndElementType("NAMESPACE");
  IElementType NODE_TYPE = new CndElementType("NODE_TYPE");
  IElementType PROPERTIES = new CndElementType("PROPERTIES");
  IElementType PROPERTY_MINUS = new CndElementType("PROPERTY_MINUS");
  IElementType PROPERTY_PLUS = new CndElementType("PROPERTY_PLUS");

  IElementType COMMENT = new CndTokenType("COMMENT");
  IElementType CRLF = new CndTokenType("CRLF");
  IElementType EXTEND_ITEM_START = new CndTokenType("EXTEND_ITEM_START");
  IElementType EXTEND_ITEM_TYPE = new CndTokenType("EXTEND_ITEM_TYPE");
  IElementType EXTEND_OPENING = new CndTokenType("EXTEND_OPENING");
  IElementType NAMESPACE_CLOSING = new CndTokenType("NAMESPACE_CLOSING");
  IElementType NAMESPACE_EQUAL = new CndTokenType("NAMESPACE_EQUAL");
  IElementType NAMESPACE_NAME = new CndTokenType("NAMESPACE_NAME");
  IElementType NAMESPACE_OPENING = new CndTokenType("NAMESPACE_OPENING");
  IElementType NAMESPACE_URI = new CndTokenType("NAMESPACE_URI");
  IElementType NODE_TYPE_ABSTRACT = new CndTokenType("NODE_TYPE_ABSTRACT");
  IElementType NODE_TYPE_DECLARATION_CLOSING = new CndTokenType("NODE_TYPE_DECLARATION_CLOSING");
  IElementType NODE_TYPE_DECLARATION_COLON = new CndTokenType("NODE_TYPE_DECLARATION_COLON");
  IElementType NODE_TYPE_DECLARATION_OPENING = new CndTokenType("NODE_TYPE_DECLARATION_OPENING");
  IElementType NODE_TYPE_INHERITANCE_COLON = new CndTokenType("NODE_TYPE_INHERITANCE_COLON");
  IElementType NODE_TYPE_INHERITANCE_NAMESPACE = new CndTokenType("NODE_TYPE_INHERITANCE_NAMESPACE");
  IElementType NODE_TYPE_INHERITANCE_OPENING = new CndTokenType("NODE_TYPE_INHERITANCE_OPENING");
  IElementType NODE_TYPE_INHERITANCE_TYPE_COMMA = new CndTokenType("NODE_TYPE_INHERITANCE_TYPE_COMMA");
  IElementType NODE_TYPE_INHERITANCE_TYPE_NAME = new CndTokenType("NODE_TYPE_INHERITANCE_TYPE_NAME");
  IElementType NODE_TYPE_MIXIN = new CndTokenType("NODE_TYPE_MIXIN");
  IElementType NODE_TYPE_NAME = new CndTokenType("NODE_TYPE_NAME");
  IElementType NODE_TYPE_NAMESPACE = new CndTokenType("NODE_TYPE_NAMESPACE");
  IElementType NODE_TYPE_ORDERABLE = new CndTokenType("NODE_TYPE_ORDERABLE");
  IElementType PROPERTY_ATTRIBUTES = new CndTokenType("PROPERTY_ATTRIBUTES");
  IElementType PROPERTY_CONSTRAINT = new CndTokenType("PROPERTY_CONSTRAINT");
  IElementType PROPERTY_CONSTRAINT_OPENING = new CndTokenType("PROPERTY_CONSTRAINT_OPENING");
  IElementType PROPERTY_DEFAULT_OPENING = new CndTokenType("PROPERTY_DEFAULT_OPENING");
  IElementType PROPERTY_DEFAULT_VALUE = new CndTokenType("PROPERTY_DEFAULT_VALUE");
  IElementType PROPERTY_MINUS_OPENING = new CndTokenType("PROPERTY_MINUS_OPENING");
  IElementType PROPERTY_NAME = new CndTokenType("PROPERTY_NAME");
  IElementType PROPERTY_PLUS_OPENING = new CndTokenType("PROPERTY_PLUS_OPENING");
  IElementType PROPERTY_TYPE = new CndTokenType("PROPERTY_TYPE");
  IElementType PROPERTY_TYPE_CLOSING = new CndTokenType("PROPERTY_TYPE_CLOSING");
  IElementType PROPERTY_TYPE_OPENING = new CndTokenType("PROPERTY_TYPE_OPENING");
  IElementType WHITE_SPACE = new CndTokenType("WHITE_SPACE");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
       if (type == EXTEND) {
        return new CndExtendImpl(node);
      }
      else if (type == INHERITANCE) {
        return new CndInheritanceImpl(node);
      }
      else if (type == INHERITANCES) {
        return new CndInheritancesImpl(node);
      }
      else if (type == NAMESPACE) {
        return new CndNamespaceImpl(node);
      }
      else if (type == NODE_TYPE) {
        return new CndNodeTypeImpl(node);
      }
      else if (type == PROPERTIES) {
        return new CndPropertiesImpl(node);
      }
      else if (type == PROPERTY_MINUS) {
        return new CndPropertyMinusImpl(node);
      }
      else if (type == PROPERTY_PLUS) {
        return new CndPropertyPlusImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}

// This is a generated file. Not intended for manual editing.
package fr.tolc.jahia.intellij.plugin.cnd.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import fr.tolc.jahia.intellij.plugin.cnd.psi.impl.*;

public interface CndTypes {

  IElementType EXTENSION = new CndElementType("EXTENSION");
  IElementType EXTENSIONS = new CndElementType("EXTENSIONS");
  IElementType ITEM_TYPE = new CndElementType("ITEM_TYPE");
  IElementType NAMESPACE = new CndElementType("NAMESPACE");
  IElementType NAMESPACE_IDENTIFIER = new CndElementType("NAMESPACE_IDENTIFIER");
  IElementType NODE_TYPE = new CndElementType("NODE_TYPE");
  IElementType NODE_TYPE_IDENTIFIER = new CndElementType("NODE_TYPE_IDENTIFIER");
  IElementType OPTIONS = new CndElementType("OPTIONS");
  IElementType PROPERTY = new CndElementType("PROPERTY");
  IElementType PROPERTY_ATTRIBUTES = new CndElementType("PROPERTY_ATTRIBUTES");
  IElementType PROPERTY_CONSTRAINT = new CndElementType("PROPERTY_CONSTRAINT");
  IElementType PROPERTY_DEFAULT = new CndElementType("PROPERTY_DEFAULT");
  IElementType PROPERTY_IDENTIFIER = new CndElementType("PROPERTY_IDENTIFIER");
  IElementType SUB_NODE = new CndElementType("SUB_NODE");
  IElementType SUB_NODE_ATTRIBUTES = new CndElementType("SUB_NODE_ATTRIBUTES");
  IElementType SUB_NODE_DEFAULT_TYPE = new CndElementType("SUB_NODE_DEFAULT_TYPE");
  IElementType SUB_NODE_TYPE = new CndElementType("SUB_NODE_TYPE");
  IElementType SUPER_TYPE = new CndElementType("SUPER_TYPE");
  IElementType SUPER_TYPES = new CndElementType("SUPER_TYPES");

  IElementType COLON = new CndTokenType("COLON");
  IElementType COMMA = new CndTokenType("COMMA");
  IElementType COMMENT = new CndTokenType("COMMENT");
  IElementType CRLF = new CndTokenType("CRLF");
  IElementType EQUAL = new CndTokenType("EQUAL");
  IElementType EXTENDS = new CndTokenType("EXTENDS");
  IElementType ITEMTYPE = new CndTokenType("ITEMTYPE");
  IElementType ITEMTYPE_TYPE = new CndTokenType("ITEMTYPE_TYPE");
  IElementType LEFT_ANGLE_BRACKET = new CndTokenType("LEFT_ANGLE_BRACKET");
  IElementType LEFT_BRACKET = new CndTokenType("LEFT_BRACKET");
  IElementType LEFT_ONLY_ANGLE_BRACKET = new CndTokenType("LEFT_ONLY_ANGLE_BRACKET");
  IElementType LEFT_PARENTHESIS = new CndTokenType("LEFT_PARENTHESIS");
  IElementType MINUS = new CndTokenType("MINUS");
  IElementType NAMESPACE_NAME = new CndTokenType("NAMESPACE_NAME");
  IElementType NAMESPACE_URI = new CndTokenType("NAMESPACE_URI");
  IElementType NODE_ATTRIBUTE = new CndTokenType("NODE_ATTRIBUTE");
  IElementType NODE_NAME = new CndTokenType("NODE_NAME");
  IElementType NODE_TYPE_NAME = new CndTokenType("NODE_TYPE_NAME");
  IElementType OPTION = new CndTokenType("OPTION");
  IElementType PLUS = new CndTokenType("PLUS");
  IElementType PROPERTY_ATTRIBUTE = new CndTokenType("PROPERTY_ATTRIBUTE");
  IElementType PROPERTY_CONSTRAINT_VALUE = new CndTokenType("PROPERTY_CONSTRAINT_VALUE");
  IElementType PROPERTY_DEFAULT_VALUE = new CndTokenType("PROPERTY_DEFAULT_VALUE");
  IElementType PROPERTY_MASK = new CndTokenType("PROPERTY_MASK");
  IElementType PROPERTY_MASK_OPTION = new CndTokenType("PROPERTY_MASK_OPTION");
  IElementType PROPERTY_MASK_OPTION_VALUE = new CndTokenType("PROPERTY_MASK_OPTION_VALUE");
  IElementType PROPERTY_NAME = new CndTokenType("PROPERTY_NAME");
  IElementType PROPERTY_TYPE = new CndTokenType("PROPERTY_TYPE");
  IElementType RIGHT_ANGLE_BRACKET = new CndTokenType("RIGHT_ANGLE_BRACKET");
  IElementType RIGHT_BRACKET = new CndTokenType("RIGHT_BRACKET");
  IElementType RIGHT_ONLY_ANGLE_BRACKET = new CndTokenType("RIGHT_ONLY_ANGLE_BRACKET");
  IElementType RIGHT_PARENTHESIS = new CndTokenType("RIGHT_PARENTHESIS");
  IElementType SINGLE_QUOTE = new CndTokenType("SINGLE_QUOTE");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
       if (type == EXTENSION) {
        return new CndExtensionImpl(node);
      }
      else if (type == EXTENSIONS) {
        return new CndExtensionsImpl(node);
      }
      else if (type == ITEM_TYPE) {
        return new CndItemTypeImpl(node);
      }
      else if (type == NAMESPACE) {
        return new CndNamespaceImpl(node);
      }
      else if (type == NAMESPACE_IDENTIFIER) {
        return new CndNamespaceIdentifierImpl(node);
      }
      else if (type == NODE_TYPE) {
        return new CndNodeTypeImpl(node);
      }
      else if (type == NODE_TYPE_IDENTIFIER) {
        return new CndNodeTypeIdentifierImpl(node);
      }
      else if (type == OPTIONS) {
        return new CndOptionsImpl(node);
      }
      else if (type == PROPERTY) {
        return new CndPropertyImpl(node);
      }
      else if (type == PROPERTY_ATTRIBUTES) {
        return new CndPropertyAttributesImpl(node);
      }
      else if (type == PROPERTY_CONSTRAINT) {
        return new CndPropertyConstraintImpl(node);
      }
      else if (type == PROPERTY_DEFAULT) {
        return new CndPropertyDefaultImpl(node);
      }
      else if (type == PROPERTY_IDENTIFIER) {
        return new CndPropertyIdentifierImpl(node);
      }
      else if (type == SUB_NODE) {
        return new CndSubNodeImpl(node);
      }
      else if (type == SUB_NODE_ATTRIBUTES) {
        return new CndSubNodeAttributesImpl(node);
      }
      else if (type == SUB_NODE_DEFAULT_TYPE) {
        return new CndSubNodeDefaultTypeImpl(node);
      }
      else if (type == SUB_NODE_TYPE) {
        return new CndSubNodeTypeImpl(node);
      }
      else if (type == SUPER_TYPE) {
        return new CndSuperTypeImpl(node);
      }
      else if (type == SUPER_TYPES) {
        return new CndSuperTypesImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}

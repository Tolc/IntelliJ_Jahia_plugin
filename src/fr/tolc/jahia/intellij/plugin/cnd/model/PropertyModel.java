package fr.tolc.jahia.intellij.plugin.cnd.model;

import java.util.regex.Pattern;

public class PropertyModel {
    private static final String PROPERTY_PART_SIMPLE = "([A-Za-z][A-Za-z0-9]*)";
    private static final String PROPERTY_PART = "([A-Za-z](?:[A-Za-z0-9:.]*[A-Za-z0-9])?)";
    public static final String CURRENT_NODE = "currentNode";
    private static final String PROPERTIES_MAP_PART = "\\.properties";
    private static final String BEFORE_PART = "([^\\s.{]+)";

    private static final String PROPERTY_GET_SIMPLE = BEFORE_PART + PROPERTIES_MAP_PART + "\\." + PROPERTY_PART_SIMPLE;
    private static final String PROPERTY_GET_BRACKETS = BEFORE_PART + PROPERTIES_MAP_PART + "\\[['\"]" + PROPERTY_PART + "['\"]]";

    public static final Pattern propertyGetRegexSimple = Pattern.compile(PROPERTY_GET_SIMPLE);
    public static final Pattern propertyGetRegexBrackets = Pattern.compile(PROPERTY_GET_BRACKETS);
    public static final Pattern propertyGetRegex = Pattern.compile(PROPERTY_GET_SIMPLE + "|" + PROPERTY_GET_BRACKETS);

    private NodeTypeModel nodeType;
    private String propertyName;

    public PropertyModel(NodeTypeModel nodeType, String propertyName) {
        this.nodeType = nodeType;
        this.propertyName = propertyName;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public NodeTypeModel getNodeType() {
        return nodeType;
    }

    public void setNodeType(NodeTypeModel nodeType) {
        this.nodeType = nodeType;
    }
}

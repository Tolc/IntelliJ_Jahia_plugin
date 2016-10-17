package fr.tolc.jahia.intellij.plugin.cnd.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PropertiesFileCndKeyModel {
    
    private static final String nodeTypePart = "[A-Za-z][A-Za-z0-9]*_[A-Za-z][A-Za-z0-9]*";
    private static final String nodeTypeDescPart = "_description";
    private static final String propertyPart = "\\.[A-Za-z][A-Za-z0-9]*(_[A-Za-z][A-Za-z0-9]*)*";
    private static final String propertyTooltipPart = "\\.ui\\.tooltip";
    private static final String choicelistPart = "\\.[A-Za-z0-9_-]+";
    
    private static final Pattern nodeTypeOnly = Pattern.compile("^" + nodeTypePart + "$");
    private static final Pattern nodeTypeDesc = Pattern.compile("^" + nodeTypePart + nodeTypeDescPart + "$");
    private static final Pattern property = Pattern.compile("^" + nodeTypePart + propertyPart + "$");
    private static final Pattern propertyTooltip = Pattern.compile("^" + nodeTypePart + propertyPart + propertyTooltipPart + "$");
    private static final Pattern choicelist = Pattern.compile("^" + nodeTypePart + propertyPart + choicelistPart + "$");
    
    private String sourceString;
    private String namespace;
    private String nodeTypeName;
    private boolean isNodeTypeDescription = false;
    private boolean isProperty = false;
    private String propertyName;
    private boolean isPropertyTooltip = false;
    private boolean isChoicelistElement = false;
    private String choicelistElement;

    public PropertiesFileCndKeyModel(String sourceString) {
        this.sourceString = sourceString;

        Matcher nodeTypeOnlyMatcher = nodeTypeOnly.matcher(sourceString);
        Matcher nodeTypeDescMatcher = nodeTypeDesc.matcher(sourceString);
        Matcher propertyMatcher = property.matcher(sourceString);
        Matcher propertyTooltipMatcher = propertyTooltip.matcher(sourceString);
        Matcher choicelistMatcher = choicelist.matcher(sourceString);
        
        if (nodeTypeOnlyMatcher.matches()) {
            String[] splitValue = sourceString.split("_");
            namespace = splitValue[0];
            nodeTypeName = splitValue[1];
        } else if (propertyMatcher.matches()) {
            isProperty = true;
            
            String[] splitValue = sourceString.split("\\.");
            String nodeType = splitValue[0];
            propertyName = splitValue[1].replace("_", ":");
            
            splitValue = nodeType.split("_");
            namespace = splitValue[0];
            nodeTypeName = splitValue[1];
        } else if (propertyTooltipMatcher.matches()) {
            isPropertyTooltip = true;

            String[] splitValue = sourceString.split("\\.");
            String nodeType = splitValue[0];
            propertyName = splitValue[1].replace("_", ":");

            splitValue = nodeType.split("_");
            namespace = splitValue[0];
            nodeTypeName = splitValue[1];
            
        } else if (choicelistMatcher.matches()) {
            isChoicelistElement = true;

            String[] splitValue = sourceString.split("\\.");
            String nodeType = splitValue[0];
            propertyName = splitValue[1].replace("_", ":");
            choicelistElement = splitValue[2];
            
            splitValue = nodeType.split("_");
            namespace = splitValue[0];
            nodeTypeName = splitValue[1];

        } else if (nodeTypeDescMatcher.matches()) {
            isNodeTypeDescription = true;
            
            String[] splitValue = sourceString.split("_");
            namespace = splitValue[0];
            nodeTypeName = splitValue[1];
        } else {
            throw new IllegalArgumentException("String is not a properties file Cnd key");
        }
    }

    public String getNamespace() {
        return namespace;
    }

    public String getNodeTypeName() {
        return nodeTypeName;
    }

    public String getSourceString() {
        return sourceString;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public boolean isChoicelistElement() {
        return isChoicelistElement;
    }

    public String getChoicelistElement() {
        return choicelistElement;
    }

    public boolean isNodeTypeDescription() {
        return isNodeTypeDescription;
    }

    public boolean isProperty() {
        return isProperty;
    }

    public boolean isPropertyTooltip() {
        return isPropertyTooltip;
    }
}

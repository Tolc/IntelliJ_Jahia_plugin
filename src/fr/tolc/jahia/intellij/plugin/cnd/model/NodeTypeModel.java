package fr.tolc.jahia.intellij.plugin.cnd.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NodeTypeModel {
    private static final String NAMESPACE_PART = "[A-Za-z][A-Za-z0-9]*";
    private static final String NODETYPE_PART = "[A-Za-z][A-Za-z0-9]*";
    private static final Pattern nodeTypeRegex = Pattern.compile("^" + NAMESPACE_PART + ":" + NODETYPE_PART + "$");
    private static final Pattern nodeTypeFolderRegex = Pattern.compile("^" + NAMESPACE_PART + "_" + NODETYPE_PART + "$");

    private String namespace;
    private String nodeTypeName;
    private String sourceString;

    public NodeTypeModel(String sourceString) {
        this(sourceString, false);
    }

    public NodeTypeModel(String sourceString, boolean isFolder) {
        this.sourceString = sourceString;

        String[] split = null;
        if (isFolder) {
            Matcher matcher = nodeTypeFolderRegex.matcher(sourceString);
            if (matcher.matches()) {
                split = sourceString.split("_");
            }
        } else {
            Matcher matcher = nodeTypeRegex.matcher(sourceString);
            if (matcher.matches()) {
                split = sourceString.split(":");
            }
        }

        if (split != null) {
            this.namespace = split[0];
            this.nodeTypeName = split[1];
        } else {
            throw new IllegalArgumentException("String is not a Cnd nodetype");
        }
    }

    public NodeTypeModel(String sourceString, String namespace, String nodeTypeName) {
        this.sourceString = sourceString;
        this.namespace = namespace;
        this.nodeTypeName = nodeTypeName;
    }

    public NodeTypeModel(String namespace, String nodeTypeName) {
        this.namespace = namespace;
        this.nodeTypeName = nodeTypeName;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getNodeTypeName() {
        return nodeTypeName;
    }

    public void setNodeTypeName(String nodeTypeName) {
        this.nodeTypeName = nodeTypeName;
    }

    public String getSourceString() {
        return sourceString;
    }

    public void setSourceString(String sourceString) {
        this.sourceString = sourceString;
    }
}
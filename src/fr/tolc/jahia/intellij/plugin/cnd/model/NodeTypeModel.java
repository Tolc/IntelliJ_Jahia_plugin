package fr.tolc.jahia.intellij.plugin.cnd.model;

public class NodeTypeModel {
    private String namespace;
    private String nodeTypeName;
    private String sourceString;

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

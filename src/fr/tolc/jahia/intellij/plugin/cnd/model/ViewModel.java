package fr.tolc.jahia.intellij.plugin.cnd.model;

import org.apache.commons.lang.StringUtils;

public class ViewModel {
    private NodeTypeModel nodeType;
    private String name;
    private String type;        //html/json/rss/xml/...
    private String language;    //jsp/groovy/...

    public ViewModel(NodeTypeModel nodeType, String name, String type, String language) {
        this.nodeType = nodeType;
        this.name = name;
        this.type = type;
        this.language = language;
    }

    public ViewModel( String namespace, String nodeTypeName, String name, String type, String language) {
        this.nodeType = new NodeTypeModel(namespace, nodeTypeName);
        this.name = name;
        this.type = type;
        this.language = language;
    }

    public NodeTypeModel getNodeType() {
        return nodeType;
    }

    public void setNodeType(NodeTypeModel nodeType) {
        this.nodeType = nodeType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
    
    public boolean isHidden() {
        return this.name.contains("hidden.") || this.name.contains(".hidden");
    }
    
    public boolean isDefault() {
        return StringUtils.isBlank(this.name) || "default.".equals(this.name);
    }
    
    public boolean isSameView(ViewModel viewModel) {
        return this.nodeType.equals(viewModel.nodeType) && this.name.equals(viewModel.name) && this.type.equals(viewModel.type);
    }
}

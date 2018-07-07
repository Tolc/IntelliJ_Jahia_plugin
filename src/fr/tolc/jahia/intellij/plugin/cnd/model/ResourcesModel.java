package fr.tolc.jahia.intellij.plugin.cnd.model;

import fr.tolc.jahia.intellij.plugin.cnd.enums.ResourcesTypeEnum;

public class ResourcesModel {
    private ResourcesTypeEnum type;
    private String resources;

    public ResourcesModel(ResourcesTypeEnum type, String resources) {
        this.type = type;
        this.resources = resources;
    }

    public ResourcesTypeEnum getType() {
        return type;
    }

    public void setType(ResourcesTypeEnum type) {
        this.type = type;
    }

    public String getResources() {
        return resources;
    }

    public void setResources(String resources) {
        this.resources = resources;
    }
}

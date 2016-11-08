package fr.tolc.jahia.intellij.plugin.cnd.enums;

public enum PropertyTypeMaskOptionEnum {
    RESOURCEBUNDLE("resourceBundle"),
    COUNTRY("country"),
    TEMPLATES("templates"),
    TEMPLATESNODE("templatesNode"),
    USERS("users"),
    NODETYPES("nodetypes"),
    SUBNODETYPES("subnodetypes"),
    NODES("nodes"),
    MENUS("menus"),
    SCRIPT("script"),
    FLAG("flag"),
    SORTABLEFIELDNAMES("sortableFieldnames"),
    MODULEIMAGE("moduleImage"),
    LINKERPROPS("linkerProps"),
    WORKFLOW("workflow"),
    WORKFLOWTYPES("workflowTypes"),
    SORT("sort"),
    COMPONENTTYPES("componenttypes"),
    AUTOSELECTPARENT("autoSelectParent"),
    TYPE("type"),
    IMAGE("image"),
    DEPENDENTPROPERTIES("dependentProperties"),
    MIME("mime"),
    RENDERMODES("renderModes"),
    PERMISSIONS("permissions"),
    AUTOCOMPLETE("autocomplete"),
    SEPARATOR("separator"),
    FOLDER("folder"),
    ROOT("root"),
    CKEDITOR_CUSTOMCONFIG("ckeditor.customConfig");
    
    private String value;

    PropertyTypeMaskOptionEnum(String value) {
        this.value = value;
    }

    public static PropertyTypeMaskOptionEnum fromValue(String value) throws IllegalArgumentException {
        for (PropertyTypeMaskOptionEnum option : PropertyTypeMaskOptionEnum.values()) {
            if (option.value.equalsIgnoreCase(value)) {
                return option;
            }
        }
        throw new IllegalArgumentException("Unknown property type mask option [" + value + "]");
    }

    public String toString() {
        return this.value;
    }
}

package fr.tolc.jahia.intellij.plugin.cnd.enums;

public enum ItemTypeEnum {
    DEFAULT("default"),
    OPTIONS("options"),
    LAYOUT("layout"),
    METADATA("metadata"),
    CONTENT("content"),
    CLASSIFICATION("classification"),
    PERMISSIONS("permissions"),
    LIST_ORDERING("listOrdering"),
    CONTRIBUTE_MODE("contributeMode"),
    PROPERTIES_VIEW("propertiesView");

    private String value;

    ItemTypeEnum(String value) {
        this.value = value;
    }

    public static ItemTypeEnum fromValue(String value) throws IllegalArgumentException {
        for (ItemTypeEnum type : ItemTypeEnum.values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown item type [" + value + "]");
    }

    public String toString() {
        return this.value;
    }
}

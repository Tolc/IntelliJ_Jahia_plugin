package fr.tolc.jahia.intellij.plugin.cnd.enums;

public enum ItemTypeEnum {
    CHANNELS("channels"),
    CLASSIFICATION("classification"),
    CONTENT("content"),
    CONTRIBUTE_MODE("contributeMode"),
    DEFAULT("default"),
    LAYOUT("layout"),
    LIST_ORDERING("listOrdering"),
    METADATA("metadata"),
    OPTIONS("options"),
    PERMISSIONS("permissions"),
    PROPERTIES_VIEW("propertiesView");

    private final String value;

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

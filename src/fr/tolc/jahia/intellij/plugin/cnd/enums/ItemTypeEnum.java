package fr.tolc.jahia.intellij.plugin.cnd.enums;

import java.util.Arrays;

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

    public static ItemTypeEnum fromValue(String value) {
        return Arrays.stream(ItemTypeEnum.values())
                .filter(type -> type.value.equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown item type [" + value + "]"));
    }

    @Override
    public String toString() {
        return this.value;
    }
}

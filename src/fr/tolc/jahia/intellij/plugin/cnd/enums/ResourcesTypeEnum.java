package fr.tolc.jahia.intellij.plugin.cnd.enums;

public enum ResourcesTypeEnum {
    CSS("css"),
    JAVASCRIPT("javascript");

    private String value;

    ResourcesTypeEnum(String value) {
        this.value = value;
    }

    public static ResourcesTypeEnum fromValue(String value) throws IllegalArgumentException {
        for (ResourcesTypeEnum type : ResourcesTypeEnum.values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown resources type [" + value + "]");
    }

    public static ResourcesTypeEnum fromAnything(String value) {
        for (ResourcesTypeEnum type : ResourcesTypeEnum.values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        return null;
    }

    public String toString() {
        return this.value;
    }
}

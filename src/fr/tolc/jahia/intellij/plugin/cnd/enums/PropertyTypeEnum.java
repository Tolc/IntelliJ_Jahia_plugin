package fr.tolc.jahia.intellij.plugin.cnd.enums;

public enum PropertyTypeEnum {
    STRING("string"),
    LONG("long"),
    DOUBLE("double"),
    DECIMAL("decimal"),
    PATH("path"),
    URI("uri"),
    BOOLEAN("boolean"),
    DATE("date"),
    BINARY("binary"),
    WEAKREFERENCE("weakreference"),
    NAME("name"),
    REFERENCE("reference"),
    UNDEFINED("undefined");

    private String value;

    PropertyTypeEnum(String value) {
        this.value = value;
    }

    public static PropertyTypeEnum fromValue(String value) {
        try {
            return PropertyTypeEnum.valueOf(value.toLowerCase());
        } catch (IllegalArgumentException e) {
            return UNDEFINED;
        }
    }

    public String toString() {
        return this.value;
    }
}

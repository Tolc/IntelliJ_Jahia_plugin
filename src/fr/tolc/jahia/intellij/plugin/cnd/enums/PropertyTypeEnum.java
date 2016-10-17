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

    public static PropertyTypeEnum fromValue(String value) throws IllegalArgumentException {
        for (PropertyTypeEnum type : PropertyTypeEnum.values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown property type [" + value + "]");
    }

    public String toString() {
        return this.value;
    }
}

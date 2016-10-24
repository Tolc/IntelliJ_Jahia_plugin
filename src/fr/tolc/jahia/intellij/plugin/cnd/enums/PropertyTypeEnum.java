package fr.tolc.jahia.intellij.plugin.cnd.enums;

public enum PropertyTypeEnum {
    STRING("string", "string"),
    LONG("long", "long"),
    DOUBLE("double", "double"),
    DECIMAL("decimal", "decimal"),
    PATH("path", "string"),
    URI("uri", "string"),
    BOOLEAN("boolean", "boolean"),
    DATE("date", "date"),
    BINARY("binary", "binary"),
    WEAKREFERENCE("weakreference", "node"),
    NAME("name", "string"),
    REFERENCE("reference", "node"),
    UNDEFINED("undefined", "");

    private String value;
    private String accessor;

    PropertyTypeEnum(String value, String accessor) {
        this.value = value;
        this.accessor = accessor;
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

    public String getAccessor() {
        return accessor;
    }
}

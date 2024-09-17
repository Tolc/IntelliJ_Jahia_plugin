package fr.tolc.jahia.constants.enums;

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
    WEAK_REFERENCE("weakreference", "node"),
    NAME("name", "string"),
    REFERENCE("reference", "node"),
    UNDEFINED("undefined", ""),
    UNDEFINED_STAR("*", "");

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

    public boolean isUndefined() {
        return UNDEFINED.equals(this) || UNDEFINED_STAR.equals(this);
    }
}

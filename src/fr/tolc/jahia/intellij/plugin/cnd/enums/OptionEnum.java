package fr.tolc.jahia.intellij.plugin.cnd.enums;

public enum OptionEnum {
    MIXIN("mixin"),
    ABSTRACT("abstract"),
    ORDERABLE("orderable"),
    NOQUERY("noquery");

    private String value;

    OptionEnum(String value) {
        this.value = value;
    }

    public static OptionEnum fromValue(String value) throws IllegalArgumentException {
        for (OptionEnum type : OptionEnum.values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown node option [" + value + "]");
    }

    public String toString() {
        return this.value;
    }
}

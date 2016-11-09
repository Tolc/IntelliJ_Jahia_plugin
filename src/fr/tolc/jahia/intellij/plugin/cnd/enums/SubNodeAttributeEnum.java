package fr.tolc.jahia.intellij.plugin.cnd.enums;

public enum SubNodeAttributeEnum {
    MANDATORY("mandatory"),
    AUTOCREATED("autocreated"),
    COPY("copy"),
    VERSION("version"),
    INITIALIZE("initialize"),
    COMPUTE("compute"),
    IGNORE("ignore"),
    ABORT("abort"),
    MULTIPLE("multiple"),
    PROTECTED("protected"),
    SNS("sns"),
    PRIMARY("primary");

    private String value;

    SubNodeAttributeEnum(String value) {
        this.value = value;
    }

    public static SubNodeAttributeEnum fromValue(String value) throws IllegalArgumentException {
        for (SubNodeAttributeEnum type : SubNodeAttributeEnum.values()) {
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

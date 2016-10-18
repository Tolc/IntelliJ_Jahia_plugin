package fr.tolc.jahia.intellij.plugin.cnd.enums;

public enum PropertyTypeMaskEnum {
    TEXT("text"),
    RICHTEXT("richtext"),
    TEXTAREA("textarea"),
    CHOICELIST("choicelist"),
    DATETIMEPICKER("datetimepicker"),
    DATEPICKER("datepicker"),
    PICKER("picker"),
    COLOR("color"),
    CATEGORY("category"),
    CHECKBOX("checkbox"),
    FILEUPLOAD("fileupload"),
    TAG("tag"),
    FILE("file");
    
    private String value;

    PropertyTypeMaskEnum(String value) {
        this.value = value;
    }

    public static PropertyTypeMaskEnum fromValue(String value) throws IllegalArgumentException {
        for (PropertyTypeMaskEnum mask : PropertyTypeMaskEnum.values()) {
            if (mask.value.equalsIgnoreCase(value)) {
                return mask;
            }
        }
        throw new IllegalArgumentException("Unknown property type mask [" + value + "]");
    }

    public String toString() {
        return this.value;
    }
}

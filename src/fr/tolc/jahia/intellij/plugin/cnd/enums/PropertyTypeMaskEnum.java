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
    FILE("file"),
    NONE("none");

    private String value;

    PropertyTypeMaskEnum(String value) {
        this.value = value;
    }

    public static PropertyTypeMaskEnum fromValue(String value) {
        try {
            return PropertyTypeMaskEnum.valueOf(value.toLowerCase());
        } catch (IllegalArgumentException e) {
            return NONE;
        }
    }

    public String toString() {
        return this.value;
    }
}

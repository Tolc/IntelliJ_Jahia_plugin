package fr.tolc.jahia.intellij.plugin.cnd.enums;

import java.util.regex.Pattern;

public enum SubNodeAttributeEnum {
    MANDATORY("mandatory", "madatory", "mandatory|man|m"),
    AUTOCREATED("autocreated", "autocreated", "autocreated|aut|a"),
    COPY("copy"),
    VERSION("version"),
    INITIALIZE("initialize"),
    COMPUTE("compute"),
    IGNORE("ignore"),
    ABORT("abort"),
    MULTIPLE("multiple", "multiple", "multiple|mul|\\*"),
    PROTECTED("protected", "protected",  "protected|pro|p"),
    SNS("sns"),
    PRIMARY("primary", "primary", "primary|pri|!");

    private String value;
    private String[] completions;
    private String validationRegex;

    SubNodeAttributeEnum(String value) {
        this.value = value;
        this.completions = new String[] { value };
        this.validationRegex = value;
    }

    SubNodeAttributeEnum(String value, String completion, String regex) {
        this.value = value;
        this.completions = new String[] { completion };
        this.validationRegex = regex;
    }

    SubNodeAttributeEnum(String value, String[] completions, String regex) {
        this.value = value;
        this.completions = completions;
        this.validationRegex = regex;
    }

    public static SubNodeAttributeEnum fromValue(String value) throws IllegalArgumentException {
        for (SubNodeAttributeEnum attribute : SubNodeAttributeEnum.values()) {
            Pattern pattern = Pattern.compile(attribute.validationRegex, Pattern.CASE_INSENSITIVE);
            if (pattern.matcher(value).matches()) {
                return attribute;
            }
        }
        throw new IllegalArgumentException("Unknown subnode attribute [" + value + "]");
    }

    public String[] getCompletions() {
        return completions;
    }
    
    public String toString() {
        return this.value;
    }
}

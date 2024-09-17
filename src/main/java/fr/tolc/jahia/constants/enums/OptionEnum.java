package fr.tolc.jahia.constants.enums;

import org.jetbrains.annotations.Nullable;

import java.util.regex.Pattern;

/**
 * <a href="https://github.com/Jahia/jahia-configuration/blob/master/osgi-tools/src/main/java/org/jahia/utils/osgi/parsers/cnd/Lexer.java">See Jahia CND lexer for reference</a>
 */
public enum OptionEnum {
    ABSTRACT("abstract", "abstract|abs|a"),
    MIXIN("mixin", "mixin|mix"),
    NOQUERY("noquery", "noquery|nq"),
    ORDERABLE("orderable", "orderable|ord|o"),
    PRIMARYITEM("primaryitem", "primaryitem|!"),
    QUERY("query", "query|q"),
    VALIDATOR("validator", "validator=myValidator", "validator|val|v");
    //TODO: add extends and itemtype, and deal with primaryitem value

    private final String value;
    private final String[] completions;
    private final String validationRegex;

    OptionEnum(String value) {
        this.value = value;
        this.completions = new String[]{value};
        this.validationRegex = value;
    }

    OptionEnum(String value, String regex) {
        this.value = value;
        this.completions = new String[]{value};
        this.validationRegex = regex;
    }

    OptionEnum(String value, String completion, String regex) {
        this.value = value;
        this.completions = new String[]{completion};
        this.validationRegex = regex;
    }

    public static @Nullable OptionEnum fromValue(String value) {
        for (OptionEnum option : OptionEnum.values()) {
            Pattern pattern = Pattern.compile(option.validationRegex, Pattern.CASE_INSENSITIVE);
            if (pattern.matcher(value).matches()) {
                return option;
            }
        }
        return null;
    }

    public String[] getCompletions() {
        return completions;
    }

    public String toString() {
        return this.value;
    }
}

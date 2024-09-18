package fr.tolc.jahia.constants.enums;

import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import fr.tolc.jahia.language.cnd.psi.CndTypes;
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
    QUERY("query", "query|q"),
    VALIDATOR("validator", "validator=myValidator", "validator|val|v"),

    PRIMARYITEM("primaryitem", "primaryitem|!", "primaryitem ", TokenType.WHITE_SPACE), //TODO: reference property from value (which is identified as another option name)

    EXTENDS("extends", "extends = ", "extends", CndTypes.OPT_VALUE_SEP),
    ITEMTYPE("itemtype", "itemtype = ", "itemtype", CndTypes.OPT_VALUE_SEP);

    private final String value;
    private final String[] completions;
    private final String validationRegex;
    private final IElementType separatorType;

    OptionEnum(String value) {
        this.value = value;
        this.completions = new String[]{value};
        this.validationRegex = value;
        this.separatorType = null;
    }

    OptionEnum(String value, String regex) {
        this.value = value;
        this.completions = new String[]{value};
        this.validationRegex = regex;
        this.separatorType = null;
    }

    OptionEnum(String value, String completion, String regex) {
        this.value = value;
        this.completions = new String[]{completion};
        this.validationRegex = regex;
        this.separatorType = null;
    }

    OptionEnum(String value, String completion, String regex, IElementType separatorType) {
        this.value = value;
        this.completions = new String[]{completion};
        this.validationRegex = regex;
        this.separatorType = separatorType;
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

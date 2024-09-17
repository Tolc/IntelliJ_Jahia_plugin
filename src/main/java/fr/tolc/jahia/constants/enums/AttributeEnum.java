package fr.tolc.jahia.constants.enums;

import fr.tolc.jahia.constants.CndConstants;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * <a href="https://github.com/Jahia/jahia-configuration/blob/master/osgi-tools/src/main/java/org/jahia/utils/osgi/parsers/cnd/Lexer.java">See Jahia CND lexer for reference</a>
 */
public enum AttributeEnum {
    ABORT("abort", true),
    ANALYZER("analyzer", new String[]{"analyzer='keyword'", "analyzer='simple'"}, "analyzer\\s*=\\s*('[a-zA-Z]+'|[a-zA-Z]+)"),
    AUTOCREATED("autocreated", "autocreated", "autocreated|aut|a", true),
    BOOST("boost", new String[]{"boost=2.0", "scoreboost=2.0"}, "(scoreboost|boost|b)\\s*=\\s*\\d+(\\.\\d+)?"),
    COMPUTE("compute", true),
    COPY("copy", true),
    FACETABLE("facetable"),
    FULLTEXTSEARCHABLE("fulltextsearchable", new String[]{"fulltextsearchable=no", "fulltextsearchable=yes"},
            "(fulltextsearchable|fts)\\s*=\\s*(no|n|yes|y)", "(fulltextsearchable|fts)\\s*=\\s*" + CndConstants.VALUE_REPLACE),
    HIDDEN("hidden"),
    HIERARCHICAL("hierarchical"),
    IGNORE("ignore", true),
    INDEXED("indexed", new String[]{"indexed=yes", "indexed=no", "indexed=tokenized", "indexed=untokenized"},
            "(indexed|ind|x)\\s*=\\s*('(yes|y|no|n|tokenized|tok|t|untokenized|untok|u)'|(yes|y|no|n|tokenized|tok|t|untokenized|untok|u))", "(indexed|ind|x)\\s*=\\s*('" + CndConstants.VALUE_REPLACE + "'|" + CndConstants.VALUE_REPLACE + ")"),
    INITIALIZE("initialize", true),
    INTERNATIONALIZED("internationalized", new String[]{"i18n", "internationalized"}, "internationalized|i18n|i15d|i"),
    ITEMTYPE("itemtype", new String[]{"itemtype=content", "itemtype=metadata", "itemtype=layout", "itemtype=options", "itemtype=codeEditor"},
            "(itemtype|type)\\s*=\\s*(content|metadata|layout|options|codeEditor)", "(itemtype|type)\\s*=\\s*" + CndConstants.VALUE_REPLACE),
    MANDATORY("mandatory", "mandatory", "mandatory|man|m", true),
    MULTIPLE("multiple", "multiple", "multiple|mul|\\*|sns", true),
    NOFULLTEXT("nofulltext", "nofulltext|nof"),
    NOQUERYORDER("noqueryorder", "noqueryorder|nqord"),
    ONCONFLICT("onconflict", new String[]{"onconflict=sum", "onconflict=latest", "onconflict=oldest", "onconflict=min", "onconflict=max", "onconflict=ignore"},
            "onconflict\\s*=\\s*(sum|latest|oldest|min|max|ignore)"),
    PRIMARY("primary", "primary", "primary|pri|!", true),
    PROTECTED("protected", "protected", "protected|pro|p", true),
    QUERYOPS("queryops", "queryops '<,<=,<>,=,>,>=,like'", "(queryops|qop)\\s+'((<|<=|<>|=|>|>=|like)(,)?)+'"),
    SORTABLE("sortable"),
    VERSION("version", true);

    private final String value;
    private final String[] completions;
    private final String validationRegex;
    private String valueRegex;
    private boolean subNodeUsable = false;

    AttributeEnum(String value) {
        this.value = value;
        this.completions = new String[]{value};
        this.validationRegex = value;
    }

    AttributeEnum(String value, boolean subNodeUsable) {
        this(value);
        this.subNodeUsable = subNodeUsable;
    }

    AttributeEnum(String value, String validationRegex) {
        this.value = value;
        this.completions = new String[]{value};
        this.validationRegex = validationRegex;
    }

    AttributeEnum(String value, String completion, String validationRegex) {
        this.value = value;
        this.completions = new String[]{completion};
        this.validationRegex = validationRegex;
    }

    AttributeEnum(String value, String completion, String validationRegex, String valueRegex) {
        this.value = value;
        this.completions = new String[]{completion};
        this.validationRegex = validationRegex;
        this.valueRegex = valueRegex;
    }

    AttributeEnum(String value, String completion, String validationRegex, boolean subNodeUsable) {
        this(value, completion, validationRegex);
        this.subNodeUsable = subNodeUsable;
    }

    AttributeEnum(String value, String[] completions, String validationRegex) {
        this.value = value;
        this.completions = completions;
        this.validationRegex = validationRegex;
    }

    AttributeEnum(String value, String[] completions, String validationRegex, String valueRegex) {
        this.value = value;
        this.completions = completions;
        this.validationRegex = validationRegex;
        this.valueRegex = valueRegex;
    }

    AttributeEnum(String value, String[] completions, String validationRegex, boolean subNodeUsable) {
        this(value, completions, validationRegex);
        this.subNodeUsable = subNodeUsable;
    }

    public static @Nullable AttributeEnum fromValue(String value) throws IllegalArgumentException {
        for (AttributeEnum attribute : AttributeEnum.values()) {
            Pattern pattern = Pattern.compile(attribute.validationRegex, Pattern.CASE_INSENSITIVE);
            if (pattern.matcher(value).matches()) {
                return attribute;
            }
        }
        return null;
    }

    public static @Nullable AttributeEnum fromValueForSubNode(String value) throws IllegalArgumentException {
        for (AttributeEnum attribute : AttributeEnum.subNodeAttributesValues()) {
            Pattern pattern = Pattern.compile(attribute.validationRegex, Pattern.CASE_INSENSITIVE);
            if (pattern.matcher(value).matches()) {
                return attribute;
            }
        }
        return null;
    }

    @NotNull
    public static List<AttributeEnum> subNodeAttributesValues() {
        List<AttributeEnum> res = new ArrayList<>();
        for (AttributeEnum attribute : AttributeEnum.values()) {
            if (attribute.isSubNodeUsable()) {
                res.add(attribute);
            }
        }
        return res;
    }

    public static boolean textContainsAttribute(String source, AttributeEnum attribute) {
        Pattern pattern = Pattern.compile(attribute.validationRegex, Pattern.CASE_INSENSITIVE);
        String[] attrs = source.split(" ");
        for (String attr : attrs) {
            if (pattern.matcher(attr).matches()) {
                return true;
            }
        }
        return false;
    }

    public static boolean textContainsAttributeValue(String source, AttributeEnum attribute, String value) {
        String searchRegex = (attribute.valueRegex != null) ? (attribute.valueRegex.replaceAll(CndConstants.VALUE_REPLACE, value)) : (attribute.validationRegex + "=" + value);
        Pattern pattern = Pattern.compile(searchRegex, Pattern.CASE_INSENSITIVE);
        String[] attrs = source.split(" ");
        for (String attr : attrs) {
            if (pattern.matcher(attr).matches()) {
                return true;
            }
        }
        return false;
    }

    public String[] getCompletions() {
        return completions;
    }

    public boolean isSubNodeUsable() {
        return subNodeUsable;
    }

    public String toString() {
        return this.value;
    }
}

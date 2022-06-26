package fr.tolc.jahia.intellij.plugin.cnd.enums;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.jetbrains.annotations.NotNull;

/**
 * <a href="https://github.com/Jahia/jahia-configuration/blob/master/osgi-tools/src/main/java/org/jahia/utils/osgi/parsers/cnd/Lexer.java">See Jahia CND lexer for reference</a>
 */
public enum AttributeEnum {
    ABORT("abort", true),
    ANALYZER("analyzer", new String[] { "analyzer='keyword'", "analyzer='simple'" }, "analyzer\\s*=\\s*('[a-zA-Z]+'|[a-zA-Z]+)"),
    AUTOCREATED("autocreated", "autocreated", "autocreated|aut|a", true),
    BOOST("boost", new String[] { "boost=2.0", "scoreboost=2.0" }, "(scoreboost|boost|b)\\s*=\\s*\\d+(\\.\\d+)?"),
    COMPUTE("compute", true),
    COPY("copy", true),
    FACETABLE("facetable"),
    FULLTEXTSEARCHABLE("fulltextsearchable", new String[] { "fulltextsearchable=no", "fulltextsearchable=yes" }, "(fulltextsearchable|fts)\\s*=\\s*(no|n|yes|y)"),
    HIDDEN("hidden"),
    HIERARCHICAL("hierarchical"),
    IGNORE("ignore", true),
    INDEXED("indexed", new String[] { "indexed=yes", "indexed=no", "indexed=tokenized", "indexed=untokenized" }, "(indexed|ind|x)\\s*=\\s*'?(yes|y|no|n|tokenized|tok|t|untokenized|untok|u)'?"),
    INITIALIZE("initialize", true),
    INTERNATIONALIZED("internationalized", new String[] { "i18n", "internationalized" }, "internationalized|i18n|i15d|i"),
    ITEMTYPE("itemtype", new String[] { "itemtype=content", "itemtype=metadata", "itemtype=layout", "itemtype=options", "itemtype=codeEditor" }, "(itemtype|type)\\s*=\\s*(content|metadata|layout|options|codeEditor)"),
    MANDATORY("mandatory", "mandatory", "mandatory|man|m", true),
    MULTIPLE("multiple", "multiple", "multiple|mul|\\*|sns", true),
    NOFULLTEXT("nofulltext","nofulltext|nof"),
    NOQUERYORDER("noqueryorder", "noqueryorder|nqord"),
    ONCONFLICT("onconflict", new String[] { "onconflict=sum", "onconflict=latest", "onconflict=oldest", "onconflict=min", "onconflict=max", "onconflict=ignore" }, "onconflict\\s*=\\s*(sum|latest|oldest|min|max|ignore)"),
    PRIMARY("primary", "primary", "primary|pri|!", true),
    PROTECTED("protected", "protected",  "protected|pro|p", true),
    QUERYOPS("queryops", "queryops '<,<=,<>,=,>,>=,like'", "(queryops|qop)\\s+'((<|<=|<>|=|>|>=|like)(,)?)+'"),
    SORTABLE("sortable"),
    VERSION("version", true);
    
    private final String value;
    private final String[] completions;
    private final String validationRegex;
    private boolean subNodeUsable = false;

    AttributeEnum(String value) {
        this.value = value;
        this.completions = new String[] { value };
        this.validationRegex = value;
    }
    
    AttributeEnum(String value, boolean subNodeUsable) {
        this(value);
        this.subNodeUsable = subNodeUsable;
    }

    AttributeEnum(String value, String regex) {
        this.value = value;
        this.completions = new String[] { value };
        this.validationRegex = regex;
    }

    AttributeEnum(String value, String completion, String regex) {
        this.value = value;
        this.completions = new String[] { completion };
        this.validationRegex = regex;
    }

    AttributeEnum(String value, String completion, String regex, boolean subNodeUsable) {
        this(value, completion, regex);
        this.subNodeUsable = subNodeUsable;
    }
    
    AttributeEnum(String value, String[] completions, String regex) {
        this.value = value;
        this.completions = completions;
        this.validationRegex = regex;
    }

    AttributeEnum(String value, String[] completions, String regex, boolean subNodeUsable) {
        this(value, completions, regex);
        this.subNodeUsable = subNodeUsable;
    }

    public static AttributeEnum fromValue(String value) throws IllegalArgumentException {
        for (AttributeEnum attribute : AttributeEnum.values()) {
            Pattern pattern = Pattern.compile(attribute.validationRegex, Pattern.CASE_INSENSITIVE);
            if (pattern.matcher(value).matches()) {
                return attribute;
            }
        }
        throw new IllegalArgumentException("Unknown attribute [" + value + "]");
    }

    public static AttributeEnum fromValueForSubNode(String value) throws IllegalArgumentException {
        for (AttributeEnum attribute : AttributeEnum.subNodeAttributesValues()) {
            Pattern pattern = Pattern.compile(attribute.validationRegex, Pattern.CASE_INSENSITIVE);
            if (pattern.matcher(value).matches()) {
                return attribute;
            }
        }
        throw new IllegalArgumentException("Unknown attribute [" + value + "]");
    }

    public String[] getCompletions() {
        return completions;
    }

    public boolean isSubNodeUsable() {
        return subNodeUsable;
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
    
    public String toString() {
        return this.value;
    }
    
    public static boolean textContainsAttribute(String source, AttributeEnum attribute) {
        Pattern pattern = Pattern.compile(attribute.validationRegex, Pattern.CASE_INSENSITIVE);
        return pattern.matcher(source).find();
    }
}

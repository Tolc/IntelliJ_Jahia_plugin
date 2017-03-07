package fr.tolc.jahia.intellij.plugin.cnd.enums;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.jetbrains.annotations.NotNull;

public enum AttributeEnum {
    MANDATORY("mandatory", "mandatory", "mandatory|man|m", true),
    PROTECTED("protected", "protected",  "protected|pro|p", true), 
    PRIMARY("primary", "primary", "primary|pri|!", true),
    I18N("i18n"), 
    I15D("i15d"),
    INTERNATIONALIZED("internationalized"),
    SORTABLE("sortable"), 
    HIDDEN("hidden"), 
    MULTIPLE("multiple", "multiple", "multiple|mul|\\*", true), 
    NOFULLTEXT("nofulltext"),
    FULLTEXTSEARCHABLE("fulltextsearchable", "fulltextsearchable=no", "fulltextsearchable\\s*=\\s*no"),
    ANALYZER("analyzer", "analyzer='keyword'", "analyzer\\s*=\\s*(')?keyword(')?"), 
    AUTOCREATED("autocreated", "autocreated", "autocreated|aut|a", true), 
    FACETABLE("facetable"), 
    HIERARCHICAL("hierarchical"), 
    NOQUERYORDER("noqueryorder"),
    INDEXED("indexed", new String[] { "indexed=no", "indexed=tokenized", "indexed=untokenized" }, "indexed\\s*=\\s*(')?(no|tokenized|untokenized)(')?"),
    BOOST("boost", new String[] { "boost=2.0", "scoreboost=2.0" }, "(boost|scoreboost)\\s*=\\s*\\d+(\\.\\d+)?"),
    ONCONFLICT("onconflict", new String[] { "onconflict=sum", "onconflict=latest", "onconflict=oldest", "onconflict=min", "onconflict=max", "onconflict=ignore" }, "onconflict\\s*=\\s*(sum|latest|oldest|min|max|ignore)"),
    ITEMTYPE("itemtype", new String[] { "itemtype=content", "itemtype=metadata", "itemtype=layout", "itemtype=options", "itemtype=codeEditor" }, "itemtype\\s*=\\s*(content|metadata|layout|options|codeEditor)"),
    COPY("copy", true), 
    VERSION("version", true), 
    INITIALIZE("initialize", true), 
    COMPUTE("compute", true), 
    IGNORE("ignore", true), 
    ABORT("abort", true),
    QUERYOPS("queryops", "queryops '<,<=,<>,=,>,>=,like'", "queryops\\s+'((<|<=|<>|=|>|>=|like)(,)?)+'"),
    SNS("sns", true);
    
    private String value;
    private String[] completions;
    private String validationRegex;
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

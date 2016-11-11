package fr.tolc.jahia.intellij.plugin.cnd.enums;

import java.util.regex.Pattern;

public enum PropertyAttributeEnum {
    MANDATORY("mandatory", "madatory", "mandatory|man|m"),
    PROTECTED("protected", "protected",  "protected|pro|p"), 
    PRIMARY("primary", "primary", "primary|pri|!"),
    I18N("i18n"), 
    I15D("i15d"),
    INTERNATIONALIZED("internationalized"),
    SORTABLE("sortable"), 
    HIDDEN("hidden"), 
    MULTIPLE("multiple", "multiple", "multiple|mul|\\*"), 
    NOFULLTEXT("nofulltext"),
    FULLTEXTSEARCHABLE("fulltextsearchable", "fulltextsearchable=no", "fulltextsearchable\\s*=\\s*no"),
    ANALYZER("analyzer", "analyzer='keyword'", "analyzer\\s*=\\s*(')?keyword(')?"), 
    AUTOCREATED("autocreated", "autocreated", "autocreated|aut|a"), 
    FACETABLE("facetable"), 
    HIERARCHICAL("hierarchical"), 
    NOQUERYORDER("noqueryorder"),
    INDEXED("indexed", new String[] { "indexed=no", "indexed=tokenized", "indexed=untokenized" }, "indexed\\s*=\\s*(')?(no|tokenized|untokenized)(')?"),
    BOOST("boost", new String[] { "boost=2.0", "scoreboost=2.0" }, "(boost|scoreboost)\\s*=\\s*\\d+(\\.\\d+)?"),
    ONCONFLICT("onconflict", new String[] { "onconflict=sum", "onconflict=latest", "onconflict=oldest", "onconflict=min", "onconflict=max", "onconflict=ignore" }, "onconflict\\s*=\\s*(sum|latest|oldest|min|max|ignore)"),
    ITEMTYPE("itemtype", new String[] { "itemtype=content", "itemtype=metadata", "itemtype=layout", "itemtype=options", "itemtype=codeEditor" }, "itemtype\\s*=\\s*(content|metadata|layout|options|codeEditor)"),
    COPY("copy"), 
    VERSION("version"), 
    INITIALIZE("initialize"), 
    COMPUTE("compute"), 
    IGNORE("ignore"), 
    ABORT("abort"),
    QUERYOPS("queryops", "queryops '<,<=,<>,=,>,>=,like'", "queryops\\s+'((<|<=|<>|=|>|>=|like)(,)?)+'");
    
    private String value;
    private String[] completions;
    private String validationRegex;

    PropertyAttributeEnum(String value) {
        this.value = value;
        this.completions = new String[] { value };
        this.validationRegex = value;
    }

    PropertyAttributeEnum(String value, String completion, String regex) {
        this.value = value;
        this.completions = new String[] { completion };
        this.validationRegex = regex;
    }
    
    PropertyAttributeEnum(String value, String[] completions, String regex) {
        this.value = value;
        this.completions = completions;
        this.validationRegex = regex;
    }

    public static PropertyAttributeEnum fromValue(String value) throws IllegalArgumentException {
        for (PropertyAttributeEnum attribute : PropertyAttributeEnum.values()) {
            Pattern pattern = Pattern.compile(attribute.validationRegex, Pattern.CASE_INSENSITIVE);
            if (pattern.matcher(value).matches()) {
                return attribute;
            }
        }
        throw new IllegalArgumentException("Unknown property attribute [" + value + "]");
    }

    public String[] getCompletions() {
        return completions;
    }

    public String toString() {
        return this.value;
    }
}

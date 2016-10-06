package fr.tolc.jahia.intellij.plugin.cnd.constants;

import java.util.regex.Pattern;

public class CndConstants {
    
    private CndConstants() {
        
    }

    public static final String[] PROPERTY_TYPES = {
            "string", "long", "double", "decimal", "path", "uri", "boolean", "date", "binary",
            "weakreference", "name", "reference", "undefined"
    };

    public static final String[] PROPERTY_MASKS = {
            "text", "richtext", "textarea", "choicelist", "datetimepicker", "datepicker", "picker",
            "color", "category", "checkbox", "fileupload", "tag", "file"
    };

    public static final String[] PROPERTY_ATTRIBUTES = {"mandatory", "protected", "primary", "i18n", "internationalized",
            "sortable", "hidden", "multiple", "nofulltext",
            "analyzer='keyword'", "autocreated", "facetable", "hierarchical", "noqueryorder",
            "indexed=no", "indexed=tokenized", "indexed=untokenized",
            "boost=2.0", "scoreboost=2.0",
            "onconflict=sum", "onconflict=latest", "onconflict=oldest", "onconflict=min", "onconflict=max", "onconflict=ignore",
            "itemtype = content", "itemtype = metadata", "itemtype = layout", "itemtype = options", "itemtype = codeEditor",
            "copy", "version", "initialize", "compute", "ignore", "abort",
            "queryops '<,<=,<>,=,>,>=,like'"
    };
    public static final Pattern PROPERTY_ATTRIBUTES_REGEX;

    static {
        PROPERTY_ATTRIBUTES_REGEX = Pattern.compile(""
                + "mandatory|protected|primary|i18n|internationalized|sortable|hidden|multiple|nofulltext|"
                + "indexed\\s*=\\s*(')?(no|tokenized|untokenized)(')?|analyzer\\s*=\\s*(')?keyword(')?|autocreated|(boost|scoreboost)\\s*=\\s*\\d+(\\.\\d+)?|"
                + "onconflict\\s*=\\s*(sum|latest|oldest|min|max|ignore)|facetable|hierarchical|noqueryorder|"
                + "itemtype\\s*=\\s*(content|metadata|layout|options|codeEditor)|"
                + "(copy|version|initialize|compute|ignore|abort)|"
                + "queryops\\s+'((<|<=|<>|=|>|>=|like)(,)?)+'", Pattern.CASE_INSENSITIVE);
    }
    
    
    
    
    /*Methods */
    
    public static boolean matches(Pattern pattern, String value) {
        return pattern.matcher(value).matches();
    }

    public static boolean containsIgnoreCase(String[] hay, String needle) {
        for (String element : hay) {
            if (element.equalsIgnoreCase(needle)) {
                return true;
            }
        }
        return false;
    }
}

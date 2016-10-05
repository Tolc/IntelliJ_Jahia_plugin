package fr.tolc.jahia.intellij.plugin.cnd.constants;

public class CndConstants {

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
}

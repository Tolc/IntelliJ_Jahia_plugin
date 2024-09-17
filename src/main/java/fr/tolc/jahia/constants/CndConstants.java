package fr.tolc.jahia.constants;

import java.util.regex.Pattern;

public final class CndConstants {

    public static final String NS_SEP = ":";
    public static final Pattern nodetypePattern = Pattern.compile("([a-zA-Z0-9_]+):([a-zA-Z0-9_]+)");

    public static final String VALUE_REPLACE = "({{value}})";

    private CndConstants() {
    }
}

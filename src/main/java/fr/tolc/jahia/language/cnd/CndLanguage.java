package fr.tolc.jahia.language.cnd;

import com.intellij.lang.Language;

public class CndLanguage extends Language {

    public static final CndLanguage INSTANCE = new CndLanguage();

    protected CndLanguage() {
        super("CND");
    }
}

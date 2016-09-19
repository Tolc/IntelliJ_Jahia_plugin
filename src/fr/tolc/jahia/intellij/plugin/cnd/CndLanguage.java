package fr.tolc.jahia.intellij.plugin.cnd;

import com.intellij.lang.Language;

public class CndLanguage extends Language {
    public static final CndLanguage INSTANCE = new CndLanguage();

    private CndLanguage() {
        super("Cnd");
    }
}
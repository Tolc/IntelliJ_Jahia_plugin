package fr.tolc.jahia.intellij.plugin.cndExtendedProperties;

import com.intellij.lang.Language;

public class CndExtendedPropertiesLanguage extends Language {
    public static final CndExtendedPropertiesLanguage INSTANCE = new CndExtendedPropertiesLanguage();

    private CndExtendedPropertiesLanguage() {
        super("CndExtendedProperties");
    }
}
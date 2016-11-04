package fr.tolc.jahia.intellij.plugin.cndExtendedProperties;

import com.intellij.lang.Language;
import com.intellij.lang.properties.PropertiesHighlighter;
import com.intellij.lang.properties.PropertiesLanguage;
import com.intellij.openapi.fileTypes.SingleLazyInstanceSyntaxHighlighterFactory;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.fileTypes.SyntaxHighlighterFactory;
import org.jetbrains.annotations.NotNull;

public class CndExtendedPropertiesLanguage extends Language {
    public static final CndExtendedPropertiesLanguage INSTANCE = new CndExtendedPropertiesLanguage();

    private CndExtendedPropertiesLanguage() {
        super(PropertiesLanguage.INSTANCE, "CndExtendedProperties", "text/properties");

        SyntaxHighlighterFactory.LANGUAGE_FACTORY.addExplicitExtension(this, new SingleLazyInstanceSyntaxHighlighterFactory() {
            @NotNull
            protected SyntaxHighlighter createHighlighter() {
                return new PropertiesHighlighter();
            }
        });
    }
}
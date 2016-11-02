package fr.tolc.jahia.intellij.plugin.cndExtendedProperties;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.fileTypes.LanguageFileType;
import fr.tolc.jahia.intellij.plugin.cnd.CndLanguage;
import fr.tolc.jahia.intellij.plugin.cnd.icons.CndIcons;
import icons.PropertiesIcons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class CndExtendedPropertiesFileType extends LanguageFileType {
    public static final CndExtendedPropertiesFileType INSTANCE = new CndExtendedPropertiesFileType();

    private CndExtendedPropertiesFileType() {
        super(CndLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public String getName() {
        return "CND extended properties file";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "Jahia Content Node Definitions extended properties file";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return "properties";
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return AllIcons.FileTypes.Properties;
    }
}
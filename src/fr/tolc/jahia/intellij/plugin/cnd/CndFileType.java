package fr.tolc.jahia.intellij.plugin.cnd;

import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class CndFileType extends LanguageFileType {
    public static final CndFileType INSTANCE = new CndFileType();

    private CndFileType() {
        super(CndLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public String getName() {
        return "CND file";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "jahia Content Node Definitions file";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return "cnd";
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return CndIcons.FILE;
    }
}
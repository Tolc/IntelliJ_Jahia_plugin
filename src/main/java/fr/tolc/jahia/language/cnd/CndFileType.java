package fr.tolc.jahia.language.cnd;

import com.intellij.openapi.fileTypes.LanguageFileType;
import com.intellij.openapi.util.NlsContexts;
import com.intellij.openapi.util.NlsSafe;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import javax.swing.Icon;

public class CndFileType extends LanguageFileType {

    public static final CndFileType INSTANCE = new CndFileType();

    private CndFileType() {
        super(CndLanguage.INSTANCE);
    }

    @Override
    public @NonNls @NotNull String getName() {
        return "CND file";
    }

    @Override
    public @NlsContexts.Label @NotNull String getDescription() {
        return "Compact Namespace and Node Type Definition file";
    }

    @Override
    public @NlsSafe @NotNull String getDefaultExtension() {
        return "cnd";
    }

    @Override
    public Icon getIcon() {
        return CndIcons.CND_FILE;
    }
}

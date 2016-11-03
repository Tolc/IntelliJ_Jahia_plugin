package fr.tolc.jahia.intellij.plugin.cndExtendedProperties;

import com.intellij.lang.Language;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.FileViewProviderFactory;
import com.intellij.psi.PsiManager;
import org.jetbrains.annotations.NotNull;

public class CndExtendedPropertiesFileViewProviderFactory implements FileViewProviderFactory {
    @NotNull
    @Override
    public FileViewProvider createFileViewProvider(@NotNull VirtualFile file, Language language, @NotNull PsiManager manager, boolean eventSystemEnabled) {
        assert language.isKindOf(CndExtendedPropertiesLanguage.INSTANCE);
        return new CndExtendedPropertiesFileViewProvider(manager, file, eventSystemEnabled);
    }
}

//TODO See: https://github.com/JetBrains/intellij-plugins/blob/d0fcca865b56466415f13763cb3c517830c8a708/handlebars/src/com/dmarcotte/handlebars/file/HbFileViewProvider.java
package fr.tolc.jahia.intellij.plugin.cndExtendedProperties;

import java.util.Arrays;
import java.util.Set;

import com.intellij.lang.Language;
import com.intellij.lang.properties.PropertiesLanguage;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.MultiplePsiFilesPerDocumentFileViewProvider;
import com.intellij.psi.PsiManager;
import gnu.trove.THashSet;
import org.jetbrains.annotations.NotNull;

public class CndExtendedPropertiesFileViewProvider extends MultiplePsiFilesPerDocumentFileViewProvider {
    
    public CndExtendedPropertiesFileViewProvider(PsiManager manager, VirtualFile file, boolean eventSystemEnabled) {
        super(manager, file, eventSystemEnabled);
    }

    @NotNull
    @Override
    public Language getBaseLanguage() {
        return PropertiesLanguage.INSTANCE;
    }

    @Override
    protected MultiplePsiFilesPerDocumentFileViewProvider cloneInner(VirtualFile fileCopy) {
        return new CndExtendedPropertiesFileViewProvider(getManager(), fileCopy, false);
    }

    @NotNull
    @Override
    public Set<Language> getLanguages() {
        return new THashSet<>(Arrays.asList(new Language[]{PropertiesLanguage.INSTANCE, CndExtendedPropertiesLanguage.INSTANCE}));
    }
}

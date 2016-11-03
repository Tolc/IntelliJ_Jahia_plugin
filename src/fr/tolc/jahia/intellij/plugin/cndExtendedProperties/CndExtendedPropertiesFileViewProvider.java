/**
 * Copyright (c) 2016 by Bank Lombard Odier & Co Ltd, Geneva, Switzerland. This software is subject
 * to copyright protection under the laws of Switzerland and other countries. ALL RIGHTS RESERVED.
 */
package fr.tolc.jahia.intellij.plugin.cndExtendedProperties;

import com.intellij.lang.Language;
import com.intellij.lang.properties.PropertiesLanguage;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.MultiplePsiFilesPerDocumentFileViewProvider;
import com.intellij.psi.PsiManager;
import org.jetbrains.annotations.NotNull;

public class CndExtendedPropertiesFileViewProvider extends MultiplePsiFilesPerDocumentFileViewProvider {
    private final Language baseLanguage;
    
    public CndExtendedPropertiesFileViewProvider(PsiManager manager, VirtualFile file, boolean eventSystemEnabled, Language language) {
        super(manager, file, eventSystemEnabled);
        baseLanguage = language;
    }

    @NotNull
    @Override
    public Language getBaseLanguage() {
        return PropertiesLanguage.INSTANCE;
    }

    @Override
    protected MultiplePsiFilesPerDocumentFileViewProvider cloneInner(VirtualFile fileCopy) {
        return new CndExtendedPropertiesFileViewProvider(getManager(), fileCopy, false, baseLanguage);
    }
}

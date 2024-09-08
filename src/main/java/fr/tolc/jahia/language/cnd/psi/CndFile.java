package fr.tolc.jahia.language.cnd.psi;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import fr.tolc.jahia.language.cnd.CndFileType;
import fr.tolc.jahia.language.cnd.CndLanguage;
import org.jetbrains.annotations.NotNull;

public class CndFile extends PsiFileBase {

    public CndFile(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, CndLanguage.INSTANCE);
    }

    @Override
    public @NotNull FileType getFileType() {
        return CndFileType.INSTANCE;
    }

    @Override
    public String toString() {
        return "Cnd File";
    }
}

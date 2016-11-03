package fr.tolc.jahia.intellij.plugin.cndExtendedProperties;

import java.io.File;

import com.intellij.lang.Language;
import com.intellij.lang.properties.PropertiesLanguage;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.FileIndexFacade;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.LanguageSubstitutor;
import fr.tolc.jahia.intellij.plugin.cnd.utils.CndProjectFilesUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PropertiesToCndExtendedProperties extends LanguageSubstitutor {
    @Nullable
    @Override
    public Language getLanguage(@NotNull VirtualFile file, @NotNull Project project) {
        //check if file is in a module (exclude libraries)
        if (FileIndexFacade.getInstance(project).getModuleForFile(file) != null) {
            if (!CndProjectFilesUtil.getProjectCndFiles(project).isEmpty()) {
                //check if file is not view cache .properties file (and then grandchild of a nodetype folder)
                File nodeTypeFolder = CndProjectFilesUtil.getNodeTypeFolderFromViewFile(project, file);
                if (nodeTypeFolder == null) {
                    return CndExtendedPropertiesLanguage.INSTANCE;
                }
            }
        }
        return null;
    }
}

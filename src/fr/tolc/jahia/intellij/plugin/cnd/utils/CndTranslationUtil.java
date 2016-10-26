package fr.tolc.jahia.intellij.plugin.cnd.utils;

import java.util.Collection;

import com.intellij.lang.properties.IProperty;
import com.intellij.lang.properties.PropertiesFileType;
import com.intellij.lang.properties.psi.PropertiesFile;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.indexing.FileBasedIndex;
import org.jetbrains.annotations.NotNull;

public class CndTranslationUtil {

    public static String getNodeTypeTranslation(Project project, String namespace, String nodeTypeName) {
        Collection<VirtualFile> virtualFiles = FileBasedIndex.getInstance().getContainingFiles(FileTypeIndex.NAME, PropertiesFileType.INSTANCE, GlobalSearchScope.allScope(project));

        for (VirtualFile virtualFile : virtualFiles) {
            PropertiesFile propertiesFile = (PropertiesFile) PsiManager.getInstance(project).findFile(virtualFile);
            if (propertiesFile != null) {
                IProperty property = propertiesFile.findPropertyByKey(convertNodeTypeIdentifierToPropertyName(namespace, nodeTypeName));
                if (property != null) {
                    return property.getValue();
                }
            }
        }
        return null;
    }

    public static String convertNodeTypeIdentifierToPropertyName(@NotNull String nodeTypeIdentifier) {
        return nodeTypeIdentifier.replace(':', '_');
    }

    public static String convertNodeTypeIdentifierToPropertyName(@NotNull String namespace, @NotNull String nodeTypeName) {
        return namespace + "_" + nodeTypeName;
    }

    public static String convertNodeTypePropertyNameToPropertyName(@NotNull String nodeTypeIdentifier, @NotNull String nodePropertyName) {
        return convertNodeTypeIdentifierToPropertyName(nodeTypeIdentifier) + "." + nodePropertyName.replace(':', '_');
    }

    public static String convertNodeTypePropertyNameToPropertyName(@NotNull String namespace, @NotNull String nodeTypeName, @NotNull String nodePropertyName) {
        return convertNodeTypeIdentifierToPropertyName(namespace, nodeTypeName) + "." + nodePropertyName.replace(':', '_');
    }
}

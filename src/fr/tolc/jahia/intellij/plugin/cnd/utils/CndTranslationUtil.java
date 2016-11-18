package fr.tolc.jahia.intellij.plugin.cnd.utils;

import java.util.Collection;

import com.intellij.lang.properties.IProperty;
import com.intellij.lang.properties.PropertiesFileType;
import com.intellij.lang.properties.psi.PropertiesFile;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiManager;
import org.jetbrains.annotations.NotNull;

public class CndTranslationUtil {

    public static String getNodeTypeTranslation(Project project, String namespace, String nodeTypeName) {
        Collection<VirtualFile> virtualFiles = CndProjectFilesUtil.findFilesInSourcesOnly(project, PropertiesFileType.INSTANCE);

        String key = convertNodeTypeIdentifierToPropertyName(namespace, nodeTypeName);
        PsiManager psiManager = PsiManager.getInstance(project);
        for (VirtualFile virtualFile : virtualFiles) {
            PropertiesFile propertiesFile = (PropertiesFile) psiManager.findFile(virtualFile);
            if (propertiesFile != null) {
                IProperty property = propertiesFile.findPropertyByKey(key);
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

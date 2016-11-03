package fr.tolc.jahia.intellij.plugin.cnd.utils;

import java.util.Collection;

import com.intellij.lang.properties.IProperty;
import com.intellij.lang.properties.PropertiesFileType;
import com.intellij.lang.properties.PropertiesLanguage;
import com.intellij.lang.properties.psi.PropertiesFile;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiManager;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CndTranslationUtil {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(CndTranslationUtil.class);

    public static String getNodeTypeTranslation(Project project, String namespace, String nodeTypeName) {
        Collection<VirtualFile> virtualFiles = CndProjectFilesUtil.findFilesInSourcesOnly(project, PropertiesFileType.INSTANCE);

        String key = convertNodeTypeIdentifierToPropertyName(namespace, nodeTypeName);
        PsiManager psiManager = PsiManager.getInstance(project);
        for (VirtualFile virtualFile : virtualFiles) {
            try {
                PropertiesFile propertiesFile = (PropertiesFile) psiManager.findViewProvider(virtualFile).getPsi(PropertiesLanguage.INSTANCE);
                if (propertiesFile != null) {
                    IProperty property = propertiesFile.findPropertyByKey(key);
                    if (property != null) {
                        return property.getValue();
                    }
                }
            } catch (Exception e) {
                //TODO: LOL
                LOGGER.warn("Virtual file error: " + virtualFile.getName());
                LOGGER.warn("Virtual file languages: " + psiManager.findViewProvider(virtualFile).getLanguages());
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

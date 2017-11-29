/*
 * Copyright (c) 2017 by Bank Lombard Odier & Co Ltd, Geneva, Switzerland. This software is subject
 * to copyright protection under the laws of Switzerland and other countries. ALL RIGHTS RESERVED.
 */
package fr.tolc.jahia.intellij.plugin.cnd;

import java.io.File;

import com.intellij.openapi.components.ApplicationComponent;
import fr.tolc.jahia.intellij.plugin.cnd.utils.CndPluginUtil;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CndApplicationComponent implements ApplicationComponent {
    private static final Logger LOGGER = LoggerFactory.getLogger(CndApplicationComponent.class);

    public static final String JAHIA_PLUGIN_SUBFOLDER = "jahia";
    public static final String JAHIA_CND_JAR_NAME = "jahia-plugin-cnds.jar";
    
    @NotNull
    @Override
    public String getComponentName() {
        return "CndApplicationComponent";
    }
    
    @Override
    public void initComponent() {
        File jahiaPluginSubFolder = CndPluginUtil.getPluginFile(JAHIA_PLUGIN_SUBFOLDER);

        if (jahiaPluginSubFolder.exists() && jahiaPluginSubFolder.isDirectory()) {
            //Re-generate 'fake' jar containing cnd files
            File jarFile = CndPluginUtil.getPluginFile(JAHIA_PLUGIN_SUBFOLDER + "/" + JAHIA_CND_JAR_NAME);
            if (jarFile.exists()) {
                jarFile.delete();
            }
            try {
                CndPluginUtil.fileToJar(jahiaPluginSubFolder, jahiaPluginSubFolder.getAbsolutePath() + "/" + JAHIA_CND_JAR_NAME, "cnd");
            } catch (Exception e) {
                LOGGER.warn("Error generating Jahia base cnd files 'fake' jar", e);
            }
        }
    }

    @Override
    public void disposeComponent() {

    }

    
}

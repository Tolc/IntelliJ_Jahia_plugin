/**
 * Copyright (c) 2016 by Bank Lombard Odier & Co Ltd, Geneva, Switzerland. This software is subject
 * to copyright protection under the laws of Switzerland and other countries. ALL RIGHTS RESERVED.
 */
package fr.tolc.jahia.intellij.plugin.cnd;

import java.io.File;
import java.util.Collection;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.roots.OrderRootType;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.roots.libraries.Library;
import com.intellij.openapi.roots.libraries.LibraryTable;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.indexing.FileBasedIndex;
import fr.tolc.jahia.intellij.plugin.cnd.utils.CndProjectFilesUtil;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CndProjectComponent implements ProjectComponent {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(CndProjectComponent.class);
    
    private static final String JAHIA_PLUGIN_LIBRARY_NAME = "jahia-plugin-base-cnd-files";
    private static final String JAHIA_RESOURCES_FOLDER = "jahia";
    private static final String JAHIA_CND_JAR_NAME = "jahia-plugin-cnds.jar";

    private Project project;

    public CndProjectComponent(Project project) {
        this.project = project;
    }

    @NotNull
    @Override
    public String getComponentName() {
        return "CndProjectComponent";
    }

    @Override
    public void projectOpened() {
        ApplicationManager.getApplication().invokeLater(new Runnable() {
            @Override
            public void run() {
                ApplicationManager.getApplication().runWriteAction(new Runnable() {
                    @Override
                    public void run() {
                        File jahiaResourcesFolder = CndProjectFilesUtil.getResourceFile(JAHIA_RESOURCES_FOLDER);

                        if (jahiaResourcesFolder.exists() && jahiaResourcesFolder.isDirectory()) {
                            //Re-generate 'fake' jar containing cnd files
                            File jarFile = CndProjectFilesUtil.getResourceFile(JAHIA_RESOURCES_FOLDER + "/" + JAHIA_CND_JAR_NAME);
                            if (jarFile != null && jarFile.exists()) {
                                jarFile.delete();
                            }
                            try {
                                CndProjectFilesUtil.fileToJar(jahiaResourcesFolder, jahiaResourcesFolder.getAbsolutePath() + "/" + JAHIA_CND_JAR_NAME, "cnd");
                            } catch (Exception e) {
                               LOGGER.warn("Error generating Jahia base cnd files 'fake' jar", e);
                            }


                            //Adding it to the modules libraries
                            Collection<VirtualFile> virtualFiles = FileBasedIndex.getInstance().getContainingFiles(FileTypeIndex.NAME, CndFileType.INSTANCE, GlobalSearchScope.allScope(project));
                            for (VirtualFile virtualFile : virtualFiles) {
                                try {
                                    Module fileModule = ProjectRootManager.getInstance(project).getFileIndex().getModuleForFile(virtualFile);

                                    if (fileModule != null) {
                                        final ModifiableRootModel rootModel = ModuleRootManager.getInstance(fileModule).getModifiableModel();
                                        LibraryTable.ModifiableModel moduleLibraryTable = rootModel.getModuleLibraryTable().getModifiableModel();

                                        if (moduleLibraryTable.getLibraryByName(JAHIA_PLUGIN_LIBRARY_NAME) == null) {
                                            Library library = moduleLibraryTable.createLibrary(JAHIA_PLUGIN_LIBRARY_NAME);

                                            File libraryJar = new File(jahiaResourcesFolder.getAbsolutePath() + "/" + JAHIA_CND_JAR_NAME);

                                            Library.ModifiableModel modifiableModel = library.getModifiableModel();
                                            modifiableModel.addRoot("jar://" + libraryJar.getAbsolutePath() + "!/", OrderRootType.CLASSES);
                                            modifiableModel.commit();
                                            moduleLibraryTable.commit();
                                            rootModel.commit();
                                        }
                                    }
                                } catch (Exception e) {
                                    LOGGER.warn("Error while adding Jahia CND files to module(s) libraries", e);
                                }
                            }
                        }
                    }
                });
            }
        });
    }

    @Override
    public void initComponent() {

    }

    @Override
    public void disposeComponent() {

    }

    @Override
    public void projectClosed() {

    }
}

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
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.roots.libraries.Library;
import com.intellij.openapi.roots.libraries.LibraryTable;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.indexing.FileBasedIndex;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CndProjectComponent implements ProjectComponent {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(CndProjectComponent.class);
    
    private static final String JAHIA_PLUGIN_LIBRARY_NAME = "jahia-base-and-modules-cnd-files";

    private Project project;

    public CndProjectComponent(Project project) {
        this.project = project;
    }

    @Override
    public void initComponent() {
      
    }

    @Override
    public void disposeComponent() {

    }

    @Override
    public void projectOpened() {
        ApplicationManager.getApplication().invokeLater(new Runnable() {
            @Override
            public void run() {
                ApplicationManager.getApplication().runWriteAction(new Runnable() {
                    @Override
                    public void run() {
                        Collection<VirtualFile> virtualFiles = FileBasedIndex.getInstance().getContainingFiles(FileTypeIndex.NAME, CndFileType.INSTANCE, GlobalSearchScope.allScope(project));

                        for (VirtualFile virtualFile : virtualFiles) {
                            try {
                                Module fileModule = ProjectRootManager.getInstance(project).getFileIndex().getModuleForFile(virtualFile);

                                if (fileModule != null) {
                                    final ModifiableRootModel rootModel = ModuleRootManager.getInstance(fileModule).getModifiableModel();
                                    LibraryTable.ModifiableModel moduleLibraryTable = rootModel.getModuleLibraryTable().getModifiableModel();

                                    if (moduleLibraryTable.getLibraryByName(JAHIA_PLUGIN_LIBRARY_NAME) == null) {
                                        Library library = moduleLibraryTable.createLibrary(JAHIA_PLUGIN_LIBRARY_NAME);

                                        String librayFolderPath = this.getClass().getClassLoader().getResource("jahia").getFile();
                                        File librayFolder = new File(librayFolderPath);

                                        Library.ModifiableModel modifiableModel = library.getModifiableModel();
                                        //                        for (OrderRootType rootType : OrderRootType.getAllTypes()) {
                                        //                            modifiableModel.addRoot(librayFolder.getAbsolutePath(), rootType);
                                        //                        }
                                        //                        modifiableModel.addRoot(librayFolder.getAbsolutePath(), OrderRootType.SOURCES);
                                        modifiableModel.addJarDirectory(librayFolder.getAbsolutePath(), false);
                                        modifiableModel.commit();
                                        moduleLibraryTable.commit();
                                        //                    ModuleRootModificationUtil.updateModel()
                                        rootModel.commit();
                                    }
                                }
                            } catch (Exception e) {
                                LOGGER.warn("Error while adding Jahia CND files to module(s) libraries", e);
                            }
                        }
                    }
                });
            }
        });
    }

    @Override
    public void projectClosed() {

    }

    @NotNull
    @Override
    public String getComponentName() {
        return "CndProjectComponent";
    }
}

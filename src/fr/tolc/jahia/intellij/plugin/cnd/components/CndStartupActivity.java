package fr.tolc.jahia.intellij.plugin.cnd.components;

import com.intellij.ide.util.RunOnceUtil;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.DumbService;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.roots.OrderRootType;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.roots.libraries.Library;
import com.intellij.openapi.roots.libraries.LibraryTable;
import com.intellij.openapi.startup.StartupActivity;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.indexing.FileBasedIndex;
import fr.tolc.jahia.intellij.plugin.cnd.toolWindow.JahiaTreeStructure;
import fr.tolc.jahia.intellij.plugin.cnd.utils.CndPluginUtil;
import fr.tolc.jahia.intellij.plugin.cnd.utils.CndProjectFilesUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.java.generate.exception.PluginException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CndStartupActivity implements StartupActivity {
    private static final Logger logger = Logger.getInstance(CndStartupActivity.class);

    private static final String JAHIA_PLUGIN_SUBFOLDER = "jahia";
    private static final String JAHIA_CND_JAR_NAME = "jahia-plugin-cnds.jar";

    private static final String JAHIA_PLUGIN_CND_LIBRARY_NAME = "jahia-plugin-base-cnd-files";
    private static final String JAHIA_PLUGIN_LIBRARY_NAME = "jahia-plugin-completion-library";
    private static final String JAHIA_COMPLETION_JAR = JAHIA_PLUGIN_LIBRARY_NAME + ".jar";
    private static final String JAHIA_COMPLETION_SOURCES = JAHIA_PLUGIN_LIBRARY_NAME + "-sources.jar";

    @Override
    public void runActivity(@NotNull Project project) {
        logger.info("Project " + project.getName() + " started");

        RunOnceUtil.runOnceForApp("jahia-plugin-regenerate-cnd-jar", () -> {
            logger.info("runOnceForApp started");
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
                    logger.warn("Error generating Jahia base cnd files 'fake' jar", e);
                }
            }
        });


        ApplicationManager.getApplication().invokeLater(new Runnable() {
            @Override
            public void run() {
                ApplicationManager.getApplication().runWriteAction(new Runnable() {
                    @Override
                    public void run() {
                        //Files/jar creation is in CndApplicationComponent
                        
                        File jahiaPluginSubFolder = CndPluginUtil.getPluginFile(JAHIA_PLUGIN_SUBFOLDER);
                        if (jahiaPluginSubFolder.exists() && jahiaPluginSubFolder.isDirectory()) {
                            Collection<VirtualFile> virtualFiles = CndProjectFilesUtil.getProjectCndFiles(project);

                            //Adding it to the modules libraries
                            Set<Module> alreadyDoneModules = new HashSet<>();
                            for (VirtualFile virtualFile : virtualFiles) {
                                try {
                                    Module fileModule = ProjectRootManager.getInstance(project).getFileIndex().getModuleForFile(virtualFile);

                                    if (fileModule != null && !alreadyDoneModules.contains(fileModule)) {
                                        final ModifiableRootModel rootModel = ModuleRootManager.getInstance(fileModule).getModifiableModel();
                                        LibraryTable.ModifiableModel moduleLibraryTable = rootModel.getModuleLibraryTable().getModifiableModel();
                                        List<File> toReindex = new ArrayList<>();
                                        //CND jar
                                        Library library = moduleLibraryTable.getLibraryByName(JAHIA_PLUGIN_CND_LIBRARY_NAME);
                                        //TODO: add version number to 'fake' jar and remove it only if version changed?
                                        if (library != null) {
                                            moduleLibraryTable.removeLibrary(library);
                                        }
                                        Library newLibrary = moduleLibraryTable.createLibrary(JAHIA_PLUGIN_CND_LIBRARY_NAME);

                                        File libraryJar = new File(jahiaPluginSubFolder.getAbsolutePath() + "/" + JAHIA_CND_JAR_NAME);
                                        toReindex.add(libraryJar);
                                        Library.ModifiableModel modifiableModel = newLibrary.getModifiableModel();
                                        modifiableModel.addRoot("jar://" + libraryJar.getAbsolutePath() + "!/", OrderRootType.CLASSES);
                                        modifiableModel.commit();


                                        //Classes jar
                                        Library completionLibrary = moduleLibraryTable.getLibraryByName(JAHIA_PLUGIN_LIBRARY_NAME);
                                        //TODO: add version number to classes jar and remove it only if version changed?
                                        if (completionLibrary != null) {
                                            moduleLibraryTable.removeLibrary(completionLibrary);
                                        }
                                        Library newCompletionLibrary = moduleLibraryTable.createLibrary(JAHIA_PLUGIN_LIBRARY_NAME);

                                        File completionLibraryJar = new File(jahiaPluginSubFolder.getAbsolutePath() + "/" + JAHIA_COMPLETION_JAR);
                                        File completionLibrarySources = new File(jahiaPluginSubFolder.getAbsolutePath() + "/" + JAHIA_COMPLETION_SOURCES);
                                        toReindex.add(completionLibraryJar);
                                        toReindex.add(completionLibrarySources);
                                        Library.ModifiableModel completionModifiableModel = newCompletionLibrary.getModifiableModel();
                                        completionModifiableModel.addRoot("jar://" + completionLibraryJar.getAbsolutePath() + "!/", OrderRootType.CLASSES);
                                        completionModifiableModel.addRoot("jar://" + completionLibrarySources.getAbsolutePath() + "!/", OrderRootType.SOURCES);
                                        completionModifiableModel.commit();


                                        //Commit the module config
                                        moduleLibraryTable.commit();
                                        rootModel.commit();

                                        alreadyDoneModules.add(fileModule);


                                        //Reindex
                                        for (File fileReindex : toReindex) {
                                            try {
                                                VirtualFile virtualFileReindex = CndProjectFilesUtil.getVirtualFileFromIoFile(fileReindex);
                                                if (virtualFileReindex != null) {
                                                    FileBasedIndex.getInstance().requestReindex(virtualFileReindex);
                                                }
                                            } catch (Exception e) {
                                                logger.warn("Error reindexing file [" + fileReindex.getAbsolutePath() + "]", e);
                                            }
                                        }
                                    }
                                } catch (Exception e) {
                                    logger.warn("Error while adding Jahia CND files to module(s) libraries", e);
                                }
                            }

                            //Tool window
                            if (!virtualFiles.isEmpty()) {
                                DumbService.getInstance(project).smartInvokeLater(() -> {
                                    new JahiaTreeStructure(project);
                                });
                            }
                        } else {
                            logger.error("Error finding Jahia plugin resources folder");
                            throw new PluginException("Error finding Jahia plugin resources folder", new FileNotFoundException("Missing folder " + jahiaPluginSubFolder.getPath()));
                        }
                    }
                });
            }
        });
    }
}

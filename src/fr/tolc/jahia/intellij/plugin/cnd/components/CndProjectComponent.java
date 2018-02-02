package fr.tolc.jahia.intellij.plugin.cnd.components;

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
import fr.tolc.jahia.intellij.plugin.cnd.CndFileType;
import fr.tolc.jahia.intellij.plugin.cnd.utils.CndPluginUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.java.generate.exception.PluginException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static fr.tolc.jahia.intellij.plugin.cnd.components.CndApplicationComponent.JAHIA_CND_JAR_NAME;
import static fr.tolc.jahia.intellij.plugin.cnd.components.CndApplicationComponent.JAHIA_PLUGIN_SUBFOLDER;

public class CndProjectComponent implements ProjectComponent {
    private static final Logger LOGGER = LoggerFactory.getLogger(CndProjectComponent.class);

    private static final String JAHIA_PLUGIN_CND_LIBRARY_NAME = "jahia-plugin-base-cnd-files";

    private static final String JAHIA_PLUGIN_LIBRARY_NAME = "jahia-plugin-completion-library";
    private static final String JAHIA_COMPLETION_JAR = JAHIA_PLUGIN_LIBRARY_NAME + ".jar";
    private static final String JAHIA_COMPLETION_SOURCES = JAHIA_PLUGIN_LIBRARY_NAME + "-sources.jar";

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
                        //Files/jar creation is in CndApplicationComponent
                        
                        File jahiaPluginSubFolder = CndPluginUtil.getPluginFile(JAHIA_PLUGIN_SUBFOLDER);
                        if (jahiaPluginSubFolder.exists() && jahiaPluginSubFolder.isDirectory()) {

                            //Adding it to the modules libraries
                            Set<Module> alreadyDoneModules = new HashSet<>();
                            Collection<VirtualFile> virtualFiles = FileBasedIndex.getInstance().getContainingFiles(FileTypeIndex.NAME, CndFileType.INSTANCE, GlobalSearchScope.allScope(project));
                            for (VirtualFile virtualFile : virtualFiles) {
                                try {
                                    Module fileModule = ProjectRootManager.getInstance(project).getFileIndex().getModuleForFile(virtualFile);

                                    if (fileModule != null && !alreadyDoneModules.contains(fileModule)) {
                                        final ModifiableRootModel rootModel = ModuleRootManager.getInstance(fileModule).getModifiableModel();
                                        LibraryTable.ModifiableModel moduleLibraryTable = rootModel.getModuleLibraryTable().getModifiableModel();

                                        //CND jar
                                        Library library = moduleLibraryTable.getLibraryByName(JAHIA_PLUGIN_CND_LIBRARY_NAME);
                                        //TODO: add version number to 'fake' jar and remove it only if version changed?
                                        if (library != null) {
                                            moduleLibraryTable.removeLibrary(library);
                                        }
                                        Library newLibrary = moduleLibraryTable.createLibrary(JAHIA_PLUGIN_CND_LIBRARY_NAME);

                                        File libraryJar = new File(jahiaPluginSubFolder.getAbsolutePath() + "/" + JAHIA_CND_JAR_NAME);

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

                                        Library.ModifiableModel completionModifiableModel = newCompletionLibrary.getModifiableModel();
                                        completionModifiableModel.addRoot("jar://" + completionLibraryJar.getAbsolutePath() + "!/", OrderRootType.CLASSES);
                                        completionModifiableModel.addRoot("jar://" + completionLibrarySources.getAbsolutePath() + "!/", OrderRootType.SOURCES);
                                        completionModifiableModel.commit();

                                        //Commit the module config
                                        moduleLibraryTable.commit();
                                        rootModel.commit();

                                        alreadyDoneModules.add(fileModule);
                                    }
                                } catch (Exception e) {
                                    LOGGER.warn("Error while adding Jahia CND files to module(s) libraries", e);
                                }
                            }
                            
                            //Reindex
                            FileBasedIndex.getInstance().scheduleRebuild(FileTypeIndex.NAME, new Throwable("Reindexing after adding Jahia jar"));
                        } else {
                            LOGGER.error("Error finding Jahia plugin resources folder");
                            throw new PluginException("Error finding Jahia plugin resources folder", new FileNotFoundException("Missing folder " + jahiaPluginSubFolder.getPath()));
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

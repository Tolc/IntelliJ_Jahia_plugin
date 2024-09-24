package fr.tolc.jahia.plugin;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectUtil;
import com.intellij.openapi.roots.FileIndexFacade;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.xml.XmlDocument;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.idea.maven.project.MavenProject;
import org.jetbrains.idea.maven.project.MavenProjectsManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class JahiaUtil {

    private static Map<Module, JahiaVersion> jahiaModules;

    private JahiaUtil() {
    }

    public static boolean isJahiaProject(final Project project) {
//        WorkspaceModel workspace = WorkspaceModel.getInstance(project);
//        Sequence<ModuleEntity> modules = workspace.getCurrentSnapshot().entities(ModuleEntity.class);
//        Iterator<ModuleEntity> moduleIterator = modules.iterator();

//        MavenUtil.isMavenModule();

        if (jahiaModules == null) {
            jahiaModules = new HashMap<>();
            MavenProjectsManager mavenProjectsManager = MavenProjectsManager.getInstance(project);
            if (mavenProjectsManager.isMavenizedProject()) {
                List<MavenProject> mavenProjects = mavenProjectsManager.getProjects();
                mavenProjects.forEach(mavenProject -> {
                    VirtualFile pomVf = mavenProject.getFile();
                    XmlFile pom = (XmlFile) PsiManager.getInstance(project).findFile(pomVf);
                    if (pom != null) {
                        XmlDocument document = pom.getDocument();
                        if (document != null) {
                            XmlTag rootTag = document.getRootTag();
                            if (rootTag != null) {
                                XmlTag parent = rootTag.findFirstSubTag("parent");
                                if (parent != null) {
                                    XmlTag artifactIdTag = parent.findFirstSubTag("artifactId");
                                    XmlTag groupIdTag = parent.findFirstSubTag("groupId");
                                    XmlTag versionTag = parent.findFirstSubTag("version");
                                    if (artifactIdTag != null && groupIdTag != null && versionTag != null
                                            && "jahia-modules".equals(artifactIdTag.getValue().getText()) && "org.jahia.modules".equals(groupIdTag.getValue().getText())) {
                                        String version = versionTag.getValue().getText();
                                        JahiaVersion jahiaVersion = JahiaVersion.getJahiaVersion(version);
                                        if (jahiaVersion != null) {
                                            Module module = getModuleForFile(project, pomVf);
                                            if (module != null) {
                                                jahiaModules.put(module, jahiaVersion);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                });
            }
        }

        return !jahiaModules.isEmpty();
    }

    public static boolean isJahiaModule(final Module module) {
        return isJahiaProject(module.getProject()) && jahiaModules.containsKey(module);
    }

    public static @Nullable String getJahiaWorkFolderPath(final Module module) {
        if (isJahiaModule(module)) {
            return ProjectUtil.guessModuleDir(module).getPath() + "/src/main/" + jahiaModules.get(module).getWorkFolder();
        }
        return null;
    }

    public static @Nullable Module getModuleForFile(Project project, VirtualFile virtualFile) {
        return FileIndexFacade.getInstance(project).getModuleForFile(virtualFile);
    }


    enum JahiaVersion {
        JAHIA_6_6("6.6.", "webapp"),
        JAHIA_7("7."),
        JAHIA_8("8.");

        private final String version;
        private final String workFolder;

        JahiaVersion(String version, String workFolder) {
            this.version = version;
            this.workFolder = workFolder;
        }

        JahiaVersion(String version) {
            this.version = version;
            this.workFolder = "resources";
        }

        public static @Nullable JahiaVersion getJahiaVersion(String version) {
            for (JahiaVersion jahiaVersion : values()) {
                if (version.startsWith(jahiaVersion.version)) {
                    return jahiaVersion;
                }
            }
            return null;
        }

        public @NotNull String getWorkFolder() {
            return workFolder;
        }
    }
}

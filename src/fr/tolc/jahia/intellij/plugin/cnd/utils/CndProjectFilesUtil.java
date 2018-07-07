package fr.tolc.jahia.intellij.plugin.cnd.utils;

import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.FileIndexFacade;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.indexing.FileBasedIndex;
import fr.tolc.jahia.intellij.plugin.cnd.CndFileType;
import fr.tolc.jahia.intellij.plugin.cnd.enums.ResourcesTypeEnum;
import fr.tolc.jahia.intellij.plugin.cnd.model.NodeTypeModel;
import fr.tolc.jahia.intellij.plugin.cnd.model.ViewModel;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndNodeType;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CndProjectFilesUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(CndProjectFilesUtil.class);
    public static final String JAHIA_6_WEBAPP = "webapp";
    public static final String JAHIA_7_RESOURCES = "resources";
    public static final String JEE_MAIN = "main";
    public static final String JEE_META_INF = "META-INF";
    private static final String JAHIA_6_PATH = JEE_MAIN + "/" + JAHIA_6_WEBAPP;
    private static final String JAHIA_7_PATH = JEE_MAIN + "/" + JAHIA_7_RESOURCES;

    private static final Map<Module, String> JAHIA_WORK_FOLDERS_PATH_MAP = new HashMap<>();
    
    private CndProjectFilesUtil() {
    }

    @Nullable
    public static String getJahiaWorkFolderPath(Module module) {
        if (!JAHIA_WORK_FOLDERS_PATH_MAP.containsKey(module)) {
            if (module != null) {
                Collection<VirtualFile> projectCndFiles = getModuleCndFiles(module);
                for (VirtualFile cndFile : projectCndFiles) {
                    String path = cndFile.getPath();
                    String jahiaWorkFolderPath = null;
                    if (path.contains(JAHIA_6_PATH)) {
                        jahiaWorkFolderPath = path.substring(0, path.lastIndexOf(JAHIA_6_PATH) + JAHIA_6_PATH.length());
                    } else if (path.contains(JAHIA_7_PATH)) {
                        jahiaWorkFolderPath = path.substring(0, path.lastIndexOf(JAHIA_7_PATH) + JAHIA_7_PATH.length());
                    }

                    if (StringUtils.isNotBlank(jahiaWorkFolderPath)) {
                        JAHIA_WORK_FOLDERS_PATH_MAP.put(module, jahiaWorkFolderPath);
                    }
                }
            }
        }
        return JAHIA_WORK_FOLDERS_PATH_MAP.get(module);
    }

    @Nullable
    public static String getJahiaWorkFolderPath(PsiElement element) {
        return getJahiaWorkFolderPath(getModuleForFile(element.getProject(), element.getContainingFile().getVirtualFile()));
    }

    @Nullable
    public static String getJahiaMetaInfFolderPath(Module module) {
        return getJahiaWorkFolderPath(module) + "/" + JEE_META_INF;
    }

    @Nullable
    public static String getJahiaMetaInfFolderPath(Project project, VirtualFile virtualFile) {
        return getJahiaWorkFolderPath(getModuleForFile(project, virtualFile)) + "/" + JEE_META_INF;
    }

    @NotNull
    @Contract(pure = true)
    public static String getNodeTypeFolderPath(String jahiaWorkFolderPath, String namespace, String nodeTypeName) {
        return jahiaWorkFolderPath + "/" + namespace + "_" + nodeTypeName;
    }
    @NotNull
    public static String getNodeTypeFolderPath(PsiElement element, String namespace, String nodeTypeName) {
        return getNodeTypeFolderPath(getJahiaWorkFolderPath(element), namespace, nodeTypeName);
    }
    @NotNull
    public static String getNodeTypeFolderPath(Module module, String namespace, String nodeTypeName) {
        return getNodeTypeFolderPath(getJahiaWorkFolderPath(module), namespace, nodeTypeName);
    }

    @NotNull
    @Contract(pure = true)
    public static String getNodeTypeDefaultViewsFolderPath(String jahiaWorkFolderPath, String namespace, String nodeTypeName) {
        return getNodeTypeViewsFolderPath(jahiaWorkFolderPath, namespace, nodeTypeName, "html");
    }

    @NotNull
    @Contract(pure = true)
    public static String getNodeTypeViewsFolderPath(String jahiaWorkFolderPath, String namespace, String nodeTypeName, String viewType) {
        return getNodeTypeFolderPath(jahiaWorkFolderPath, namespace, nodeTypeName) + "/" + viewType;
    }
    
    @NotNull
    public static String getNodeTypeViewTypeFolderPath(PsiElement element, String namespace, String nodeTypeName, String viewType) {
        return getNodeTypeFolderPath(element, namespace, nodeTypeName) + "/" + viewType;
    }
    @NotNull
    public static String getNodeTypeViewTypeFolderPath(Module module, String namespace, String nodeTypeName, String viewType) {
        return getNodeTypeFolderPath(module, namespace, nodeTypeName) + "/" + viewType;
    }

    @NotNull
    @Contract(pure = true)
    public static String getNodeTypeViewTypeFolderPath(String jahiaWorkFolderPath, String namespace, String nodeTypeName, String viewType) {
        return getNodeTypeFolderPath(jahiaWorkFolderPath, namespace, nodeTypeName) + "/" + viewType;
    }

    @NotNull
    public static String getNodeTypeViewFileName(String nodeTypeName, String viewName, String viewLanguage, boolean isHiddenView) {
        String fileName = nodeTypeName + ".";
        if (isHiddenView && !viewName.contains("hidden.") && !viewName.contains(".hidden")) {
            fileName += "hidden.";
        }
        if (StringUtils.isNotBlank(viewName) && !"default".equals(viewName)) {
            fileName += viewName + ".";
        }
        fileName += viewLanguage;
        return fileName;
    }

    @NotNull
    public static List<ViewModel> getNodeTypeViews(PsiElement element, String namespace, String nodeTypeName, String templateType) {
        List<ViewModel> res = new ArrayList<ViewModel>();

        List<ViewModel> nodeTypeViews = getNodeTypeViews(element, namespace, nodeTypeName);
        for (ViewModel nodeTypeView : nodeTypeViews) {
            if (templateType.equals(nodeTypeView.getType())) {
                res.add(nodeTypeView);
            }
        }
        
        return res;
    }

    @NotNull
    public static List<ViewModel> getNodeTypeViews(Project project, String namespace, String nodeTypeName, String templateType) {
        List<ViewModel> res = new ArrayList<ViewModel>();
        for (Module module : CndPluginUtil.getProjectModules(project)) {
            String nodeTypeFolderPath = getNodeTypeFolderPath(getJahiaWorkFolderPath(module), namespace, nodeTypeName);
            List<ViewModel> nodeTypeViews = getNodeTypeViews(nodeTypeFolderPath, namespace, nodeTypeName);
            for (ViewModel nodeTypeView : nodeTypeViews) {
                if (templateType.equals(nodeTypeView.getType())) {
                    res.add(nodeTypeView);
                }
            }    
        }
        return res;
    }

    @NotNull
    public static List<ViewModel> getNodeTypeViews(PsiElement element, String namespace, String nodeTypeName) {
        return getNodeTypeViews(getNodeTypeFolderPath(element, namespace, nodeTypeName), namespace, nodeTypeName);
    }

    @NotNull
    public static List<ViewModel> getNodeTypeViews(String nodeTypeFolderPath, String namespace, String nodeTypeName) {
        List<ViewModel> res = new ArrayList<ViewModel>();
        File nodeTypeFolder = new File(nodeTypeFolderPath);

        if (nodeTypeFolder.exists() && nodeTypeFolder.isDirectory()) {
            File[] viewTypesFolders = nodeTypeFolder.listFiles();
            for (File viewTypeFolder : viewTypesFolders) {
                if (viewTypeFolder.isDirectory()) {
                    String viewType = viewTypeFolder.getName();

                    File[] viewsFiles = viewTypeFolder.listFiles(new FilenameFilter() {
                        @Override
                        public boolean accept(File dir, String name) {
                            return !name.endsWith(".properties") && name.startsWith(nodeTypeName + ".");
                        }
                    });

                    if (viewsFiles != null) {
                        for (File viewFile : viewsFiles) {
                            String name = viewFile.getName();
                            String[] split = name.split("\\.");
                            if (split.length >= 2) {
                                String viewLanguage = split[split.length - 1];
                                String viewName;
                                if (split.length == 2) {
                                    viewName = "";
                                } else {
                                    viewName = name.substring(name.indexOf('.') + 1, name.lastIndexOf('.'));
                                }
                                ViewModel viewModel = new ViewModel(namespace, nodeTypeName, viewName, viewType, viewLanguage);
                                res.add(viewModel);
                            }
                        }
                    }
                }
            }
        }
        return res;
    }

    @NotNull
    public static List<ViewModel> getNodeTypeAndAncestorsViews(Project project, String namespace, String nodeTypeName, String templateType) {
        CndNodeType nodeType = CndUtil.findNodeType(project, namespace, nodeTypeName);
        return getNodeTypeAndAncestorsViews(nodeType, templateType);
    }

    @NotNull
    public static List<ViewModel> getNodeTypeAndAncestorsViews(CndNodeType nodeType, String templateType) {
        List<ViewModel> res = new ArrayList<ViewModel>();
        res.addAll(getNodeTypeViews(nodeType.getProject(), nodeType.getNodeTypeNamespace(), nodeType.getNodeTypeName(), templateType));
        for (CndNodeType parentNodeType : nodeType.getParentsNodeTypes()) {
            res.addAll(getNodeTypeAndAncestorsViews(parentNodeType, templateType));
        }
        return res;
    }
    
    @NotNull
    public static List<ViewModel> getProjectNodeTypeViews(Project project) {
        List<ViewModel> res = new ArrayList<ViewModel>();
        List<CndNodeType> nodeTypes = CndUtil.findNodeTypes(project);
        for (CndNodeType nodeType : nodeTypes) {
            for (Module module : CndPluginUtil.getProjectModules(project)) {
                String namespace = nodeType.getNodeTypeNamespace();
                String nodeTypeName = nodeType.getNodeTypeName();
                String nodeTypeFolderPath = getNodeTypeFolderPath(getJahiaWorkFolderPath(module), namespace, nodeTypeName);
                res.addAll(getNodeTypeViews(nodeTypeFolderPath, namespace, nodeTypeName));
            }
        }
        return res;
    }

    @NotNull
    public static List<ViewModel> getProjectNodeTypeViews(Project project, String templateType) {
        List<ViewModel> res = new ArrayList<ViewModel>();

        List<ViewModel> nodeTypeViews = getProjectNodeTypeViews(project);
        for (ViewModel nodeTypeView : nodeTypeViews) {
            if (templateType.equals(nodeTypeView.getType())) {
                res.add(nodeTypeView);
            }
        }

        return res;
    }
    
    @NotNull
    public static List<PsiFile> findViewFiles(Module module, String namespace, String nodeTypeName, String viewType, String viewName) {
        CndNodeType element = CndUtil.findNodeType(module.getProject(), namespace, nodeTypeName);
        return findViewFiles(module, element, viewType, viewName);
    }

    @NotNull
    public static List<PsiFile> findViewFiles(Project project, String namespace, String nodeTypeName, String viewType, String viewName) {
        List<PsiFile> res = new ArrayList<PsiFile>();
        for (Module module : CndPluginUtil.getProjectModules(project)) {
            res.addAll(findViewFiles(module, namespace, nodeTypeName, viewType, viewName));
        }
        return res;
    }

    @NotNull
    public static List<PsiFile> findViewFiles(Module module, CndNodeType element, String viewType, String viewName) {
        List<PsiFile> res = new ArrayList<PsiFile>();
        if (element != null) {
            String namespace = element.getNodeTypeNamespace();
            String nodeTypeName = element.getNodeTypeName();
            String viewTypeFolderPath = getNodeTypeViewTypeFolderPath(module, namespace, nodeTypeName, viewType);
            File viewTypeFolder = new File(viewTypeFolderPath);

            if (viewTypeFolder.exists() && viewTypeFolder.isDirectory()) {
                File[] viewFiles = viewTypeFolder.listFiles(new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String name) {
                        if (StringUtils.isNotBlank(viewName)) {
                            return (name.startsWith(nodeTypeName + "." + viewName + ".") && (name.lastIndexOf('.') == (nodeTypeName.length() + viewName.length() + 1))) || (ViewModel.DEFAULT.equals(viewName) && name.startsWith(nodeTypeName) && name.split("\\.").length == 2);
                        } else {
                            return name.startsWith(nodeTypeName) && name.split("\\.").length == 2;
                        }
                    }
                });
                if (viewFiles != null) {
                    for (File viewFile : viewFiles) {
                        VirtualFile virtualFile = LocalFileSystem.getInstance().refreshAndFindFileByIoFile(viewFile);
                        if (virtualFile != null) {
                            PsiFile psiFile = PsiManager.getInstance(element.getProject()).findFile(virtualFile);
                            if (psiFile != null) {
                                res.add(psiFile);
                            }
                        }
                    }
                }
            }
        }
        return res;
    }

    @NotNull
    public static List<PsiFile> findViewFiles(CndNodeType element, String viewType, String viewName) {
        List<PsiFile> res = new ArrayList<PsiFile>();
        if (element != null) {
            for (Module module : CndPluginUtil.getProjectModules(element.getProject())) {
                res.addAll(findViewFiles(module, element, viewType, viewName));
            }
        }
        return res;
    }
    
    @NotNull
    public static List<PsiFile> findViewFiles(Project project, String viewType, String viewName) {
        List<PsiFile> res = new ArrayList<PsiFile>();
        List<ViewModel> projectNodeTypeViews = getProjectNodeTypeViews(project);
        for (ViewModel nodeTypeView : projectNodeTypeViews) {
            if (viewType.equals(nodeTypeView.getType()) && viewName.equals(nodeTypeView.getName())) {
                res.addAll(findViewFiles(project, nodeTypeView.getNodeType().getNamespace(), nodeTypeView.getNodeType().getNodeTypeName(), viewType, viewName));
            }
        }
        return res;
    }

    @NotNull
    public static List<PsiFile> findViewFiles(Module module, ViewModel viewModel) {
        return findViewFiles(module, viewModel.getNodeType().getNamespace(), viewModel.getNodeType().getNodeTypeName(), viewModel.getType(), viewModel.getName());
    }

    @NotNull
    public static List<PsiFile> findViewFiles(Project project, ViewModel viewModel) {
        List<PsiFile> res = new ArrayList<PsiFile>();
        for (Module module : CndPluginUtil.getProjectModules(project)) {
            res.addAll(findViewFiles(module, viewModel));
        }
        return res;
    }

    @NotNull
    public static List<PsiFile> findViewFilesIncludingAncestors(Project project, String namespace, String nodeTypeName, String viewType, String viewName) {
        return findViewFilesIncludingAncestors(CndUtil.findNodeType(project, namespace, nodeTypeName), viewType, viewName);
    }

    @NotNull
    public static List<PsiFile> findViewFilesIncludingAncestors(CndNodeType nodeType, String viewType, String viewName) {
        List<PsiFile> viewFiles = findViewFiles(nodeType, viewType, viewName);
        if (!viewFiles.isEmpty()) {
            return viewFiles;
        }
        if (nodeType != null) {
            for (CndNodeType parentType : nodeType.getParentsNodeTypes()) {
                List<PsiFile> parentViewFiles = findViewFilesIncludingAncestors(parentType, viewType, viewName);
                if (!parentViewFiles.isEmpty()) {
                    return parentViewFiles;
                }
            }

        }
        return viewFiles;
    }

    @Nullable
    public static ViewModel getViewModelFromPotentialViewFile(@NotNull VirtualFile virtualFile) {
        if (!virtualFile.isDirectory()) {
            String name = virtualFile.getName();
            String[] split = name.split("\\.");

            if (split.length >= 2) {
                String nodeTypeName = split[0];
                String viewLanguage = split[split.length - 1];
                String viewName;
                if (split.length == 2) {
                    viewName = "";
                } else {
                   viewName = name.substring(name.indexOf('.') + 1, name.lastIndexOf('.'));
                }

                VirtualFile viewTypeFolder = virtualFile.getParent();
                if (viewTypeFolder != null && viewTypeFolder.isDirectory()) {
                    String viewType = viewTypeFolder.getName();
                    VirtualFile nodeTypeFolder = viewTypeFolder.getParent();

                    if (nodeTypeFolder != null && nodeTypeFolder.isDirectory()) {
                        String nodeTypeFolderName = nodeTypeFolder.getName();
                        String[] nodeTypeSplit = nodeTypeFolderName.split("_");

                        if (nodeTypeSplit.length == 2) {
                            if (nodeTypeSplit[1].equals(nodeTypeName)) {
                                String namespace = nodeTypeSplit[0];

                                return new ViewModel(namespace, nodeTypeName, viewName, viewType, viewLanguage);
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    @NotNull
    public static Collection<VirtualFile> getProjectCndFiles(Project project) {
        return FileBasedIndex.getInstance().getContainingFiles(FileTypeIndex.NAME, CndFileType.INSTANCE, GlobalSearchScope.allScope(project));
    }

    @NotNull
    public static Collection<VirtualFile> getModuleCndFiles(Module module) {
        Collection<VirtualFile> virtualFiles = FileBasedIndex.getInstance().getContainingFiles(FileTypeIndex.NAME, CndFileType.INSTANCE, GlobalSearchScope.allScope(module.getProject()));

        Collection<VirtualFile> res = new ArrayList<VirtualFile>();
        for (VirtualFile virtualFile : virtualFiles) {
            if (module.equals(getModuleForFile(module.getProject(), virtualFile))) {
                res.add(virtualFile);
            }
        }
        return res;
    }

    @Nullable
    public static Module getModuleForFile(Project project, VirtualFile virtualFile) {
        return FileIndexFacade.getInstance(project).getModuleForFile(virtualFile);
    }

    @NotNull
    public static Collection<VirtualFile> findFilesInLibrariesOnly(Project project, FileType fileType) {
        Collection<VirtualFile> virtualFiles = FileBasedIndex.getInstance().getContainingFiles(FileTypeIndex.NAME, fileType, GlobalSearchScope.allScope(project));

        Collection<VirtualFile> res = new ArrayList<VirtualFile>();
        for (VirtualFile virtualFile : virtualFiles) {
            if (FileIndexFacade.getInstance(project).getModuleForFile(virtualFile) == null) {
                res.add(virtualFile);
            }
        }
        return res;
    }
    
    @NotNull
    public static Collection<VirtualFile> findFilesInSourcesOnly(Project project, FileType fileType) {
        Collection<VirtualFile> virtualFiles = FileBasedIndex.getInstance().getContainingFiles(FileTypeIndex.NAME, fileType, GlobalSearchScope.allScope(project));
        
        Collection<VirtualFile> res = new ArrayList<VirtualFile>();
        for (VirtualFile virtualFile : virtualFiles) {
            if (FileIndexFacade.getInstance(project).getModuleForFile(virtualFile) != null) {
               res.add(virtualFile);
            }
        }
        return res;
    }
    
    public static boolean isNodeTypeFolderChildFolder(VirtualFile viewFolderChildFolder) {
        VirtualFile nodeTypeFolder = viewFolderChildFolder.getParent();
        if (nodeTypeFolder != null) {
            NodeTypeModel nodeTypeModel = null;
            try {
                nodeTypeModel = new NodeTypeModel(nodeTypeFolder.getName(), true);
            } catch (IllegalArgumentException e) {
                //Nothing to do
            }
            
            if (nodeTypeModel != null) {
                VirtualFile webappOrResources = nodeTypeFolder.getParent();
                if (webappOrResources != null) {
                    String folderName = webappOrResources.getName();
                    if (JAHIA_6_WEBAPP.equals(folderName) || JAHIA_7_RESOURCES.equals(folderName)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Nullable
    public static PsiFile getPsiFileFromVirtualFile(Project project, VirtualFile virtualFile) {
        if (project != null && virtualFile != null) {
            return PsiManager.getInstance(project).findFile(virtualFile);
        }
        return null;
    }

    @Nullable
    public static VirtualFile getVirtualFileFromIoFile(File file) {
        return LocalFileSystem.getInstance().findFileByIoFile(file);
    }

    @Nullable
    public static PsiFile getPsiFileFromIoFile(Project project, File file) {
        VirtualFile virtualFile = getVirtualFileFromIoFile(file);
        if (virtualFile != null) {
            return getPsiFileFromVirtualFile(project, virtualFile);
        }
        return null;
    }

    @Nullable
    public static PsiFile getResource(Module module, ResourcesTypeEnum resourcesType, String resource) {
        String jahiaWorkFolderPath = getJahiaWorkFolderPath(module);
        File resourceFile = new File(jahiaWorkFolderPath + "/" + resourcesType.name() + "/" + resource);
        if (resourceFile.exists() && !resourceFile.isDirectory()) {
            return getPsiFileFromIoFile(module.getProject(), resourceFile);
        }
        return null;
    }

    @NotNull
    public static Map<String, PsiFile> getResources(Module module, ResourcesTypeEnum resourcesType) {
        String jahiaWorkFolderPath = getJahiaWorkFolderPath(module);
        File resourcesFolder = new File(jahiaWorkFolderPath + "/" + resourcesType.name());

        return getFilesRecursive(module.getProject(), resourcesFolder, resourcesFolder.getAbsolutePath() + "\\");
    }

    @NotNull
    private static Map<String, PsiFile> getFilesRecursive(Project project, File file, String relativeToFolder) {
        Map<String, PsiFile> res = new HashMap<>();

        if (file != null && file.exists()) {
            if (file.isDirectory()) {
                for (File child : file.listFiles()) {
                    res.putAll(getFilesRecursive(project, child, relativeToFolder));
                }
            } else {
                res.put(
                        StringUtils.substringAfter(file.getAbsolutePath(), relativeToFolder).replace("\\", "/"),
                        getPsiFileFromIoFile(project, file)
                        );
            }
        }

        return res;
    }
}
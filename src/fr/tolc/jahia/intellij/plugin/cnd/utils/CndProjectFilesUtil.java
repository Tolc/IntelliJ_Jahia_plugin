package fr.tolc.jahia.intellij.plugin.cnd.utils;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.PsiManager;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.indexing.FileBasedIndex;
import fr.tolc.jahia.intellij.plugin.cnd.CndFileType;
import fr.tolc.jahia.intellij.plugin.cnd.model.ViewModel;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndNodeType;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CndProjectFilesUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(CndProjectFilesUtil.class);
    private static final String JAHIA_6_WEBAPP = "webapp";
    private static final String JAHIA_7_RESOURCES = "resources";

    private static CndProjectFilesUtil instance;
    
    private CndProjectFilesUtil() {
    }

    private static CndProjectFilesUtil getInstance() {
        if (instance == null) {
            instance = new CndProjectFilesUtil();
        }
        return instance;
    }
    
    public static String getJahiaWorkFolderPath(PsiElement element) {
        PsiFile cndFile = element.getContainingFile();
        PsiDirectory metaInf = cndFile.getParent();
        if (metaInf != null) {
            String metaInfNameFolderName = metaInf.getName();
            if (JAHIA_6_WEBAPP.equals(metaInfNameFolderName) || JAHIA_7_RESOURCES.equals(metaInfNameFolderName)) {
                return metaInf.getVirtualFile().getPath();
            }
            
            PsiDirectory webappOrResources = metaInf.getParent();
            if (webappOrResources != null) {
                String folderName = webappOrResources.getName();
                if (JAHIA_6_WEBAPP.equals(folderName) || JAHIA_7_RESOURCES.equals(folderName)) {
                    return webappOrResources.getVirtualFile().getPath();
                }
            }
        }
        return null;
    }
    
    public static String getNodeTypeFolderPath(String jahiaWorkFolderPath, String namespace, String nodeTypeName) {
        return jahiaWorkFolderPath + "/" + namespace + "_" + nodeTypeName;
    }
    public static String getNodeTypeFolderPath(PsiElement element, String namespace, String nodeTypeName) {
        return getNodeTypeFolderPath(getJahiaWorkFolderPath(element), namespace, nodeTypeName);
    }

    public static String getNodeTypeDefaultViewsFolderPath(String jahiaWorkFolderPath, String namespace, String nodeTypeName) {
        return getNodeTypeViewsFolderPath(jahiaWorkFolderPath, namespace, nodeTypeName, "html");
    }

    public static String getNodeTypeViewsFolderPath(String jahiaWorkFolderPath, String namespace, String nodeTypeName, String viewType) {
        return getNodeTypeFolderPath(jahiaWorkFolderPath, namespace, nodeTypeName) + "/" + viewType;
    }
    
    public static String getNodeTypeViewTypeFolderPath(PsiElement element, String namespace, String nodeTypeName, String viewType) {
        return getNodeTypeFolderPath(element, namespace, nodeTypeName) + "/" + viewType;
    }

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
    public static List<ViewModel> getNodeTypeViews(PsiElement element, String namespace, String nodeTypeName) {
        List<ViewModel> res = new ArrayList<ViewModel>();
        String nodeTypeFolderPath = getNodeTypeFolderPath(element, namespace, nodeTypeName);
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
    public static List<PsiFile> findViewFiles(Project project, String namespace, String nodeTypeName, String viewType, String viewName) {
        CndNodeType element = CndUtil.findNodeType(project, namespace, nodeTypeName);

        List<PsiFile> res = new ArrayList<PsiFile>();
        String viewTypeFolderPath = getNodeTypeViewTypeFolderPath(element, namespace, nodeTypeName, viewType);
        File viewTypeFolder = new File(viewTypeFolderPath);

        if (viewTypeFolder.exists() && viewTypeFolder.isDirectory()) {
            File[] viewFiles = viewTypeFolder.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    if (StringUtils.isNotBlank(viewName)) {
                        return name.startsWith(nodeTypeName + "." + viewName + ".");
                    } else {
                        return name.startsWith(nodeTypeName) && name.split("\\.").length == 2;
                    }
                }
            });
            if (viewFiles != null) {
                for (File viewFile : viewFiles) {
                    VirtualFile virtualFile = LocalFileSystem.getInstance().refreshAndFindFileByIoFile(viewFile);
                    PsiFile psiFile = PsiManager.getInstance(element.getProject()).findFile(virtualFile);
                    res.add(psiFile);
                }
            }
        }
        return res;
    }

    @NotNull
    public static List<PsiFile> findViewFiles(Project project, ViewModel viewModel) {
        return findViewFiles(project, viewModel.getNodeType().getNamespace(), viewModel.getNodeType().getNodeTypeName(), viewModel.getType(), viewModel.getName());
    }

    public static ViewModel getViewModelFromPotentialViewFile(Project project, VirtualFile virtualFile) {
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

                                CndNodeType nodeType = CndUtil.findNodeType(project, namespace, nodeTypeName);
                                if (nodeType != null) {
                                    return new ViewModel(namespace, nodeTypeName, viewName, viewType, viewLanguage);
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    public static Collection<VirtualFile> getProjectCndFiles(Project project) {
        Collection<VirtualFile> virtualFiles = FileBasedIndex.getInstance().getContainingFiles(FileTypeIndex.NAME, CndFileType.INSTANCE, GlobalSearchScope.allScope(project));

        Path filePath = Paths.get(getInstance().getClass().getClassLoader().getResource("jahia/02-jahia-nodetypes-7.1.2.0.cnd").getFile().substring(1));
        try {
            PsiFile fileFromText = PsiFileFactory.getInstance(project).createFileFromText("02-jahia-nodetypes-7.1.2.0.cnd", CndFileType.INSTANCE, new String(Files.readAllBytes(filePath)));
            virtualFiles.add(fileFromText.getVirtualFile());
        } catch (IOException e) {
            LOGGER.warn("Error while getting Jahia CND files from resources", e);
        }

        return virtualFiles;
    }
}
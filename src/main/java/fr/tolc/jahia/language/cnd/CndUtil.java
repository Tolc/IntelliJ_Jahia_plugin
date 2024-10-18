package fr.tolc.jahia.language.cnd;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiTreeUtil;
import fr.tolc.jahia.language.cnd.psi.CndFile;
import fr.tolc.jahia.language.cnd.psi.CndNamespace;
import fr.tolc.jahia.language.cnd.psi.CndNodetype;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class CndUtil {
    private static final Logger logger = LoggerFactory.getLogger(CndUtil.class);

    private static final String[] FALLBACK_CND_PATHS = new String[]{
            "cnd/8.2.0.0/02-jahia-nodetypes.cnd",
            "cnd/8.2.0.0/99-internal-nodetypes.cnd",
    };

    private static List<VirtualFile> fallbackCndFiles;

    private CndUtil() {
    }


    public static @NotNull Collection<VirtualFile> getCndFiles(Project project) {
        Collection<VirtualFile> virtualFiles = FileTypeIndex.getFiles(CndFileType.INSTANCE, GlobalSearchScope.allScope(project));

        if (fallbackCndFiles == null) {
            fallbackCndFiles = new ArrayList<>();
            for (String path : FALLBACK_CND_PATHS) {
                URL resource = CndUtil.class.getClassLoader().getResource(path);
                if (resource != null) {
                    VirtualFile fallbackVirtualFile = VfsUtil.findFileByURL(resource);
                    if (fallbackVirtualFile != null) {
                        fallbackCndFiles.add(fallbackVirtualFile);
                        logger.info("Added fallback CND file: {}", fallbackVirtualFile);
                    }
                }
            }
        }
        virtualFiles.addAll(fallbackCndFiles);

        return virtualFiles;
    }


    // Namespaces

    public static @NotNull List<CndNamespace> findNamespaces(Project project) {
        return findNamespaces(project, null);
    }

    public static @NotNull List<CndNamespace> findNamespaces(Project project, String identifier) {
        List<CndNamespace> result = new ArrayList<>();
        Collection<VirtualFile> virtualFiles = getCndFiles(project);
        for (VirtualFile virtualFile : virtualFiles) {
            CndFile cndFile = (CndFile) PsiManager.getInstance(project).findFile(virtualFile);
            result.addAll(CndUtil.findNamespaces(cndFile, identifier));
        }
        return result;
    }

    public static @NotNull List<CndNamespace> findNamespaces(CndFile cndFile) {
        return findNamespaces(cndFile, null);
    }

    public static @NotNull List<CndNamespace> findNamespaces(CndFile cndFile, String identifier) {
        List<CndNamespace> result = new ArrayList<>();
        if (cndFile != null) {
            @Unmodifiable @NotNull List<CndNamespace> namespaces = PsiTreeUtil.getChildrenOfTypeAsList(cndFile, CndNamespace.class);
            if (StringUtils.isNotBlank(identifier)) {
                for (CndNamespace namespace : namespaces) {
                    if (namespace.getNamespaceIdentifier() != null && identifier.equals(namespace.getNamespaceIdentifier().getText().trim())) {
                        result.add(namespace);
                    }
                }
            } else {
                result.addAll(namespaces);
            }
        }
        return result;
    }


    // Nodetypes

    public static @NotNull List<CndNodetype> findNodetypes(Project project) {
        return findNodetypes(project, null);
    }

    public static @NotNull List<CndNodetype> findNodetypes(Project project, String identifier) {
        List<CndNodetype> result = new ArrayList<>();
        Collection<VirtualFile> virtualFiles = getCndFiles(project);
        for (VirtualFile virtualFile : virtualFiles) {
            CndFile cndFile = (CndFile) PsiManager.getInstance(project).findFile(virtualFile);
            result.addAll(CndUtil.findNodetypes(cndFile, identifier));
        }
        return result;
    }

    public static @NotNull List<CndNodetype> findNodetypes(CndFile cndFile) {
        return findNodetypes(cndFile, null);
    }

    public static @NotNull List<CndNodetype> findNodetypes(CndFile cndFile, String identifier) {
        List<CndNodetype> result = new ArrayList<>();
        if (cndFile != null) {
            @Unmodifiable @NotNull List<CndNodetype> nodetypes = PsiTreeUtil.getChildrenOfTypeAsList(cndFile, CndNodetype.class);
            if (StringUtils.isNotBlank(identifier)) {
                for (CndNodetype nodetype : nodetypes) {
                    if (nodetype.getNodetypeIdentifier() != null && identifier.equals(nodetype.getNodetypeIdentifier().getText().trim())) {
                        result.add(nodetype);
                    }
                }
            } else {
                result.addAll(nodetypes);
            }
        }
        return result;
    }
}

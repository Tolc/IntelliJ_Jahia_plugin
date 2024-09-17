package fr.tolc.jahia.language.cnd;

import com.intellij.openapi.project.Project;
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class CndUtil {

    private CndUtil() {
    }

    public static List<CndNamespace> findNamespaces(Project project) {
        return findNamespaces(project, null);
    }

    public static List<CndNamespace> findNamespaces(Project project, String identifier) {
        List<CndNamespace> result = new ArrayList<>();
        Collection<VirtualFile> virtualFiles = FileTypeIndex.getFiles(CndFileType.INSTANCE, GlobalSearchScope.allScope(project));
        for (VirtualFile virtualFile : virtualFiles) {
            CndFile cndFile = (CndFile) PsiManager.getInstance(project).findFile(virtualFile);
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
        }
        return result;
    }

    public static List<CndNodetype> findNodetypes(Project project) {
        return findNodetypes(project, null);
    }

    public static List<CndNodetype> findNodetypes(Project project, String identifier) {
        List<CndNodetype> result = new ArrayList<>();
        Collection<VirtualFile> virtualFiles = FileTypeIndex.getFiles(CndFileType.INSTANCE, GlobalSearchScope.allScope(project));
        for (VirtualFile virtualFile : virtualFiles) {
            CndFile cndFile = (CndFile) PsiManager.getInstance(project).findFile(virtualFile);
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
        }
        return result;
    }

}

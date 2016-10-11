package fr.tolc.jahia.intellij.plugin.cnd;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.indexing.FileBasedIndex;
import fr.tolc.jahia.intellij.plugin.cnd.model.NodeTypeModel;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndFile;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndNamespace;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndNodeType;

public class CndUtil {

    public static List<CndNodeType> findNodeTypes(Project project, String namespace, String nodeTypeName) {
        List<CndNodeType> result = new ArrayList<CndNodeType>();
        Collection<VirtualFile> virtualFiles = FileBasedIndex.getInstance().getContainingFiles(FileTypeIndex.NAME, CndFileType.INSTANCE, GlobalSearchScope.allScope(project));
        for (VirtualFile virtualFile : virtualFiles) {
            CndFile cndFile = (CndFile) PsiManager.getInstance(project).findFile(virtualFile);
            if (cndFile != null) {
                CndNodeType[] nodeTypes = PsiTreeUtil.getChildrenOfType(cndFile, CndNodeType.class);
                if (nodeTypes != null) {
                    for (CndNodeType nodeType : nodeTypes) {
                        if (namespace.equals(nodeType.getNodeTypeNamespace())
                                && nodeTypeName.equals(nodeType.getNodeTypeName())) {
                            result.add(nodeType);
                        }
                    }
                }
            }
        }
        return result;
    }

    public static CndNodeType findNodeType(Project project, String namespace, String nodeTypeName) {
        List<CndNodeType> nodeTypes = findNodeTypes(project, namespace, nodeTypeName);
        if (nodeTypes.size() > 0) {
            return nodeTypes.get(0);
        }
        return null;
    }

    public static List<CndNodeType> findNodeTypes(Project project, String namespace) {
        List<CndNodeType> result = new ArrayList<CndNodeType>();
        Collection<VirtualFile> virtualFiles = FileBasedIndex.getInstance().getContainingFiles(FileTypeIndex.NAME, CndFileType.INSTANCE, GlobalSearchScope.allScope(project));
        for (VirtualFile virtualFile : virtualFiles) {
            CndFile cndFile = (CndFile) PsiManager.getInstance(project).findFile(virtualFile);
            if (cndFile != null) {
                CndNodeType[] nodeTypes = PsiTreeUtil.getChildrenOfType(cndFile, CndNodeType.class);
                if (nodeTypes != null) {
                    for (CndNodeType nodeType : nodeTypes) {
                        if (namespace.equals(nodeType.getNodeTypeNamespace())) {
                            result.add(nodeType);
                        }
                    }
                }
            }
        }
        return result;
    }

    public static List<CndNodeType> findNodeTypes(Project project) {
        List<CndNodeType> result = new ArrayList<CndNodeType>();
        Collection<VirtualFile> virtualFiles = FileBasedIndex.getInstance().getContainingFiles(FileTypeIndex.NAME, CndFileType.INSTANCE, GlobalSearchScope.allScope(project));
        for (VirtualFile virtualFile : virtualFiles) {
            CndFile cndFile = (CndFile) PsiManager.getInstance(project).findFile(virtualFile);
            if (cndFile != null) {
                CndNodeType[] nodeTypes = PsiTreeUtil.getChildrenOfType(cndFile, CndNodeType.class);
                if (nodeTypes != null) {
                    Collections.addAll(result, nodeTypes);
                }
            }
        }
        return result;
    }

    public static List<CndNamespace> findNamespaces(Project project) {
        List<CndNamespace> result = new ArrayList<CndNamespace>();
        Collection<VirtualFile> virtualFiles = FileBasedIndex.getInstance().getContainingFiles(FileTypeIndex.NAME, CndFileType.INSTANCE, GlobalSearchScope.allScope(project));
        for (VirtualFile virtualFile : virtualFiles) {
            CndFile cndFile = (CndFile) PsiManager.getInstance(project).findFile(virtualFile);
            if (cndFile != null) {
                CndNamespace[] namespaces = PsiTreeUtil.getChildrenOfType(cndFile, CndNamespace.class);
                if (namespaces != null) {
                    Collections.addAll(result, namespaces);
                }
            }
        }
        return result;
    }

    public static List<CndNamespace> findNamespaces(Project project, String namespace) {
        List<CndNamespace> result = new ArrayList<CndNamespace>();
        Collection<VirtualFile> virtualFiles = FileBasedIndex.getInstance().getContainingFiles(FileTypeIndex.NAME, CndFileType.INSTANCE, GlobalSearchScope.allScope(project));
        for (VirtualFile virtualFile : virtualFiles) {
            CndFile cndFile = (CndFile) PsiManager.getInstance(project).findFile(virtualFile);
            if (cndFile != null) {
                CndNamespace[] namespaces = PsiTreeUtil.getChildrenOfType(cndFile, CndNamespace.class);
                if (namespaces != null) {
                    for (CndNamespace cndNamespace: namespaces) {
                        if (namespace.equals(cndNamespace.getNamespaceName())) {
                            result.add(cndNamespace);
                        }
                    }
                }
            }
        }
        return result;
    }

    public static CndNamespace findNamespace(Project project, String namespace) {
        List<CndNamespace> namespaces = findNamespaces(project, namespace);
        if (namespaces.size() > 0) {
            return namespaces.get(0);
        }
        return null;
    }


    private static final Pattern nodeTypeRegex = Pattern.compile("^[A-Za-z]+" + ":" + "[A-Za-z0-9]+$");
    
    public static NodeTypeModel getNodeTypeModel(String value) {
        if (value != null && value.contains(":")) {
            Matcher matcher = nodeTypeRegex.matcher(value);
            if (matcher.matches()) {
                String[] splitValue = value.split(":");
                String namespace = splitValue[0];
                String nodeTypeName = splitValue[1];
                return new NodeTypeModel(value, namespace, nodeTypeName);
            }
        }
        return null;
    }
}
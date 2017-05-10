package fr.tolc.jahia.intellij.plugin.cnd.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.ArrayUtil;
import fr.tolc.jahia.intellij.plugin.cnd.model.NodeTypeModel;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndFile;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndNamespace;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndNodeType;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CndUtil {
    private CndUtil() {}

    @NotNull
    public static List<CndProperty> findProperties(Project project, String namespace, String nodeTypeName, String propertyName) {
        List<CndProperty> result = new ArrayList<CndProperty>();
        Collection<VirtualFile> virtualFiles = CndProjectFilesUtil.getProjectCndFiles(project);
        for (VirtualFile virtualFile : virtualFiles) {
            CndFile cndFile = (CndFile) PsiManager.getInstance(project).findFile(virtualFile);
            if (cndFile != null) {
                CndNodeType[] nodeTypes = PsiTreeUtil.getChildrenOfType(cndFile, CndNodeType.class);
                if (nodeTypes != null) {
                    for (CndNodeType nodeType : nodeTypes) {
                        if (namespace.equals(nodeType.getNodeTypeNamespace()) && nodeTypeName.equals(nodeType.getNodeTypeName())) {
                            List<CndProperty> properties = nodeType.getPropertyList();
                            for (CndProperty property : properties) {
                                if (propertyName.equals(property.getPropertyName())) {
                                    result.add(property);
                                }
                            }
                        }
                    }
                }
            }
        }
        return result;
    }

    @Nullable
    public static CndProperty findProperty(Project project, String namespace, String nodeTypeName, String propertyName) {
        List<CndProperty> properties = findProperties(project, namespace, nodeTypeName, propertyName);
        if (!properties.isEmpty()) {
            return properties.get(0);
        }
        return null;
    }

    @NotNull
    public static List<CndNodeType> findNodeTypes(Project project, String namespace, String nodeTypeName) {
        List<CndNodeType> result = new ArrayList<CndNodeType>();
        Collection<VirtualFile> virtualFiles = CndProjectFilesUtil.getProjectCndFiles(project);
        for (VirtualFile virtualFile : virtualFiles) {
            CndFile cndFile = (CndFile) PsiManager.getInstance(project).findFile(virtualFile);
            if (cndFile != null) {
                CndNodeType[] nodeTypes = PsiTreeUtil.getChildrenOfType(cndFile, CndNodeType.class);
                if (nodeTypes != null) {
                    for (CndNodeType nodeType : nodeTypes) {
                        if (namespace.equals(nodeType.getNodeTypeNamespace()) && nodeTypeName.equals(nodeType.getNodeTypeName())) {
                            result.add(nodeType);
                        }
                    }
                }
            }
        }
        return result;
    }

    @Nullable
    public static CndNodeType findNodeType(Project project, NodeTypeModel nodeTypeModel) {
        return findNodeType(project, nodeTypeModel.getNamespace(), nodeTypeModel.getNodeTypeName());
    }

    @Nullable
    public static CndNodeType findNodeType(Project project, String namespace, String nodeTypeName) {
        List<CndNodeType> nodeTypes = findNodeTypes(project, namespace, nodeTypeName);
        if (!nodeTypes.isEmpty()) {
            return nodeTypes.get(0);
        }
        return null;
    }

    @NotNull
    public static List<CndNodeType> findNodeTypes(Project project, String namespace) {
        List<CndNodeType> result = new ArrayList<CndNodeType>();
        List<CndNodeType> projectNodeTypes =  findNodeTypes(project);
        for (CndNodeType nodeType : projectNodeTypes) {
            if (namespace.equals(nodeType.getNodeTypeNamespace())) {
                result.add(nodeType);
            }
        }
        return result;
    }

    @NotNull
    public static List<CndNodeType> findNodeTypes(Project project) {
        List<CndNodeType> result = new ArrayList<CndNodeType>();
        Collection<VirtualFile> virtualFiles = CndProjectFilesUtil.getProjectCndFiles(project);
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

    @NotNull
    public static List<CndNamespace> findNamespaces(Project project) {
        List<CndNamespace> result = new ArrayList<CndNamespace>();
        Collection<VirtualFile> virtualFiles = CndProjectFilesUtil.getProjectCndFiles(project);
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

    @NotNull
    public static List<CndNamespace> findNamespaces(Project project, String namespace) {
        List<CndNamespace> result = new ArrayList<CndNamespace>();
        List<CndNamespace> projectNamespaces = findNamespaces(project);
        for (CndNamespace cndNamespace: projectNamespaces) {
            if (namespace.equals(cndNamespace.getNamespaceName())) {
                result.add(cndNamespace);
            }
        }
        return result;
    }

    @Nullable
    public static CndNamespace findNamespace(Project project, String namespace) {
        List<CndNamespace> namespaces = findNamespaces(project, namespace);
        if (!namespaces.isEmpty()) {
            return namespaces.get(0);
        }
        return null;
    }


    private static final String[] JAHIA_NAMESPACES = {"mix", "nt", "jmix", "jnt"};
    
    public static boolean isJahiaNamespace(String namespace) {
        return ArrayUtil.contains(namespace, JAHIA_NAMESPACES);
    }
}
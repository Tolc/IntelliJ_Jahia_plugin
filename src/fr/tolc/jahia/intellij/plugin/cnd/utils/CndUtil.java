package fr.tolc.jahia.intellij.plugin.cnd.utils;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiManager;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.ArrayUtil;
import fr.tolc.jahia.intellij.plugin.cnd.model.NodeTypeModel;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndExtension;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndFile;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndNamespace;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndNodeType;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndProperty;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class CndUtil {
    private CndUtil() {}

    @NotNull
    public static Set<CndProperty> findProperties(Project project, String namespace, String nodeTypeName, String propertyName) {
        Set<CndProperty> result = new LinkedHashSet<>();
        Collection<VirtualFile> virtualFiles = CndProjectFilesUtil.getProjectCndFiles(project);
        for (VirtualFile virtualFile : virtualFiles) {
            CndFile cndFile = (CndFile) PsiManager.getInstance(project).findFile(virtualFile);
            if (cndFile != null) {
                CndNodeType[] nodeTypes = PsiTreeUtil.getChildrenOfType(cndFile, CndNodeType.class);
                if (nodeTypes != null) {
                    for (CndNodeType nodeType : nodeTypes) {
                        if (namespace.equals(nodeType.getNodeTypeNamespace()) && nodeTypeName.equals(nodeType.getNodeTypeName())) {
                            Set<CndProperty> properties = nodeType.getProperties();
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
        Set<CndProperty> properties = findProperties(project, namespace, nodeTypeName, propertyName);
        if (!properties.isEmpty()) {
            for (CndProperty property : properties) {
                return property;
            }

        }
        return null;
    }

    @NotNull
    public static Set<CndProperty> findProperties(Project project) {
        Set<CndProperty> result = new LinkedHashSet<>();
        List<CndNodeType> nodeTypes = findNodeTypes(project);
        for (CndNodeType nodeType : nodeTypes) {
            result.addAll(nodeType.getOwnProperties());
        }
        return result;
    }

    @NotNull
    public static Set<CndProperty> findProperties(Project project, String propertyName) {
        Set<CndProperty> result = new LinkedHashSet<>();
        Set<CndProperty> properties = findProperties(project);
        for (CndProperty property : properties) {
            if (propertyName.equals(property.getPropertyName())) {
                result.add(property);
            }
        }
        return result;
    }

    @NotNull
    public static List<CndNodeType> findNodeTypes(Project project, String namespace, String nodeTypeName) {
        List<CndNodeType> result = new ArrayList<>();
        Collection<VirtualFile> virtualFiles = CndProjectFilesUtil.getProjectCndFiles(project);
        for (VirtualFile virtualFile : virtualFiles) {
            try {
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
            } catch (ClassCastException e) {
                //Nothing to do
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
        List<CndNodeType> result = new ArrayList<>();
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
        List<CndNodeType> result = new ArrayList<>();
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
        List<CndNamespace> result = new ArrayList<>();
        Collection<VirtualFile> virtualFiles = CndProjectFilesUtil.getProjectCndFiles(project);
        for (VirtualFile virtualFile : virtualFiles) {
            try {
                CndFile cndFile = (CndFile) PsiManager.getInstance(project).findFile(virtualFile);
                if (cndFile != null) {
                    CndNamespace[] namespaces = PsiTreeUtil.getChildrenOfType(cndFile, CndNamespace.class);
                    if (namespaces != null) {
                        Collections.addAll(result, namespaces);
                    }
                }
            } catch (ClassCastException e) {
                //Nothing to do
            }
        }
        return result;
    }

    @NotNull
    public static List<CndNamespace> findNamespaces(Project project, String namespace) {
        List<CndNamespace> result = new ArrayList<>();
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

    @NotNull
    public static Set<CndNodeType> findExtensionNodeTypes(CndNodeType element) {
        Set<CndNodeType> result = new LinkedHashSet<>();
        Project project = element.getProject();

        Collection<VirtualFile> virtualFiles = CndProjectFilesUtil.getProjectCndFiles(project);
        if (!virtualFiles.isEmpty()) {
            Set<CndNodeType> ancestorsNodeTypes = element.getAncestorsNodeTypes();

            for (VirtualFile virtualFile : virtualFiles) {
                CndFile cndFile = (CndFile) PsiManager.getInstance(project).findFile(virtualFile);
                if (cndFile != null) {
                    Set<PsiElement> extensions = PsiUtil.findDescendantsByType(cndFile, CndTypes.EXTENSION);
                    if (extensions != null) {
                        for (PsiElement extensionEl : extensions) {
                            if (extensionEl instanceof CndExtension) {
                                CndExtension extension = (CndExtension) extensionEl;

                                String nodeTypeNamespace = element.getNodeTypeNamespace();
                                String nodeTypeName = element.getNodeTypeName();

                                boolean isExt = nodeTypeNamespace != null && nodeTypeName != null 
                                        && nodeTypeNamespace.equals(extension.getNodeTypeNamespace()) && nodeTypeName.equals(extension.getNodeTypeName());
                                if (!isExt) {
                                    for (CndNodeType ancestorNodeType : ancestorsNodeTypes) {
                                        String ancestorNodeTypeNamespace = ancestorNodeType.getNodeTypeNamespace();
                                        String ancestorNodeTypeName = ancestorNodeType.getNodeTypeName();
                                        if (ancestorNodeTypeNamespace != null && ancestorNodeTypeName != null 
                                                && ancestorNodeTypeNamespace.equals(extension.getNodeTypeNamespace()) && ancestorNodeTypeName.equals(extension.getNodeTypeName())) {
                                            isExt = true;
                                            break;
                                        }
                                    }
                                }
    
                                if (isExt) {
                                    PsiElement parent = extension.getParent();
                                    if (parent != null) {
                                        PsiElement grandParent = parent.getParent();
                                        if (grandParent != null) {
                                            result.add((CndNodeType) grandParent);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return result;
    }

    private static final String[] JAHIA_NAMESPACES = {"mix", "nt", "jmix", "jnt"};
    
    public static boolean isJahiaNamespace(String namespace) {
        return ArrayUtil.contains(namespace, JAHIA_NAMESPACES);
    }
}
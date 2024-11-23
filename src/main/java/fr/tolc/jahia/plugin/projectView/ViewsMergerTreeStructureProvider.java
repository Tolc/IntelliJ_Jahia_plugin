package fr.tolc.jahia.plugin.projectView;

import com.intellij.ide.projectView.TreeStructureProvider;
import com.intellij.ide.projectView.ViewSettings;
import com.intellij.ide.util.treeView.AbstractTreeNode;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import fr.tolc.jahia.plugin.JahiaUtil;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ViewsMergerTreeStructureProvider implements TreeStructureProvider {
    private final Project project;

    public ViewsMergerTreeStructureProvider(Project project) {
        this.project = project;
    }

    @Override
    public @NotNull Collection<AbstractTreeNode<?>> modify(@NotNull AbstractTreeNode<?> parent, @NotNull Collection<AbstractTreeNode<?>> children, ViewSettings settings) {
        if (!children.isEmpty() && parent.getValue() instanceof PsiDirectory parentDirectory && JahiaUtil.isJahiaProject(project)) {
            // "Views" virtual folder
            VirtualFile parentDirectoryVF = parentDirectory.getVirtualFile();
            Module module = JahiaUtil.getModuleForFile(project, parentDirectoryVF);
            if (module != null && JahiaUtil.isJahiaModule(module)) {
                String jahiaWorkFolderPath = JahiaUtil.getJahiaWorkFolderPath(module);

                if (parentDirectoryVF.getPath().equals(jahiaWorkFolderPath)) {
                    Map<String, List<PsiDirectory>> nsMap = new LinkedHashMap<>();

                    ArrayList<AbstractTreeNode<?>> childrenCopy = new ArrayList<>(children);
                    for (AbstractTreeNode<?> child : childrenCopy) {
                        if (child.getValue() instanceof PsiDirectory childDirectory) {
                            PsiDirectory ntDirectory = childDirectory;
                            boolean isViewFolder = couldBeViewFolder(ntDirectory);
                            if (!isViewFolder) {
                                //Try with parent directory (because of IntelliJ's weird way of merging directories into one if only one subdirectory)
                                ntDirectory = childDirectory.getParent();
                                if (ntDirectory != null && ntDirectory.getChildren().length == 1 && !ntDirectory.equals(parent.getValue())) {
                                    isViewFolder = couldBeViewFolder(ntDirectory);
                                }
                            }

                            if (isViewFolder) {
                                children.remove(child);

                                String[] split = ntDirectory.getName().split("_");
                                String ns = split[0];
                                String nt = split[1];

                                if (!nsMap.containsKey(ns)) {
                                    nsMap.put(ns, new ArrayList<>());
                                }
                                List<PsiDirectory> ntFolders = nsMap.get(ns);
                                ntFolders.add(childDirectory);
                            }
                        }
                    }

                    List<NamespaceFolderNode> nsFolderNodes = new ArrayList<>();
                    for (Map.Entry<String, List<PsiDirectory>> nsEntry : nsMap.entrySet()) {
                        nsFolderNodes.add(new NamespaceFolderNode(project, new NamespaceFolder(parentDirectory, nsEntry.getKey(), nsEntry.getValue()), settings));
                    }

                    children.add(new ViewsFolderNode(project, new ViewsFolder(parentDirectory, nsFolderNodes), settings));
                }
            }
        }

        return children;
    }

    private static boolean couldBeViewFolder(PsiDirectory psiDirectory) {
        return StringUtils.countMatches(psiDirectory.getName(), '_') == 1;
    }

    @Override
    public @Nullable Object getData(@NotNull Collection<? extends AbstractTreeNode<?>> selected, @NotNull String dataId) {
        return TreeStructureProvider.super.getData(selected, dataId);
    }
}

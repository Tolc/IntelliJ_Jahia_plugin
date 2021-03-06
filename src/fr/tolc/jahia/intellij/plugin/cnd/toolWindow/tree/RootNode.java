package fr.tolc.jahia.intellij.plugin.cnd.toolWindow.tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiManager;
import com.intellij.util.containers.ContainerUtil;
import fr.tolc.jahia.intellij.plugin.cnd.CndFileType;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndFile;
import fr.tolc.jahia.intellij.plugin.cnd.toolWindow.tree.root.ActionsNode;
import fr.tolc.jahia.intellij.plugin.cnd.toolWindow.tree.root.CndFileNode;
import fr.tolc.jahia.intellij.plugin.cnd.toolWindow.tree.root.FiltersNode;
import fr.tolc.jahia.intellij.plugin.cnd.utils.CndProjectFilesUtil;

public class RootNode extends CndSimpleNode {
    private final List<CndFileNode> myCndFileNodes = new ArrayList<>();
    private final ActionsNode myActionsNode;
    private final FiltersNode myFiltersNode;

    public RootNode(Project project) {
        super(null);
        myActionsNode = new ActionsNode(this, project);
        myFiltersNode = new FiltersNode(this, project);

        Collection<VirtualFile> projectCndFiles = CndProjectFilesUtil.findFilesInSourcesOnly(project, CndFileType.INSTANCE);
        ((List<VirtualFile>) projectCndFiles).sort(new Comparator<VirtualFile>() {
            @Override
            public int compare(VirtualFile o1, VirtualFile o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        for (VirtualFile projectCndFile : projectCndFiles) {
            add(new CndFileNode((CndFile) PsiManager.getInstance(project).findFile(projectCndFile)));
        }
    }

    protected List<? extends CndSimpleNode> doGetChildren() {
        return ContainerUtil.concat(myCndFileNodes, Collections.singletonList(myActionsNode), Collections.singletonList(myFiltersNode));
    }

    protected void add(CndFileNode cndFileNode) {
        cndFileNode.setParent(this);
        myCndFileNodes.add(cndFileNode);
//        childrenChanged();
    }

    public void remove(CndFileNode cndFileNode) {
        cndFileNode.setParent(null);
        myCndFileNodes.remove(cndFileNode);
//        childrenChanged();
    }
}




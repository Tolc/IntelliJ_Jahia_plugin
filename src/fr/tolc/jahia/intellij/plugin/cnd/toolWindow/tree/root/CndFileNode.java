package fr.tolc.jahia.intellij.plugin.cnd.toolWindow.tree.root;

import java.util.Collections;
import java.util.List;

import com.intellij.openapi.module.Module;
import com.intellij.util.containers.ContainerUtil;
import fr.tolc.jahia.intellij.plugin.cnd.icons.CndIcons;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndFile;
import fr.tolc.jahia.intellij.plugin.cnd.toolWindow.tree.CndSimpleNode;
import fr.tolc.jahia.intellij.plugin.cnd.toolWindow.tree.root.cndFiles.NodeTypesNode;
import fr.tolc.jahia.intellij.plugin.cnd.utils.CndProjectFilesUtil;

public class CndFileNode extends CndSimpleNode {
    private final CndFile cndFile;
    private final NodeTypesNode myMixinsNode;
    private final NodeTypesNode myNodeTypesNode;
    
    public CndFileNode(CndFile cndFile) {
        super(null);
        setIcon(CndIcons.FILE);
        this.cndFile = cndFile;
        myMixinsNode = new NodeTypesNode(this, true);
        myNodeTypesNode = new NodeTypesNode(this);
    }

    @Override
    public String getName() {
        Module moduleForFile = CndProjectFilesUtil.getModuleForFile(cndFile.getProject(), cndFile.getVirtualFile());
        return cndFile.getName() + (moduleForFile != null? " [" + moduleForFile.getName() + "]" : "");
    }

    @Override
    protected List<? extends CndSimpleNode> doGetChildren() {
        return ContainerUtil.concat(Collections.singletonList(myMixinsNode), Collections.singletonList(myNodeTypesNode));
    }

    public CndFile getCndFile() {
        return cndFile;
    }
}

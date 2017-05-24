package fr.tolc.jahia.intellij.plugin.cnd.toolWindow.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.intellij.ide.util.treeView.NodeDescriptor;
import com.intellij.ui.treeStructure.CachingSimpleNode;
import com.intellij.ui.treeStructure.SimpleNode;

public class CndSimpleNode extends CachingSimpleNode {
    private CndSimpleNode myParent;

    public CndSimpleNode(CndSimpleNode parent) {
//        super(MavenProjectsStructure.this.myProject, null);
        super(parent);
        setParent(parent);
    }

    public void setParent(CndSimpleNode parent) {
        myParent = parent;
    }
    
    @Override
    public NodeDescriptor getParentDescriptor() {
        return myParent;
    }
    
    @Override
    protected SimpleNode[] buildChildren() {
        List<? extends CndSimpleNode> children = doGetChildren();
        if (children.isEmpty()) return NO_CHILDREN;

        List<CndSimpleNode> result = new ArrayList<>();
        result.addAll(children);
        return result.toArray(new CndSimpleNode[result.size()]);
    }

    protected List<? extends CndSimpleNode> doGetChildren() {
        return Collections.emptyList();
    }
}

package fr.tolc.jahia.intellij.plugin.cnd.toolWindow.tree.root.cndFiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import fr.tolc.jahia.intellij.plugin.cnd.icons.CndIcons;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndNodeType;
import fr.tolc.jahia.intellij.plugin.cnd.toolWindow.tree.CndSimpleNode;
import fr.tolc.jahia.intellij.plugin.cnd.toolWindow.tree.root.CndFileNode;
import fr.tolc.jahia.intellij.plugin.cnd.toolWindow.tree.root.cndFiles.nodeTypes.NodeTypeNode;

public class NodeTypesNode extends CndSimpleNode {
    private final List<NodeTypeNode> myNodeTypeNodes = new ArrayList<>();
    private boolean isMixin = false;

    public NodeTypesNode(CndSimpleNode parent) {
        this(parent, false);
    }
    public NodeTypesNode(CndSimpleNode parent, boolean isMixin) {
        super(parent);
        this.isMixin = isMixin;
        setIcon(isMixin? CndIcons.MIXIN_FOLDER : CndIcons.NODE_TYPE_FOLDER);

        CndFileNode cndFileNode = (CndFileNode) parent;
        CndNodeType[] nodeTypes = cndFileNode.getCndFile().findChildrenByClass(CndNodeType.class);
        (Arrays.asList(nodeTypes)).sort(new Comparator<CndNodeType>() {
            @Override
            public int compare(CndNodeType o1, CndNodeType o2) {
                return o1.getPresentation().getPresentableText().compareTo(o2.getPresentation().getPresentableText());
            }
        });
        for (CndNodeType nodeType : nodeTypes) {
            if ((isMixin && nodeType.isMixin()) || (!isMixin && !nodeType.isMixin())) {
                add(new NodeTypeNode(nodeType));
            }
        }
    }
    
    @Override
    public String getName() {
//        return message("view.node.plugins");
        return isMixin? "Mixins" : "Node Types";
    }

    @Override
    protected List<? extends CndSimpleNode> doGetChildren() {
        return myNodeTypeNodes;
    }

    protected void add(NodeTypeNode nodeTypeNode) {
        nodeTypeNode.setParent(this);
        myNodeTypeNodes.add(nodeTypeNode);
        //        childrenChanged();
    }

    public void remove(NodeTypeNode nodeTypeNode) {
        nodeTypeNode.setParent(null);
        myNodeTypeNodes.remove(nodeTypeNode);
        //        childrenChanged();
    }
}


    
    
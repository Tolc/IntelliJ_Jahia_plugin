/*
 * Copyright (c) 2017 by Bank Lombard Odier & Co Ltd, Geneva, Switzerland. This software is subject
 * to copyright protection under the laws of Switzerland and other countries. ALL RIGHTS RESERVED.
 */
package fr.tolc.jahia.intellij.plugin.cnd.toolWindow.tree.root.cndFiles;

import java.util.ArrayList;
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
        setIcon(isMixin? CndIcons.MIXIN : CndIcons.NODE_TYPE);

        CndFileNode cndFileNode = (CndFileNode) parent;
        CndNodeType[] nodeTypes = cndFileNode.getCndFile().findChildrenByClass(CndNodeType.class);
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


    
    
/*
 * Copyright (c) 2017 by Bank Lombard Odier & Co Ltd, Geneva, Switzerland. This software is subject
 * to copyright protection under the laws of Switzerland and other countries. ALL RIGHTS RESERVED.
 */
package fr.tolc.jahia.intellij.plugin.cnd.toolWindow.tree.root.cndFiles;

import java.util.ArrayList;
import java.util.List;

import fr.tolc.jahia.intellij.plugin.cnd.toolWindow.tree.CndSimpleNode;
import fr.tolc.jahia.intellij.plugin.cnd.toolWindow.tree.root.cndFiles.nodeTypes.NodeTypeNode;

public class NodeTypesNode extends CndSimpleNode {
    private final List<NodeTypeNode> myNodeTypeNodes = new ArrayList<>();
    
    public NodeTypesNode(CndSimpleNode parent) {
        super(parent);
        //TODO: icon
//        setIcon(MavenIcons.PhasesClosed);
    }
    
    @Override
    public String getName() {
//        return message("view.node.plugins");
        return "Node Types";
    }

    @Override
    protected List<? extends CndSimpleNode> doGetChildren() {
        return myNodeTypeNodes;
    }
}


    
    
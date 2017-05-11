/*
 * Copyright (c) 2017 by Bank Lombard Odier & Co Ltd, Geneva, Switzerland. This software is subject
 * to copyright protection under the laws of Switzerland and other countries. ALL RIGHTS RESERVED.
 */
package fr.tolc.jahia.intellij.plugin.cnd.toolWindow.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.intellij.util.containers.ContainerUtil;
import fr.tolc.jahia.intellij.plugin.cnd.toolWindow.tree.root.ActionsNode;
import fr.tolc.jahia.intellij.plugin.cnd.toolWindow.tree.root.CndFileNode;

public class RootNode extends CndSimpleNode {
    private final List<CndFileNode> myCndFileNodes = new ArrayList<>();
    private final ActionsNode myActionsNode;

    public RootNode() {
        super(null);
        myActionsNode = new ActionsNode(this);
    }

    protected List<? extends CndSimpleNode> doGetChildren() {
        return ContainerUtil.concat(Collections.singletonList(myActionsNode), myCndFileNodes);
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




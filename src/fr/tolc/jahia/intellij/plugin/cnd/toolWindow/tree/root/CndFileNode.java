/*
 * Copyright (c) 2017 by Bank Lombard Odier & Co Ltd, Geneva, Switzerland. This software is subject
 * to copyright protection under the laws of Switzerland and other countries. ALL RIGHTS RESERVED.
 */
package fr.tolc.jahia.intellij.plugin.cnd.toolWindow.tree.root;

import java.util.Collections;
import java.util.List;

import fr.tolc.jahia.intellij.plugin.cnd.icons.CndIcons;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndFile;
import fr.tolc.jahia.intellij.plugin.cnd.toolWindow.tree.CndSimpleNode;
import fr.tolc.jahia.intellij.plugin.cnd.toolWindow.tree.root.cndFiles.NodeTypesNode;

public class CndFileNode extends CndSimpleNode {
    private final CndFile cndFile;
    private final NodeTypesNode myNodeTypesNode;
    
    public CndFileNode(CndFile cndFile) {
        super(null);
        setIcon(CndIcons.FILE);
        this.cndFile = cndFile;
        myNodeTypesNode = new NodeTypesNode(this);
    }

    @Override
    public String getName() {
        return cndFile.getName();
    }

    @Override
    protected List<? extends CndSimpleNode> doGetChildren() {
        return Collections.singletonList(myNodeTypesNode);
    }
}

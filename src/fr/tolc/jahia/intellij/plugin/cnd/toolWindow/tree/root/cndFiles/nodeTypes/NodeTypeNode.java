/*
 * Copyright (c) 2017 by Bank Lombard Odier & Co Ltd, Geneva, Switzerland. This software is subject
 * to copyright protection under the laws of Switzerland and other countries. ALL RIGHTS RESERVED.
 */
package fr.tolc.jahia.intellij.plugin.cnd.toolWindow.tree.root.cndFiles.nodeTypes;

import fr.tolc.jahia.intellij.plugin.cnd.psi.CndNodeType;
import fr.tolc.jahia.intellij.plugin.cnd.toolWindow.tree.CndSimpleNode;

public class NodeTypeNode extends CndSimpleNode {
    private CndNodeType cndNodeType;
    
    public NodeTypeNode(CndNodeType cndNodeType) {
        super(null);
        this.cndNodeType = cndNodeType;
    }

    @Override
    public String getName() {
        return cndNodeType.getPresentation().getPresentableText();
    }
}

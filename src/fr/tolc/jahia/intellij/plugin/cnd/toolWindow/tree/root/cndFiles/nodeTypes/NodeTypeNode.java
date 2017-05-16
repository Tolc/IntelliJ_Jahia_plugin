/*
 * Copyright (c) 2017 by Bank Lombard Odier & Co Ltd, Geneva, Switzerland. This software is subject
 * to copyright protection under the laws of Switzerland and other countries. ALL RIGHTS RESERVED.
 */
package fr.tolc.jahia.intellij.plugin.cnd.toolWindow.tree.root.cndFiles.nodeTypes;

import java.util.ArrayList;
import java.util.List;

import fr.tolc.jahia.intellij.plugin.cnd.icons.CndIcons;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndNodeType;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndProperty;
import fr.tolc.jahia.intellij.plugin.cnd.toolWindow.tree.CndSimpleNode;
import fr.tolc.jahia.intellij.plugin.cnd.toolWindow.tree.root.cndFiles.nodeTypes.nodeType.PropertyNode;

public class NodeTypeNode extends CndSimpleNode {
    private CndNodeType cndNodeType;
    private final List<PropertyNode> myPropertyNodes = new ArrayList<>();
    
    public NodeTypeNode(CndNodeType cndNodeType) {
        super(null);
        this.cndNodeType = cndNodeType;
        setIcon(cndNodeType.isMixin()? CndIcons.MIXIN : CndIcons.NODE_TYPE);
        
        for (CndProperty cndProperty : cndNodeType.getPropertyList()) {
            add(new PropertyNode(cndProperty));
        }

    }

    @Override
    public String getName() {
        return cndNodeType.getPresentation().getPresentableText();
    }

    @Override
    protected List<? extends CndSimpleNode> doGetChildren() {
        return myPropertyNodes;
    }

    protected void add(PropertyNode propertyNode) {
        propertyNode.setParent(this);
        myPropertyNodes.add(propertyNode);
        //        childrenChanged();
    }

    public void remove(PropertyNode propertyNode) {
        propertyNode.setParent(null);
        myPropertyNodes.remove(propertyNode);
        //        childrenChanged();
    }
}

/*
 * Copyright (c) 2017 by Bank Lombard Odier & Co Ltd, Geneva, Switzerland. This software is subject
 * to copyright protection under the laws of Switzerland and other countries. ALL RIGHTS RESERVED.
 */
package fr.tolc.jahia.intellij.plugin.cnd.toolWindow.tree.root;

import fr.tolc.jahia.intellij.plugin.cnd.toolWindow.tree.CndSimpleNode;

public class ActionsNode extends CndSimpleNode {
//    private List<ActionNode> myActionNodes = new ArrayList<>();
    
    public ActionsNode(CndSimpleNode parent) {
        super(parent);
        //TODO: icon
//        setIcon(MavenIcons.ProfilesClosed); 
    }

//    @Override
//    protected List<? extends CndSimpleNode> doGetChildren() {
//        return myActionNodes;
//    }

    @Override
    public String getName() {
//        return message("view.node.profiles");
        return "Jahia Actions";
    }
}

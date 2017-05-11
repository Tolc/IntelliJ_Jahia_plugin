/*
 * Copyright (c) 2017 by Bank Lombard Odier & Co Ltd, Geneva, Switzerland. This software is subject
 * to copyright protection under the laws of Switzerland and other countries. ALL RIGHTS RESERVED.
 */
package fr.tolc.jahia.intellij.plugin.cnd.toolWindow;

import javax.swing.tree.DefaultTreeModel;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Disposer;
import com.intellij.ui.treeStructure.SimpleTree;
import com.intellij.ui.treeStructure.SimpleTreeBuilder;
import com.intellij.ui.treeStructure.SimpleTreeStructure;
import fr.tolc.jahia.intellij.plugin.cnd.toolWindow.tree.RootNode;

public class JahiaTreeStructure extends SimpleTreeStructure {
    private final SimpleTreeBuilder myTreeBuilder;
    private final RootNode myRoot = new RootNode();

    public JahiaTreeStructure(Project project, SimpleTree tree) {
        tree.setRootVisible(false);
        tree.setShowsRootHandles(true);
        
        myTreeBuilder = new SimpleTreeBuilder(tree, (DefaultTreeModel)tree.getModel(), this, null);
        Disposer.register(project, myTreeBuilder);

        myTreeBuilder.initRoot();
        myTreeBuilder.expand(myRoot, null);
    }

    @Override
    public Object getRootElement() {
        return myRoot;
    }
}

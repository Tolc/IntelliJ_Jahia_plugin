/*
 * Copyright (c) 2017 by Bank Lombard Odier & Co Ltd, Geneva, Switzerland. This software is subject
 * to copyright protection under the laws of Switzerland and other countries. ALL RIGHTS RESERVED.
 */
package fr.tolc.jahia.intellij.plugin.cnd.toolWindow;

import java.awt.*;

import javax.swing.*;
import javax.swing.tree.TreeSelectionModel;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.ScrollPaneFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.intellij.ui.treeStructure.SimpleTree;
import org.jetbrains.annotations.NotNull;

public class JahiaToolWindow implements ToolWindowFactory {

    private ToolWindow myToolWindow;
    private SimpleTree myTree;
    private JahiaTreeStructure myStructure;
    

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        myToolWindow = toolWindow;
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();

        initTree();
        initStructure(project);

        Content treeContent = contentFactory.createContent(ScrollPaneFactory.createScrollPane(myTree), "", false);
        myToolWindow.getContentManager().addContent(treeContent);
    }

    private void initTree() {
        myTree = new SimpleTree() {
            //            private final JLabel myLabel = new JLabel(
            //                    ProjectBundle.message("maven.navigator.nothing.to.display", MavenUtil.formatHtmlImage(ADD_ICON_URL),
            //                            MavenUtil.formatHtmlImage(SYNC_ICON_URL)));

            private final JLabel myLabel = new JLabel("There are no CND definitions files to display.");

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                //                if (myProjectsManager.hasProjects()) return;

                myLabel.setFont(getFont());
                myLabel.setBackground(getBackground());
                myLabel.setForeground(getForeground());
                Rectangle bounds = getBounds();
                Dimension size = myLabel.getPreferredSize();
                myLabel.setBounds(0, 0, size.width, size.height);

                int x = (bounds.width - size.width) / 2;
                Graphics g2 = g.create(bounds.x + x, bounds.y + 20, bounds.width, bounds.height);
                try {
                    myLabel.paint(g2);
                }
                finally {
                    g2.dispose();
                }
            }
            
            
        };
        myTree.getEmptyText().clear();
        myTree.getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
        
        
    }
    
    private void initStructure(Project project) {
        myStructure = new JahiaTreeStructure(project, myTree);
    }
}

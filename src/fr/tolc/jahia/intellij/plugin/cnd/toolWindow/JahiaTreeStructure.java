package fr.tolc.jahia.intellij.plugin.cnd.toolWindow;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.RegisterToolWindowTask;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowAnchor;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.ScrollPaneFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.intellij.ui.tree.AsyncTreeModel;
import com.intellij.ui.tree.StructureTreeModel;
import com.intellij.ui.treeStructure.SimpleNode;
import com.intellij.ui.treeStructure.SimpleTree;
import com.intellij.ui.treeStructure.SimpleTreeStructure;
import com.intellij.ui.treeStructure.Tree;
import fr.tolc.jahia.intellij.plugin.cnd.icons.CndIcons;
import fr.tolc.jahia.intellij.plugin.cnd.toolWindow.tree.CndSimpleNode;
import fr.tolc.jahia.intellij.plugin.cnd.toolWindow.tree.RootNode;
import org.jetbrains.annotations.NotNull;

import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import java.util.ArrayList;
import java.util.List;

public class JahiaTreeStructure extends SimpleTreeStructure implements Disposable {
    private final RootNode myRoot;

    public JahiaTreeStructure(Project project) {
        Tree tree = new SimpleTree();
        tree.getEmptyText().clear();
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);

        tree.setRootVisible(false);
        tree.setShowsRootHandles(true);
        
        myRoot = new RootNode(project);

        StructureTreeModel<JahiaTreeStructure> structureTreeModel = new StructureTreeModel<>(this, this);
        AsyncTreeModel asyncTreeModel = new AsyncTreeModel(structureTreeModel, this);
        tree.setModel(asyncTreeModel);

        ToolWindow toolWindow = ToolWindowManager.getInstance(project)
                .registerToolWindow(RegisterToolWindowTask.closable("Jahia", CndIcons.JAHIA_TOOL_WINDOW, ToolWindowAnchor.RIGHT));
        Content treeContent = ContentFactory.getInstance().createContent(ScrollPaneFactory.createScrollPane(tree), "", false);
        toolWindow.getContentManager().addContent(treeContent);
    }

    @NotNull
    @Override
    public Object getRootElement() {
        return myRoot;
    }

    public static <T extends CndSimpleNode> List<T> getSelectedNodes(SimpleTree tree, Class<T> nodeClass) {
        final List<T> filtered = new ArrayList<>();
        for (SimpleNode node : getSelectedNodes(tree)) {
            if ((nodeClass != null) && (!nodeClass.isInstance(node))) {
                filtered.clear();
                break;
            }
            filtered.add((T)node);
        }
        return filtered;
    }

    private static List<SimpleNode> getSelectedNodes(SimpleTree tree) {
        List<SimpleNode> nodes = new ArrayList<>();
        TreePath[] treePaths = tree.getSelectionPaths();
        if (treePaths != null) {
            for (TreePath treePath : treePaths) {
                nodes.add(tree.getNodeFor(treePath));
            }
        }
        return nodes;
    }

    @Override
    public void dispose() {
        //Nothing to do
    }
}

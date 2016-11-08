package fr.tolc.jahia.intellij.plugin.cnd.treeStructure.nodetype;

import java.util.Collection;

import com.intellij.ide.projectView.TreeStructureProvider;
import com.intellij.ide.projectView.ViewSettings;
import com.intellij.ide.projectView.impl.nodes.PsiDirectoryNode;
import com.intellij.ide.util.treeView.AbstractTreeNode;
import com.intellij.openapi.vfs.VirtualFile;
import fr.tolc.jahia.intellij.plugin.cnd.icons.CndIcons;
import fr.tolc.jahia.intellij.plugin.cnd.model.NodeTypeModel;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndNodeType;
import fr.tolc.jahia.intellij.plugin.cnd.treeStructure.view.View;
import fr.tolc.jahia.intellij.plugin.cnd.utils.CndUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NodeTypeFoldersTreeStructureProvider implements TreeStructureProvider {
    @NotNull
    @Override
    public Collection<AbstractTreeNode> modify(@NotNull AbstractTreeNode parent, @NotNull Collection<AbstractTreeNode> children, ViewSettings settings) {
        if (parent.getValue() instanceof View) {
            return children;
        }

        for (AbstractTreeNode child : children) {
            if (child instanceof PsiDirectoryNode) {
                PsiDirectoryNode directoryNode = (PsiDirectoryNode) child;

                NodeTypeModel nodeTypeModel = null;
                try {
                    nodeTypeModel = new NodeTypeModel(directoryNode.getVirtualFile().getName(), true);
                } catch (IllegalArgumentException e) {
                    //Nothing to do
                }
                
                if (nodeTypeModel == null) {
                    //Try with parent directory (because of IntelliJ weird way of merging directories into one if only one subdirectory)
                    VirtualFile parentVirtualFile = directoryNode.getVirtualFile().getParent();
                    if (parentVirtualFile != null && parentVirtualFile.isDirectory()) {
                        try {
                            nodeTypeModel = new NodeTypeModel(parentVirtualFile.getName(), true);
                        } catch (IllegalArgumentException e) {
                            //Nothing to do
                        }
                    }
                }
                
                if (nodeTypeModel != null) {

                    CndNodeType cndNodeType = CndUtil.findNodeType(child.getProject(), nodeTypeModel);
                    if (cndNodeType != null) {
                        if (cndNodeType.isMixin()) {
                            child.setIcon(CndIcons.MIXIN_FOLDER);
                        } else {
                            child.setIcon(CndIcons.NODE_TYPE_FOLDER);
                        }
                    }
                }
            }
        }

        return children;
    }

    @Nullable
    @Override
    public Object getData(Collection<AbstractTreeNode> selected, String dataName) {
        return null;
    }
}

package fr.tolc.jahia.intellij.plugin.cnd.icons;

import javax.swing.*;

import com.intellij.ide.IconProvider;
import com.intellij.openapi.util.Iconable;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import fr.tolc.jahia.intellij.plugin.cnd.model.NodeTypeModel;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndNodeType;
import fr.tolc.jahia.intellij.plugin.cnd.utils.CndUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CndNodeTypeFoldersIconProvider extends IconProvider {
    @Nullable
    @Override
    public Icon getIcon(@NotNull PsiElement element, @Iconable.IconFlags int flags) {
        if(element instanceof PsiDirectory) {
            PsiDirectory psiDirectory = (PsiDirectory) element;

            NodeTypeModel nodeTypeModel = null;
            try {
                nodeTypeModel = new NodeTypeModel(psiDirectory.getName(), true);
            } catch (IllegalArgumentException e) {
                //Nothing to do
            }

            if (nodeTypeModel == null) {
                //Try with parent directory (because of IntelliJ's weird way of merging directories into one if only one subdirectory)
                PsiDirectory parent = psiDirectory.getParent();
                if (parent != null && parent.getChildren().length == 1) {
                    try {
                        nodeTypeModel = new NodeTypeModel(parent.getName(), true);
                    } catch (IllegalArgumentException e) {
                        //Nothing to do
                    }
                }
            }

            if (nodeTypeModel != null) {

                CndNodeType cndNodeType = CndUtil.findNodeType(psiDirectory.getProject(), nodeTypeModel);
                if (cndNodeType != null) {
                    if (cndNodeType.isMixin()) {
                        return CndIcons.MIXIN_FOLDER;
                    } else {
                        return CndIcons.NODE_TYPE_FOLDER;
                    }
                }
            }
        }
        return null;
    }
}

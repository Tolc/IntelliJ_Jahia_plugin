package fr.tolc.jahia.intellij.plugin.cnd.icons;

import static fr.tolc.jahia.intellij.plugin.cnd.utils.CndProjectFilesUtil.JAHIA_6_WEBAPP;
import static fr.tolc.jahia.intellij.plugin.cnd.utils.CndProjectFilesUtil.JAHIA_7_RESOURCES;
import static fr.tolc.jahia.intellij.plugin.cnd.utils.CndProjectFilesUtil.JEE_MAIN;

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
            PsiDirectory parent = psiDirectory.getParent();

            if (parent != null) {
                //Not working here so...
//                        String jahiaWorkFolderPath = CndProjectFilesUtil.getJahiaWorkFolderPath(element);
//                        if (jahiaWorkFolderPath != null && jahiaWorkFolderPath.equals(parent.getVirtualFile().getPath())) {

                PsiDirectory grandParent = parent.getParent();
                if (grandParent != null) {
                    
                    NodeTypeModel nodeTypeModel = null;

                    if (JEE_MAIN.equals(grandParent.getName()) && (JAHIA_6_WEBAPP.equals(parent.getName()) || JAHIA_7_RESOURCES.equals(parent.getName()))) {
                        switch (psiDirectory.getName()) {
                        case "css":
                            return CndIcons.JAHIA_FOLDER_CSS;
                        case "javascript":
                            return CndIcons.JAHIA_FOLDER_JAVASCRIPT;
                        case "icons":
                            return CndIcons.JAHIA_FOLDER_ICONS;
                        case "img":
                            return CndIcons.JAHIA_FOLDER_IMG;
                        case "errors":
                            return CndIcons.JAHIA_FOLDER_ERRORS;
                        }

                        try {
                            nodeTypeModel = new NodeTypeModel(psiDirectory.getName(), true);
                        } catch (IllegalArgumentException e) {
                            //Nothing to do
                        }
                    } else if (parent.getChildren().length == 1) {
                        //Try with parent directory (because of IntelliJ's weird way of merging directories into one if only one subdirectory)
                        PsiDirectory greatGrandParent = grandParent.getParent();
                        if (greatGrandParent != null 
                                && JEE_MAIN.equals(greatGrandParent.getName()) && (JAHIA_6_WEBAPP.equals(grandParent.getName()) || JAHIA_7_RESOURCES.equals(grandParent.getName()))) {
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
            }
        }
        return null;
    }
}

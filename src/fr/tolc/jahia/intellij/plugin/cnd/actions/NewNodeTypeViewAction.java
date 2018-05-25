package fr.tolc.jahia.intellij.plugin.cnd.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import fr.tolc.jahia.intellij.plugin.cnd.model.NodeTypeModel;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndNodeType;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndNodeTypeIdentifier;
import fr.tolc.jahia.intellij.plugin.cnd.quickfixes.CreateNodeTypeViewQuickFix;
import fr.tolc.jahia.intellij.plugin.cnd.utils.CndProjectFilesUtil;
import fr.tolc.jahia.intellij.plugin.cnd.utils.CndUtil;
import org.apache.commons.lang.StringUtils;

import java.io.File;

import static fr.tolc.jahia.intellij.plugin.cnd.utils.CndProjectFilesUtil.JEE_META_INF;
import static fr.tolc.jahia.intellij.plugin.cnd.utils.CndProjectFilesUtil.getModuleForFile;

public class NewNodeTypeViewAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        CndNodeType cndNodeType = getNodeType(e);
        if (cndNodeType != null) {
            Project project = e.getProject();
            VirtualFile virtualFile = e.getData(CommonDataKeys.VIRTUAL_FILE);
            new CreateNodeTypeViewQuickFix((virtualFile != null)? CndProjectFilesUtil.getModuleForFile(project, virtualFile) : null, cndNodeType)
                    .invoke(project, e.getData(CommonDataKeys.EDITOR), e.getData(CommonDataKeys.PSI_FILE));
        }
    }

    @Override
    public void update(AnActionEvent e) {
        boolean showAction = false;
        CndNodeType cndNodeType = getNodeType(e);
        if (cndNodeType != null) {
            showAction = true;
        }
        e.getPresentation().setEnabledAndVisible(showAction);
    }

    private CndNodeType getNodeType(AnActionEvent e) {
        Project project = e.getProject();
        VirtualFile virtualFile = e.getData(CommonDataKeys.VIRTUAL_FILE);
        if (virtualFile != null && project != null) {
            String jahiaWorkFolderPath = CndProjectFilesUtil.getJahiaWorkFolderPath(getModuleForFile(project, virtualFile));
            if (StringUtils.isNotBlank(jahiaWorkFolderPath) && virtualFile.getPath().contains(jahiaWorkFolderPath)) {
                NodeTypeModel nodeTypeModel = null;
                try {
                    nodeTypeModel = new NodeTypeModel(virtualFile.getName(), true);
                } catch (IllegalArgumentException ex) {
                    //Nothing to do
                }

                VirtualFile jahiaWorkFolderFile = LocalFileSystem.getInstance().refreshAndFindFileByIoFile(new File(jahiaWorkFolderPath));
                VirtualFile parentDir = virtualFile;
                while (nodeTypeModel == null && parentDir != null && jahiaWorkFolderFile != null && !parentDir.equals(jahiaWorkFolderFile)) {
                    //Try with parent directory (because of IntelliJ's weird way of merging directories into one if only one subdirectory)
                    parentDir = parentDir.getParent();
                    try {
                        nodeTypeModel = new NodeTypeModel(parentDir.getName(), true);
                    } catch (IllegalArgumentException ex) {
                        //Nothing to do
                    }

                }

                if (nodeTypeModel != null) {
                    return CndUtil.findNodeType(project, nodeTypeModel);
                }
            }
        }
        return null;
    }
}

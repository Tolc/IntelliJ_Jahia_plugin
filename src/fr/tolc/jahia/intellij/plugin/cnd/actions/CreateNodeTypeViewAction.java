package fr.tolc.jahia.intellij.plugin.cnd.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndNodeType;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndNodeTypeIdentifier;
import fr.tolc.jahia.intellij.plugin.cnd.quickfixes.CreateNodeTypeViewQuickFix;
import fr.tolc.jahia.intellij.plugin.cnd.utils.CndProjectFilesUtil;
import org.apache.commons.lang.StringUtils;

public class CreateNodeTypeViewAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        PsiElement element  = e.getData(CommonDataKeys.PSI_ELEMENT);
        VirtualFile file = e.getData(CommonDataKeys.VIRTUAL_FILE);

        CndNodeTypeIdentifier nodeTypeIdentifier = null;
        if (element instanceof CndNodeTypeIdentifier) {
            nodeTypeIdentifier = (CndNodeTypeIdentifier) element;
        } else {
            PsiElement parent  = element.getParent();
            if (parent instanceof CndNodeType) {
                nodeTypeIdentifier = ((CndNodeType) parent).getNodeTypeIdentifier();
            }
        }
        if (nodeTypeIdentifier != null) {
            new CreateNodeTypeViewQuickFix((file != null)? CndProjectFilesUtil.getModuleForFile(element.getProject(), file) : null, nodeTypeIdentifier.getNodeType())
                    .invoke(element.getProject(), e.getData(CommonDataKeys.EDITOR), e.getData(CommonDataKeys.PSI_FILE));
        }
    }

    @Override
    public void update(AnActionEvent e) {
        boolean showAction = false;
        PsiElement element  = e.getData(CommonDataKeys.PSI_ELEMENT);
        if (element != null) {
            PsiElement parent  = element.getParent();
            showAction = element instanceof CndNodeType || (parent != null && parent instanceof CndNodeType);
        }
        e.getPresentation().setEnabledAndVisible(showAction);
    }
}

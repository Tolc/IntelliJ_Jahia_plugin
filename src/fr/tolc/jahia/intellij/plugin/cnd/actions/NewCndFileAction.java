package fr.tolc.jahia.intellij.plugin.cnd.actions;

import com.intellij.ide.projectView.ProjectView;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.pom.Navigatable;
import com.intellij.psi.PsiManager;
import com.intellij.util.IncorrectOperationException;
import fr.tolc.jahia.intellij.plugin.cnd.dialogs.CreateCndFileDialog;
import fr.tolc.jahia.intellij.plugin.cnd.utils.CndPluginUtil;
import fr.tolc.jahia.intellij.plugin.cnd.utils.CndProjectFilesUtil;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class NewCndFileAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        ApplicationManager.getApplication().invokeLater(new Runnable() {
            @Override
            public void run() {
                CreateCndFileDialog dialog = new CreateCndFileDialog(e.getProject());
                dialog.setVisible(true);

                if (dialog.isOkClicked()) {
                    String fileName = dialog.getCndFileName();
                    if (StringUtils.isNotBlank(fileName)) {
                        Project project = e.getProject();
                        VirtualFile virtualFile = e.getData(CommonDataKeys.VIRTUAL_FILE);
                        if (virtualFile != null && project != null) {
                            createCndFile(project, virtualFile.getCanonicalPath(), fileName);
                        }
                    }
                }
            }
        });
    }

    @Override
    public void update(AnActionEvent e) {
        boolean showAction = false;
        Project project = e.getProject();
        VirtualFile virtualFile = e.getData(CommonDataKeys.VIRTUAL_FILE);
        if (virtualFile != null && project != null) {
            String metaInfFolderPath = CndProjectFilesUtil.getJahiaMetaInfFolderPath(e.getProject(), virtualFile);
            if (StringUtils.isNotBlank(metaInfFolderPath)) {
                showAction = virtualFile.getPath().contains(metaInfFolderPath);
            }
        }
        e.getPresentation().setEnabledAndVisible(showAction);
    }

    private void createCndFile(final Project project, final String directory, final String cndFileName) {
        File folder = new File(directory);
        if(!folder.exists() || !folder.isDirectory()) {
            folder.mkdirs();
        }
        VirtualFile virtualFolder = LocalFileSystem.getInstance().refreshAndFindFileByIoFile(folder);

        String realFileName = cndFileName;
        if (!realFileName.endsWith(".cnd"))
            realFileName += ".cnd";
        File cndFile = new File(virtualFolder.getCanonicalPath(), realFileName);

        //Copying default cnd file to create the new file
        try {
            if (!cndFile.exists()) {
                Path defaultCndFilePath = CndPluginUtil.getPluginFilePath("default/cnd-file.cnd");
                Files.copy(defaultCndFilePath, cndFile.toPath());
            }
        } catch (IOException e) {
            throw new IncorrectOperationException(e);
        }

        //Open new file in editor
        VirtualFile cndVirtualFile = LocalFileSystem.getInstance().refreshAndFindFileByIoFile(cndFile);
        FileEditorManager.getInstance(project).openFile(cndVirtualFile, true);

        //Expand folder in Project view
        ProjectView.getInstance(project).select(null, cndVirtualFile, false);

        //Caret at the end of the file
        ((Navigatable) PsiManager.getInstance(project).findFile(cndVirtualFile).getLastChild().getNavigationElement()).navigate(true);
        CaretModel caretModel = FileEditorManager.getInstance(project).getSelectedTextEditor().getCaretModel();
        caretModel.moveCaretRelatively(-caretModel.getLogicalPosition().column, 2, false, false, false);
    }
}

package fr.tolc.jahia.intellij.plugin.cnd.quickfixes;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.*;

import com.intellij.codeInsight.intention.impl.BaseIntentionAction;
import com.intellij.ide.projectView.ProjectView;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.util.IncorrectOperationException;
import fr.tolc.jahia.intellij.plugin.cnd.CndUtil;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

public class CreateNodeTypeViewQuickFix extends BaseIntentionAction {

    private String jahiaWorkFolderPath;
    private String namespace;
    private String nodeTypeName;

    public CreateNodeTypeViewQuickFix(String jahiaWorkFolderPath, String namespace, String nodeTypeName) {
        this.jahiaWorkFolderPath = jahiaWorkFolderPath;
        this.namespace = namespace;
        this.nodeTypeName = nodeTypeName;
    }

    @NotNull
    @Override
    public String getText() {
        return "Create new view";
    }
    
    @Nls
    @NotNull
    @Override
    public String getFamilyName() {
        return "Cnd";
    }

    @Override
    public boolean isAvailable(@NotNull Project project, Editor editor, PsiFile file) {
        return true;
    }

    @Override
    public void invoke(@NotNull Project project, Editor editor, PsiFile file) throws IncorrectOperationException {
        ApplicationManager.getApplication().invokeLater(new Runnable() {
            @Override
            public void run() {
                CreateNodeTypeViewDialog createNodeTypeViewDialog = new CreateNodeTypeViewDialog(project);
                createNodeTypeViewDialog.setVisible(true);

                if (createNodeTypeViewDialog.isOkClicked()) {
                    String viewName = createNodeTypeViewDialog.getViewName();
                    boolean isHiddenView = createNodeTypeViewDialog.isHiddenView();
                    String viewType = createNodeTypeViewDialog.getViewType();
                    String viewLanguage = createNodeTypeViewDialog.getViewLanguage();

                    if (StringUtils.isNotBlank(viewType) && StringUtils.isNotBlank(viewLanguage)) {
                        String finalDirectory = CndUtil.getNodeTypeViewsFolderPath(jahiaWorkFolderPath, namespace, nodeTypeName, viewType);
                        String fileName = CndUtil.getNodeTypeViewFileName(nodeTypeName, viewName, viewLanguage, isHiddenView);
                        String propertiesFileName = CndUtil.getNodeTypeViewFileName(nodeTypeName, viewName, "properties", isHiddenView);

                        createNodeTypeView(project, finalDirectory, fileName, propertiesFileName);
                    }
                }
            }
        });
    }
    
    private void createNodeTypeView(final Project project, final String directory, final String viewFileName, final String propertiesFileName) {
        File folder = new File(directory);
        if(!folder.exists() || !folder.isDirectory()) {
            folder.mkdirs();
        }
        VirtualFile nodeTypeFolder = LocalFileSystem.getInstance().refreshAndFindFileByIoFile(folder);

        File viewFile = new File(nodeTypeFolder.getCanonicalPath(), viewFileName);
        File properties = new File(nodeTypeFolder.getCanonicalPath(), propertiesFileName);

        //Copying default content files to create the new files
        try {
            if (!viewFile.exists()) {
                Path defaultViewPath;
                if (viewFileName.endsWith(".jsp")) {
                    defaultViewPath = Paths.get(getClass().getClassLoader().getResource("default/view.jsp").getFile().substring(1));   //Because of starting "/" /E:/...
                } else {
                    defaultViewPath = Paths.get(getClass().getClassLoader().getResource("default/view.default").getFile().substring(1));   //Because of starting "/" /E:/...
                }
                Files.copy(defaultViewPath, viewFile.toPath());
            }

            if (!properties.exists()) {
                Path defaultPropertiesPath = Paths.get(getClass().getClassLoader().getResource("default/view.properties").getFile().substring(1));  //Because of starting "/" /E:/...
                Files.copy(defaultPropertiesPath, properties.toPath());
            }
        } catch (IOException e) {
            throw new IncorrectOperationException(e);
        }

        //Open new files in editor
        VirtualFile propertiesFile = LocalFileSystem.getInstance().refreshAndFindFileByIoFile(properties);
        FileEditorManager.getInstance(project).openFile(propertiesFile, false);
        
        VirtualFile viewVirtualFile = LocalFileSystem.getInstance().refreshAndFindFileByIoFile(viewFile);
        FileEditorManager.getInstance(project).openFile(viewVirtualFile, true);

        //Expand folder in Project view
        ProjectView.getInstance(project).select(null, viewVirtualFile, false);
    }
}

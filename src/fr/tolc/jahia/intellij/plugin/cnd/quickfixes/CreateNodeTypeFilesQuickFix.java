package fr.tolc.jahia.intellij.plugin.cnd.quickfixes;

import java.io.File;
import java.io.IOException;

import com.intellij.codeInsight.intention.impl.BaseIntentionAction;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

public class CreateNodeTypeFilesQuickFix extends BaseIntentionAction {

    private String namespace;
    private String nodeTypeName;

    public CreateNodeTypeFilesQuickFix(String namespace, String nodeTypeName) {
        this.namespace = namespace;
        this.nodeTypeName = nodeTypeName;
    }

    @NotNull
    @Override
    public String getText() {
        return "Create node type associated files";
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
                String finalDirectory = "main/webapp/" + namespace + "_" + nodeTypeName;

                Module currentModule = ProjectRootManager.getInstance(project).getFileIndex().getModuleForFile(file.getVirtualFile());
                VirtualFile[] sourceRoots = ModuleRootManager.getInstance(currentModule).getSourceRoots();

                if (sourceRoots.length == 1) {
                    createNodeTypeFiles(project, sourceRoots[0], finalDirectory);
                } else {
                    //TODO: only allow to choose between source folders
                    final FileChooserDescriptor descriptor = FileChooserDescriptorFactory.createSingleFolderDescriptor();
                    descriptor.setTitle("Select Root Folder");
                    descriptor.setRoots(sourceRoots);
                    final VirtualFile file = FileChooser.chooseFile(descriptor, project, null);
                    if (file != null) {
                        createNodeTypeFiles(project, file, finalDirectory);
                    }
                }
            }
        });
    }
    
    private void createNodeTypeFiles(final Project project, final VirtualFile sourceDirectory, final String directory) {
        File folder = new File(sourceDirectory.getCanonicalPath(), directory);
        if(!folder.exists()) {
            folder.mkdirs();
        }
        VirtualFile nodeTypeFolder = LocalFileSystem.getInstance().refreshAndFindFileByIoFile(folder);

        File jsp = new File(nodeTypeFolder.getCanonicalPath(),nodeTypeName + ".jsp");
        File properties = new File(nodeTypeFolder.getCanonicalPath(),nodeTypeName + ".properties");
        try {
            jsp.createNewFile();
            properties.createNewFile();
        } catch (IOException e) {
            throw new IncorrectOperationException(e);
        }

        //TODO: default content

        VirtualFile propertiesFile = LocalFileSystem.getInstance().refreshAndFindFileByIoFile(properties);
        FileEditorManager.getInstance(project).openFile(propertiesFile, false);
        
        VirtualFile jspFile = LocalFileSystem.getInstance().refreshAndFindFileByIoFile(jsp);
        FileEditorManager.getInstance(project).openFile(jspFile, true);

        //TODO: expand new folder in project explorer
    }
}

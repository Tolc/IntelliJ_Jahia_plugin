package fr.tolc.jahia.intellij.plugin.cnd.quickfixes;

import com.intellij.codeInsight.FileModificationService;
import com.intellij.codeInsight.intention.impl.BaseIntentionAction;
import com.intellij.ide.projectView.ProjectView;
import com.intellij.lang.properties.IProperty;
import com.intellij.lang.properties.PropertiesFileType;
import com.intellij.lang.properties.editor.ResourceBundleAsVirtualFile;
import com.intellij.lang.properties.psi.PropertiesElementFactory;
import com.intellij.lang.properties.psi.PropertiesFile;
import com.intellij.lang.properties.references.I18nUtil;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.CommandProcessor;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.pom.Navigatable;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.IncorrectOperationException;
import com.intellij.util.indexing.FileBasedIndex;
import fr.tolc.jahia.intellij.plugin.cnd.CndFileType;
import fr.tolc.jahia.intellij.plugin.cnd.CndTranslationUtil;
import fr.tolc.jahia.intellij.plugin.cnd.CndUtil;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndNodeType;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndProperty;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CreateNodeTypeTranslationsQuickFix extends BaseIntentionAction {

    private String jahiaWorkFolderPath;
    private String namespace;
    private String nodeTypeName;

    public CreateNodeTypeTranslationsQuickFix(String jahiaWorkFolderPath, String namespace, String nodeTypeName) {
        this.jahiaWorkFolderPath = jahiaWorkFolderPath;
        this.namespace = namespace;
        this.nodeTypeName = nodeTypeName;
    }

    @NotNull
    @Override
    public String getText() {
        return "Add translations in resource bundle";
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
                Collection<VirtualFile> virtualFiles = FileBasedIndex.getInstance().getContainingFiles(FileTypeIndex.NAME, PropertiesFileType.INSTANCE, GlobalSearchScope.allScope(project));
                if (virtualFiles.size() == 1) {
                    VirtualFile virtualFile = virtualFiles.iterator().next();
                    PropertiesFile propertiesFile = (PropertiesFile) PsiManager.getInstance(project).findFile(virtualFile);
                    createNodeTypeTranslations(project, propertiesFile);
                } else {
                    final FileChooserDescriptor descriptor = FileChooserDescriptorFactory.createSingleFileDescriptor(PropertiesFileType.INSTANCE);
                    descriptor.setRoots(project.getBaseDir());
                    final VirtualFile file = FileChooser.chooseFile(descriptor, project, null);
                    if (file != null) {
                        PropertiesFile propertiesFile = (PropertiesFile) PsiManager.getInstance(project).findFile(file);
                        createNodeTypeTranslations(project, propertiesFile);
                    }
                }
            }
        });
    }

    private void createNodeTypeTranslations(final Project project, final PropertiesFile file) {
        String key = CndTranslationUtil.convertNodeTypeIdentifierToPropertyName(namespace, nodeTypeName);

        ApplicationManager.getApplication().runWriteAction(new Runnable() {
            @Override
            public void run() {
                CommandProcessor.getInstance().executeCommand(project, new Runnable() {
                    @Override
                    public void run() {
                        IProperty nodeTypeProperty = file.addProperty(key, "");
//                        I18nUtil.createProperty(project, fileList, key, "");

                        CndNodeType nodeType = CndUtil.findNodeType(project, namespace, nodeTypeName);
                        if (nodeType != null) {
                            List<CndProperty> cndProperties = nodeType.getPropertyList();
                            for (CndProperty cndProperty : cndProperties) {
                                String propertyKey = CndTranslationUtil.convertNodeTypePropertyNameToPropertyName(namespace, nodeTypeName, cndProperty.getPropertyName());
                                file.addProperty(propertyKey, "");
                            }
                        }

                        VirtualFile propertiesFile = file.getContainingFile().getVirtualFile();

                        //Open file in editor
                        FileEditorManager.getInstance(project).openFile(propertiesFile, false);

                        //Expand folder in Project view
                        ProjectView.getInstance(project).select(null, propertiesFile, false);

                        //Caret at the end of node type property
                        ((Navigatable) nodeTypeProperty.getPsiElement().getLastChild().getNavigationElement()).navigate(true);
                        FileEditorManager.getInstance(project).getSelectedTextEditor().getCaretModel().moveCaretRelatively(1, 0, false, false, false);
                    }
                }, "add translations for node type " + namespace + ":" + nodeTypeName, project);
            }
        });
    }
}

package fr.tolc.jahia.intellij.plugin.cnd.quickfixes;

import com.intellij.codeInsight.intention.impl.BaseIntentionAction;
import com.intellij.ide.projectView.ProjectView;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.pom.Navigatable;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.util.IncorrectOperationException;
import fr.tolc.jahia.intellij.plugin.cnd.dialogs.CreateNodeTypeViewDialog;
import fr.tolc.jahia.intellij.plugin.cnd.enums.PropertyTypeEnum;
import fr.tolc.jahia.intellij.plugin.cnd.model.NodeTypeModel;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndNodeType;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndProperty;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndSubNode;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndSubNodeType;
import fr.tolc.jahia.intellij.plugin.cnd.utils.CndPluginUtil;
import fr.tolc.jahia.intellij.plugin.cnd.utils.CndProjectFilesUtil;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Set;

public class CreateNodeTypeViewQuickFix extends BaseIntentionAction {

    private final Module module;
    private final String namespace;
    private final String nodeTypeName;
    private final CndNodeType cndNodeType;

    public CreateNodeTypeViewQuickFix(Module module, CndNodeType cndNodeType) {
        this.module = module;
        this.namespace = cndNodeType.getNodeTypeNamespace();
        this.nodeTypeName = cndNodeType.getNodeTypeName();
        this.cndNodeType = cndNodeType;
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
                CreateNodeTypeViewDialog createNodeTypeViewDialog = new CreateNodeTypeViewDialog(project, module, namespace + ":" + nodeTypeName);
                createNodeTypeViewDialog.setVisible(true);

                if (createNodeTypeViewDialog.isOkClicked()) {
                    String viewName = createNodeTypeViewDialog.getViewName();
                    boolean isHiddenView = createNodeTypeViewDialog.isHiddenView();
                    String viewType = createNodeTypeViewDialog.getViewType();
                    String viewLanguage = createNodeTypeViewDialog.getViewLanguage();
                    Module module = createNodeTypeViewDialog.getModule();

                    if (StringUtils.isNotBlank(viewType) && StringUtils.isNotBlank(viewLanguage)) {
                        String jahiaWorkFolderPath = CndProjectFilesUtil.getJahiaWorkFolderPath(module);
                        String finalDirectory = CndProjectFilesUtil.getNodeTypeViewsFolderPath(jahiaWorkFolderPath, namespace, nodeTypeName, viewType);
                        String fileName = CndProjectFilesUtil.getNodeTypeViewFileName(nodeTypeName, viewName, viewLanguage, isHiddenView);
                        String propertiesFileName = CndProjectFilesUtil.getNodeTypeViewFileName(nodeTypeName, viewName, "properties", isHiddenView);

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
                    defaultViewPath = CndPluginUtil.getPluginFilePath("default/view.jsp");
                } else {
                    defaultViewPath = CndPluginUtil.getPluginFilePath("default/view.default");
                }
                Files.copy(defaultViewPath, viewFile.toPath());
                
                if (viewFileName.endsWith(".jsp")) {
                    appendAvailableResources(viewFile);
                }
            }

            if (!properties.exists()) {
                Path defaultPropertiesPath = CndPluginUtil.getPluginFilePath("default/view.properties");
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

        //Caret at the end of the file
        ((Navigatable) PsiManager.getInstance(project).findFile(viewVirtualFile).getLastChild().getLastChild().getLastChild().getNavigationElement()).navigate(true);
        CaretModel caretModel = FileEditorManager.getInstance(project).getSelectedTextEditor().getCaretModel();
        caretModel.moveCaretRelatively(-caretModel.getLogicalPosition().column, 2, false, false, false);
    }


    private static final String NAME = "##name##";
    private static final String VAR_NAME = "##varName##";
    private static final String ACCESSOR = "##accessor##";
    private static final String NODE_TYPE = "##nodeType##";

    private static final String PROPERTY_TEMPLATE = "<c:set var=\"" + VAR_NAME + "\" value=\"${currentNode.properties['" + NAME + "']." + ACCESSOR + "}\"/>\r\n";
    private static final String PROPERTY_MULTIPLE_TEMPLATE = "<c:set var=\"" + VAR_NAME + "\" value=\"${currentNode.properties['" + NAME + "']}\"/>\r\n";
    private static final String SUBNODES_TEMPLATE = "<c:set var=\"" + VAR_NAME + "\" value=\"${jcr:getNodes(currentNode, '" + NODE_TYPE + "')}\"/>\r\n";
    private static final String SUBNODE_TEMPLATE  = "<jcr:node var=\"" + VAR_NAME + "\" path=\"${currentNode.path}/" + NAME + "\"/>\r\n";

    private static final String PROPERTY_LOOP_TEMPLATE  = "<c:forEach items=\"${" + VAR_NAME + "}\" var=\"item\">\r\n\t${item." + ACCESSOR + "}\r\n</c:forEach>\r\n";
    private static final String SUBNODE_LOOP_TEMPLATE  = "<c:forEach items=\"${" + VAR_NAME + "}\" var=\"node\">\r\n\t<template:module node=\"${node}\"/>\r\n</c:forEach>\r\n";

    private void appendAvailableResources(File viewFile) throws IOException {
        StringBuilder toAppend = new StringBuilder();
        StringBuilder toAppendLoops = new StringBuilder();
        
        //Properties
        Set<CndProperty> properties = cndNodeType.getProperties();
        if (!properties.isEmpty()) {
            toAppend.append("\r\n");
            
            for (CndProperty property : properties) {
                PropertyTypeEnum type = property.getType();
                if (type != null) {
                    String accessor = type.getAccessor();
                    if (property.isMultiple()) {
                        String varName = convertToVariableName(property.getPropertyName());
                        toAppend.append(PROPERTY_MULTIPLE_TEMPLATE.replace(VAR_NAME, varName).replace(NAME, property.getPropertyName()));
                        toAppendLoops.append(PROPERTY_LOOP_TEMPLATE.replace(VAR_NAME, varName).replace(ACCESSOR, accessor));
                    } else {
                        if (StringUtils.isNotBlank(accessor) && !"*".equals(property.getPropertyName())) {
                            String varName = convertToVariableName(property.getPropertyName());
                            toAppend.append(PROPERTY_TEMPLATE.replace(VAR_NAME, varName).replace(NAME, property.getPropertyName()).replace(ACCESSOR, accessor));
                        }
                    }
                }
            }
        }

        //Subnodes
        List<CndSubNode> subNodes = cndNodeType.getSubNodeList();
        if (!subNodes.isEmpty()) {
            toAppend.append("\r\n");
            
            for (CndSubNode subNode : subNodes) {
                for (CndSubNodeType subNodeType : subNode.getSubNodeTypeList()) {
                    String subNodeName = subNode.getSubNodeName();
                    if (StringUtils.isNotBlank(subNodeName)) {
                        if ("*".equals(subNodeName)) {
                            String varName = convertNodeTypeToVariableName(subNodeType);
                            toAppend.append(SUBNODES_TEMPLATE.replace(VAR_NAME, varName).replace(NODE_TYPE, subNodeType.getText()));
                            toAppendLoops.append(SUBNODE_LOOP_TEMPLATE.replace(VAR_NAME, varName));
                        } else {
                            String varName = convertToVariableName(subNodeName);
                            toAppend.append(SUBNODE_TEMPLATE.replace(VAR_NAME, varName).replace(NAME, subNodeName));
                        }
                    }
                }
            }
        }
        
        
        //Loops
        if (toAppendLoops.length() > 0) {
            toAppend.append("\r\n");
            toAppend.append("\r\n");
            toAppend.append(toAppendLoops);
        }
        
        toAppend.append("\r\n");
        Files.write(viewFile.toPath(), toAppend.toString().getBytes(), StandardOpenOption.APPEND);
    }
    
    private String convertToVariableName(String propertyName) {
        if (propertyName.contains(":")) {
            return propertyName.split(":")[1];
        }
        return propertyName;
    }
    private String convertNodeTypeToVariableName(CndSubNodeType subNodeType) {
        NodeTypeModel model = new NodeTypeModel(subNodeType.getText());
        if (model.getNodeTypeName().endsWith("y")) {
            return model.getNodeTypeName().substring(0, model.getNodeTypeName().length() - 1) + "ies";
        }
        return model.getNodeTypeName() + "s";
    }
}

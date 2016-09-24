package fr.tolc.jahia.intellij.plugin.cnd;

import java.util.Collection;

import com.intellij.codeInsight.intention.impl.BaseIntentionAction;
import com.intellij.lang.ASTNode;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.pom.Navigatable;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.IncorrectOperationException;
import com.intellij.util.indexing.FileBasedIndex;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndElementFactory;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndFile;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndNodeType;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndTypes;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

public class CreateNodeTypeQuickFix extends BaseIntentionAction {

    private String namespace;
    private String nodeTypeName;

    CreateNodeTypeQuickFix(String namespace, String nodeTypeName) {
        this.namespace = namespace;
        this.nodeTypeName = nodeTypeName;
    }

    @NotNull
    @Override
    public String getText() {
        return "Create node type";
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
                Collection<VirtualFile> virtualFiles = FileBasedIndex.getInstance().getContainingFiles(FileTypeIndex.NAME, CndFileType.INSTANCE, GlobalSearchScope.allScope(project));
                if (virtualFiles.size() == 1) {
                    createNodeType(project, virtualFiles.iterator().next());
                } else {
                    final FileChooserDescriptor descriptor = FileChooserDescriptorFactory.createSingleFileDescriptor(CndFileType.INSTANCE);
                    descriptor.setRoots(project.getBaseDir());
                    final VirtualFile file = FileChooser.chooseFile(descriptor, project, null);
                    if (file != null) {
                        createNodeType(project, file);
                    }
                }
            }
        });
    }

    private void createNodeType(final Project project, final VirtualFile file) {
        new WriteCommandAction.Simple(project) {
            @Override
            public void run() {
                CndFile cndFile = (CndFile) PsiManager.getInstance(project).findFile(file);
                ASTNode lastChildNode = cndFile.getNode().getLastChildNode();
                ASTNode beforeLastChildNode = lastChildNode.getTreePrev();

                //There should be an empty line between two node type declarations
                //TODO: fix that
                if (!lastChildNode.getElementType().equals(CndTypes.CRLF)) {
                    cndFile.getNode().addChild(CndElementFactory.createCRLF(project).getNode());
                }
                if (!beforeLastChildNode.getElementType().equals(CndTypes.CRLF)) {
                    cndFile.getNode().addChild(CndElementFactory.createCRLF(project).getNode());
                }
                
                CndNodeType cndNodeType = CndElementFactory.createNodeType(project, nodeTypeName, namespace);
                cndFile.getNode().addChild(cndNodeType.getNode());
                ((Navigatable) cndNodeType.getLastChild().getNavigationElement()).navigate(true);
                FileEditorManager.getInstance(project).getSelectedTextEditor().getCaretModel().moveCaretRelatively(2, 0, false, false, false);

                // almost the same thing but manipulating plain text of the document instead of PSI
                //                FileEditorManager.getInstance(project).openFile(file, true);
                //                final Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();
                //                final Document document = editor.getDocument();
                //                document.insertString(document.getTextLength(), "\n" + key.replaceAll(" ", "\\\\ ") + " = ");
                //                editor.getCaretModel().getPrimaryCaret().moveToOffset(document.getTextLength());
            }
        }.execute();
    }
}

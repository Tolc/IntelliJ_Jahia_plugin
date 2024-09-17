package fr.tolc.jahia.plugin.quickfixes;

import com.intellij.codeInsight.intention.impl.BaseIntentionAction;
import com.intellij.lang.ASTNode;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.pom.Navigatable;
import com.intellij.psi.PsiFile;
import com.intellij.util.IncorrectOperationException;
import fr.tolc.jahia.language.cnd.CndElementFactory;
import fr.tolc.jahia.language.cnd.psi.CndSubnode;
import fr.tolc.jahia.language.cnd.psi.CndSubnodeDefault;
import fr.tolc.jahia.language.cnd.psi.CndTypes;
import org.jetbrains.annotations.NotNull;

public class CndAddSubnodeDefaultValueQuickFix extends BaseIntentionAction {

    private final CndSubnode subnode;

    public CndAddSubnodeDefaultValueQuickFix(CndSubnode subnode) {
        this.subnode = subnode;
    }

    @NotNull
    @Override
    public String getText() {
        return "Add default value to sub node '" + subnode.getSubnodeName().getText() + "'";
    }

    @NotNull
    @Override
    public String getFamilyName() {
        return "Add default value";
    }

    @Override
    public boolean isAvailable(@NotNull Project project, Editor editor, PsiFile file) {
        return true;
    }

    @Override
    public void invoke(@NotNull final Project project, final Editor editor, PsiFile file) throws IncorrectOperationException {
        ApplicationManager.getApplication().invokeLater(() -> {
            createSubnodeDefaultValue(project, editor);
        });
    }

    private void createSubnodeDefaultValue(final Project project, final Editor editor) {
        WriteCommandAction.writeCommandAction(project).run(() -> {
            String firstType = subnode.getSubnodeTypeList().get(0).getText();
            ASTNode subnodeAST = subnode.getNode();

            ASTNode anchor = subnodeAST.findChildByType(CndTypes.SUB_TYPES_END).getTreeNext();

            subnodeAST.addChild(CndElementFactory.createWhiteSpace(project).getNode(), anchor);
            subnodeAST.addChild(CndElementFactory.createSubnodeDefaultEqual(project), anchor);
            subnodeAST.addChild(CndElementFactory.createWhiteSpace(project).getNode(), anchor);
            CndSubnodeDefault subnodeDefault = CndElementFactory.createSubnodeDefault(project, firstType);
            subnodeAST.addChild(subnodeDefault.getNode(), anchor);

            ((Navigatable) subnodeDefault.getLastChild().getNavigationElement()).navigate(true);
            editor.getCaretModel().moveCaretRelatively(firstType.length(), 0, false, false, false);
        });
    }

}

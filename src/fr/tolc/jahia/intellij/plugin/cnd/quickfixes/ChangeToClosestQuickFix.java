package fr.tolc.jahia.intellij.plugin.cnd.quickfixes;

import com.intellij.codeInsight.intention.impl.BaseIntentionAction;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.util.IncorrectOperationException;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

public class ChangeToClosestQuickFix extends BaseIntentionAction {

    private PsiElement element;
    private String[] options;

    public ChangeToClosestQuickFix(PsiElement element, String[] options) {
        this.element = element;
        this.options = options;
    }

    @NotNull
    @Override
    public String getText() {
        return "Change to";
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
                String replacement = getClosest(options, element.getText());

                //TODO: change element text to closest one
//               element.getTextRange().
            }
        });
    }
    
    private static String getClosest(String[] options, String value) {
        String closestOption = options[0];
        int closestDistance = Integer.MAX_VALUE;
        for (String option : options) {
            int distance = StringUtils.getLevenshteinDistance(option, value);
            if (distance < closestDistance) {
                closestDistance = distance;
                closestOption = option;
            }
        }
        return closestOption;
    }
}

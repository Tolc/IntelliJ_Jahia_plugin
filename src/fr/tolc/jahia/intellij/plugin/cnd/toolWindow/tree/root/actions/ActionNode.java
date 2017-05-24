package fr.tolc.jahia.intellij.plugin.cnd.toolWindow.tree.root.actions;

import com.intellij.icons.AllIcons;
import com.intellij.psi.PsiClass;
import fr.tolc.jahia.intellij.plugin.cnd.toolWindow.tree.CndSimpleNode;

public class ActionNode extends CndSimpleNode {
    private final PsiClass psiClass;
    
    public ActionNode(PsiClass psiClass) {
        super(null);
        setIcon(AllIcons.FileTypes.Java);
        this.psiClass = psiClass;
    }

    @Override
    public String getName() {
        return psiClass.getName();
    }

    public PsiClass getPsiClass() {
        return psiClass;
    }
}

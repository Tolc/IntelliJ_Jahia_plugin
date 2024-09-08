package fr.tolc.jahia.language.cnd.psi.mixins;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import fr.tolc.jahia.language.cnd.psi.CndNodetype;
import fr.tolc.jahia.language.cnd.psi.CndTypes;
import org.jetbrains.annotations.NotNull;

public abstract class CndNodetypeMixin extends ASTWrapperPsiElement implements CndNodetype {

    public CndNodetypeMixin(@NotNull ASTNode node) {
        super(node);
    }

    public String getIdentifier() {
        ASTNode ntName = this.getNode().findChildByType(CndTypes.NT_NAME);
        if (ntName != null) {
            return ntName.getText().trim();
        }
        return null;
    }
}

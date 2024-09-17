package fr.tolc.jahia.language.cnd.psi.mixins;

import com.intellij.lang.ASTNode;
import fr.tolc.jahia.language.cnd.psi.CndReferencialElement;
import fr.tolc.jahia.language.cnd.psi.CndSupertype;
import org.jetbrains.annotations.NotNull;

public class CndSupertypeMixin extends CndReferencialElement implements CndSupertype {

    public CndSupertypeMixin(@NotNull ASTNode node) {
        super(node);
    }
}

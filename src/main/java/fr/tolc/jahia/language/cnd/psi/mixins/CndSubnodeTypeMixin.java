package fr.tolc.jahia.language.cnd.psi.mixins;

import com.intellij.lang.ASTNode;
import fr.tolc.jahia.language.cnd.psi.CndReferencialElement;
import fr.tolc.jahia.language.cnd.psi.CndSubnodeType;
import org.jetbrains.annotations.NotNull;

public class CndSubnodeTypeMixin extends CndReferencialElement implements CndSubnodeType {

    public CndSubnodeTypeMixin(@NotNull ASTNode node) {
        super(node);
    }
}

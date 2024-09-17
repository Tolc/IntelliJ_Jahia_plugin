package fr.tolc.jahia.language.cnd.psi.mixins;

import com.intellij.lang.ASTNode;
import fr.tolc.jahia.language.cnd.psi.CndReferencialElement;
import fr.tolc.jahia.language.cnd.psi.CndSubnodeDefault;
import org.jetbrains.annotations.NotNull;

public class CndSubnodeDefaultMixin extends CndReferencialElement implements CndSubnodeDefault {

    public CndSubnodeDefaultMixin(@NotNull ASTNode node) {
        super(node);
    }
}

package fr.tolc.jahia.language.cnd.psi.interfaces;

import fr.tolc.jahia.language.cnd.psi.CndNamedElement;
import org.jetbrains.annotations.NotNull;

public interface CndNamespaceInterface extends CndNamedElement {

    @NotNull String getIdentifier();

    @NotNull String getUri();

}

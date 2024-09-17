package fr.tolc.jahia.language.cnd.psi.interfaces;

import fr.tolc.jahia.language.cnd.psi.CndNamedElement;

public interface CndNodetypeInterface extends CndNamedElement {

    String getIdentifier();

    boolean isMixin();
}

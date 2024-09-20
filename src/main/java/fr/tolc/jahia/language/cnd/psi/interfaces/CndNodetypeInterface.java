package fr.tolc.jahia.language.cnd.psi.interfaces;

import fr.tolc.jahia.language.cnd.psi.CndNamedElement;
import fr.tolc.jahia.language.cnd.psi.CndProperty;
import fr.tolc.jahia.language.cnd.psi.CndSubnode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface CndNodetypeInterface extends CndNamedElement {

    @NotNull String getIdentifier();

    boolean isMixin();

    @Nullable CndProperty getProperty(String name);

    @Nullable CndSubnode getSubnode(String name);
}

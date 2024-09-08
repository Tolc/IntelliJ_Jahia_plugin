package fr.tolc.jahia.language.cnd.psi;

import com.intellij.psi.tree.IElementType;
import fr.tolc.jahia.language.cnd.CndLanguage;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class CndElementType extends IElementType {

    public CndElementType(@NotNull @NonNls String debugName) {
        super(debugName, CndLanguage.INSTANCE);
    }
}

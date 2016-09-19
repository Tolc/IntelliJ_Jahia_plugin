package fr.tolc.jahia.intellij.plugin.cnd.psi;

import com.intellij.psi.tree.IElementType;
import fr.tolc.jahia.intellij.plugin.cnd.CndLanguage;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class CndElementType extends IElementType {
    public CndElementType(@NotNull @NonNls String debugName) {
        super(debugName, CndLanguage.INSTANCE);
    }
}
package fr.tolc.jahia.plugin.icons;

import com.intellij.ide.IconProvider;
import com.intellij.psi.PsiElement;
import fr.tolc.jahia.language.cnd.CndIcons;
import fr.tolc.jahia.language.cnd.psi.CndNodetype;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.Icon;

//TODO: namespaces, properties and subnodes
public class CndIconProvider extends IconProvider {

    @Override
    public @Nullable Icon getIcon(@NotNull PsiElement element, int flags) {
        if (element instanceof CndNodetype nodetype) {
            return nodetype.isMixin() ? CndIcons.CND_MIX : CndIcons.CND_NT;
        }
        return null;
    }
}

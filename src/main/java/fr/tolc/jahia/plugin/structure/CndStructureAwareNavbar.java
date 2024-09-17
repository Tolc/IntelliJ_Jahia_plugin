package fr.tolc.jahia.plugin.structure;

import com.intellij.ide.navigationToolbar.StructureAwareNavBarModelExtension;
import com.intellij.lang.Language;
import fr.tolc.jahia.language.cnd.CndIcons;
import fr.tolc.jahia.language.cnd.CndLanguage;
import fr.tolc.jahia.language.cnd.psi.CndFile;
import fr.tolc.jahia.language.cnd.psi.CndNamespace;
import fr.tolc.jahia.language.cnd.psi.CndNodetype;
import fr.tolc.jahia.language.cnd.psi.CndProperty;
import fr.tolc.jahia.language.cnd.psi.CndSubnode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.Icon;

//TODO: PsiNamedElement for namespace, property and subnodes
public class CndStructureAwareNavbar extends StructureAwareNavBarModelExtension {

    @Override
    protected @NotNull Language getLanguage() {
        return CndLanguage.INSTANCE;
    }

    @Override
    public @Nullable String getPresentableText(Object object) {
        if (object instanceof CndFile cndFile) {
            return cndFile.getName();
        }
//        if (object instanceof CndNamespace namespace) {
//            return namespace.getName();
//        }
        if (object instanceof CndNodetype nodetype) {
            return nodetype.getName();
        }
//        if (object instanceof CndProperty property) {
//            return property.getName();
//        }
//        if (object instanceof CndSubnode subnode) {
//            return subnode.getName();
//        }

        return null;
    }

    @Override
    public @Nullable Icon getIcon(Object object) {
        if (object instanceof CndFile) {
            return CndIcons.CND_FILE;
        }
        if (object instanceof CndNamespace) {
            return CndIcons.CND_NS;
        }
        if (object instanceof CndNodetype nodetype) {
            return nodetype.isMixin() ? CndIcons.CND_MIX : CndIcons.CND_NT;
        }
        if (object instanceof CndProperty) {
            return CndIcons.CND_PROP;
        }
        if (object instanceof CndSubnode) {
            return CndIcons.CND_PROP; //TODO: icon for subnodes
        }

        return null;
    }
}

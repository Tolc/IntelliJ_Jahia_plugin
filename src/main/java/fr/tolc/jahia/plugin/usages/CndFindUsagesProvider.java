package fr.tolc.jahia.plugin.usages;

import com.intellij.lang.cacheBuilder.DefaultWordsScanner;
import com.intellij.lang.cacheBuilder.WordsScanner;
import com.intellij.lang.findUsages.FindUsagesProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.tree.TokenSet;
import fr.tolc.jahia.language.cnd.CndLexerAdapter;
import fr.tolc.jahia.language.cnd.psi.CndNamespaceIdentifier;
import fr.tolc.jahia.language.cnd.psi.CndNodetypeIdentifier;
import fr.tolc.jahia.language.cnd.psi.CndPropertyName;
import fr.tolc.jahia.language.cnd.psi.CndSubnodeName;
import fr.tolc.jahia.language.cnd.psi.CndTokenSets;
import fr.tolc.jahia.plugin.messages.CndBundle;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

//TODO: make it work by making other Identifiers PSiNamedElements (like CndNodetypeIdentifier)
// and then hope
public class CndFindUsagesProvider implements FindUsagesProvider {

    @Override
    public @Nullable WordsScanner getWordsScanner() {
        return new DefaultWordsScanner(new CndLexerAdapter(),
                CndTokenSets.IDENTIFIERS,
                CndTokenSets.COMMENTS,
                TokenSet.EMPTY
        );
    }

    @Override
    public boolean canFindUsagesFor(@NotNull PsiElement psiElement) {
        return psiElement instanceof PsiNamedElement;
    }

    @Override
    public @Nullable @NonNls String getHelpId(@NotNull PsiElement psiElement) {
        return null;
    }

    @Override
    public @Nls @NotNull String getType(@NotNull PsiElement element) {
        if (element instanceof CndNamespaceIdentifier) {
            return CndBundle.message("cnd.namespace");
        } else if (element instanceof CndNodetypeIdentifier) {
            return CndBundle.message("cnd.nodetype");
        } else if (element instanceof CndPropertyName) {
            return CndBundle.message("cnd.property");
        } else if (element instanceof CndSubnodeName) {
            return CndBundle.message("cnd.subnode");
        }
        return StringUtils.EMPTY;
    }

    @Override
    public @Nls @NotNull String getDescriptiveName(@NotNull PsiElement element) {
        if (element instanceof CndNamespaceIdentifier namespaceIdentifier) {
            return namespaceIdentifier.getText();
        } else if (element instanceof CndNodetypeIdentifier nodetypeIdentifier) {
            return nodetypeIdentifier.getText();
        } else if (element instanceof CndPropertyName propertyName) {
            return propertyName.getText();
        } else if (element instanceof CndSubnodeName subnodeName) {
            return subnodeName.getText();
        }
        return StringUtils.EMPTY;
    }

    @Override
    public @Nls @NotNull String getNodeText(@NotNull PsiElement element, boolean useFullName) {
        //TODO
        if (element instanceof CndNamespaceIdentifier namespaceIdentifier) {
            return namespaceIdentifier.getParent().getText();
        } else if (element instanceof CndNodetypeIdentifier nodetypeIdentifier) {
            return nodetypeIdentifier.getParent().getText();
        } else if (element instanceof CndPropertyName propertyName) {
            return propertyName.getParent().getText();
        } else if (element instanceof CndSubnodeName subnodeName) {
            return subnodeName.getParent().getText();
        }
        return StringUtils.EMPTY;
    }
}

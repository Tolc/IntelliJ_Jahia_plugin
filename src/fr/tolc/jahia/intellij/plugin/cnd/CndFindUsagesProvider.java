package fr.tolc.jahia.intellij.plugin.cnd;

import com.intellij.lang.cacheBuilder.DefaultWordsScanner;
import com.intellij.lang.cacheBuilder.WordsScanner;
import com.intellij.lang.findUsages.FindUsagesProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.tree.TokenSet;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndNamespace;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndNodeType;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CndFindUsagesProvider implements FindUsagesProvider {
    @Nullable
    @Override
    public WordsScanner getWordsScanner() {
        return new DefaultWordsScanner(
                new CndLexerAdapter(),
                TokenSet.create(CndTypes.NODE_TYPE, CndTypes.NAMESPACE),
                TokenSet.create(CndTypes.COMMENT),
                TokenSet.EMPTY
                );
    }

    @Override
    public boolean canFindUsagesFor(@NotNull PsiElement psiElement) {
        return psiElement instanceof PsiNamedElement;
    }

    @Nullable
    @Override
    public String getHelpId(@NotNull PsiElement psiElement) {
        return null;
    }

    @NotNull
    @Override
    public String getType(@NotNull PsiElement element) {
        if (element instanceof CndNodeType) {
            return "Node type";
        } else if (element instanceof CndNamespace) {
            return "Namespace";
        }
        return "";
    }

    @NotNull
    @Override
    public String getDescriptiveName(@NotNull PsiElement element) {
        if (element instanceof CndNodeType) {
            return ((CndNodeType) element).getNodeTypeName();
        } else if (element instanceof CndNamespace) {
            return ((CndNamespace) element).getNamespaceName();
        }
        return "";
    }

    @NotNull
    @Override
    public String getNodeText(@NotNull PsiElement element, boolean useFullName) {
        if (element instanceof CndNodeType) {
            return ((CndNodeType) element).getNodeTypeNamespace() + ":" + ((CndNodeType) element).getNodeTypeName();
        } else if (element instanceof CndNamespace) {
            return ((CndNamespace) element).getNamespaceName() + " = '" + ((CndNamespace) element).getNamespaceURI() + "'";
        }
        return "";
    }
}

package fr.tolc.jahia.intellij.plugin.cnd;

import com.intellij.lang.cacheBuilder.DefaultWordsScanner;
import com.intellij.lang.cacheBuilder.WordsScanner;
import com.intellij.lang.findUsages.FindUsagesProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.tree.TokenSet;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndNamespaceIdentifier;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndNodeTypeIdentifier;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndPropertyIdentifier;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CndFindUsagesProvider implements FindUsagesProvider {
    @Nullable
    @Override
    public WordsScanner getWordsScanner() {
        return new DefaultWordsScanner(
                new CndLexerAdapter(),
                TokenSet.EMPTY,
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
        if (element instanceof CndNodeTypeIdentifier) {
            return "Node type";
        } else if (element instanceof CndNamespaceIdentifier) {
            return "Namespace";
        } else if (element instanceof CndPropertyIdentifier) {
            return "Property";
        }
        return "";
    }

    @NotNull
    @Override
    public String getDescriptiveName(@NotNull PsiElement element) {
        if (element instanceof CndNodeTypeIdentifier) {
            CndNodeTypeIdentifier id = (CndNodeTypeIdentifier) element;
            return id.getNodeType().getNodeTypeNamespace() + ":" + id.getNodeTypeName();
        } else if (element instanceof CndNamespaceIdentifier) {
            return ((CndNamespaceIdentifier) element).getNamespaceName();
        } else if (element instanceof CndPropertyIdentifier) {
            return ((CndPropertyIdentifier) element).getPropertyName();
        }        
        return "";
    }

    @NotNull
    @Override
    public String getNodeText(@NotNull PsiElement element, boolean useFullName) {
        if (element instanceof CndNodeTypeIdentifier) {
            CndNodeTypeIdentifier id = (CndNodeTypeIdentifier) element;
            return id.getNodeType().getNodeTypeNamespace() + ":" + id.getNodeTypeName();
        } else if (element instanceof CndNamespaceIdentifier) {
            CndNamespaceIdentifier id = (CndNamespaceIdentifier) element;
            return id.getNamespaceName() + " = '" + id.getNamespace().getNamespaceURI() + "'";
        }else if (element instanceof CndPropertyIdentifier) {
            CndPropertyIdentifier id = (CndPropertyIdentifier) element;
            return id.getPropertyName() + " (" + id.getProperty().getType() + ", " + id.getProperty().getTypeMask() +  ")";
        }
        return "";
    }
}

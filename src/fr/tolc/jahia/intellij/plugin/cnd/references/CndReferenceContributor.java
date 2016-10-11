package fr.tolc.jahia.intellij.plugin.cnd.references;

import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiLiteralExpression;
import com.intellij.psi.PsiReferenceContributor;
import com.intellij.psi.PsiReferenceRegistrar;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndTypes;
import fr.tolc.jahia.intellij.plugin.cnd.references.CndReferenceProvider;
import org.jetbrains.annotations.NotNull;

public class CndReferenceContributor extends PsiReferenceContributor {


    @Override
    public void registerReferenceProviders(@NotNull PsiReferenceRegistrar registrar) {
        CndReferenceProvider cndReferenceProvider = new CndReferenceProvider();
        registrar.registerReferenceProvider(PlatformPatterns.psiElement(PsiLiteralExpression.class),        cndReferenceProvider);

        registrar.registerReferenceProvider(PlatformPatterns.psiElement(CndTypes.SUPER_TYPE),               cndReferenceProvider);
        registrar.registerReferenceProvider(PlatformPatterns.psiElement(CndTypes.EXTENSION),                cndReferenceProvider);
        registrar.registerReferenceProvider(PlatformPatterns.psiElement(CndTypes.SUB_NODE_TYPE),            cndReferenceProvider);
        registrar.registerReferenceProvider(PlatformPatterns.psiElement(CndTypes.SUB_NODE_DEFAULT_TYPE),    cndReferenceProvider);
    }
}

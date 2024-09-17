package fr.tolc.jahia.plugin.references;

import com.intellij.openapi.util.TextRange;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceContributor;
import com.intellij.psi.PsiReferenceProvider;
import com.intellij.psi.PsiReferenceRegistrar;
import com.intellij.util.ProcessingContext;
import fr.tolc.jahia.language.cnd.psi.CndTypes;
import fr.tolc.jahia.plugin.references.types.NodetypeReference;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CndReferenceContributor extends PsiReferenceContributor {
    private static final Logger logger = LoggerFactory.getLogger(CndReferenceContributor.class);

    @Override
    public void registerReferenceProviders(@NotNull PsiReferenceRegistrar registrar) {
        registrar.registerReferenceProvider(
                PlatformPatterns.or(
                        PlatformPatterns.psiElement(CndTypes.SUPERTYPE),
                        PlatformPatterns.psiElement(CndTypes.SUBNODE_TYPE),
                        PlatformPatterns.psiElement(CndTypes.SUBNODE_DEFAULT)
                ),
                new PsiReferenceProvider() {
                    @Override
                    public PsiReference @NotNull [] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext context) {
                        PsiReference[] refs = new PsiReference[1];
                        refs[0] = new NodetypeReference(element, new TextRange(0, element.getTextLength()));
                        return refs;
                    }
                }
        );
    }
}

package fr.tolc.jahia.intellij.plugin.cnd.references;

import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiReferenceContributor;
import com.intellij.psi.PsiReferenceRegistrar;
import com.intellij.psi.xml.XmlElementType;
import org.jetbrains.annotations.NotNull;

public class ResourceReferenceContributor extends PsiReferenceContributor {


    @Override
    public void registerReferenceProviders(@NotNull PsiReferenceRegistrar registrar) {
        ResourceReferenceProvider resourceReferenceProvider = new ResourceReferenceProvider();

        registrar.registerReferenceProvider(PlatformPatterns.psiElement(XmlElementType.XML_ATTRIBUTE_VALUE), resourceReferenceProvider);  //XML/JSP
    }
}

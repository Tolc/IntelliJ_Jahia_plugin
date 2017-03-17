package fr.tolc.jahia.intellij.plugin.cnd.references;

import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiReferenceContributor;
import com.intellij.psi.PsiReferenceRegistrar;
import com.intellij.psi.xml.XmlElementType;
import org.jetbrains.annotations.NotNull;

public class ViewReferenceContributor extends PsiReferenceContributor {


    @Override
    public void registerReferenceProviders(@NotNull PsiReferenceRegistrar registrar) {
        ViewReferenceProvider viewReferenceProvider = new ViewReferenceProvider();

        registrar.registerReferenceProvider(PlatformPatterns.psiElement(XmlElementType.XML_ATTRIBUTE_VALUE), viewReferenceProvider);  //XML/JSP
    }
}

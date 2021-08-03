package fr.tolc.jahia.intellij.plugin.cnd.references;

import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiReferenceContributor;
import com.intellij.psi.PsiReferenceRegistrar;
import com.intellij.psi.jsp.el.ELElementType;
import com.intellij.psi.jsp.el.ELElementTypes;
import com.intellij.psi.xml.XmlElementType;
import org.jetbrains.annotations.NotNull;

public class CndJspReferenceContributor extends PsiReferenceContributor {


    @Override
    public void registerReferenceProviders(@NotNull PsiReferenceRegistrar registrar) {
        CndJspReferenceProvider cndJspReferenceProvider = new CndJspReferenceProvider();

        registrar.registerReferenceProvider(PlatformPatterns.psiElement(XmlElementType.XML_ATTRIBUTE_VALUE),      cndJspReferenceProvider);  //XML Attribute value
        registrar.registerReferenceProvider(PlatformPatterns.psiElement(ELElementType.JSP_EL_VARIABLE),      cndJspReferenceProvider);  //JSP El variable
        registrar.registerReferenceProvider(PlatformPatterns.psiElement(ELElementTypes.EL_VARIABLE),      cndJspReferenceProvider);  //JSP El variable
        registrar.registerReferenceProvider(PlatformPatterns.psiElement(ELElementType.JSP_EL_LITERAL),      cndJspReferenceProvider);  //JSP El Literal expression
        registrar.registerReferenceProvider(PlatformPatterns.psiElement(ELElementTypes.EL_LITERAL_EXPRESSION),      cndJspReferenceProvider);  //JSP El Literal expression
    }
}

package fr.tolc.jahia.intellij.plugin.cnd.references;

import com.intellij.lang.properties.parsing.PropertiesTokenTypes;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiLiteralExpression;
import com.intellij.psi.PsiReferenceContributor;
import com.intellij.psi.PsiReferenceRegistrar;
import com.intellij.psi.xml.XmlElementType;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndTypes;
import org.jetbrains.annotations.NotNull;

public class CndReferenceContributor extends PsiReferenceContributor {


    @Override
    public void registerReferenceProviders(@NotNull PsiReferenceRegistrar registrar) {
        CndReferenceProvider cndReferenceProvider = new CndReferenceProvider();
        registrar.registerReferenceProvider(PlatformPatterns.psiElement(PsiLiteralExpression.class),        cndReferenceProvider);  //Java

        registrar.registerReferenceProvider(PlatformPatterns.psiElement(CndTypes.SUPER_TYPE),               cndReferenceProvider);  //Cnd super types
        registrar.registerReferenceProvider(PlatformPatterns.psiElement(CndTypes.EXTENSION),                cndReferenceProvider);  //Cnd extends types
        registrar.registerReferenceProvider(PlatformPatterns.psiElement(CndTypes.SUB_NODE_TYPE),            cndReferenceProvider);  //Cnd subnode types
        registrar.registerReferenceProvider(PlatformPatterns.psiElement(CndTypes.SUB_NODE_DEFAULT_TYPE),    cndReferenceProvider);  //Cnd subnode default type

//        registrar.registerReferenceProvider(PlatformPatterns.psiElement(XmlElementType.XML_ATTRIBUTE_VALUE),      cndReferenceProvider);  //XML Attribute value
        registrar.registerReferenceProvider(PlatformPatterns.psiElement(XmlElementType.XML_DATA_CHARACTERS),      cndReferenceProvider);  //XML Text
        
        registrar.registerReferenceProvider(PlatformPatterns.psiElement(PropertiesTokenTypes.KEY_CHARACTERS),    cndReferenceProvider);  //Properties
    }
}

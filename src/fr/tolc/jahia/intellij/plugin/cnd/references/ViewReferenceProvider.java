package fr.tolc.jahia.intellij.plugin.cnd.references;

import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceProvider;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlAttributeValue;
import com.intellij.psi.xml.XmlTag;
import com.intellij.util.ProcessingContext;
import fr.tolc.jahia.intellij.plugin.cnd.model.ViewModel;
import fr.tolc.jahia.intellij.plugin.cnd.references.types.ViewIncludeReference;
import fr.tolc.jahia.intellij.plugin.cnd.references.types.ViewModuleReference;
import fr.tolc.jahia.intellij.plugin.cnd.utils.CndProjectFilesUtil;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ViewReferenceProvider extends PsiReferenceProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(ViewReferenceProvider.class);
    
    public static final String TAG_ATTRIBUTE_VIEW = "view";
    public static final String TAG_ATTRIBUTE_TEMPLATETYPE = "templateType";
    public static final String TAG_INCLUDE = "include";
    public static final String TAG_MODULE = "module";
    public static final String TAGLIB_TEMPLATE_NAMESPACE = "http://www.jahia.org/tags/templateLib";

    @NotNull
    @Override
    public PsiReference[] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext context) {
        if (element instanceof XmlAttributeValue) {    
            String value = ((XmlAttributeValue) element).getValue();

            VirtualFile virtualFile = element.getContainingFile().getVirtualFile();
            if (virtualFile != null) {  //Not a file just in memory
                
                ViewModel viewModel = getViewModelFromJspTagAttributeValue((XmlAttributeValue) element, virtualFile);
                if (viewModel != null) {
                    String localName = viewModel.getTagName();
                    if (TAG_INCLUDE.equals(localName)) {
                        LOGGER.debug("template:include view found");
                        return new PsiReference[] {
                                //Text ranges here are relative!!
                                new ViewIncludeReference(element, new TextRange(1, value.length() + 1), viewModel),
                        };
                    } else if (TAG_MODULE.equals(localName)) {
                        LOGGER.debug("template:module view found");
                        return new PsiReference[] {
                                //Text ranges here are relative!!
                                new ViewModuleReference(element, new TextRange(1, value.length() + 1), viewModel),
                        };
                    }
                }
            }
        }
        return new PsiReference[0];
    }
    
    @Nullable
    public static ViewModel getViewModelFromJspTagAttributeValue(XmlAttributeValue attributeValue, VirtualFile virtualFile) {
        String value = attributeValue.getValue();
        
        PsiElement xmlAttribute = attributeValue.getParent();
        if (xmlAttribute instanceof XmlAttribute) {
            String attributeName = ((XmlAttribute) xmlAttribute).getName();
            if (TAG_ATTRIBUTE_VIEW.equals(attributeName)) {
                PsiElement xmlTag = xmlAttribute.getParent();
                if (xmlTag instanceof XmlTag) {
                    String tagNamespace = ((XmlTag) xmlTag).getNamespace();
                    if (TAGLIB_TEMPLATE_NAMESPACE.equals(tagNamespace)) {
                        String localName = ((XmlTag) xmlTag).getLocalName();
                        if (TAG_INCLUDE.equals(localName) || TAG_MODULE.equals(localName)) {
                            ViewModel viewModel = CndProjectFilesUtil.getViewModelFromPotentialViewFile(virtualFile);
                            if (viewModel != null) {
                                viewModel.setName(value);
                                viewModel.setLanguage(null);    //Language normally does not interfere, but just in case
                                viewModel.setTagName(localName);

                                //'templateType' attribute
                                XmlAttribute templateTypeAttribute = ((XmlTag) xmlTag).getAttribute(TAG_ATTRIBUTE_TEMPLATETYPE);
                                if (templateTypeAttribute != null) {
                                    String templateType = templateTypeAttribute.getValue();
                                    if (StringUtils.isNotBlank(templateType)) {
                                        viewModel.setType(templateType);
                                    }
                                }
                                
                                return viewModel;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }
}

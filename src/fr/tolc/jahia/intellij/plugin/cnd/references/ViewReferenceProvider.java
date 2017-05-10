package fr.tolc.jahia.intellij.plugin.cnd.references;

import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceProvider;
import com.intellij.psi.xml.XmlAttributeValue;
import com.intellij.util.ProcessingContext;
import fr.tolc.jahia.intellij.plugin.cnd.model.ViewModel;
import fr.tolc.jahia.intellij.plugin.cnd.references.types.ViewIncludeReference;
import fr.tolc.jahia.intellij.plugin.cnd.references.types.ViewModuleReference;
import fr.tolc.jahia.intellij.plugin.cnd.utils.JspUtil;
import org.jetbrains.annotations.NotNull;

public class ViewReferenceProvider extends PsiReferenceProvider {
    
    @NotNull
    @Override
    public PsiReference[] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext context) {
        if (element instanceof XmlAttributeValue) {    
            String value = ((XmlAttributeValue) element).getValue();

            VirtualFile virtualFile = element.getContainingFile().getVirtualFile();
            if (virtualFile != null) {  //Not a file just in memory
                
                ViewModel viewModel = JspUtil.getViewModelFromJspTagAttributeValue((XmlAttributeValue) element, virtualFile);
                if (viewModel != null) {
                    String localName = viewModel.getTagName();
                    if (JspUtil.TAG_INCLUDE.equals(localName)) {
                        return new PsiReference[] {
                                //Text ranges here are relative!!
                                new ViewIncludeReference(element, new TextRange(1, value.length() + 1), viewModel),
                        };
                    } else if (JspUtil.TAG_MODULE.equals(localName)) {
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
}

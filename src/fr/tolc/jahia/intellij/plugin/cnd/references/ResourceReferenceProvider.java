package fr.tolc.jahia.intellij.plugin.cnd.references;

import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceProvider;
import com.intellij.psi.xml.XmlAttributeValue;
import com.intellij.util.ProcessingContext;
import fr.tolc.jahia.intellij.plugin.cnd.model.ResourcesModel;
import fr.tolc.jahia.intellij.plugin.cnd.references.types.ResourceReference;
import fr.tolc.jahia.intellij.plugin.cnd.utils.JspUtil;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

public class ResourceReferenceProvider extends PsiReferenceProvider {
    
    @NotNull
    @Override
    public PsiReference[] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext context) {
        if (element instanceof XmlAttributeValue) {    
            String value = ((XmlAttributeValue) element).getValue();

            VirtualFile virtualFile = element.getContainingFile().getVirtualFile();
            if (virtualFile != null) {  //Not a file just in memory

                ResourcesModel resourcesModel = JspUtil.getResourcesModelFromJspTagAttributeValue((XmlAttributeValue) element);
                if (resourcesModel != null) {
                    String[] resources = value.split(",");

                    PsiReference[] refs = new PsiReference[resources.length];
                    int length = 0;
                    for (int i = 0;  i < resources.length; i++) {
                        String resource = resources[i];
                        int resourceLength = resource.length();
                        if (StringUtils.isNotBlank(resource)) {
                            int leadingSpaces = resourceLength - resource.replaceAll("^\\s+", "").length();
                            int trailingSpaces = resourceLength - resource.replaceAll("\\s+$", "").length();

                            //Text ranges here are relative!!
                            refs[i] = new ResourceReference(element, new TextRange(length + leadingSpaces + 1, length + resource.length() - trailingSpaces + 1), resourcesModel, resource.trim());
                        }
                        length += resourceLength + 1;
                    }
                    return refs;
                }
            }
        }
        return new PsiReference[0];
    }
}

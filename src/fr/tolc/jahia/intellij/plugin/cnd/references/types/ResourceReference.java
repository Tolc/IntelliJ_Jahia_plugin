package fr.tolc.jahia.intellij.plugin.cnd.references.types;

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementResolveResult;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiPolyVariantReference;
import com.intellij.psi.PsiReferenceBase;
import com.intellij.psi.ResolveResult;
import fr.tolc.jahia.intellij.plugin.cnd.enums.ResourcesTypeEnum;
import fr.tolc.jahia.intellij.plugin.cnd.icons.CndIcons;
import fr.tolc.jahia.intellij.plugin.cnd.model.ResourcesModel;
import fr.tolc.jahia.intellij.plugin.cnd.utils.CndProjectFilesUtil;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResourceReference extends PsiReferenceBase<PsiElement> implements PsiPolyVariantReference {
    private final ResourcesModel resourcesModel;
    private final String resource;

    public ResourceReference(@NotNull PsiElement element, TextRange textRange, ResourcesModel resourcesModel, String resource) {
        super(element, textRange);
        this.resourcesModel = resourcesModel;
        this.resource = resource.trim()
                .replaceAll("^\\$\\{url\\.currentModule}/" + resourcesModel.getType().toString(), "")
                .replaceAll("\\?.+$", "");
    }

    @Nullable
    @Override
    public PsiElement resolve() {
        ResolveResult[] resolveResults = multiResolve(false);
        return resolveResults.length == 1 ? resolveResults[0].getElement() : null;
    }

    @NotNull
    @Override
    public Object[] getVariants() {
        Project project = myElement.getProject();
        Module module = CndProjectFilesUtil.getModuleForFile(project, myElement.getContainingFile().getVirtualFile());
        Map<String, PsiFile> resourcesMap = CndProjectFilesUtil.getResources(module, resourcesModel.getType());
        List<LookupElement> variants = new ArrayList<>();
        for (Map.Entry<String, PsiFile> resource : resourcesMap.entrySet()) {
            variants.add(LookupElementBuilder.create(resource.getValue())
                    .withTypeText(resource.getKey())
                    .withIcon(resourcesModel.getType().equals(ResourcesTypeEnum.CSS)? CndIcons.JAHIA_FOLDER_CSS : CndIcons.JAHIA_FOLDER_JAVASCRIPT));
        }
        return variants.toArray();
    }

    @NotNull
    @Override
    public ResolveResult[] multiResolve(boolean incompleteCode) {
        List<ResolveResult> results = new ArrayList<>();
        if (StringUtils.isNotBlank(resource)) {
            Project project = myElement.getProject();
            Module module = CndProjectFilesUtil.getModuleForFile(project, myElement.getContainingFile().getVirtualFile());
            PsiFile resourceFile = CndProjectFilesUtil.getResource(module, resourcesModel.getType(), resource);
            if (resourceFile != null) {
                results.add(new PsiElementResolveResult(resourceFile));
            }
        }
        return results.toArray(new ResolveResult[results.size()]);
    }
}

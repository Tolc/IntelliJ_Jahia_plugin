package fr.tolc.jahia.intellij.plugin.cnd.references.types;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementResolveResult;
import com.intellij.psi.PsiPolyVariantReference;
import com.intellij.psi.PsiReferenceBase;
import com.intellij.psi.ResolveResult;
import fr.tolc.jahia.intellij.plugin.cnd.icons.CndIcons;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndNodeType;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndProperty;
import fr.tolc.jahia.intellij.plugin.cnd.utils.CndUtil;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CndPropertyIdentifierReference extends PsiReferenceBase<PsiElement> implements PsiPolyVariantReference {
    private String namespace;
    private String nodeType;
    private String propertyName;
    private boolean forPropertiesFile = false;

    public CndPropertyIdentifierReference(@NotNull PsiElement element, TextRange textRange, String propertyName) {
        super(element, textRange);
        this.propertyName = propertyName;
    }
    
    public CndPropertyIdentifierReference(@NotNull PsiElement element, TextRange textRange, String namespace, String nodeType, String propertyName) {
        super(element, textRange);
        this.namespace = namespace;
        this.nodeType = nodeType;
        this.propertyName = propertyName;
    }

    public CndPropertyIdentifierReference(@NotNull PsiElement element, TextRange textRange, String namespace, String nodeType, String propertyName, boolean forPropertiesFile) {
        super(element, textRange);
        this.namespace = namespace;
        this.nodeType = nodeType;
        this.propertyName = propertyName;
        this.forPropertiesFile = forPropertiesFile;
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
        List<LookupElement> variants = new ArrayList<LookupElement>();
        if (nodeType != null) {
            CndNodeType cndNodeType = CndUtil.findNodeType(project, namespace, nodeType);
            if (cndNodeType != null) {
                Set<CndProperty> properties = cndNodeType.getProperties();
                for (final CndProperty property : properties) {
                    if (StringUtils.isNotBlank(property.getPropertyName())) {
                        if (forPropertiesFile) {
                            variants.add(LookupElementBuilder.create(property.getPropertyName().replace(':', '_')).withIcon(CndIcons.PROPERTY).withTypeText(property.getContainingFile().getName()));
                        } else {
                            variants.add(LookupElementBuilder.create(property.getPropertyIdentifier()).withIcon(CndIcons.PROPERTY).withTypeText(property.getContainingFile().getName()));
                        }
                    }
                }
            }
        } else {
            Set<CndProperty> properties = CndUtil.findProperties(project);
            for (final CndProperty property : properties) {
                variants.add(LookupElementBuilder.create(property.getPropertyIdentifier()).withIcon(CndIcons.PROPERTY).withTypeText(property.getContainingFile().getName()));
            }
        }
        return variants.toArray();
    }

    @NotNull
    @Override
    public ResolveResult[] multiResolve(boolean incompleteCode) {
        Project project = myElement.getProject();
        Set<CndProperty> properties = (nodeType != null)? CndUtil.findProperties(project, namespace, nodeType, propertyName) : CndUtil.findProperties(project, propertyName);
        List<ResolveResult> results = new ArrayList<ResolveResult>();
        for (CndProperty property : properties) {
            results.add(new PsiElementResolveResult(property.getPropertyIdentifier()));
        }
        return results.toArray(new ResolveResult[results.size()]);
    }
}

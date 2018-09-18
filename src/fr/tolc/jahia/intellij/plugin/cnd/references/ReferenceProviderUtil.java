package fr.tolc.jahia.intellij.plugin.cnd.references;

import static fr.tolc.jahia.intellij.plugin.cnd.model.NodeTypeModel.nodeTypeGlobalRegex;
import static fr.tolc.jahia.intellij.plugin.cnd.model.PropertyModel.CURRENT_NODE;

import java.util.List;
import java.util.regex.Matcher;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import fr.tolc.jahia.intellij.plugin.cnd.model.NodeTypeModel;
import fr.tolc.jahia.intellij.plugin.cnd.model.ViewModel;
import fr.tolc.jahia.intellij.plugin.cnd.references.types.CndNamespaceIdentifierReference;
import fr.tolc.jahia.intellij.plugin.cnd.references.types.CndNodeTypeIdentifierReference;
import fr.tolc.jahia.intellij.plugin.cnd.references.types.CndPropertyIdentifierReference;
import fr.tolc.jahia.intellij.plugin.cnd.utils.CndProjectFilesUtil;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

public class ReferenceProviderUtil {
    
    private ReferenceProviderUtil() {}

    public static void createPropertyReferences(@NotNull PsiElement element, @NotNull List<PsiReference> psiReferences, String nodeVar, String propertyName, int offset) {
        TextRange propertyRange = new TextRange(offset, offset + propertyName.length());

        if (CURRENT_NODE.equals(nodeVar.trim())) {
            String namespace = null;
            String nodeTypeName = null;

            ViewModel viewModel = CndProjectFilesUtil.getViewModelFromPotentialViewFile(element.getContainingFile().getOriginalFile().getVirtualFile());
            if (viewModel != null) {
                namespace = viewModel.getNodeType().getNamespace();
                nodeTypeName = viewModel.getNodeType().getNodeTypeName();
            }
            //Text ranges here are relative!!
            psiReferences.add(new CndPropertyIdentifierReference(element, propertyRange, namespace, nodeTypeName, propertyName));
        } else {
            //Text ranges here are relative!!
            psiReferences.add(new CndPropertyIdentifierReference(element, propertyRange, propertyName));
        }
    }
    
    public static void createNodeTypeReferences(@NotNull PsiElement element, @NotNull List<PsiReference> psiReferences, String text, int offsetShift) {
        if (StringUtils.isNotBlank(text)) {
            Matcher matcher = nodeTypeGlobalRegex.matcher(text);
            while (matcher.find()) {
                String group = matcher.group();

                NodeTypeModel nodeTypeModel = null;
                try {
                    nodeTypeModel = new NodeTypeModel(group);
                } catch (IllegalArgumentException e) {
                    //Nothing to do
                }

                if (nodeTypeModel != null) {
                    int offset = matcher.start() + offsetShift;
                    String namespace = nodeTypeModel.getNamespace();
                    String nodeTypeName = nodeTypeModel.getNodeTypeName();

                    //Text ranges here are relative!!
                    psiReferences.add(new CndNamespaceIdentifierReference(element, new TextRange(offset, namespace.length() + offset), namespace));
                    psiReferences.add(new CndNodeTypeIdentifierReference(element, new TextRange(namespace.length() + offset + 1, group.length() + offset), namespace, nodeTypeName));
                }
            }
        }
    }
}

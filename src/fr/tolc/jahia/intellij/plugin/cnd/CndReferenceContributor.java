package fr.tolc.jahia.intellij.plugin.cnd;

import com.intellij.openapi.util.TextRange;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiLiteralExpression;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceContributor;
import com.intellij.psi.PsiReferenceProvider;
import com.intellij.psi.PsiReferenceRegistrar;
import com.intellij.util.ProcessingContext;
import fr.tolc.jahia.intellij.plugin.cnd.model.NodeTypeModel;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndSuperType;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndTypes;
import org.jetbrains.annotations.NotNull;

public class CndReferenceContributor extends PsiReferenceContributor {


    @Override
    public void registerReferenceProviders(@NotNull PsiReferenceRegistrar registrar) {
        registrar.registerReferenceProvider(PlatformPatterns.psiElement(PsiLiteralExpression.class),
                new PsiReferenceProvider() {
                    @NotNull
                    @Override
                    public PsiReference[] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext context) {
                        PsiLiteralExpression literalExpression = (PsiLiteralExpression) element;
                        String value = literalExpression.getValue() instanceof String ? (String) literalExpression.getValue() : null;
                        NodeTypeModel nodeTypeModel = CndUtil.getNodeTypeModel(value);
                        
                        if (nodeTypeModel != null) {
                            String namespace = nodeTypeModel.getNamespace();
                            String nodeTypeName = nodeTypeModel.getNodeTypeName();

                            return new PsiReference[]{
                                    //Text range here is relative!!
                                    new CndNamespaceReference(element, new TextRange(1, namespace.length() + 1), namespace),
                                    new CndNodeTypeReference(element, new TextRange(namespace.length() + 2, value.length() + 1), namespace, nodeTypeName)
                            };
                        }
                        return new PsiReference[0];
                    }
                }
        );

        registrar.registerReferenceProvider(PlatformPatterns.psiElement(CndTypes.SUPER_TYPE),
                new PsiReferenceProvider() {
                    @NotNull
                    @Override
                    public PsiReference[] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext context) {
                        CndSuperType superType = (CndSuperType) element;
                        String text = superType.getText();
                        NodeTypeModel nodeTypeModel = CndUtil.getNodeTypeModel(superType.getText());

                        if (nodeTypeModel != null) {
                            String namespace = nodeTypeModel.getNamespace();
                            String nodeTypeName = nodeTypeModel.getNodeTypeName();
                            
                            return new PsiReference[]{
                                    //Text range here is relative!!
                                    new CndNamespaceReference(element, new TextRange(0, namespace.length()), namespace),
                                    new CndNodeTypeReference(element, new TextRange(namespace.length() + 1, text.length()), namespace, nodeTypeName)
                            };
                        }
                        return new PsiReference[0];
                    }
                }
        );
    }
}

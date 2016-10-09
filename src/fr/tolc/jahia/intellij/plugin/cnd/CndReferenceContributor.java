package fr.tolc.jahia.intellij.plugin.cnd;

import com.intellij.openapi.util.TextRange;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.*;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CndReferenceContributor extends PsiReferenceContributor {

    private static final Pattern nodeTypeRegex = Pattern.compile("^[A-Za-z]+" + ":" + "[A-Za-z0-9]+$");

    @Override
    public void registerReferenceProviders(@NotNull PsiReferenceRegistrar registrar) {
        registrar.registerReferenceProvider(PlatformPatterns.psiElement(PsiLiteralExpression.class),
                new PsiReferenceProvider() {
                    @NotNull
                    @Override
                    public PsiReference[] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext context) {
                        PsiLiteralExpression literalExpression = (PsiLiteralExpression) element;
                        String value = literalExpression.getValue() instanceof String ? (String) literalExpression.getValue() : null;

                        if (value != null && value.contains(":")) {
                            Matcher matcher = nodeTypeRegex.matcher(value);
                            if (matcher.matches()) {
                                String[] splitValue = value.split(":");
                                String namespace = splitValue[0];
                                String nodeTypeName = splitValue[1];

                                int offset = element.getTextRange().getStartOffset() + 1; //because of starting "
                                TextRange namespaceRange = new TextRange(offset, offset + namespace.length());
                                TextRange colonRange = new TextRange(offset + namespace.length(), offset + namespace.length() + 1);
                                TextRange nodeTypeNameRange = new TextRange(offset + namespace.length() + 1, element.getTextRange().getEndOffset() - 1); //because of closing "

                                return new PsiReference[]{
                                        //Text range here is relative!!
                                        new CndNamespaceReference(element, new TextRange(1, namespace.length() + 1), namespace),
                                        new CndNodeTypeReference(element, new TextRange(namespace.length() + 2, value.length() + 1), namespace, nodeTypeName)
                                };
                            }
                        }
                        return new PsiReference[0];
                    }
                }
        );
    }
}

package fr.smile.jahia.intellij.plugin.cnd;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.project.Project;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.util.ProcessingContext;
import fr.smile.jahia.intellij.plugin.cnd.psi.CndNamespace;
import fr.smile.jahia.intellij.plugin.cnd.psi.CndNodeType;
import fr.smile.jahia.intellij.plugin.cnd.psi.CndTypes;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CndCompletionContributor extends CompletionContributor {
    public CndCompletionContributor() {
        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement(CndTypes.NAMESPACE_NAME).withLanguage(CndLanguage.INSTANCE),
                new CompletionProvider<CompletionParameters>() {
                    public void addCompletions(@NotNull CompletionParameters parameters,
                                               ProcessingContext context,
                                               @NotNull CompletionResultSet resultSet) {

                        List<CndNamespace> namespaces = CndUtil.findNamespaces(parameters.getPosition().getProject());
                        for (CndNamespace namespace : namespaces) {
                            resultSet.addElement(LookupElementBuilder.create(namespace.getNamespaceName() + ":"));
                        }
                    }
                }
        );

        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement(CndTypes.NODE_TYPE_NAMESPACE).withLanguage(CndLanguage.INSTANCE),
                new CompletionProvider<CompletionParameters>() {
                    public void addCompletions(@NotNull CompletionParameters parameters,
                                               ProcessingContext context,
                                               @NotNull CompletionResultSet resultSet) {

                        List<CndNamespace> namespaces = CndUtil.findNamespaces(parameters.getPosition().getProject());
                        for (CndNamespace namespace : namespaces) {
                            resultSet.addElement(LookupElementBuilder.create(namespace.getNamespaceName() + ":"));
                        }
                    }
                }
        );

        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement(CndTypes.NODE_TYPE_INHERITANCE_NAMESPACE).withLanguage(CndLanguage.INSTANCE),
                new CompletionProvider<CompletionParameters>() {
                    public void addCompletions(@NotNull CompletionParameters parameters,
                                               ProcessingContext context,
                                               @NotNull CompletionResultSet resultSet) {

                        Project project = parameters.getPosition().getProject();
                        List<CndNamespace> namespaces = CndUtil.findNamespaces(project);
                        for (CndNamespace namespace : namespaces) {
                            List<CndNodeType> nodeTypes = CndUtil.findNodeTypes(project, namespace.getNamespaceName());
                            for (CndNodeType nodeType : nodeTypes) {
                                resultSet.addElement(LookupElementBuilder.create(namespace.getNamespaceName() + ":" + nodeType.getNodeTypeName()));
                            }
                        }
                    }
                }
        );
    }
}

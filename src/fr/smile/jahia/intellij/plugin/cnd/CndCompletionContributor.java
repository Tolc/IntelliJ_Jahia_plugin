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
        //Namespaces
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

        //Node types
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

        //Property types
        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement(CndTypes.PROPERTY_TYPE).withLanguage(CndLanguage.INSTANCE),
                new CompletionProvider<CompletionParameters>() {
                    public void addCompletions(@NotNull CompletionParameters parameters,
                                               ProcessingContext context,
                                               @NotNull CompletionResultSet resultSet) {
                        String[] types = {"string", "string, richtext", "string, textarea", "string, choicelist", "string, choicelist[resourceBundle]",
                                "long", "boolean", "date",
                                "weakreference", "weakreference, picker[type='image']", "weakreference, picker[type='file']", "weakreference, picker[type='page']", "weakreference, picker[type='category']",
                                "weakreference, category", "weakreference, category[autoSelectParent=true]", "weakreference, category[autoSelectParent=false]"
                        };
                        for (String type : types) {
                            resultSet.addElement(LookupElementBuilder.create(type));
                        }
                    }
                }
        );

        //Property attributes
        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement(CndTypes.PROPERTY_ATTRIBUTE).withLanguage(CndLanguage.INSTANCE),
                new CompletionProvider<CompletionParameters>() {
                    public void addCompletions(@NotNull CompletionParameters parameters,
                                               ProcessingContext context,
                                               @NotNull CompletionResultSet resultSet) {
                        String[] attributes = {"mandatory", "hidden", "multiple", "nofulltext",
                                "indexed=no", "autocreated", "boost=", "onconflict=sum"
                        };
                        for (String attribute : attributes) {
                            resultSet.addElement(LookupElementBuilder.create(attribute));
                        }
                    }
                }
        );

        //Property "+" name
        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement(CndTypes.PROPERTY_PLUS_NAME).withLanguage(CndLanguage.INSTANCE),
                new CompletionProvider<CompletionParameters>() {
                    public void addCompletions(@NotNull CompletionParameters parameters,
                                               ProcessingContext context,
                                               @NotNull CompletionResultSet resultSet) {
                        String[] names = {"*"};
                        for (String name : names) {
                            resultSet.addElement(LookupElementBuilder.create(name));
                        }
                    }
                }
        );
    }
}

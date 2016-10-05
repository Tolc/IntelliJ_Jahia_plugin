package fr.tolc.jahia.intellij.plugin.cnd;

import java.util.List;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.project.Project;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiElement;
import com.intellij.util.ProcessingContext;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndNamespace;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndNodeType;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndTypes;
import org.jetbrains.annotations.NotNull;

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
                            resultSet.addElement(LookupElementBuilder.create(namespace.getNamespaceName()));
                        }
                    }
                }
        );

        //Node types
//        extend(CompletionType.BASIC,
//                PlatformPatterns.psiElement(CndTypes.NODE_TYPE_NAME).withLanguage(CndLanguage.INSTANCE),
//                new CompletionProvider<CompletionParameters>() {
//                    public void addCompletions(@NotNull CompletionParameters parameters,
//                                               ProcessingContext context,
//                                               @NotNull CompletionResultSet resultSet) {
//
//                        Project project = parameters.getPosition().getProject();
//                        List<CndNamespace> namespaces = CndUtil.findNamespaces(project);
//                        for (CndNamespace namespace : namespaces) {
//                            List<CndNodeType> nodeTypes = CndUtil.findNodeTypes(project, namespace.getNamespaceName());
//                            for (CndNodeType nodeType : nodeTypes) {
//                                resultSet.addElement(LookupElementBuilder.create(namespace.getNamespaceName() + ":" + nodeType.getNodeTypeName()));
//                            }
//                        }
//                    }
//                }
//        );

        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement(CndTypes.NODE_TYPE_NAME).withLanguage(CndLanguage.INSTANCE),
                new CompletionProvider<CompletionParameters>() {
                    public void addCompletions(@NotNull CompletionParameters parameters,
                                               ProcessingContext context,
                                               @NotNull CompletionResultSet resultSet) {

                        Project project = parameters.getPosition().getProject();
                        //TODO: check for NPE
                        PsiElement namespace = parameters.getPosition().getPrevSibling().getPrevSibling();

                        List<CndNodeType> nodeTypes = CndUtil.findNodeTypes(project, namespace.getText());
                        for (CndNodeType nodeType : nodeTypes) {
                            resultSet.addElement(LookupElementBuilder.create(nodeType.getNodeTypeName()));
                        }
                    }
                }
        );

        //TODO: see http://stackoverflow.com/questions/34328133/intellij-completion-contributor
        
//        extend(CompletionType.BASIC,
//                PlatformPatterns.psiElement(), new CompletionProvider<CompletionParameters>() {
//                    public void addCompletions(@NotNull CompletionParameters parameters,
//                                               ProcessingContext context,
//                                               @NotNull CompletionResultSet resultSet) {
//                        String ctxMsg = parameters.getPosition().getContext().toString();
//                        String prevCtxMsg = parameters.getPosition().getContext().getPrevSibling().toString();
//                        String msg = ctxMsg + prevCtxMsg;
//                        if (msg.contains("CndTokenType.PROPERTY_TYPE")) {
//                            String[] types = {
//                                    "string", "long", "double", "decimal", "path", "uri", "boolean", "date", "binary",
//                                    "weakreference", "name", "reference", "UNDEFINED"
//                            };
//                            for (String type : types) {
//                                resultSet.addElement(LookupElementBuilder.create(type));
//                            }
//                        }
//                    }
//                }
//        );

        //TODO: refactor PROPERTY_TYPE to accept any characters, and validate value afterwards
        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement(CndTypes.PROPERTY_TYPE).withLanguage(CndLanguage.INSTANCE),
                new CompletionProvider<CompletionParameters>() {
                    public void addCompletions(@NotNull CompletionParameters parameters,
                                               ProcessingContext context,
                                               @NotNull CompletionResultSet resultSet) {
                        String[] types = {
                                "string", "long", "double", "decimal", "path", "uri", "boolean", "date", "binary",
                                "weakreference", "name", "reference", "UNDEFINED"
                        };
                        for (String type : types) {
                            resultSet.addElement(LookupElementBuilder.create(type));
                        }
                    }
                }
        );

        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement(CndTypes.PROPERTY_MASK).withLanguage(CndLanguage.INSTANCE),
                new CompletionProvider<CompletionParameters>() {
                    public void addCompletions(@NotNull CompletionParameters parameters,
                                               ProcessingContext context,
                                               @NotNull CompletionResultSet resultSet) {
                        String[] masks = {
                                "text", "richtext", "textarea", "choicelist", "datetimepicker", "datepicker", "picker",
                                "color", "category", "checkbox", "fileupload", "tag", "file"
                        };
                        for (String mask : masks) {
                            resultSet.addElement(LookupElementBuilder.create(mask));
                        }
                    }
                }
        );

        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement(CndTypes.PROPERTY_MASK_OPTION).withLanguage(CndLanguage.INSTANCE),
                new CompletionProvider<CompletionParameters>() {
                    public void addCompletions(@NotNull CompletionParameters parameters,
                                               ProcessingContext context,
                                               @NotNull CompletionResultSet resultSet) {
                        String[] masks = {
                                "resourceBundle", "country", "templates", "templatesNode", "users", "nodetypes",
                                "subnodetypes", "nodes", "menus", "script", "flag", "sortableFieldnames", "moduleImage",
                                "linkerProps", "workflow", "workflowTypes", "sort", "componenttypes",
                                "autoSelectParent", "type", "image", "dependentProperties", "mime", "renderModes",
                                "permissions", "autocomplete", "separator", "folder"
                        };
                        for (String mask : masks) {
                            resultSet.addElement(LookupElementBuilder.create(mask));
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
                        String[] attributes = {"mandatory", "protected", "primary", "i18n", "internationalized",
                                "sortable", "hidden", "multiple", "nofulltext",
                                "analyzer='keyword'", "autocreated", "facetable", "hierarchical", "noqueryorder",
                                "indexed=no", "indexed=tokenized", "indexed=untokenized",
                                "boost=2.0", "scoreboost=2.0",
                                "onconflict=sum", "onconflict=latest", "onconflict=oldest", "onconflict=min", "onconflict=max", "onconflict=ignore",
                                "itemtype = content", "itemtype = metadata", "itemtype = layout", "itemtype = options", "itemtype = codeEditor",
                                "copy", "version", "initialize", "compute", "ignore", "abort",
                                "queryops '<,<=,<>,=,>,>=,like'"
                        };
                        for (String attribute : attributes) {
                            resultSet.addElement(LookupElementBuilder.create(attribute));
                        }
                    }
                }
        );

        //Sub node name
        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement(CndTypes.NODE_NAME).withLanguage(CndLanguage.INSTANCE),
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

        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement(CndTypes.COLON).withLanguage(CndLanguage.INSTANCE),
                new CompletionProvider<CompletionParameters>() {
                    public void addCompletions(@NotNull CompletionParameters parameters,
                                               ProcessingContext context,
                                               @NotNull CompletionResultSet resultSet) {
                        String[] names = {":"};
                        for (String name : names) {
                            resultSet.addElement(LookupElementBuilder.create(name));
                        }
                    }
                }
        );
        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement(CndTypes.COMMA).withLanguage(CndLanguage.INSTANCE),
                new CompletionProvider<CompletionParameters>() {
                    public void addCompletions(@NotNull CompletionParameters parameters,
                                               ProcessingContext context,
                                               @NotNull CompletionResultSet resultSet) {
                        String[] names = {","};
                        for (String name : names) {
                            resultSet.addElement(LookupElementBuilder.create(name));
                        }
                    }
                }
        );
        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement(CndTypes.EQUAL).withLanguage(CndLanguage.INSTANCE),
                new CompletionProvider<CompletionParameters>() {
                    public void addCompletions(@NotNull CompletionParameters parameters,
                                               ProcessingContext context,
                                               @NotNull CompletionResultSet resultSet) {
                        String[] names = {"="};
                        for (String name : names) {
                            resultSet.addElement(LookupElementBuilder.create(name));
                        }
                    }
                }
        );
    }
}

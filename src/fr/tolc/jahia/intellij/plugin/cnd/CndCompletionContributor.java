package fr.tolc.jahia.intellij.plugin.cnd;

import java.util.List;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiWhiteSpace;
import com.intellij.util.ProcessingContext;
import fr.tolc.jahia.intellij.plugin.cnd.enums.ItemTypeEnum;
import fr.tolc.jahia.intellij.plugin.cnd.enums.OptionEnum;
import fr.tolc.jahia.intellij.plugin.cnd.enums.PropertyAttributeEnum;
import fr.tolc.jahia.intellij.plugin.cnd.enums.PropertyTypeEnum;
import fr.tolc.jahia.intellij.plugin.cnd.enums.PropertyTypeMaskEnum;
import fr.tolc.jahia.intellij.plugin.cnd.enums.PropertyTypeMaskOptionEnum;
import fr.tolc.jahia.intellij.plugin.cnd.enums.SubNodeAttributeEnum;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndNamespace;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndTypes;
import fr.tolc.jahia.intellij.plugin.cnd.utils.CndUtil;
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

//        extend(CompletionType.BASIC,
//                PlatformPatterns.psiElement(CndTypes.NODE_TYPE_NAME).withLanguage(CndLanguage.INSTANCE),
//                new CompletionProvider<CompletionParameters>() {
//                    public void addCompletions(@NotNull CompletionParameters parameters,
//                                               ProcessingContext context,
//                                               @NotNull CompletionResultSet resultSet) {
//                        PsiElement colon = parameters.getPosition().getPrevSibling();
//                        if (colon != null) {
//                            PsiElement namespace = colon.getPrevSibling();
//
//                            if (namespace != null) {
//                                Project project = parameters.getPosition().getProject();
//                                List<CndNodeType> nodeTypes = CndUtil.findNodeTypes(project, namespace.getText());
//                                for (CndNodeType nodeType : nodeTypes) {
//                                    resultSet.addElement(LookupElementBuilder.create(nodeType.getNodeTypeName()));
//                                }
//                            }
//                        }
//                    }
//                }
//        );

        //Option
        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement(CndTypes.OPTION).withLanguage(CndLanguage.INSTANCE),
                new CompletionProvider<CompletionParameters>() {
                    public void addCompletions(@NotNull CompletionParameters parameters,
                                               ProcessingContext context,
                                               @NotNull CompletionResultSet resultSet) {
                        for (OptionEnum option : OptionEnum.values()) {
                            resultSet.addElement(LookupElementBuilder.create(option));
                        }
                        
                        //Workaround for 'extends' and 'itemtype' completions
                        PsiElement prevElement = parameters.getPosition().getPrevSibling();
                        while (prevElement != null && prevElement instanceof PsiWhiteSpace) {
                            prevElement = prevElement.getPrevSibling();
                        }
                        if (prevElement != null && prevElement.getNode().getElementType().equals(CndTypes.CRLF)) {
                            resultSet.addElement(LookupElementBuilder.create("extends = "));
                            resultSet.addElement(LookupElementBuilder.create("itemtype = "));
                        }
                    }
                }
        );

        //Property type
        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement(CndTypes.PROPERTY_TYPE).withLanguage(CndLanguage.INSTANCE),
                new CompletionProvider<CompletionParameters>() {
                    public void addCompletions(@NotNull CompletionParameters parameters,
                                               ProcessingContext context,
                                               @NotNull CompletionResultSet resultSet) {
                        for (PropertyTypeEnum type : PropertyTypeEnum.values()) {
                            resultSet.addElement(LookupElementBuilder.create(type));
                        }
                    }
                }
        );

        //Property type mask
        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement(CndTypes.PROPERTY_MASK).withLanguage(CndLanguage.INSTANCE),
                new CompletionProvider<CompletionParameters>() {
                    public void addCompletions(@NotNull CompletionParameters parameters,
                                               ProcessingContext context,
                                               @NotNull CompletionResultSet resultSet) {
                        for (PropertyTypeMaskEnum mask : PropertyTypeMaskEnum.values()) {
                            resultSet.addElement(LookupElementBuilder.create(mask));
                        }
                    }
                }
        );

        //Property type mask option
        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement(CndTypes.PROPERTY_MASK_OPTION).withLanguage(CndLanguage.INSTANCE),
                new CompletionProvider<CompletionParameters>() {
                    public void addCompletions(@NotNull CompletionParameters parameters,
                                               ProcessingContext context,
                                               @NotNull CompletionResultSet resultSet) {
                        for (PropertyTypeMaskOptionEnum option : PropertyTypeMaskOptionEnum.values()) {
                            resultSet.addElement(LookupElementBuilder.create(option));
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
                        for (PropertyAttributeEnum attribute : PropertyAttributeEnum.values()) {
                            for (String completion : attribute.getCompletions()) {
                                resultSet.addElement(LookupElementBuilder.create(completion));
                            }
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

        //Sub node attribute
        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement(CndTypes.NODE_ATTRIBUTE).withLanguage(CndLanguage.INSTANCE),
                new CompletionProvider<CompletionParameters>() {
                    public void addCompletions(@NotNull CompletionParameters parameters,
                                               ProcessingContext context,
                                               @NotNull CompletionResultSet resultSet) {
                        for (SubNodeAttributeEnum attribute : SubNodeAttributeEnum.values()) {
                            resultSet.addElement(LookupElementBuilder.create(attribute));
                        }
                    }
                }
        );


        //Item type
        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement(CndTypes.ITEMTYPE_TYPE).withLanguage(CndLanguage.INSTANCE),
                new CompletionProvider<CompletionParameters>() {
                    public void addCompletions(@NotNull CompletionParameters parameters,
                                               ProcessingContext context,
                                               @NotNull CompletionResultSet resultSet) {
                        for (ItemTypeEnum itemType : ItemTypeEnum.values()) {
                            resultSet.addElement(LookupElementBuilder.create(itemType));
                        }
                    }
                }
        );


        //Common symbols
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

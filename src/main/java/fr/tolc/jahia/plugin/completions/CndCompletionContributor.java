package fr.tolc.jahia.plugin.completions;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.util.ProcessingContext;
import fr.tolc.jahia.constants.enums.AttributeEnum;
import fr.tolc.jahia.language.cnd.psi.CndTypes;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CndCompletionContributor extends CompletionContributor {
    private static final Logger logger = LoggerFactory.getLogger(CndCompletionContributor.class);

    public CndCompletionContributor() {
        extend(CompletionType.BASIC, PlatformPatterns.psiElement(CndTypes.PROP_ATTR),
                new CompletionProvider<>() {
                    @Override
                    protected void addCompletions(@NotNull CompletionParameters parameters, @NotNull ProcessingContext context, @NotNull CompletionResultSet result) {
                        for (AttributeEnum attribute : AttributeEnum.values()) {
                            for (String completion : attribute.getCompletions()) {
                                result.addElement(LookupElementBuilder.create(completion));
                            }
                        }
                    }
                }
        );

        extend(CompletionType.BASIC, PlatformPatterns.psiElement(CndTypes.SUB_ATTR),
                new CompletionProvider<>() {
                    @Override
                    protected void addCompletions(@NotNull CompletionParameters parameters, @NotNull ProcessingContext context, @NotNull CompletionResultSet result) {
                        for (AttributeEnum attribute : AttributeEnum.subNodeAttributesValues()) {
                            for (String completion : attribute.getCompletions()) {
                                result.addElement(LookupElementBuilder.create(completion));
                            }
                        }
                    }
                }
        );
    }
}

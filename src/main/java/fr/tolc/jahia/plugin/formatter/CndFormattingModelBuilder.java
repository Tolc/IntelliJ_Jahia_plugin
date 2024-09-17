package fr.tolc.jahia.plugin.formatter;

import com.intellij.formatting.Alignment;
import com.intellij.formatting.FormattingContext;
import com.intellij.formatting.FormattingModel;
import com.intellij.formatting.FormattingModelBuilder;
import com.intellij.formatting.FormattingModelProvider;
import com.intellij.formatting.SpacingBuilder;
import com.intellij.formatting.Wrap;
import com.intellij.formatting.WrapType;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import fr.tolc.jahia.language.cnd.CndLanguage;
import fr.tolc.jahia.language.cnd.psi.CndTypes;
import org.jetbrains.annotations.NotNull;

public final class CndFormattingModelBuilder implements FormattingModelBuilder {

    @Override
    public @NotNull FormattingModel createModel(@NotNull FormattingContext formattingContext) {
        final CodeStyleSettings codeStyleSettings = formattingContext.getCodeStyleSettings();
        return FormattingModelProvider
                .createFormattingModelForPsiFile(formattingContext.getContainingFile(),
                        new CndBlock(formattingContext.getNode(),
                                Wrap.createWrap(WrapType.ALWAYS, false),
                                Alignment.createAlignment(),
                                createSpaceBuilder(codeStyleSettings)),
                        codeStyleSettings);
    }

    private static SpacingBuilder createSpaceBuilder(CodeStyleSettings settings) {
        return new SpacingBuilder(settings, CndLanguage.INSTANCE)

                .between(CndTypes.NS_START, CndTypes.NAMESPACE_IDENTIFIER).none()
                .around(CndTypes.NS_EQUAL).spaces(1)
                .between(CndTypes.NS_URI_QUOTE, CndTypes.NS_URI).none()
                .between(CndTypes.NS_URI_QUOTE, CndTypes.NS_END).none()
                .between(CndTypes.NAMESPACE, CndTypes.NAMESPACE).lineBreakInCode()

                .between(CndTypes.NAMESPACE, CndTypes.NODETYPE).blankLines(2)

                .between(CndTypes.NT_START, CndTypes.NODETYPE_IDENTIFIER).none()
                .between(CndTypes.NODETYPE_IDENTIFIER, CndTypes.NT_END).none()
                .around(CndTypes.ST_START).spaces(1)
                .before(CndTypes.ST_SEP).none().after(CndTypes.ST_SEP).spaces(1)
                .before(CndTypes.OPTION).spaces(1)  //TODO: extends and itemtype to new line
                .around(CndTypes.OPT_EQUAL).spaces(1)
                .before(CndTypes.OPT_VALUE_SEP).none().after(CndTypes.OPT_VALUE_SEP).spaces(1)

                .before(CndTypes.PROPERTY).lineBreakInCode()
                .between(CndTypes.PROP_START, CndTypes.PROPERTY_NAME).spaces(1)
                .between(CndTypes.PROPERTY_NAME, CndTypes.PROP_TYPE_START).spaces(1)
                .between(CndTypes.PROP_TYPE_START, CndTypes.PROPERTY_TYPE).none()
                .between(CndTypes.PROPERTY_TYPE, CndTypes.PROP_TYPE_MASK_START).none()
                .between(CndTypes.PROP_TYPE_MASK_START, CndTypes.PROPERTY_TYPE_MASK).spaces(1)
                .between(CndTypes.PROPERTY_TYPE_MASK, CndTypes.PROP_TYPE_MASK_OPTS_START).none()
                .around(CndTypes.PROP_TYPE_MASK_OPT_EQUAL).none()
                .before(CndTypes.PROP_TYPE_MASK_OPT_SEP).none().after(CndTypes.PROP_TYPE_MASK_OPT_SEP).spaces(1)
                .before(CndTypes.PROP_TYPE_MASK_OPTS_END).none()
                .before(CndTypes.PROP_TYPE_END).none()
                .around(CndTypes.PROP_DEFAULT_EQUAL).spaces(1)
                .before(CndTypes.PROPERTY_ATTRIBUTE).spaces(1)
                .around(CndTypes.PROP_CONST_START).spaces(1)

                .before(CndTypes.SUBNODE).lineBreakInCode()
                .between(CndTypes.SUB_START, CndTypes.SUBNODE_NAME).spaces(1)
                .between(CndTypes.SUBNODE_NAME, CndTypes.SUB_TYPES_START).spaces(1)
                .between(CndTypes.SUB_TYPES_START, CndTypes.SUBNODE_TYPE).none()
                .before(CndTypes.SUB_TYPE_SEP).none().after(CndTypes.SUB_TYPE_SEP).spaces(1)
                .before(CndTypes.SUB_TYPES_END).none()
                .around(CndTypes.SUB_DEFAULT_EQUAL).spaces(1)
                .before(CndTypes.SUBNODE_ATTRIBUTE).spaces(1)

                .between(CndTypes.NODETYPE, CndTypes.NODETYPE).blankLines(1);
    }
}

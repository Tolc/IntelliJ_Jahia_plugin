package fr.tolc.jahia.intellij.plugin.cnd.codeFormatting;

import com.intellij.formatting.Alignment;
import com.intellij.formatting.FormattingModel;
import com.intellij.formatting.FormattingModelBuilder;
import com.intellij.formatting.FormattingModelProvider;
import com.intellij.formatting.SpacingBuilder;
import com.intellij.formatting.Wrap;
import com.intellij.formatting.WrapType;
import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.intellij.psi.tree.TokenSet;
import fr.tolc.jahia.intellij.plugin.cnd.CndLanguage;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CndFormattingModelBuilder implements FormattingModelBuilder {
    @NotNull
    @Override
    public FormattingModel createModel(PsiElement element, CodeStyleSettings settings) {
        return FormattingModelProvider.createFormattingModelForPsiFile(element.getContainingFile(),
                new CndBlock(element.getNode(), Wrap.createWrap(WrapType.ALWAYS, false),
                        Alignment.createAlignment(), createSpaceBuilder(settings)), settings);
    }

    private static SpacingBuilder createSpaceBuilder(CodeStyleSettings settings) {
        return new SpacingBuilder(settings, CndLanguage.INSTANCE).
                aroundInside(CndTypes.EQUAL, CndTypes.PROPERTY_TYPE_MASK_OPTION).spaces(0).
                aroundInside(CndTypes.EQUAL, TokenSet.create(CndTypes.NAMESPACE, CndTypes.EXTENSIONS, CndTypes.ITEM_TYPE, CndTypes.PROPERTY_DEFAULT, CndTypes.SUB_NODE)).spaces(1).
                aroundInside(TokenSet.create(CndTypes.LEFT_BRACKET, CndTypes.RIGHT_BRACKET), CndTypes.PROPERTY).spaces(0).
                around(TokenSet.create(CndTypes.COLON, CndTypes.LEFT_ANGLE_BRACKET, CndTypes.RIGHT_ANGLE_BRACKET)).spaces(0).
                around(TokenSet.create(CndTypes.RIGHT_ONLY_ANGLE_BRACKET, CndTypes.LEFT_ONLY_ANGLE_BRACKET, CndTypes.MINUS, CndTypes.PLUS,
                                CndTypes.PROPERTY_ATTRIBUTE, CndTypes.NODE_ATTRIBUTE)).spaces(1).
                after(TokenSet.create(CndTypes.COMMA, CndTypes.RIGHT_PARENTHESIS)).spaces(1).
                before(CndTypes.LEFT_PARENTHESIS).spaces(1).
                before(CndTypes.COMMA).spaces(0).
                after(CndTypes.LEFT_PARENTHESIS).spaces(0).
                before(CndTypes.RIGHT_PARENTHESIS).spaces(0).
                between(CndTypes.NODE_TYPE, TokenSet.create(CndTypes.NODE_TYPE, CndTypes.COMMENT)).blankLines(1).
                between(CndTypes.NAMESPACE, TokenSet.create(CndTypes.NODE_TYPE, CndTypes.COMMENT)).blankLines(2);
    }

    @Nullable
    @Override
    public TextRange getRangeAffectingIndent(PsiFile file, int offset, ASTNode elementAtOffset) {
        return null;
    }
}
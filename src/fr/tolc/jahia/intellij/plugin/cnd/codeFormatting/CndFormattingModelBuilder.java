package fr.tolc.jahia.intellij.plugin.cnd.codeFormatting;

import com.intellij.formatting.*;
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
        return new SpacingBuilder(settings);
//        return new SpacingBuilder(settings, CndLanguage.INSTANCE).
//                around(CndTypes.EQUAL).spaces(1);
//                before(CndTypes.NAMESPACE).none().
//                before(CndTypes.NODE_TYPE).none().
//                between(CndTypes.NODE_TYPE, CndTypes.NODE_TYPE).blankLines(1).
//                between(CndTypes.NAMESPACE, TokenSet.create(CndTypes.NODE_TYPE, CndTypes.COMMENT)).blankLines(2);
    }

    @Nullable
    @Override
    public TextRange getRangeAffectingIndent(PsiFile file, int offset, ASTNode elementAtOffset) {
        return null;
    }
}
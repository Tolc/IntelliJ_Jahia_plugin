package fr.tolc.jahia.language.cnd;

import com.intellij.lang.ASTNode;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import fr.tolc.jahia.language.cnd.psi.CndFile;
import fr.tolc.jahia.language.cnd.psi.CndTokenSets;
import fr.tolc.jahia.language.cnd.psi.CndTypes;
import org.jetbrains.annotations.NotNull;

public class CndParserDefinition implements ParserDefinition {

    public static final IFileElementType FILE = new IFileElementType(CndLanguage.INSTANCE);

    @Override
    public @NotNull Lexer createLexer(Project project) {
        return new CndLexerAdapter();
    }

    @Override
    public @NotNull PsiParser createParser(Project project) {
        return new CndParser();
    }

    @Override
    public @NotNull IFileElementType getFileNodeType() {
        return FILE;
    }

    @Override
    public @NotNull TokenSet getCommentTokens() {
        return CndTokenSets.COMMENTS;
    }

    @Override
    public @NotNull TokenSet getStringLiteralElements() {
        return TokenSet.EMPTY;
    }

    @Override
    public @NotNull PsiElement createElement(ASTNode node) {
        return CndTypes.Factory.createElement(node);
    }

    @Override
    public @NotNull PsiFile createFile(@NotNull FileViewProvider viewProvider) {
        return new CndFile(viewProvider);
    }
}

package fr.tolc.jahia.plugin.folding;

import com.intellij.lang.ASTNode;
import com.intellij.lang.folding.FoldingBuilderEx;
import com.intellij.lang.folding.FoldingDescriptor;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.FoldingGroup;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiComment;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import fr.tolc.jahia.language.cnd.psi.CndNamespace;
import fr.tolc.jahia.language.cnd.psi.CndNodetype;
import fr.tolc.jahia.language.cnd.psi.CndTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CndFoldingBuilder extends FoldingBuilderEx implements DumbAware {
    @Override
    public FoldingDescriptor @NotNull [] buildFoldRegions(@NotNull PsiElement root, @NotNull Document document, boolean quick) {
        List<FoldingDescriptor> descriptors = new ArrayList<>();

        //Namespaces all at once
        Collection<CndNamespace> cndNamespaces = PsiTreeUtil.findChildrenOfType(root, CndNamespace.class);
        FoldingGroup namespaceGroup = FoldingGroup.newGroup("CndNamespaces");
        int namespacesStartRange = Integer.MAX_VALUE;
        int namespacesEndRange = Integer.MIN_VALUE;
        for (final CndNamespace cndNamespace : cndNamespaces) {
            if (cndNamespace.getTextRange().getStartOffset() < namespacesStartRange) {
                namespacesStartRange = cndNamespace.getTextRange().getStartOffset();
            }
            if (cndNamespace.getTextRange().getEndOffset() > namespacesEndRange) {
                namespacesEndRange = cndNamespace.getTextRange().getEndOffset();
            }
        }
        if (namespacesEndRange > namespacesStartRange) {
            descriptors.add(new FoldingDescriptor(root.getNode(), new TextRange(namespacesStartRange, namespacesEndRange), namespaceGroup));
        }

        //Nodetypes
        Collection<CndNodetype> cndNodeTypes = PsiTreeUtil.findChildrenOfType(root, CndNodetype.class);
        for (final CndNodetype cndNodeType : cndNodeTypes) {
            ASTNode ntEndNode = cndNodeType.getNode().findChildByType(CndTypes.NT_END);
            if (ntEndNode != null) {
                FoldingGroup group = FoldingGroup.newGroup("Cnd" + cndNodeType.getName());

                PsiElement lastRealChild = cndNodeType.getLastChild();
                while (CndTypes.CRLF.equals(lastRealChild.getNode().getElementType())) {
                    lastRealChild = lastRealChild.getPrevSibling();
                }

                int startRange = ntEndNode.getStartOffset() + 1;
                int endRange = lastRealChild.getTextRange().getEndOffset();
                if (endRange > startRange) {
                    descriptors.add(new FoldingDescriptor(cndNodeType.getNode(), new TextRange(startRange, endRange), group));
                }
            }
        }

        //Comments
        //TODO: simple line comments in a row
        Collection<PsiComment> comments = PsiTreeUtil.findChildrenOfType(root, PsiComment.class);
        for (final PsiComment comment : comments) {
            FoldingGroup group = FoldingGroup.newGroup("Comment" + comment.getTextRange().getStartOffset());

            int startRange = comment.getTextRange().getStartOffset();
            int endRange = comment.getTextRange().getEndOffset();
            if (endRange > startRange) {
                descriptors.add(new FoldingDescriptor(comment.getNode(), new TextRange(startRange, endRange), group));
            }
        }

        return descriptors.toArray(new FoldingDescriptor[0]);
    }

    @Override
    public @Nullable String getPlaceholderText(@NotNull ASTNode node) {
        return "...";
    }

    @Override
    public boolean isCollapsedByDefault(@NotNull ASTNode node) {
        return false;
    }
}

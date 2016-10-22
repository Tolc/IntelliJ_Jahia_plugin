package fr.tolc.jahia.intellij.plugin.cnd;

import com.intellij.lang.ASTNode;
import com.intellij.lang.folding.FoldingBuilderEx;
import com.intellij.lang.folding.FoldingDescriptor;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.FoldingGroup;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndNodeType;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CndFoldingBuilder extends FoldingBuilderEx {

    @NotNull
    @Override
    public FoldingDescriptor[] buildFoldRegions(@NotNull PsiElement root, @NotNull Document document, boolean quick) {
        List<FoldingDescriptor> descriptors = new ArrayList<FoldingDescriptor>();

        Collection<CndNodeType> cndNodeTypes = PsiTreeUtil.findChildrenOfType(root, CndNodeType.class);
        for (final CndNodeType cndNodeType : cndNodeTypes) {
            FoldingGroup group = FoldingGroup.newGroup("Cnd" + cndNodeType.getNodeTypeNamespace() + cndNodeType.getNodeTypeName());

            PsiElement lastRealChild = cndNodeType.getLastChild();
            while (CndTypes.CRLF.equals(lastRealChild.getNode().getElementType())) {
                lastRealChild = lastRealChild.getPrevSibling();
            }

            int startRange = cndNodeType.getTextRange().getStartOffset() + 4 + cndNodeType.getNodeTypeNamespace().length() + cndNodeType.getNodeTypeName().length();
            int endRange = lastRealChild.getTextRange().getEndOffset();
            if (endRange > startRange) {
                descriptors.add(new FoldingDescriptor(cndNodeType.getNode(), new TextRange(startRange, endRange), group));
            }
        }
        return descriptors.toArray(new FoldingDescriptor[descriptors.size()]);
    }

    @Nullable
    @Override
    public String getPlaceholderText(@NotNull ASTNode node) {
        return "...";
    }

    @Override
    public boolean isCollapsedByDefault(@NotNull ASTNode node) {
        return false;
    }
}

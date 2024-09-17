package fr.tolc.jahia.plugin.formatter;

import com.intellij.formatting.Alignment;
import com.intellij.formatting.Block;
import com.intellij.formatting.Indent;
import com.intellij.formatting.Spacing;
import com.intellij.formatting.SpacingBuilder;
import com.intellij.formatting.Wrap;
import com.intellij.formatting.WrapType;
import com.intellij.lang.ASTNode;
import com.intellij.psi.TokenType;
import com.intellij.psi.formatter.common.AbstractBlock;
import com.intellij.psi.tree.IElementType;
import fr.tolc.jahia.language.cnd.psi.CndTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CndBlock extends AbstractBlock {

    private final SpacingBuilder spacingBuilder;

    protected CndBlock(@NotNull ASTNode node, @Nullable Wrap wrap, @Nullable Alignment alignment, SpacingBuilder spacingBuilder) {
        super(node, wrap, alignment);
        this.spacingBuilder = spacingBuilder;
    }

    @Override
    protected List<Block> buildChildren() {
        List<Block> blocks = new ArrayList<>();

        ASTNode child = myNode.getFirstChildNode();
        while (child != null) {
            IElementType type = child.getElementType();

            if (!TokenType.WHITE_SPACE.equals(type) && !CndTypes.CRLF.equals(type)) {
                Block block = new CndBlock(child, Wrap.createWrap(WrapType.NONE, false), Alignment.createAlignment(), spacingBuilder);
                blocks.add(block);
            }

            child = child.getTreeNext();
        }
        return blocks;
    }

    @Override
    public Indent getIndent() {
        IElementType type = myNode.getElementType();
        if (CndTypes.PROPERTY.equals(type) || CndTypes.SUBNODE.equals(type) || CndTypes.OPTION.equals(type)) {
            return Indent.getSpaceIndent(1);
        }
        return Indent.getNoneIndent();
    }

    @Override
    public @Nullable Spacing getSpacing(@Nullable Block child1, @NotNull Block child2) {
        return spacingBuilder.getSpacing(this, child1, child2);
    }

    @Override
    public boolean isLeaf() {
        return myNode.getFirstChildNode() == null;
    }
}

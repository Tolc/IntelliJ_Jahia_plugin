package fr.tolc.jahia.intellij.plugin.cnd.codeFormatting;

import com.intellij.formatting.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.formatter.common.AbstractBlock;
import com.intellij.psi.tree.IElementType;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CndBlock extends AbstractBlock {
    private SpacingBuilder spacingBuilder;

    protected CndBlock(@NotNull ASTNode node, @Nullable Wrap wrap, @Nullable Alignment alignment,
                       SpacingBuilder spacingBuilder) {
        super(node, wrap, alignment);
        this.spacingBuilder = spacingBuilder;
    }

    @Override
    protected List<Block> buildChildren() {
        List<Block> blocks = new ArrayList<Block>();

        ASTNode child = myNode.getFirstChildNode();
        while (child != null) {
            IElementType type = child.getElementType();

            if (CndTypes.NAMESPACE.equals(type)) {
                Block block = new CndBlock(child, Wrap.createWrap(WrapType.NONE, false), Alignment.createAlignment(), spacingBuilder);
                blocks.add(block);
            } else if (CndTypes.NODE_TYPE.equals(type)) {
                Block block = new CndBlock(child, null, Alignment.createAlignment(), spacingBuilder);
                blocks.add(block);
//            } else if (CndTypes.PROPERTY.equals(type) || CndTypes.SUB_NODE.equals(type)
//                    || CndTypes.EXTENSIONS.equals(type) || CndTypes.ITEM_TYPE.equals(type) || CndTypes.NODE_OPTION.equals(type)) {
//                Block childBlock = new CndBlock(child, Wrap.createWrap(WrapType.NONE, false), Alignment.createAlignment(), spacingBuilder);
//                blocks.add(childBlock);
            } else if (CndTypes.COMMENT.equals(type)) {
                Block block = new CndBlock(child, Wrap.createWrap(WrapType.NONE, false), Alignment.createAlignment(), spacingBuilder);
                blocks.add(block);
            } else if (CndTypes.CRLF.equals(type)) {
                Block block = new CndBlock(child, Wrap.createWrap(WrapType.NONE, false), Alignment.createAlignment(), spacingBuilder);
                blocks.add(block);
            }

            child = child.getTreeNext();
        }
        return blocks;
    }

    @Override
    public Indent getIndent() {
        return Indent.getNoneIndent();
    }

    @Nullable
    @Override
    public Spacing getSpacing(@Nullable Block child1, @NotNull Block child2) {
        return spacingBuilder.getSpacing(this, child1, child2);
    }

    @Override
    public boolean isLeaf() {
        return myNode.getFirstChildNode() == null;
    }
}

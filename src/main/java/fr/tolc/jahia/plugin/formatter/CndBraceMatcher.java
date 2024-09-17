package fr.tolc.jahia.plugin.formatter;

import com.intellij.lang.BracePair;
import com.intellij.lang.PairedBraceMatcher;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IElementType;
import fr.tolc.jahia.language.cnd.psi.CndTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CndBraceMatcher implements PairedBraceMatcher {

    private static final BracePair[] PAIRS = new BracePair[]{
            new BracePair(CndTypes.NS_START, CndTypes.NS_END, true),    //TODO: why this line does not work??
            new BracePair(CndTypes.NS_URI_QUOTE, CndTypes.NS_URI_QUOTE, true),
            new BracePair(CndTypes.NT_START, CndTypes.NT_END, true),
            new BracePair(CndTypes.PROP_TYPE_START, CndTypes.PROP_TYPE_END, true),
            new BracePair(CndTypes.PROP_TYPE_MASK_OPTS_START, CndTypes.PROP_TYPE_MASK_OPTS_END, true),
            new BracePair(CndTypes.SUB_TYPES_START, CndTypes.SUB_TYPES_END, true),
    };

    @Override
    public BracePair @NotNull [] getPairs() {
        return PAIRS;
    }

    @Override
    public boolean isPairedBracesAllowedBeforeType(@NotNull IElementType lbraceType, @Nullable IElementType contextType) {
        return true;
    }

    @Override
    public int getCodeConstructStart(PsiFile file, int openingBraceOffset) {
        return openingBraceOffset;
    }
}

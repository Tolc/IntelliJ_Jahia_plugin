package fr.tolc.jahia.intellij.plugin.cnd;

import com.intellij.lang.BracePair;
import com.intellij.lang.PairedBraceMatcher;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CndBraceMatcher implements PairedBraceMatcher {
    @Override
    public BracePair[] getPairs() {
        return new BracePair[0];
    }

    @Override
    public boolean isPairedBracesAllowedBeforeType(@NotNull IElementType lbraceType, @Nullable IElementType contextType) {
        return true;
    }

    @Override
    public int getCodeConstructStart(PsiFile file, int openingBraceOffset) {
        return 0;
    }
}
//TODO: see https://github.com/yifanz/Intellij-Dust/blob/master/src/com/linkedin/intellij/dust/DustBraceMatcher.java
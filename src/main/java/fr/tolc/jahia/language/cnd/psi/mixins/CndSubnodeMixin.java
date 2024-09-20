package fr.tolc.jahia.language.cnd.psi.mixins;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.navigation.ItemPresentation;
import com.intellij.openapi.util.NlsSafe;
import com.intellij.psi.PsiElement;
import com.intellij.util.IncorrectOperationException;
import fr.tolc.jahia.language.cnd.CndIcons;
import fr.tolc.jahia.language.cnd.psi.CndSubnode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.Icon;
import java.util.stream.Collectors;

public abstract class CndSubnodeMixin extends ASTWrapperPsiElement implements CndSubnode {

    public CndSubnodeMixin(@NotNull ASTNode node) {
        super(node);
    }

    @Override
    public @Nullable PsiElement getNameIdentifier() {
        return getSubnodeName() != null ? getSubnodeName().getNameIdentifier() : null;
    }

    @Override
    public @NotNull String getName() {
        return getSubnodeName() != null ? getSubnodeName().getName() : "Unknown";
    }

    @Override
    public PsiElement setName(@NlsSafe @NotNull String name) throws IncorrectOperationException {
        if (getSubnodeName() != null) {
            getSubnodeName().setName(name);
        }
        return this;
    }

    @Override
    public ItemPresentation getPresentation() {
        return new ItemPresentation() {
            @Override
            public @NlsSafe @NotNull String getPresentableText() {
                return getName();
            }

            @Override
            public @NlsSafe @Nullable String getLocationString() {
                return getSubnodeTypeList().stream().map(PsiElement::getText).collect(Collectors.joining(", "));
            }

            @Override
            public @NotNull Icon getIcon(boolean unused) {
                return CndIcons.CND_PROP;   //TODO: icon
            }
        };
    }
}

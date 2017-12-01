package fr.tolc.jahia.intellij.plugin.cnd.utils;

import java.util.LinkedHashSet;
import java.util.Set;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PsiUtil {

    private PsiUtil() {}

    public static Set<ASTNode> findDescendantsByType(@NotNull ASTNode node, @Nullable IElementType type) {
        Set<ASTNode> result = new LinkedHashSet<>();
        for (ASTNode child : node.getChildren(null)) {
            if (type == null || type.equals(child.getElementType())) {
                result.add(child);
            }
            result.addAll(findDescendantsByType(child, type));
        }
        return result;
    }

    public static Set<ASTNode> findFirstDescendantsByType(@NotNull ASTNode node, @Nullable IElementType type) {
        Set<ASTNode> result = new LinkedHashSet<>();
        for (ASTNode child : node.getChildren(null)) {
            if (type == null || type.equals(child.getElementType())) {
                result.add(child);
            } else {
                result.addAll(findDescendantsByType(child, type));
            }
        }
        return result;
    }

    public static Set<PsiElement> findDescendantsByType(@NotNull PsiElement element, @Nullable IElementType type) {
        Set<PsiElement> result = new LinkedHashSet<>();
        Set<ASTNode> descendantNodes = findDescendantsByType(element.getNode(), type);
        for (ASTNode node : descendantNodes) {
            result.add(node.getPsi());
        }

        return result;
    }

    public static Set<PsiElement> findFirstDescendantsByType(@NotNull PsiElement element, @Nullable IElementType type) {
        Set<PsiElement> result = new LinkedHashSet<>();
        Set<ASTNode> descendantNodes = findFirstDescendantsByType(element.getNode(), type);
        for (ASTNode node : descendantNodes) {
            result.add(node.getPsi());
        }

        return result;
    }
}

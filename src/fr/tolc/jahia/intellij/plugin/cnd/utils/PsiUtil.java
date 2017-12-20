package fr.tolc.jahia.intellij.plugin.cnd.utils;

import java.util.LinkedHashSet;
import java.util.Set;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;
import org.apache.commons.lang.ArrayUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PsiUtil {

    private PsiUtil() {}

    public static Set<ASTNode> findDescendantsByType(@NotNull ASTNode node, @Nullable IElementType... types) {
        Set<ASTNode> result = new LinkedHashSet<>();
        for (ASTNode child : node.getChildren(null)) {
            if (types == null || ArrayUtils.contains(types, child.getElementType())) {
                result.add(child);
            }
            result.addAll(findDescendantsByType(child, types));
        }
        return result;
    }

    public static Set<ASTNode> findFirstDescendantsByType(@NotNull ASTNode node, @Nullable IElementType... types) {
        Set<ASTNode> result = new LinkedHashSet<>();
        for (ASTNode child : node.getChildren(null)) {
            if (types == null || ArrayUtils.contains(types, child.getElementType())) {
                result.add(child);
            } else {
                result.addAll(findFirstDescendantsByType(child, types));
            }
        }
        return result;
    }

    public static Set<PsiElement> findDescendantsByType(@NotNull PsiElement element, @Nullable IElementType... types) {
        Set<PsiElement> result = new LinkedHashSet<>();
        Set<ASTNode> descendantNodes = findDescendantsByType(element.getNode(), types);
        for (ASTNode node : descendantNodes) {
            result.add(node.getPsi());
        }

        return result;
    }

    public static Set<PsiElement> findFirstDescendantsByType(@NotNull PsiElement element, @Nullable IElementType... types) {
        Set<PsiElement> result = new LinkedHashSet<>();
        Set<ASTNode> descendantNodes = findFirstDescendantsByType(element.getNode(), types);
        for (ASTNode node : descendantNodes) {
            result.add(node.getPsi());
        }

        return result;
    }
}

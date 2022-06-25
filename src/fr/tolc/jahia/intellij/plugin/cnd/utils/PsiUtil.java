package fr.tolc.jahia.intellij.plugin.cnd.utils;

import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndNodeType;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndProperty;
import org.apache.commons.lang.ArrayUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PsiUtil {

    private PsiUtil() {}

    //TODO: use a proper cache mechanism...
    private static final Map<CndNodeType, Set<CndProperty>> propertiesCache = new ConcurrentHashMap<>();
    private static final Map<CndNodeType, Date> propertiesCacheDate = new ConcurrentHashMap<>();
    private static final long CACHE_DELAY = 10000;   //10s

    @NotNull
    public static Set<CndProperty> getNodeTypeProperties(CndNodeType element) {
        return getNodeTypeProperties(element, new HashSet<>());
    }

    @NotNull
    private static Set<CndProperty> getNodeTypeProperties(CndNodeType element, Set<CndNodeType> alreadyDoneTypes) {
        Set<CndProperty> result = null;
        if (propertiesCacheDate.containsKey(element)) {
            Date date = propertiesCacheDate.get(element);
            if (date != null && new Date().getTime() - date.getTime() >= CACHE_DELAY) {
                //remove from cache
                propertiesCache.remove(element);
                propertiesCacheDate.remove(element);
            } else {
                result = propertiesCache.get(element);
            }
        }
        if (result == null) {
            result = new LinkedHashSet<>(element.getPropertyList());
            boolean shouldCache = true;
            //Ancestors
            try {
                for (CndNodeType ancestorNodeType : element.getAncestorsNodeTypes()) {
                    if (!alreadyDoneTypes.contains(ancestorNodeType)) {
                        alreadyDoneTypes.add(ancestorNodeType);     //prevent infinite loop
                        result.addAll(getNodeTypeProperties(ancestorNodeType, alreadyDoneTypes));
                    }
                }
            } catch (StackOverflowError soe) {
                //In case a nodetype has a lot of ancestors, which also have a lot of ancestors, and so on...
                //we'll be missing some late ancestors properties but whatever
               shouldCache = false;
            }
            //Extensions
            try {
                for (CndNodeType extensionNodeType : element.getExtensions()) {
                    if (!alreadyDoneTypes.contains(extensionNodeType)) {
                        alreadyDoneTypes.add(extensionNodeType);    //prevent infinite loop
                        result.addAll(getNodeTypeProperties(extensionNodeType, alreadyDoneTypes));
                    }
                }
            } catch (StackOverflowError soe) {
                //In case a nodetype has a lot of extension types, which also have a lot of ancestors, and so on...
                //we'll be missing some late ancestors properties but whatever
                shouldCache = false;
            }

            //Put in cache
            if (shouldCache) {
                propertiesCache.put(element, result);
                propertiesCacheDate.put(element, new Date());
            }
        }
        return result;
    }

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

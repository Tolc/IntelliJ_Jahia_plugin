package fr.tolc.jahia.plugin.inlay;

import com.intellij.concurrency.JobLauncher;
import com.intellij.java.JavaBundle;
import com.intellij.openapi.progress.EmptyProgressIndicator;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressIndicatorProvider;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiReference;
import com.intellij.psi.search.PsiSearchHelper;
import com.intellij.psi.search.SearchScope;
import com.intellij.psi.search.searches.ReferencesSearch;
import com.intellij.util.ObjectUtils;
import com.intellij.util.Query;
import fr.tolc.jahia.language.cnd.psi.CndNamedElement;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Utility class to support Code Vision for CND
 *
 * @see com.intellij.codeInsight.daemon.impl.JavaTelescope
 */
public class CndTelescope {
    static final int TOO_MANY_USAGES = -1;

    static @Nls CndTelescope.UsagesHint usagesHint(@NotNull CndNamedElement cndNamedElement, @NotNull PsiFile file) {
        int totalUsageCount = CndUsagesCountManager.getInstance(cndNamedElement.getProject()).countMemberUsages(file, cndNamedElement);
        if (totalUsageCount == TOO_MANY_USAGES) return null;
        if (totalUsageCount < 0) return null;
        return new CndTelescope.UsagesHint(JavaBundle.message("usages.telescope", totalUsageCount), totalUsageCount);
    }

    public static int usagesCount(@NotNull PsiFile file, List<CndNamedElement> cndNamedElements, SearchScope scope) {
        Project project = file.getProject();
        ProgressIndicator progress = ObjectUtils.notNull(ProgressIndicatorProvider.getGlobalProgressIndicator(), /*todo remove*/new EmptyProgressIndicator());
        AtomicInteger totalUsageCount = new AtomicInteger();
        JobLauncher.getInstance().invokeConcurrentlyUnderProgress(cndNamedElements, progress, cndNamedElement -> {
            int count = usagesCount(project, file, cndNamedElement, scope, progress);
            int newCount = totalUsageCount.updateAndGet(old -> count == TOO_MANY_USAGES ? TOO_MANY_USAGES : old + count);
            return newCount != TOO_MANY_USAGES;
        });
        return totalUsageCount.get();
    }

    private static int usagesCount(@NotNull Project project, @NotNull PsiFile containingFile, @NotNull final CndNamedElement cndNamedElement, @NotNull SearchScope scope, @NotNull ProgressIndicator progress) {
        SearchScope useScope = getUseScope(cndNamedElement);
        AtomicInteger count = new AtomicInteger();

        Query<PsiReference> query = ReferencesSearch.search(cndNamedElement, useScope.intersectWith(scope));
//        query.findAll().size();
        boolean ok = query.forEach(psiReference -> {
            PsiFile psiFile = psiReference.getElement().getContainingFile();
            if (psiFile == null) {
                return true;
            }
            int offset = psiFile.getTextOffset();
            if (offset == -1) {
                return true;
            }
            count.incrementAndGet();
            return true;
        });

        if (!ok) {
            return TOO_MANY_USAGES;
        }
        return count.get();
    }

    @NotNull
    private static SearchScope getUseScope(@NotNull CndNamedElement cndNamedElement) {
        Project project = cndNamedElement.getProject();
        return PsiSearchHelper.getInstance(project).getUseScope(cndNamedElement);
    }


    static class UsagesHint {
        @Nls
        private final String hint;
        private final int count;

        UsagesHint(@Nls String hint, int count) {
            this.hint = hint;
            this.count = count;
        }

        public @Nls String getHint() {
            return hint;
        }

        public int getCount() {
            return count;
        }
    }
}

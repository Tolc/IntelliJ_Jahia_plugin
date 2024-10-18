package fr.tolc.jahia.plugin.inlay;

import com.intellij.ide.actions.QualifiedNameProviderUtil;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiTreeAnyChangeAbstractAdapter;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.SearchScope;
import com.intellij.psi.util.PsiUtilCore;
import com.intellij.serviceContainer.NonInjectable;
import com.intellij.util.containers.ContainerUtil;
import fr.tolc.jahia.language.cnd.psi.CndNamedElement;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

/**
 * @see com.intellij.codeInsight.daemon.impl.UsagesCountManager
 */
@Service(Service.Level.PROJECT)
public final @NonInjectable class CndUsagesCountManager implements Disposable {

    private final Project project;
    private final UsagesCounter usagesCounter;
    private final ConcurrentMap<VirtualFile, FileUsagesCache> externalUsagesCache;

    public CndUsagesCountManager(Project project) {
        this.project = project;
        this.usagesCounter = new DefaultUsagesCounter();
        this.externalUsagesCache = ContainerUtil.createConcurrentWeakKeySoftValueMap();

        PsiManager.getInstance(project).addPsiTreeChangeListener(new PsiTreeAnyChangeAbstractAdapter() {
            @Override
            protected void onChange(@Nullable PsiFile psiFile) {
                if (psiFile == null || psiFile.getVirtualFile() == null) {
                    return;
                }
                VirtualFile file = psiFile.getVirtualFile();
                FileUsagesCache valueToKeep = externalUsagesCache.get(file);
                externalUsagesCache.clear();
                if (valueToKeep != null) {
                    externalUsagesCache.put(file, valueToKeep);
                }
            }
        }, this);
    }

    public static CndUsagesCountManager getInstance(Project project) {
        return project.getService(CndUsagesCountManager.class);
    }

    public int countMemberUsages(PsiFile file, CndNamedElement cndNamedElement) {
        VirtualFile virtualFile = PsiUtilCore.getVirtualFile(file);
        if (virtualFile == null) {
            return usagesCounter.countUsages(file, findSuperCndElements(cndNamedElement), GlobalSearchScope.allScope(file.getProject()));
        }
        FileUsagesCache fileUsagesCache = externalUsagesCache.get(virtualFile);
        if (fileUsagesCache == null) {
            fileUsagesCache = new FileUsagesCache();
            externalUsagesCache.put(virtualFile, fileUsagesCache);
        }
        return fileUsagesCache.countMemberUsagesCached(usagesCounter, file, cndNamedElement);
    }

    @Override
    public void dispose() {
        //Do nothing
    }

    private static List<CndNamedElement> findSuperCndElements(CndNamedElement cndNamedElement) {
        //TODO: maybe also search for super properties in the case of overridden property?
        return List.of(cndNamedElement);
    }


    interface UsagesCounter {
        int countUsages(PsiFile file, List<CndNamedElement> cndNamedElements, SearchScope scope);
    }

    private static class DefaultUsagesCounter implements UsagesCounter {
        @Override
        public int countUsages(PsiFile file, List<CndNamedElement> cndNamedElements, SearchScope scope) {
            return CndTelescope.usagesCount(file, cndNamedElements, scope);
//        Query<PsiReference> query = ReferencesSearch.search(element, GlobalSearchScope.allScope(project));
//        query.findAll().size();
        }
    }


    private static class FileUsagesCache {
        private final ConcurrentMap<String, Integer> externalUsagesCache = ContainerUtil.createConcurrentWeakKeySoftValueMap();

        public int countMemberUsagesCached(CndUsagesCountManager.UsagesCounter usagesCounter, PsiFile file, CndNamedElement cndNamedElement) {
            String key = QualifiedNameProviderUtil.getQualifiedName(cndNamedElement);
            List<CndNamedElement> cndNamedElements = findSuperCndElements(cndNamedElement);
            if (key == null) {
                return usagesCounter.countUsages(file, cndNamedElements, GlobalSearchScope.allScope(file.getProject()));
            }
            //external usages should be counted first to ensure heavy cases are skipped and to avoid freezes in ScopeOptimizer
            //CompilerReferenceServiceBase#getScopeWithCodeReferences method invokes kotlin resolve and can be very slow
            GlobalSearchScope localScope = GlobalSearchScope.fileScope(file);
            GlobalSearchScope externalScope = GlobalSearchScope.notScope(localScope);
            Integer externalUsages = externalUsagesCache.get(key);
            if (externalUsages == null) {
                externalUsages = usagesCounter.countUsages(file, cndNamedElements, externalScope);
                externalUsagesCache.put(key, externalUsages);
            }
            if (externalUsages < 0) {
                return externalUsages;
            }
            int localUsages = usagesCounter.countUsages(file, cndNamedElements, localScope);
            if (localUsages < 0) {
                return localUsages;
            }
            return externalUsages + localUsages;
        }
    }
}

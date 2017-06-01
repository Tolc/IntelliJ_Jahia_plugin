package fr.tolc.jahia.intellij.plugin.cnd.toolWindow.tree.root.filters;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.google.common.collect.Lists;
import com.intellij.icons.AllIcons;
import com.intellij.psi.PsiClass;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.searches.ClassInheritorsSearch;
import com.intellij.util.Query;
import fr.tolc.jahia.intellij.plugin.cnd.toolWindow.tree.CndSimpleNode;

public class FilterNode extends CndSimpleNode {
    private final PsiClass psiClass;
    private List<FilterNode> mySubFilterNodes = new ArrayList<>();
    
    public FilterNode(PsiClass psiClass) {
        super(null);
        setIcon(AllIcons.FileTypes.Java);
        this.psiClass = psiClass;

        Query<PsiClass> actionClasses = ClassInheritorsSearch.search(this.psiClass, GlobalSearchScope.projectScope(this.psiClass.getProject()), false);
        ArrayList<PsiClass> actionClassesList = Lists.newArrayList(actionClasses);
        actionClassesList.sort(new Comparator<PsiClass>() {
            @Override
            public int compare(PsiClass o1, PsiClass o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        for (PsiClass actionClass : actionClassesList) {
            if (this.psiClass.equals(actionClass.getSuperClass())) {
                add(new FilterNode(actionClass));
            }
        }
    }

    @Override
    public String getName() {
        return psiClass.getName();
    }

    public PsiClass getPsiClass() {
        return psiClass;
    }

    @Override
    protected List<? extends CndSimpleNode> doGetChildren() {
        return mySubFilterNodes;
    }

    protected void add(FilterNode actionNode) {
        actionNode.setParent(this);
        mySubFilterNodes.add(actionNode);
        //        childrenChanged();
    }

    public void remove(FilterNode actionNode) {
        actionNode.setParent(null);
        mySubFilterNodes.remove(actionNode);
        //        childrenChanged();
    }
}

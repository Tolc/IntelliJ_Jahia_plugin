package fr.tolc.jahia.intellij.plugin.cnd.toolWindow.tree.root;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.google.common.collect.Lists;
import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.searches.ClassInheritorsSearch;
import com.intellij.util.Query;
import fr.tolc.jahia.intellij.plugin.cnd.icons.CndIcons;
import fr.tolc.jahia.intellij.plugin.cnd.toolWindow.tree.CndSimpleNode;
import fr.tolc.jahia.intellij.plugin.cnd.toolWindow.tree.root.filters.FilterNode;

public class FiltersNode extends CndSimpleNode {
    private final static String JAHIA_FILTER_CLASS = "org.jahia.services.render.filter.AbstractFilter";
    private List<FilterNode> myFilterNodes = new ArrayList<>();
    
    public FiltersNode(CndSimpleNode parent, Project project) {
        super(parent);
        setIcon(CndIcons.JAHIA_FILTER);

        PsiClass jahiaFilterClass = JavaPsiFacade.getInstance(project).findClass(JAHIA_FILTER_CLASS, GlobalSearchScope.allScope(project));
        if (jahiaFilterClass != null) {
            Query<PsiClass> filterClasses = ClassInheritorsSearch.search(jahiaFilterClass, GlobalSearchScope.projectScope(project), false);
            ArrayList<PsiClass> filterClassesList = Lists.newArrayList(filterClasses);
            filterClassesList.sort(new Comparator<PsiClass>() {
                @Override
                public int compare(PsiClass o1, PsiClass o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });
            for (PsiClass filterClass : filterClassesList) {
                if (jahiaFilterClass.equals(filterClass.getSuperClass())) {
                    add(new FilterNode(filterClass));
                }
                //TODO: change icon if abstract?
                //                ArrayUtils.toString(filterClass.getModifierList());
            }
        }
    }

    @Override
    public String getName() {
//        return message("view.node.profiles");
        return "Jahia Filters";
    }

    @Override
    protected List<? extends CndSimpleNode> doGetChildren() {
        return myFilterNodes;
    }

    protected void add(FilterNode filterNode) {
        filterNode.setParent(this);
        myFilterNodes.add(filterNode);
        //        childrenChanged();
    }

    public void remove(FilterNode filterNode) {
        filterNode.setParent(null);
        myFilterNodes.remove(filterNode);
        //        childrenChanged();
    }
}

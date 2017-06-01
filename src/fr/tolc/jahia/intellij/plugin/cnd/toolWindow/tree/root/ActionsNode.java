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
import fr.tolc.jahia.intellij.plugin.cnd.toolWindow.tree.root.actions.ActionNode;

public class ActionsNode extends CndSimpleNode {
    private final static String JAHIA_ACTION_CLASS = "org.jahia.bin.Action";
    private List<ActionNode> myActionNodes = new ArrayList<>();
    
    public ActionsNode(CndSimpleNode parent, Project project) {
        super(parent);
        setIcon(CndIcons.JAHIA_ACTION);

        PsiClass jahiaActionClass = JavaPsiFacade.getInstance(project).findClass(JAHIA_ACTION_CLASS, GlobalSearchScope.allScope(project));
        if (jahiaActionClass != null) {
            Query<PsiClass> actionClasses = ClassInheritorsSearch.search(jahiaActionClass, GlobalSearchScope.projectScope(project), false);
            ArrayList<PsiClass> actionClassesList = Lists.newArrayList(actionClasses);
            actionClassesList.sort(new Comparator<PsiClass>() {
                @Override
                public int compare(PsiClass o1, PsiClass o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });
            for (PsiClass actionClass : actionClassesList) {
                if (jahiaActionClass.equals(actionClass.getSuperClass())) {
                    add(new ActionNode(actionClass));
                }
                //TODO: change icon if abstract?
//                ArrayUtils.toString(actionClass.getModifierList());
            }
        }
    }

    @Override
    public String getName() {
        //        return message("view.node.profiles");
        return "Jahia Actions";
    }

    @Override
    protected List<? extends CndSimpleNode> doGetChildren() {
        return myActionNodes;
    }

    protected void add(ActionNode actionNode) {
        actionNode.setParent(this);
        myActionNodes.add(actionNode);
        //        childrenChanged();
    }

    public void remove(ActionNode actionNode) {
        actionNode.setParent(null);
        myActionNodes.remove(actionNode);
        //        childrenChanged();
    }
}

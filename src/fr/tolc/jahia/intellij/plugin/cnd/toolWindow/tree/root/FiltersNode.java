package fr.tolc.jahia.intellij.plugin.cnd.toolWindow.tree.root;

import fr.tolc.jahia.intellij.plugin.cnd.icons.CndIcons;
import fr.tolc.jahia.intellij.plugin.cnd.toolWindow.tree.CndSimpleNode;

public class FiltersNode extends CndSimpleNode {
//    private List<FilterNode> myFilterNodes = new ArrayList<>();
    
    public FiltersNode(CndSimpleNode parent) {
        super(parent);
        setIcon(CndIcons.JAHIA_FILTER); 
    }

//    @Override
//    protected List<? extends CndSimpleNode> doGetChildren() {
//        return myFilterNodes;
//    }

    @Override
    public String getName() {
//        return message("view.node.profiles");
        return "Jahia Filters";
    }
}

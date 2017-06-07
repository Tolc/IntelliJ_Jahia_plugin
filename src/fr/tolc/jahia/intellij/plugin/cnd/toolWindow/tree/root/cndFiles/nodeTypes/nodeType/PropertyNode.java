package fr.tolc.jahia.intellij.plugin.cnd.toolWindow.tree.root.cndFiles.nodeTypes.nodeType;

import fr.tolc.jahia.intellij.plugin.cnd.icons.CndIcons;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndProperty;
import fr.tolc.jahia.intellij.plugin.cnd.toolWindow.tree.CndSimpleNode;

public class PropertyNode extends CndSimpleNode {
    private CndProperty cndProperty;
    
    public PropertyNode(CndProperty cndProperty) {
        super(null);
        this.cndProperty = cndProperty;
        setIcon(CndIcons.PROPERTY);
    }

    @Override
    public String getName() {
        return cndProperty.getPresentation().getPresentableText();
    }
}

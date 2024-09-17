package fr.tolc.jahia.plugin.structure;

import com.intellij.ide.projectView.PresentationData;
import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.ide.util.treeView.smartTree.SortableTreeElement;
import com.intellij.ide.util.treeView.smartTree.TreeElement;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.NavigatablePsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import fr.tolc.jahia.language.cnd.psi.CndFile;
import fr.tolc.jahia.language.cnd.psi.CndNamespace;
import fr.tolc.jahia.language.cnd.psi.CndNodetype;
import fr.tolc.jahia.language.cnd.psi.CndProperty;
import fr.tolc.jahia.language.cnd.psi.CndSubnode;
import fr.tolc.jahia.language.cnd.psi.impl.CndNamespaceImpl;
import fr.tolc.jahia.language.cnd.psi.impl.CndNodetypeImpl;
import fr.tolc.jahia.language.cnd.psi.impl.CndPropertyImpl;
import fr.tolc.jahia.language.cnd.psi.impl.CndSubnodeImpl;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CndStructureViewElement implements StructureViewTreeElement, SortableTreeElement {

    private final NavigatablePsiElement myElement;

    public CndStructureViewElement(NavigatablePsiElement element) {
        this.myElement = element;
    }

    @Override
    public Object getValue() {
        return myElement;
    }

    @Override
    public void navigate(boolean requestFocus) {
        myElement.navigate(requestFocus);
    }

    @Override
    public boolean canNavigate() {
        return myElement.canNavigate();
    }

    @Override
    public boolean canNavigateToSource() {
        return myElement.canNavigateToSource();
    }

    @NotNull
    @Override
    public String getAlphaSortKey() {
        String name = myElement.getName();
        return name != null ? name : "";
    }

    @NotNull
    @Override
    public ItemPresentation getPresentation() {
        ItemPresentation presentation = myElement.getPresentation();
        return presentation != null ? presentation : new PresentationData();
    }

    @Override
    public TreeElement @NotNull [] getChildren() {
        if (myElement instanceof CndFile cndFile) {
            List<CndNamespace> namespaces = PsiTreeUtil.getChildrenOfTypeAsList(cndFile, CndNamespace.class);
            List<CndNodetype> nodetypes = PsiTreeUtil.getChildrenOfTypeAsList(cndFile, CndNodetype.class);

            List<TreeElement> treeElements = new ArrayList<>(namespaces.size() + nodetypes.size());
            for (CndNamespace namespace : namespaces) {
                treeElements.add(new CndStructureViewElement((CndNamespaceImpl) namespace));
            }
            for (CndNodetype nodetype : nodetypes) {
                treeElements.add(new CndStructureViewElement((CndNodetypeImpl) nodetype));
            }
            return treeElements.toArray(new TreeElement[0]);
        } else if (myElement instanceof CndNodetype nodetype) {
            List<CndProperty> properties = PsiTreeUtil.getChildrenOfTypeAsList(nodetype, CndProperty.class);
            List<CndSubnode> subnodes = PsiTreeUtil.getChildrenOfTypeAsList(nodetype, CndSubnode.class);

            List<TreeElement> treeElements = new ArrayList<>(properties.size() + subnodes.size());
            for (CndProperty property : properties) {
                treeElements.add(new CndStructureViewElement((CndPropertyImpl) property));
            }
            for (CndSubnode subnode : subnodes) {
                treeElements.add(new CndStructureViewElement((CndSubnodeImpl) subnode));
            }
            return treeElements.toArray(new TreeElement[0]);
        }
        return EMPTY_ARRAY;
    }
}

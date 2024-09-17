package fr.tolc.jahia.plugin.structure;

import com.intellij.ide.structureView.StructureViewModel;
import com.intellij.ide.structureView.StructureViewModelBase;
import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.ide.util.treeView.smartTree.Sorter;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiFile;
import fr.tolc.jahia.language.cnd.psi.CndNamespace;
import fr.tolc.jahia.language.cnd.psi.CndNodetype;
import fr.tolc.jahia.language.cnd.psi.CndProperty;
import fr.tolc.jahia.language.cnd.psi.CndSubnode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CndStructureViewModel extends StructureViewModelBase implements StructureViewModel.ElementInfoProvider {

    public CndStructureViewModel(@Nullable Editor editor, PsiFile psiFile) {
        super(psiFile, editor, new CndStructureViewElement(psiFile));
    }

    @NotNull
    public Sorter @NotNull [] getSorters() {
        return new Sorter[]{Sorter.ALPHA_SORTER};
    }

    @Override
    public boolean isAlwaysShowsPlus(StructureViewTreeElement element) {
        return false;
    }

    @Override
    public boolean isAlwaysLeaf(StructureViewTreeElement element) {
        return element.getValue() instanceof CndNamespace || element.getValue() instanceof CndProperty || element.getValue() instanceof CndSubnode;
    }

    @Override
    protected Class<?> @NotNull [] getSuitableClasses() {
        return new Class[]{CndNamespace.class, CndNodetype.class, CndProperty.class, CndSubnode.class};
    }
}

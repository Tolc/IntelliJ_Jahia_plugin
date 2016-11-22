package fr.tolc.jahia.intellij.plugin.cnd.treeStructure;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.swing.*;

import com.intellij.ide.projectView.PresentationData;
import com.intellij.ide.projectView.ProjectViewNode;
import com.intellij.ide.projectView.ViewSettings;
import com.intellij.ide.projectView.impl.nodes.BasePsiNode;
import com.intellij.ide.projectView.impl.nodes.PsiFileNode;
import com.intellij.ide.util.treeView.AbstractTreeNode;
import com.intellij.navigation.NavigationItemFileStatus;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Condition;
import com.intellij.openapi.vcs.FileStatus;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import fr.tolc.jahia.intellij.plugin.cnd.icons.CndIcons;
import org.jetbrains.annotations.NotNull;

public class ViewNode extends ProjectViewNode<View> {
    private final Collection<BasePsiNode<? extends PsiElement>> children;

    public ViewNode(Project project, Object value, ViewSettings viewSettings) {
        this(project, (View) value, viewSettings, getChildren(project, (View) value, viewSettings));
    }

    public ViewNode(Project project, View value, ViewSettings viewSettings, Collection<BasePsiNode<? extends PsiElement>> children) {
        super(project, value, viewSettings);
        this.children = children;
        this.myName = value.getName();
    }

    @NotNull
    public Collection<BasePsiNode<? extends PsiElement>> getChildren() {
        return children;
    }

    public String getTestPresentation() {
        return "View:" + getValue().getName();
    }

    public boolean contains(@NotNull VirtualFile file) {
        for (final AbstractTreeNode aMyChildren : children) {
            ProjectViewNode treeNode = (ProjectViewNode) aMyChildren;
            if (treeNode.contains(file)) {
                return true;
            }
        }
        return false;
    }

    public void update(PresentationData presentation) {
        if (getValue() == null || !getValue().isValid()) {
            setValue(null);
        } else {
            presentation.setPresentableText(getValue().getName());

            Icon icon;
            if (getValue().getViewModel().isHidden()) {
                icon = CndIcons.VIEW_BIG_HIDDEN;
            } else {
                icon = CndIcons.VIEW_BIG;
            }
            presentation.setIcon(icon);
        }
    }

    @Override
    protected boolean shouldPostprocess() {
        return true;
    }

    @Override
    public boolean someChildContainsFile(VirtualFile file) {
        return contains(file);
    }

//    public void navigate(final boolean requestFocus) {
//        getValue().navigate(requestFocus);
//    }

    public VirtualFile getVirtualFile() {
        final PsiFile[] array = getValue().getViewFiles();
        if (array != null && array.length > 0) {
            return array[0].getVirtualFile();
        }
        return null;
    }
    
//    public boolean canNavigate() {
//        final View value = getValue();
//        return value != null && value.canNavigate();
//    }
//
//    public boolean canNavigateToSource() {
//        final View value = getValue();
//        return value != null && value.canNavigateToSource();
//    }

    public String getToolTip() {
//        return IdeBundle.message("tooltip.ui.designer.view");
        return "ViewNode Tooltip";
    }

    @Override
    public FileStatus getFileStatus() {
        for (BasePsiNode<? extends PsiElement> child : children) {
            final PsiElement value = child.getValue();
            if (value == null || !value.isValid()) {
                continue;
            }
            final FileStatus fileStatus = NavigationItemFileStatus.get(child);
            if (fileStatus != FileStatus.NOT_CHANGED) {
                return fileStatus;
            }
        }
        return FileStatus.NOT_CHANGED;
    }

    @Override
    public boolean canHaveChildrenMatching(final Condition<PsiFile> condition) {
        for (BasePsiNode<? extends PsiElement> child : children) {
            if (condition.value(child.getValue().getContainingFile())) {
                return true;
            }
        }
        return false;
    }

//    public static AbstractTreeNode constructViewNode(final PsiClass classToBind, final Project project, final ViewSettings settings) {
//        final View view = new View(classToBind);
//        final Collection<BasePsiNode<? extends PsiElement>> children = getChildren(project, view, settings);
//        return new ViewNode(project, view, settings, children);
//    }

    private static Collection<BasePsiNode<? extends PsiElement>> getChildren(final Project project, final View view, final ViewSettings settings) {
        final Set<BasePsiNode<? extends PsiElement>> children = new LinkedHashSet<>();
//        children.add(new ClassTreeNode(project, view.getClassToBind(), settings));
        for (PsiFile viewFile : view.getViewFiles()) {
            children.add(new PsiFileNode(project, viewFile, settings));
        }
        return children;
    }
}

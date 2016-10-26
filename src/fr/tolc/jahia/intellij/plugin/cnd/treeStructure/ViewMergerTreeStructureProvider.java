package fr.tolc.jahia.intellij.plugin.cnd.treeStructure;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.intellij.ide.DeleteProvider;
import com.intellij.ide.projectView.ProjectViewNode;
import com.intellij.ide.projectView.TreeStructureProvider;
import com.intellij.ide.projectView.ViewSettings;
import com.intellij.ide.projectView.impl.nodes.BasePsiNode;
import com.intellij.ide.util.DeleteHandler;
import com.intellij.ide.util.treeView.AbstractTreeNode;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.PlatviewDataKeys;
import com.intellij.openapi.fileTypes.StdFileTypes;
import com.intellij.openapi.progress.ProcessCanceledException;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiClassOwner;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiUtilCore;
import com.intellij.uiDesigner.binding.ViewClassIndex;
import com.intellij.util.containers.ContainerUtil;
import org.jetbrains.annotations.NotNull;

public class ViewMergerTreeStructureProvider implements TreeStructureProvider {
  private final Project myProject;

  public ViewMergerTreeStructureProvider(Project project) {
    myProject = project;
  }

  @NotNull
  public Collection<AbstractTreeNode> modify(@NotNull AbstractTreeNode parent, @NotNull Collection<AbstractTreeNode> children, ViewSettings settings) {
    if (parent.getValue() instanceof View) {
      return children;
    }

    // Optimization. Check if there are any views at all.
    boolean viewsFound = false;
    for (AbstractTreeNode node : children) {
      if (node.getValue() instanceof PsiFile) {
        PsiFile file = (PsiFile)node.getValue();
        if (file.getFileType() == StdFileTypes.GUI_DESIGNER_VIEW) {
          viewsFound = true;
          break;
        }
      }
    }

    if (!viewsFound) {
      return children;
    }

    Collection<AbstractTreeNode> result = new LinkedHashSet<>(children);
    ProjectViewNode[] copy = children.toArray(new ProjectViewNode[children.size()]);
    for (ProjectViewNode element : copy) {
      PsiClass psiClass = null;
      if (element.getValue() instanceof PsiClass) {
        psiClass = (PsiClass)element.getValue();
      }
      else if (element.getValue() instanceof PsiClassOwner) {
        final PsiClass[] psiClasses = ((PsiClassOwner) element.getValue()).getClasses();
        if (psiClasses.length == 1) {
          psiClass = psiClasses[0];
        }
      }
      if (psiClass == null) {
        continue;
      }
      String qName = psiClass.getQualifiedName();
      if (qName == null) {
        continue;
      }
      List<PsiFile> views;
      try {
        views = ViewClassIndex.findViewsBoundToClass(myProject, qName);
      }
      catch (ProcessCanceledException e) {
        continue;
      }
      Collection<BasePsiNode<? extends PsiElement>> viewNodes = findViewsIn(children, views);
      if (!viewNodes.isEmpty()) {
        Collection<PsiFile> viewFiles = convertToFiles(viewNodes);
        Collection<BasePsiNode<? extends PsiElement>> subNodes = new ArrayList<>();
        //noinspection unchecked
        subNodes.add((BasePsiNode<? extends PsiElement>) element);
        subNodes.addAll(viewNodes);
        result.add(new ViewNode(myProject, new View(psiClass, viewFiles), settings, subNodes));
        result.remove(element);
        result.removeAll(viewNodes);
      }
    }
    return result;
  }

  public Object getData(Collection<AbstractTreeNode> selected, String dataId) {
    if (selected != null) {
      if (View.DATA_KEY.is(dataId)) {
        List<View> result = new ArrayList<>();
        for(AbstractTreeNode node: selected) {
          if (node.getValue() instanceof View) {
            result.add((View) node.getValue());
          }
        }
        if (!result.isEmpty()) {
          return result.toArray(new View[result.size()]);
        }
      }
      else if (PlatviewDataKeys.DELETE_ELEMENT_PROVIDER.is(dataId)) {
        for(AbstractTreeNode node: selected) {
          if (node.getValue() instanceof View) {
            return new MyDeleteProvider(selected);
          }
        }
      }
    }
    return null;
  }

  private static Collection<PsiFile> convertToFiles(Collection<BasePsiNode<? extends PsiElement>> viewNodes) {
    ArrayList<PsiFile> psiFiles = new ArrayList<>();
    for (AbstractTreeNode treeNode : viewNodes) {
      psiFiles.add((PsiFile)treeNode.getValue());
    }
    return psiFiles;
  }

  private static Collection<BasePsiNode<? extends PsiElement>> findViewsIn(Collection<AbstractTreeNode> children, List<PsiFile> views) {
    if (children.isEmpty() || views.isEmpty()) {
      return Collections.emptyList();
    }
    ArrayList<BasePsiNode<? extends PsiElement>> result = new ArrayList<>();
    HashSet<PsiFile> psiFiles = new HashSet<>(views);
    for (final AbstractTreeNode child : children) {
      if (child instanceof BasePsiNode) {
        //noinspection unchecked
        BasePsiNode<? extends PsiElement> treeNode = (BasePsiNode<? extends PsiElement>)child;
        //noinspection SuspiciousMethodCalls
        if (psiFiles.contains(treeNode.getValue())) {
          result.add(treeNode);
        }
      }
    }
    return result;
  }

  private static class MyDeleteProvider implements DeleteProvider {
    private final PsiElement[] myElements;

    public MyDeleteProvider(final Collection<AbstractTreeNode> selected) {
      myElements = collectViewPsiElements(selected);
    }

    public void deleteElement(@NotNull DataContext dataContext) {
      Project project = CommonDataKeys.PROJECT.getData(dataContext);
      DeleteHandler.deletePsiElement(myElements, project);
    }

    public boolean canDeleteElement(@NotNull DataContext dataContext) {
      return DeleteHandler.shouldEnableDeleteAction(myElements);
    }

    private static PsiElement[] collectViewPsiElements(Collection<AbstractTreeNode> selected) {
      Set<PsiElement> result = new HashSet<>();
      for(AbstractTreeNode node: selected) {
        if (node.getValue() instanceof View) {
          View view = (View)node.getValue();
          result.add(view.getClassToBind());
          ContainerUtil.addAll(result, view.getViewFiles());
        }
        else if (node.getValue() instanceof PsiElement) {
          result.add((PsiElement) node.getValue());
        }
      }
      return PsiUtilCore.toPsiElementArray(result);
    }
  }
}

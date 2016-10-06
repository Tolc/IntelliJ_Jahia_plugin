package fr.tolc.jahia.intellij.plugin.cnd.annotators;

import com.intellij.lang.annotation.Annotation;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import fr.tolc.jahia.intellij.plugin.cnd.CndSyntaxHighlighter;
import fr.tolc.jahia.intellij.plugin.cnd.constants.CndConstants;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndTypes;
import fr.tolc.jahia.intellij.plugin.cnd.quickfixes.ChangeToClosestQuickFix;
import fr.tolc.jahia.intellij.plugin.cnd.quickfixes.CreateNodeTypeFilesQuickFix;
import org.jetbrains.annotations.NotNull;

public class CndCndAnnotator implements Annotator {

    @Override
    public void annotate(@NotNull final PsiElement element, @NotNull AnnotationHolder holder) {
        if (element.getNode() != null) {
            if (CndTypes.NODE_TYPE_NAME.equals(element.getNode().getElementType())) {
                String nodeTypeName = element.getText();

                PsiElement nextSibling = element.getNextSibling();
                if (nextSibling != null && CndTypes.RIGHT_BRACKET.equals(nextSibling.getNode().getElementType())) { //Make sure this is a node type declaration, and not use

                    PsiElement colonEl = element.getPrevSibling();
                    if (colonEl != null && CndTypes.COLON.equals(colonEl.getNode().getElementType())) {

                        PsiElement namespaceEl = colonEl.getPrevSibling();
                        if (namespaceEl != null && CndTypes.NAMESPACE_NAME.equals(namespaceEl.getNode().getElementType())) {
                            String namespace = namespaceEl.getText();

                            Project project = element.getProject();
                            Module currentModule = ProjectRootManager.getInstance(project).getFileIndex().getModuleForFile(element.getContainingFile().getVirtualFile());
                            VirtualFile[] sourceRoots = ModuleRootManager.getInstance(currentModule).getSourceRoots();

                            VirtualFile nodeTypeFolder = null;
                            for (VirtualFile sourceRoot : sourceRoots) {
                                nodeTypeFolder = sourceRoot.findFileByRelativePath("main/webapp/" + namespace + "_" + nodeTypeName);
                                if (nodeTypeFolder != null) {
                                    break;
                                }
                            }
                            if (nodeTypeFolder == null) {
                                Annotation annotation = holder.createInfoAnnotation(element.getTextRange(), "Node type " + namespace + ":" + nodeTypeName + " does not have any associated files");
                                annotation.setTextAttributes(CndSyntaxHighlighter.NODE_TYPE);
                                annotation.registerFix(new CreateNodeTypeFilesQuickFix(namespace, nodeTypeName));
                            }
                        }
                    }
                }
            }

            //Property type
            if (CndTypes.PROPERTY_TYPE.equals(element.getNode().getElementType())) {
                if (!CndConstants.containsIgnoreCase(CndConstants.PROPERTY_TYPES, element.getText())) {
                    holder.createErrorAnnotation(element.getTextRange(), "Invalid property type");
                }
            }

            //Property mask
            if (CndTypes.PROPERTY_MASK.equals(element.getNode().getElementType())) {
                if (!CndConstants.containsIgnoreCase(CndConstants.PROPERTY_MASKS, element.getText())) {
                    holder.createErrorAnnotation(element.getTextRange(), "Invalid property type mask");
                }
            }

            //Property attribute
            if (CndTypes.PROPERTY_ATTRIBUTE.equals(element.getNode().getElementType())) {
                if (!CndConstants.matches(CndConstants.PROPERTY_ATTRIBUTES_REGEX, element.getText())) {
                    Annotation annotation = holder.createErrorAnnotation(element.getTextRange(), "Invalid property attribute");
                    annotation.registerFix(new ChangeToClosestQuickFix(element, CndConstants.PROPERTY_ATTRIBUTES));
                }
            }
        }
    }
}

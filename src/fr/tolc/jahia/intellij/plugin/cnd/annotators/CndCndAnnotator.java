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
import fr.tolc.jahia.intellij.plugin.cnd.quickfixes.CreateNodeTypeFilesQuickFix;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndTypes;
import org.apache.commons.lang.ArrayUtils;
import org.jetbrains.annotations.NotNull;

public class CndCndAnnotator implements Annotator {

    @Override
    public void annotate(@NotNull final PsiElement element, @NotNull AnnotationHolder holder) {
        if (element.getNode() != null && CndTypes.NODE_TYPE_NAME.equals(element.getNode().getElementType())) {
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

        if (element.getNode() != null && CndTypes.PROPERTY_TYPE.equals(element.getNode().getElementType())) {
            String propertyType = element.getText().toLowerCase();

            if (!ArrayUtils.contains(CndConstants.PROPERTY_TYPES, propertyType)) {
                holder.createErrorAnnotation(element.getTextRange(), "Invalid property type");
            }
        }

        if (element.getNode() != null && CndTypes.PROPERTY_MASK.equals(element.getNode().getElementType())) {
            String propertyMask = element.getText().toLowerCase();

            if (!ArrayUtils.contains(CndConstants.PROPERTY_MASKS, propertyMask)) {
                holder.createErrorAnnotation(element.getTextRange(), "Invalid property type mask");
            }
        }

        if (element.getNode() != null && CndTypes.PROPERTY_ATTRIBUTE.equals(element.getNode().getElementType())) {
            String attribute = element.getText().toLowerCase();
            //TODO: compare without spaces
            if (!ArrayUtils.contains(CndConstants.PROPERTY_ATTRIBUTES, attribute)) {
                holder.createErrorAnnotation(element.getTextRange(), "Invalid property attribute");
            }
        }
    }
}

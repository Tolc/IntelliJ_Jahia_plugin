package fr.tolc.jahia.intellij.plugin.cnd;

import com.intellij.lang.annotation.Annotation;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndTypes;
import org.jetbrains.annotations.NotNull;

public class CndCndAnnotator implements Annotator {

    @Override
    public void annotate(@NotNull final PsiElement element, @NotNull AnnotationHolder holder) {
        if (CndTypes.NODE_TYPE_NAME.equals(element.getNode().getElementType())) {
            String nodeTypeName = element.getText();

            PsiElement namespaceEl = element.getPrevSibling().getPrevSibling();
            if (CndTypes.NODE_TYPE_NAMESPACE.equals(namespaceEl.getNode().getElementType())) {
                String namespace = namespaceEl.getText();

                Project project = element.getProject();
                VirtualFile nodeTypeFolder = project.getBaseDir().findFileByRelativePath("src/main/webapp/" + namespace + "_" + nodeTypeName);
                if (nodeTypeFolder == null) {
                    Annotation annotation = holder.createInfoAnnotation(element.getTextRange(), "Node type " + namespace + ":" + nodeTypeName + " does not have any associated files");
                    annotation.setTextAttributes(CndSyntaxHighlighter.NODE_TYPE);
                    annotation.registerFix(new CreateNodeTypeFilesQuickFix(namespace, nodeTypeName));
                }
            }
        }
    }
}

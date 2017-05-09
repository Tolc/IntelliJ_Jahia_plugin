package fr.tolc.jahia.intellij.plugin.cnd.lineMarker;

import java.util.Collection;
import java.util.List;

import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider;
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.xml.XmlAttributeValue;
import fr.tolc.jahia.intellij.plugin.cnd.icons.CndIcons;
import fr.tolc.jahia.intellij.plugin.cnd.model.ViewModel;
import fr.tolc.jahia.intellij.plugin.cnd.references.ViewReferenceProvider;
import fr.tolc.jahia.intellij.plugin.cnd.utils.CndProjectFilesUtil;
import org.jetbrains.annotations.NotNull;

public class ViewJspLineMarkerProvider extends RelatedItemLineMarkerProvider {

    @Override
    protected void collectNavigationMarkers(@NotNull PsiElement element, Collection<? super RelatedItemLineMarkerInfo> result) {
        if (element instanceof XmlAttributeValue) {
//            String value = ((XmlAttributeValue) element).getValue();

            VirtualFile virtualFile = element.getContainingFile().getVirtualFile();
            if (virtualFile != null) {  //Not a file just in memory

                ViewModel viewModel = ViewReferenceProvider.getViewModelFromJspTagAttributeValue((XmlAttributeValue) element, virtualFile);

                if (viewModel != null) {
                    String localName = viewModel.getTagName();
                    if (ViewReferenceProvider.TAG_INCLUDE.equals(localName)) {
                        NavigationGutterIconBuilder<PsiElement> builder = NavigationGutterIconBuilder.create(CndIcons.TEMPLATE_INCLUDE);
                        
                        List<PsiFile> viewFiles = CndProjectFilesUtil.findViewFiles(element.getProject(), viewModel);
                        if (!viewFiles.isEmpty()) {
                            //TODO: maybe better icon depending on hidden or not?
                            builder.setTargets(viewFiles).setTooltipText("Navigate to view file");
                        } else {
                            builder.setTarget(element.getContainingFile()).setTooltipText("Unknown view");
                        }
                        
                        result.add(builder.createLineMarkerInfo(element));
                        
                    } else if (ViewReferenceProvider.TAG_MODULE.equals(localName)) {
                        NavigationGutterIconBuilder<PsiElement> builder = NavigationGutterIconBuilder.create(CndIcons.TEMPLATE_MODULE);

                        List<PsiFile> viewFiles = CndProjectFilesUtil.findViewFiles(element.getProject(), viewModel.getType(), viewModel.getName());
                        if (!viewFiles.isEmpty()) {
                            //TODO: maybe better icon depending on hidden or not?
                            builder.setTargets(viewFiles).setTooltipText("Navigate to node view file");
                        } else {
                            builder.setTarget(element.getContainingFile()).setTooltipText("Unknown view");
                        }

                        result.add(builder.createLineMarkerInfo(element));
                    }
                }
            }
        }
    }
}

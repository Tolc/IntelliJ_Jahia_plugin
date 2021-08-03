package fr.tolc.jahia.intellij.plugin.cnd.lineMarker;

import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider;
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlAttributeValue;
import com.intellij.psi.xml.XmlTag;
import fr.tolc.jahia.intellij.plugin.cnd.enums.ResourcesTypeEnum;
import fr.tolc.jahia.intellij.plugin.cnd.icons.CndIcons;
import fr.tolc.jahia.intellij.plugin.cnd.model.ResourcesModel;
import fr.tolc.jahia.intellij.plugin.cnd.model.ViewModel;
import fr.tolc.jahia.intellij.plugin.cnd.utils.CndProjectFilesUtil;
import fr.tolc.jahia.intellij.plugin.cnd.utils.JspUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CndXmlLineMarkerProvider extends RelatedItemLineMarkerProvider {

    @Override
    protected void collectNavigationMarkers(@NotNull PsiElement element, Collection<? super RelatedItemLineMarkerInfo> result) {
        if (element instanceof XmlTag) {
            XmlTag tag = (XmlTag) element;
            
            if (JspUtil.isJcrNodeProperty(tag)) {
                XmlAttribute nodeAttr = tag.getAttribute(JspUtil.TAG_ATTRIBUTE_NODE);                
                XmlAttribute nameAttr = tag.getAttribute(JspUtil.TAG_ATTRIBUTE_NAME);

                if (nodeAttr != null && nameAttr != null) {
                    String nodeVar = (nodeAttr.getValue() != null)? nodeAttr.getValue().replaceAll("\\$\\{|}", "") : "";
                    String propertyName = nameAttr.getValue();

                    LineMarkerUtil.createPropertyLineMarkers(element, result, nodeVar, propertyName);
                }
            } else {
                XmlAttribute viewAttr = tag.getAttribute(JspUtil.TAG_ATTRIBUTE_VIEW);
                if (viewAttr == null) {
                    if (JspUtil.isTemplateInclude(tag)) {
                        NavigationGutterIconBuilder<PsiElement> builder = NavigationGutterIconBuilder.create(CndIcons.TEMPLATE_INCLUDE);
                        builder.setTarget(element.getContainingFile()).setTooltipText("template:include");
                        result.add(builder.createLineMarkerInfo(element));
                    } else if (JspUtil.isTemplateModule(tag)) {
                        NavigationGutterIconBuilder<PsiElement> builder = NavigationGutterIconBuilder.create(CndIcons.TEMPLATE_MODULE);
                        builder.setTarget(element.getContainingFile()).setTooltipText("template:module");
                        result.add(builder.createLineMarkerInfo(element));
                    } else if (JspUtil.isTemplateOption(tag)) {
                        NavigationGutterIconBuilder<PsiElement> builder = NavigationGutterIconBuilder.create(CndIcons.TEMPLATE_MODULE);
                        builder.setTarget(element.getContainingFile()).setTooltipText("template:option");
                        result.add(builder.createLineMarkerInfo(element));
                    }
                }
            }
        }
        
        if (element instanceof XmlAttributeValue) {
            String value = ((XmlAttributeValue) element).getValue();

            VirtualFile virtualFile = element.getContainingFile().getVirtualFile();
            if (virtualFile != null) {  //Not a file just in memory

                //template:addResources
                ResourcesModel resourcesModel = JspUtil.getResourcesModelFromJspTagAttributeValue((XmlAttributeValue) element);
                if (resourcesModel != null) {
                    if (resourcesModel.getType() != null) {
                        Module module = CndProjectFilesUtil.getModuleForFile(element.getProject(), virtualFile);
                        String[] resources = value.replace("\"", "").split(",");
                        List<PsiFile> resourcesFiles = new ArrayList<>();
                        for (String resource : resources) {
                            PsiFile resourceFile = CndProjectFilesUtil.getResource(module, resourcesModel.getType(), resource);
                            if (resourceFile != null) {
                                resourcesFiles.add(resourceFile);
                            }
                        }
                        NavigationGutterIconBuilder<PsiElement> builder = NavigationGutterIconBuilder.create(resourcesModel.getType().equals(ResourcesTypeEnum.CSS) ? CndIcons.JAHIA_FOLDER_CSS : CndIcons.JAHIA_FOLDER_JAVASCRIPT);
                        if (!resourcesFiles.isEmpty()) {
                            builder.setTargets(resourcesFiles).setTooltipText("Navigate to resource file");
                        } else {
                            builder.setTarget(element.getContainingFile()).setTooltipText("Unknown resource file");
                        }
                        result.add(builder.createLineMarkerInfo(element));
                    }

                } else {
                    //template:module/include
                    ViewModel viewModel = JspUtil.getViewModelFromJspTagAttributeValue((XmlAttributeValue) element, virtualFile);

                    if (viewModel != null) {
                        String localName = viewModel.getTagName();
                        if (JspUtil.TAG_INCLUDE.equals(localName)) {
                            NavigationGutterIconBuilder<PsiElement> builder = NavigationGutterIconBuilder.create(CndIcons.TEMPLATE_INCLUDE);

                            List<PsiFile> viewFiles = CndProjectFilesUtil.findViewFilesIncludingAncestors(element.getProject(),
                                    viewModel.getNodeType().getNamespace(), viewModel.getNodeType().getNodeTypeName(), viewModel.getType(), viewModel.getName());
                            if (!viewFiles.isEmpty()) {
                                //TODO: maybe better icon depending on hidden or not?
                                builder.setTargets(viewFiles).setTooltipText("Navigate to view file");
                            } else {
                                builder.setTarget(element.getContainingFile()).setTooltipText("Unknown view");
                            }

                            result.add(builder.createLineMarkerInfo(element));

                        } else if (JspUtil.TAG_MODULE.equals(localName) || JspUtil.TAG_OPTION.equals(localName)) {
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
}

package fr.tolc.jahia.intellij.plugin.cnd.utils;

import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlAttributeValue;
import com.intellij.psi.xml.XmlTag;
import fr.tolc.jahia.intellij.plugin.cnd.enums.ResourcesTypeEnum;
import fr.tolc.jahia.intellij.plugin.cnd.model.ResourcesModel;
import fr.tolc.jahia.intellij.plugin.cnd.model.ViewModel;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.Nullable;

public class JspUtil {
    public static final String TAG_ATTRIBUTE_VIEW = "view";
    public static final String TAG_ATTRIBUTE_TEMPLATETYPE = "templateType";
    public static final String TAG_ATTRIBUTE_TYPE = "type";
    public static final String TAG_ATTRIBUTE_RESOURCES = "resources";
    public static final String TAG_INCLUDE = "include";
    public static final String TAG_MODULE = "module";
    public static final String TAG_OPTION = "option";
    public static final String TAG_ADD_RESOURCES = "addResources";
    public static final String TAGLIB_TEMPLATE_NAMESPACE = "http://www.jahia.org/tags/templateLib";

    public static final String TAG_ATTRIBUTE_NODE = "node";
    public static final String TAG_ATTRIBUTE_NAME = "name";
    public static final String TAG_NODEPROPERTY = "nodeProperty";
    public static final String TAGLIB_JCR_NAMESPACE = "http://www.jahia.org/tags/jcr";

    private JspUtil() {}

    @Nullable
    public static ViewModel getViewModelFromJspTagAttributeValue(XmlAttributeValue attributeValue, VirtualFile virtualFile) {
        String value = attributeValue.getValue();

        PsiElement xmlAttribute = attributeValue.getParent();
        if (xmlAttribute instanceof XmlAttribute) {
            String attributeName = ((XmlAttribute) xmlAttribute).getName();
            if (TAG_ATTRIBUTE_VIEW.equals(attributeName)) {
                PsiElement xmlTag = xmlAttribute.getParent();
                if (xmlTag instanceof XmlTag) {
                    String tagNamespace = ((XmlTag) xmlTag).getNamespace();
                    if (TAGLIB_TEMPLATE_NAMESPACE.equals(tagNamespace)) {
                        String localName = ((XmlTag) xmlTag).getLocalName();
                        if (TAG_INCLUDE.equals(localName) || TAG_MODULE.equals(localName) || TAG_OPTION.equals(localName)) {
                            ViewModel viewModel = CndProjectFilesUtil.getViewModelFromPotentialViewFile(virtualFile);
                            if (viewModel != null) {
                                viewModel.setName(value);
                                viewModel.setLanguage(null);    //Language normally does not interfere, but just in case
                                viewModel.setTagName(localName);

                                //'templateType' attribute
                                XmlAttribute templateTypeAttribute = ((XmlTag) xmlTag).getAttribute(TAG_ATTRIBUTE_TEMPLATETYPE);
                                if (templateTypeAttribute != null) {
                                    String templateType = templateTypeAttribute.getValue();
                                    if (StringUtils.isNotBlank(templateType)) {
                                        viewModel.setType(templateType);
                                    }
                                }

                                return viewModel;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    @Nullable
    public static ResourcesModel getResourcesModelFromJspTagAttributeValue(XmlAttributeValue attributeValue) {
        String value = attributeValue.getValue();

        PsiElement xmlAttribute = attributeValue.getParent();
        if (xmlAttribute instanceof XmlAttribute) {
            String attributeName = ((XmlAttribute) xmlAttribute).getName();
            if (TAG_ATTRIBUTE_RESOURCES.equals(attributeName)) {
                PsiElement xmlTag = xmlAttribute.getParent();
                if (xmlTag instanceof XmlTag) {
                    String tagNamespace = ((XmlTag) xmlTag).getNamespace();
                    if (TAGLIB_TEMPLATE_NAMESPACE.equals(tagNamespace)) {
                        String localName = ((XmlTag) xmlTag).getLocalName();
                        if (TAG_ADD_RESOURCES.equals(localName)) {
                            //'type' attribute
                            XmlAttribute typeAttribute = ((XmlTag) xmlTag).getAttribute(TAG_ATTRIBUTE_TYPE);
                            if (typeAttribute != null) {
                                String type = typeAttribute.getValue();
                                if (StringUtils.isNotBlank(type)) {
                                    return new ResourcesModel(ResourcesTypeEnum.fromAnything(type), value);
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    public static boolean isTagAttribute(PsiElement attribute, String namespace, String tagName, String attributeName) {
        if (attribute instanceof XmlAttribute) {
            String attrName = ((XmlAttribute) attribute).getName();
            if (attributeName.equals(attrName)) {
                PsiElement xmlTag = attribute.getParent();
                if (xmlTag instanceof XmlTag) {
                    String tagNamespace = ((XmlTag) xmlTag).getNamespace();
                    if (namespace.equals(tagNamespace)) {
                        String localName = ((XmlTag) xmlTag).getLocalName();
                        return tagName.equals(localName);
                    }
                }
            }
        }
        return false;
    }
    
    public static boolean isTag(XmlTag tag, String namespace, String tagName) {
        String tagNamespace = tag.getNamespace();
        if (namespace.equals(tagNamespace)) {
            String localName = tag.getLocalName();
            return tagName.equals(localName);
        }
        return false;
    }
    
    public static boolean isTemplateInclude(XmlTag tag) {
        return isTag(tag, TAGLIB_TEMPLATE_NAMESPACE, TAG_INCLUDE);
    }

    public static boolean isTemplateModule(XmlTag tag) {
        return isTag(tag, TAGLIB_TEMPLATE_NAMESPACE, TAG_MODULE);
    }

    public static boolean isTemplateAddResources(XmlTag tag) {
        return isTag(tag, TAGLIB_TEMPLATE_NAMESPACE, TAG_ADD_RESOURCES);
    }

    public static boolean isTemplateOption(XmlTag tag) {
        return isTag(tag, TAGLIB_TEMPLATE_NAMESPACE, TAG_OPTION);
    }
    
    public static boolean isJcrNodeProperty(XmlTag tag) {
        return isTag(tag, TAGLIB_JCR_NAMESPACE, TAG_NODEPROPERTY);
    }
    
    public static boolean isJcrNodePropertyName(XmlAttributeValue attributeValue) {
        PsiElement parent = attributeValue.getParent();
        if (parent != null && parent instanceof XmlAttribute) {
            XmlAttribute attr = (XmlAttribute) parent;
            if (TAG_ATTRIBUTE_NAME.equals(attr.getName())) {
                return isJcrNodeProperty(attr.getParent());
            }
        }
        return false;
    }
}
package fr.tolc.jahia.intellij.plugin.cnd.annotators;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.intellij.lang.annotation.Annotation;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.psi.PsiElement;
import fr.tolc.jahia.intellij.plugin.cnd.CndSyntaxHighlighter;
import fr.tolc.jahia.intellij.plugin.cnd.CndTranslationUtil;
import fr.tolc.jahia.intellij.plugin.cnd.CndUtil;
import fr.tolc.jahia.intellij.plugin.cnd.enums.ItemTypeEnum;
import fr.tolc.jahia.intellij.plugin.cnd.enums.OptionEnum;
import fr.tolc.jahia.intellij.plugin.cnd.enums.PropertyAttributeEnum;
import fr.tolc.jahia.intellij.plugin.cnd.enums.PropertyTypeEnum;
import fr.tolc.jahia.intellij.plugin.cnd.enums.PropertyTypeMaskEnum;
import fr.tolc.jahia.intellij.plugin.cnd.enums.SubNodeAttributeEnum;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndNamespace;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndNodeType;
import fr.tolc.jahia.intellij.plugin.cnd.psi.CndTypes;
import fr.tolc.jahia.intellij.plugin.cnd.quickfixes.CreateNodeTypeFilesQuickFix;
import fr.tolc.jahia.intellij.plugin.cnd.quickfixes.CreateNodeTypeTranslationsQuickFix;
import fr.tolc.jahia.intellij.plugin.cnd.quickfixes.CreateNodeTypeViewQuickFix;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

public class CndCndAnnotator implements Annotator {

    private static final Pattern namespaceUriPattern = Pattern.compile("^http(s)?://[A-Za-z0-9][A-Za-z0-9./-_]+[A-Za-z0-9]$");

    @Override
    public void annotate(@NotNull final PsiElement element, @NotNull AnnotationHolder holder) {
        if (element.getNode() != null) {
            //Node type declaration
            if (CndTypes.NODE_TYPE_IDENTIFIER.equals(element.getNode().getElementType())) {
                String nodeTypeName = element.getText();

                PsiElement nextSibling = element.getNextSibling();
                if (nextSibling != null && CndTypes.RIGHT_BRACKET.equals(nextSibling.getNode().getElementType())) { //Make sure this is a node type declaration, and not use

                    PsiElement colonEl = element.getPrevSibling();
                    if (colonEl != null && CndTypes.COLON.equals(colonEl.getNode().getElementType())) {

                        PsiElement namespaceEl = colonEl.getPrevSibling();
                        if (namespaceEl != null && CndTypes.NAMESPACE_NAME.equals(namespaceEl.getNode().getElementType())) {
                            String namespace = namespaceEl.getText();

//                            Project project = element.getProject();
//                            Module currentModule = ProjectRootManager.getInstance(project).getFileIndex().getModuleForFile(element.getContainingFile().getVirtualFile());
//                            VirtualFile[] sourceRoots = ModuleRootManager.getInstance(currentModule).getSourceRoots();

                            CndNamespace cndNamespace = CndUtil.findNamespace(element.getProject(), namespace);
                            if (cndNamespace == null) {
                                holder.createErrorAnnotation(namespaceEl.getTextRange(), "Unresolved CND namespace");
                            } else {
                                String jahiaWorkFolderPath = CndUtil.getJahiaWorkFolderPath(element);
                                if (jahiaWorkFolderPath != null) {
                                    //                            VirtualFile nodeTypeFolder = null;
                                    //                            for (VirtualFile sourceRoot : sourceRoots) {
                                    //                                nodeTypeFolder = sourceRoot.findFileByRelativePath("main/webapp/" + namespace + "_" + nodeTypeName);
                                    //                                if (nodeTypeFolder != null) {
                                    //                                    break;
                                    //                                }
                                    //                            }

                                    String nodeTypeFolderPath = CndUtil.getNodeTypeFolderPath(jahiaWorkFolderPath, namespace, nodeTypeName);
                                    File nodeTypeFolder = new File(nodeTypeFolderPath);
                                    if (!nodeTypeFolder.exists() || !nodeTypeFolder.isDirectory()) {
                                        Annotation annotation = holder.createInfoAnnotation(element.getTextRange(), "Node type " + namespace + ":" + nodeTypeName + " does not have any associated folder");
                                        annotation.setTextAttributes(CndSyntaxHighlighter.NODE_TYPE);
                                        annotation.registerFix(new CreateNodeTypeFilesQuickFix(jahiaWorkFolderPath, namespace, nodeTypeName));
                                    } else {
                                        Annotation annotation = holder.createInfoAnnotation(element.getTextRange(), "Create a new view for " + namespace + ":" + nodeTypeName);
                                        annotation.setTextAttributes(CndSyntaxHighlighter.NODE_TYPE);
                                        annotation.registerFix(new CreateNodeTypeViewQuickFix(jahiaWorkFolderPath, namespace, nodeTypeName));
                                    }

                                    //Translation
                                    if (CndTranslationUtil.getNodeTypeTranslation(element.getProject(), namespace, nodeTypeName) == null) {
                                        Annotation annotation = holder.createInfoAnnotation(element.getTextRange(), "Node type " + namespace + ":" + nodeTypeName + "does not have any translation");
                                        annotation.setTextAttributes(CndSyntaxHighlighter.NODE_TYPE);
                                        annotation.registerFix(new CreateNodeTypeTranslationsQuickFix(jahiaWorkFolderPath, namespace, nodeTypeName));
                                    }
                                }
                            }
                        }
                    }
                }
            }

            //Namespace URI
            else if (CndTypes.NAMESPACE_URI.equals(element.getNode().getElementType())) {
                Matcher matcher = namespaceUriPattern.matcher(element.getText());
                if (!matcher.matches()) {
                    holder.createErrorAnnotation(element.getTextRange(), "Not a valid URI");
                }
            }

            //Option
            else if (CndTypes.OPTION.equals(element.getNode().getElementType())) {
                try {
                    OptionEnum.fromValue(element.getText());
                } catch (IllegalArgumentException e) {
                    holder.createErrorAnnotation(element.getTextRange(), "Invalid option");
                }
            }

            //Property type
            else if (CndTypes.PROPERTY_TYPE.equals(element.getNode().getElementType())) {
                try {
                    PropertyTypeEnum.fromValue(element.getText());
                } catch (IllegalArgumentException e) {
                    holder.createErrorAnnotation(element.getTextRange(), "Invalid property type");
                }
            }

            //Property mask
            else if (CndTypes.PROPERTY_MASK.equals(element.getNode().getElementType())) {
                try {
                    PropertyTypeMaskEnum.fromValue(element.getText());
                } catch (IllegalArgumentException e) {
                    holder.createErrorAnnotation(element.getTextRange(), "Invalid property type mask");
                }
            }

            //Property attribute
            else if (CndTypes.PROPERTY_ATTRIBUTE.equals(element.getNode().getElementType())) {
                try {
                    PropertyAttributeEnum.fromValue(element.getText());
                } catch (IllegalArgumentException e) {
                    holder.createErrorAnnotation(element.getTextRange(), "Invalid property attribute");
//                    Annotation annotation = holder.createErrorAnnotation(element.getTextRange(), "Invalid property attribute");
//                    annotation.registerFix(new ChangeToClosestQuickFix(element, CndConstants.PROPERTY_ATTRIBUTES));
                }
            }

            //Item type
            else if (CndTypes.ITEMTYPE_TYPE.equals(element.getNode().getElementType())) {
                try {
                    ItemTypeEnum.fromValue(element.getText());
                } catch (IllegalArgumentException e) {
                    holder.createErrorAnnotation(element.getTextRange(), "Invalid item type");
                }
            }

            //Sub node attribute
            else if (CndTypes.NODE_ATTRIBUTE.equals(element.getNode().getElementType())) {
                try {
                    SubNodeAttributeEnum.fromValue(element.getText());
                } catch (IllegalArgumentException e) {
                    holder.createErrorAnnotation(element.getTextRange(), "Invalid sub node attribute");
                }
            }

            //Check for invalid namespaces and nodetypes
            else if (CndTypes.SUPER_TYPE.equals(element.getNode().getElementType())
                || CndTypes.EXTENSION.equals(element.getNode().getElementType())
                || CndTypes.SUB_NODE_TYPE.equals(element.getNode().getElementType())
                || CndTypes.SUB_NODE_DEFAULT_TYPE.equals(element.getNode().getElementType())) {

                PsiElement namespaceElt = element.getFirstChild();
                if (namespaceElt != null) {
                    CndNamespace cndNamespace = CndUtil.findNamespace(element.getProject(), namespaceElt.getText());
                    if (cndNamespace == null && !"nt".equals(namespaceElt.getText()) && !"jnt".equals(namespaceElt.getText()) && !"jmix".equals(namespaceElt.getText()) && !"mix".equals(namespaceElt.getText())) {
                        holder.createErrorAnnotation(namespaceElt.getTextRange(), "Unresolved CND namespace");
                    } else {
                        PsiElement colonElt = namespaceElt.getNextSibling();
                        if (colonElt == null) {
                            holder.createErrorAnnotation(element.getTextRange(), "Invalid CND node type (mising colon)");
                        } else {
                            PsiElement nodeTypeElt = colonElt.getNextSibling();
                            if (nodeTypeElt == null || StringUtils.isBlank(nodeTypeElt.getText())) {
                                holder.createErrorAnnotation(element.getTextRange(), "Invalid CND node type (missing node type name)"); 
                            } else if (!"nt".equals(namespaceElt.getText()) && !"jnt".equals(namespaceElt.getText()) && !"jmix".equals(namespaceElt.getText()) && !"mix".equals(namespaceElt.getText())) {
                                //TODO: change or remove this condition after embedding Jahia default and modules cnd files  
                                CndNodeType cndNodeType = CndUtil.findNodeType(element.getProject(), namespaceElt.getText(), nodeTypeElt.getText());
                                if (cndNodeType == null) {
                                    holder.createErrorAnnotation(nodeTypeElt.getTextRange(), "Unresolved CND node type");
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

package fr.tolc.jahia.intellij.plugin.cnd.references;

import static fr.tolc.jahia.intellij.plugin.cnd.model.NodeTypeModel.nodeTypeGlobalRegex;
import static fr.tolc.jahia.intellij.plugin.cnd.model.PropertyModel.CURRENT_NODE;
import static fr.tolc.jahia.intellij.plugin.cnd.model.PropertyModel.propertyGetRegex;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceProvider;
import com.intellij.psi.jsp.el.ELElementTypes;
import com.intellij.psi.jsp.el.ELLiteralExpression;
import com.intellij.psi.jsp.el.ELVariable;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlAttributeValue;
import com.intellij.psi.xml.XmlElementType;
import com.intellij.psi.xml.XmlTag;
import com.intellij.util.ProcessingContext;
import com.intellij.util.containers.HashSet;
import fr.tolc.jahia.intellij.plugin.cnd.model.NodeTypeModel;
import fr.tolc.jahia.intellij.plugin.cnd.model.ViewModel;
import fr.tolc.jahia.intellij.plugin.cnd.references.types.CndNamespaceIdentifierReference;
import fr.tolc.jahia.intellij.plugin.cnd.references.types.CndNodeTypeIdentifierReference;
import fr.tolc.jahia.intellij.plugin.cnd.references.types.CndPropertyIdentifierReference;
import fr.tolc.jahia.intellij.plugin.cnd.utils.CndProjectFilesUtil;
import fr.tolc.jahia.intellij.plugin.cnd.utils.JspUtil;
import fr.tolc.jahia.intellij.plugin.cnd.utils.PsiUtil;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

public class CndJspReferenceProvider extends PsiReferenceProvider {

    @NotNull
    @Override
    public PsiReference[] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext context) {
        List<PsiReference> psiReferences = new ArrayList<>();

        if (element instanceof XmlAttributeValue) {
            if (JspUtil.isJcrNodePropertyName((XmlAttributeValue) element)) {
                //<jcr:nodeProperty var="lol" node="${currentNode}" name="lol"/>
                XmlAttributeValue attributeValue = (XmlAttributeValue) element;

                String propertyName = attributeValue.getValue();
                XmlAttribute nodeAttr = ((XmlTag) attributeValue.getParent().getParent()).getAttribute(JspUtil.TAG_ATTRIBUTE_NODE);

                if (nodeAttr != null) {
                    String nodeVar = (nodeAttr.getValue() != null) ? nodeAttr.getValue().replaceAll("\\$\\{|}", "") : "";

                    int offset = 1;
                    TextRange propertyRange = new TextRange(offset, offset + propertyName.length());
                    
                    if (CURRENT_NODE.equals(nodeVar)) {
                        String namespace = null;
                        String nodeTypeName = null;

                        ViewModel viewModel = CndProjectFilesUtil.getViewModelFromPotentialViewFile(element.getContainingFile().getOriginalFile().getVirtualFile());
                        if (viewModel != null) {
                            namespace = viewModel.getNodeType().getNamespace();
                            nodeTypeName = viewModel.getNodeType().getNodeTypeName();
                        }
                        //Text ranges here are relative!!
                        psiReferences.add(new CndPropertyIdentifierReference(element, propertyRange, namespace, nodeTypeName, propertyName));
                    } else {
                        //Text ranges here are relative!!
                        psiReferences.add(new CndPropertyIdentifierReference(element,propertyRange, propertyName));
                    }
                    PsiReference[] psiReferencesArray = new PsiReference[psiReferences.size()];
                    return psiReferences.toArray(psiReferencesArray);
                }
            } else {
                Set<PsiElement> literalExpressions = PsiUtil.findDescendantsByType(element, ELElementTypes.EL_LITERAL_EXPRESSION);
                Set<PsiElement> attributeValueTokens = PsiUtil.findDescendantsByType(element, XmlElementType.XML_ATTRIBUTE_VALUE_TOKEN);

                Set<PsiElement> elements = new HashSet<PsiElement>();
                elements.addAll(literalExpressions);
                elements.addAll(attributeValueTokens);

                if (!elements.isEmpty()) {
                    for (PsiElement psiElement : elements) {
                        PsiElement parent = psiElement.getParent();
                        IElementType parentElementType = parent.getNode().getElementType();
                        if (!ELElementTypes.EL_SLICE_EXPRESSION.equals(parentElementType) && !ELElementTypes.EL_SELECT_EXPRESSION.equals(parentElementType)) {
                            String nodetypeText = psiElement.getText();

                            if (nodetypeText != null) {

                                Matcher matcher = nodeTypeGlobalRegex.matcher(nodetypeText);
                                while (matcher.find()) {
                                    String group = matcher.group();

                                    NodeTypeModel nodeTypeModel = null;
                                    try {
                                        nodeTypeModel = new NodeTypeModel(group);
                                    } catch (IllegalArgumentException e) {
                                        //Nothing to do
                                    }

                                    if (nodeTypeModel != null) {
                                        int offset = (psiElement.getTextRange().getStartOffset() - element.getTextRange().getStartOffset()) + matcher.start();
                                        String namespace = nodeTypeModel.getNamespace();
                                        String nodeTypeName = nodeTypeModel.getNodeTypeName();

                                        //Text ranges here are relative!!
                                        psiReferences.add(new CndNamespaceIdentifierReference(element, new TextRange(offset, namespace.length() + offset), namespace));
                                        psiReferences.add(new CndNodeTypeIdentifierReference(element, new TextRange(namespace.length() + offset + 1, group.length() + offset), namespace, nodeTypeName));
                                    }
                                }
                            }
                        }
                    }
                    PsiReference[] psiReferencesArray = new PsiReference[psiReferences.size()];
                    return psiReferences.toArray(psiReferencesArray);
                }
            }
        } else if (element instanceof ELLiteralExpression || element instanceof ELVariable) {
            //currentNode.properties['desc'].string and/or currentNode.properties.desc
            PsiElement parent = element.getParent();
            IElementType parentElementType = parent.getNode().getElementType();

            if (ELElementTypes.EL_SLICE_EXPRESSION.equals(parentElementType) || ELElementTypes.EL_SELECT_EXPRESSION.equals(parentElementType)) {
                String value = parent.getText();

                Matcher matcher = propertyGetRegex.matcher(value);
                while (matcher.find()) {
                    String nodeVar = StringUtils.isNotBlank(matcher.group(1)) ? matcher.group(1) : matcher.group(3);
                    String propertyName = StringUtils.isNotBlank(matcher.group(2)) ? matcher.group(2) : matcher.group(4);

                    int startOffset = element instanceof ELLiteralExpression ? 1 : 0;
                    int endOffset = element instanceof ELLiteralExpression ? 1 : 0;

                    if (element.getText().substring(startOffset, element.getText().length() - endOffset).equals(propertyName)) {
                        TextRange propertyRange = new TextRange(startOffset, startOffset + propertyName.length());

                        if (CURRENT_NODE.equals(nodeVar)) {
                            String namespace = null;
                            String nodeTypeName = null;

                            ViewModel viewModel = CndProjectFilesUtil.getViewModelFromPotentialViewFile(element.getContainingFile().getOriginalFile().getVirtualFile());
                            if (viewModel != null) {
                                namespace = viewModel.getNodeType().getNamespace();
                                nodeTypeName = viewModel.getNodeType().getNodeTypeName();
                            }
                            //Text ranges here are relative!!
                            psiReferences.add(new CndPropertyIdentifierReference(element, propertyRange, namespace, nodeTypeName, propertyName));
                        } else {
                            //Text ranges here are relative!!
                            psiReferences.add(new CndPropertyIdentifierReference(element, propertyRange, propertyName));
                        }
                    }
                }
            }
            PsiReference[] psiReferencesArray = new PsiReference[psiReferences.size()];
            return psiReferences.toArray(psiReferencesArray);
        }
        return new PsiReference[0];
    }
}
    

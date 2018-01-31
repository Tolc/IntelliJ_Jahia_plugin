package fr.tolc.jahia.intellij.plugin.cnd.extensions.javaee.jsp;

import java.util.Iterator;
import java.util.Map;

import com.intellij.lang.injection.InjectedLanguageManager;
import com.intellij.openapi.util.Key;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiLanguageInjectionHost;
import com.intellij.psi.impl.source.jsp.el.impl.ELElementProcessor;
import com.intellij.psi.impl.source.jsp.el.impl.ELResolveUtil;
import com.intellij.psi.impl.source.jsp.el.impl.ELResolveUtil.VariableInfoData;
import com.intellij.psi.impl.source.jsp.el.impl.ElVariablesProvider;
import com.intellij.psi.jsp.JspFile;
import com.intellij.psi.jsp.JspImplicitVariable;
import com.intellij.psi.jsp.el.ELExpressionHolder;
import com.intellij.psi.util.CachedValue;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.xml.XmlTag;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

/**
 * See com.intellij.psi.impl.source.jsp.el.impl.JspElVariablesProvider
 */
public class CndJspElVariablesProvider extends ElVariablesProvider {
    private static final VariableInfoData ourData;

    private static final Key<CachedValue<Map<String, JspImplicitVariable>>> CND_EL_IMPLICIT_VARS_MAP = Key.create("cnd el implicit vars");
    //    private static final Pattern ourVarCommentAnnotationPattern = Pattern.compile("@elvariable id=\"(.+)\" type=\"(.*)\"");

    static {
        ourData = new VariableInfoData(CND_EL_IMPLICIT_VARS_MAP);
//        ourData.add("currentNode", "org.jahia.services.content.JCRNodeWrapper");
        ourData.add("currentNode", "org.jahia.services.content.mod.JCRNodeWrapperMod");
        ourData.add("out", "java.io.PrintWriter");
        ourData.add("script", "org.jahia.services.render.scripting.Script");
        ourData.add("scriptInfo", "java.lang.String");
        ourData.add("workspace", "java.lang.String");
        ourData.add("renderContext", "org.jahia.services.render.RenderContext");
        ourData.add("currentResource", "org.jahia.services.render.Resource");
        ourData.add("url", "org.jahia.services.render.URLGenerator");
        ourData.add("currentAliasUser", "org.jahia.services.usermanager.JahiaUser");
        ourData.add("moduleMap", "java.lang.Object", true);
        ourData.add("propertyDefinition", "org.jahia.services.content.nodetypes.ExtendedPropertyDefinition");
    }

    @Override
    public boolean processImplicitVariables(@NotNull PsiElement element, @NotNull ELExpressionHolder expressionHolder, @NotNull ELElementProcessor processor) {
        PsiLanguageInjectionHost host = InjectedLanguageManager.getInstance(element.getProject()).getInjectionHost(element);
        PsiFile containingFile = host == null ? expressionHolder.getContainingFile() : host.getContainingFile();
        if (!(containingFile instanceof JspFile)) {
            return true;
        } else {

            //TODO
            XmlTag tag = PsiTreeUtil.getParentOfType(element, XmlTag.class, false);
            if(tag != null) {
                if ("http://www.jahia.org/tags/jcr".equals(tag.getNamespace()) && "nodeProperty".equals(tag.getLocalName())) {
                    String varName = tag.getAttributeValue("var");
                    if (StringUtils.isNotBlank(varName)) {
//                        Project project = containingFile.getProject();
//                        PsiElementFactory elementFactory = JavaPsiFacade.getInstance(project).getElementFactory();
//                        GlobalSearchScope allScope = GlobalSearchScope.allScope(project);
//
//                        JspImplicitVariableImpl variable = new JspImplicitVariableImpl(
//                                containingFile, 
//                                varName, 
//                                elementFactory.createTypeByFQClassName("org.jahia.services.content.JCRValueWrapper", allScope), 
//                                containingFile,
//                                BEGIN_RANGE
//                        );
//                        boolean processRes = processor.processVariable(variable);
//                        return processRes;

//                        Key<CachedValue<Map<String, JspImplicitVariable>>> key = Key.create("lol");
//                        VariableInfoData lol = new VariableInfoData(key);
//                        lol.add(varName , "org.jahia.services.content.JCRValueWrapper");
//
//                        Iterator variableIterator = ELResolveUtil.createOrGetPredefinedVariablesMapImpl(containingFile, ourData).values().iterator();
//
//                        JspImplicitVariable jspImplicitVariable;
//                        do {
//                            if (!variableIterator.hasNext()) {
//                                return true;
//                            }
//
//                            jspImplicitVariable = (JspImplicitVariable) variableIterator.next();
//                        } while (processor.processVariable(jspImplicitVariable));
                    }
                }
            }

            Iterator variableIterator = ELResolveUtil.createOrGetPredefinedVariablesMapImpl(containingFile, ourData).values().iterator();

            JspImplicitVariable jspImplicitVariable;
            do {
                if (!variableIterator.hasNext()) {
                    return true;
                }

                jspImplicitVariable = (JspImplicitVariable) variableIterator.next();
            } while (processor.processVariable(jspImplicitVariable));

            return false;
        }
    }
    
    
}

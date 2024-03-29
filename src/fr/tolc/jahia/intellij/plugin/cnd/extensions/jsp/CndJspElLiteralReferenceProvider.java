package fr.tolc.jahia.intellij.plugin.cnd.extensions.jsp;

import com.intellij.javaee.el.ElLiteralCustomReferenceProvider;
import com.intellij.javaee.el.psi.ELElementTypes;
import com.intellij.javaee.el.psi.ELLiteralExpression;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.tree.IElementType;
import com.intellij.util.ProcessingContext;
import fr.tolc.jahia.intellij.plugin.cnd.references.CndJspReferenceProvider;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;

import static fr.tolc.jahia.intellij.plugin.cnd.model.PropertyModel.propertyGetRegex;

public class CndJspElLiteralReferenceProvider implements ElLiteralCustomReferenceProvider {
    private static final CndJspReferenceProvider cndJspReferenceProvider = new CndJspReferenceProvider();
    
    @Override
    public boolean accept(@NotNull ELLiteralExpression elLiteralExpression) {
        PsiElement parent = elLiteralExpression.getParent();
        IElementType parentElementType = parent.getNode().getElementType();
        if (ELElementTypes.EL_SLICE_EXPRESSION.equals(parentElementType) || ELElementTypes.EL_SELECT_EXPRESSION.equals(parentElementType)) {
            String value = parent.getText();

            Matcher matcher = propertyGetRegex.matcher(value);
            return matcher.find();
        }
        
        return false;
    }

    @NotNull
    @Override
    public PsiReference[] createReferences(@NotNull ELLiteralExpression elLiteralExpression) {
        return cndJspReferenceProvider.getReferencesByElement(elLiteralExpression, new ProcessingContext());
    }
}

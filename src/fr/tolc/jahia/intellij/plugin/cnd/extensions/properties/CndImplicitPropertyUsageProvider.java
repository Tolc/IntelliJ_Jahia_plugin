package fr.tolc.jahia.intellij.plugin.cnd.extensions.properties;

import com.intellij.codeInspection.unused.ImplicitPropertyUsageProvider;
import com.intellij.lang.properties.psi.Property;
import com.intellij.openapi.project.Project;
import fr.tolc.jahia.intellij.plugin.cnd.model.PropertiesFileCndKeyModel;
import fr.tolc.jahia.intellij.plugin.cnd.utils.CndUtil;

public class CndImplicitPropertyUsageProvider extends ImplicitPropertyUsageProvider {
    @Override
    protected boolean isUsed(Property property) {
        PropertiesFileCndKeyModel cndKeyModel = null;
        try {
            cndKeyModel = new PropertiesFileCndKeyModel(property.getKey());
        } catch (IllegalArgumentException e) {
            //Nothing to do
        }

        if (cndKeyModel != null) {
            Project project = property.getProject();
            String namespace = cndKeyModel.getNamespace();
            String nodeTypeName = cndKeyModel.getNodeTypeName();

            if (cndKeyModel.isProperty() || cndKeyModel.isProperty() || cndKeyModel.isChoicelistElement()) {
                return CndUtil.findProperty(project, namespace, nodeTypeName, cndKeyModel.getPropertyName()) != null;
            } else {
                return CndUtil.findNodeType(project, namespace, nodeTypeName) != null;
            }
        }

        return false;
    }
}

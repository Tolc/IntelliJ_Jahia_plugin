package fr.tolc.jahia.intellij.plugin.cnd.psi;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFileFactory;
import fr.tolc.jahia.intellij.plugin.cnd.CndFileType;
import fr.tolc.jahia.intellij.plugin.cnd.enums.PropertyTypeEnum;

public class CndElementFactory {
    public static CndNodeType createNamespace(Project project, String namespaceName) {
        final CndFile file = createFile(project, "<" + namespaceName + " = 'http://dummy.dummy'>");
        return (CndNodeType) file.getFirstChild();
    }

    public static CndNodeType createNodeType(Project project, String nodeTypeName, String namespace) {
        final CndFile file = createFile(project, "[" + namespace + ":" + nodeTypeName + "]");
        return (CndNodeType) file.getFirstChild();
    }

    public static CndProperty createProperty(Project project, String propertyName, PropertyTypeEnum propertyType) {
        final CndFile file = createFile(project,    "[dummyNamespace:dummyNodeTypeName]" +
                                                    "\r\n - " + propertyName + "(" + propertyType + ")");
        return (CndProperty) file.getFirstChild().getLastChild();
    }

    public static CndProperty createProperty(Project project, String propertyName) {
        return createProperty(project, propertyName, PropertyTypeEnum.UNDEFINED);
    }

    public static PsiElement createCRLF(Project project) {
        final CndFile file = createFile(project, "\n");
        return file.getFirstChild();
    }

    public static CndFile createFile(Project project, String text) {
        String name = "dummy.cnd";
        return (CndFile) PsiFileFactory.getInstance(project).createFileFromText(name, CndFileType.INSTANCE, text);
    }
}
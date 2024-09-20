package fr.tolc.jahia.language.cnd;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFileFactory;
import fr.tolc.jahia.constants.enums.PropertyTypeEnum;
import fr.tolc.jahia.language.cnd.psi.CndFile;
import fr.tolc.jahia.language.cnd.psi.CndNamespace;
import fr.tolc.jahia.language.cnd.psi.CndNodetype;
import fr.tolc.jahia.language.cnd.psi.CndProperty;
import fr.tolc.jahia.language.cnd.psi.CndSubnode;
import fr.tolc.jahia.language.cnd.psi.CndSubnodeDefault;
import fr.tolc.jahia.language.cnd.psi.CndTypes;

public class CndElementFactory {

    public static CndFile createFile(Project project, String text) {
        String name = "dummy.cnd";
        return (CndFile) PsiFileFactory.getInstance(project).createFileFromText(name, CndFileType.INSTANCE, text);
    }

    public static PsiElement createCRLF(Project project) {
        final CndFile file = createFile(project, "\n");
        return file.getFirstChild();
    }

    public static PsiElement createWhiteSpace(Project project) {
        final CndFile file = createFile(project, " ");
        return file.getFirstChild();
    }

    public static CndNamespace createNamespace(Project project, String namespaceName) {
        final CndFile file = createFile(project, "<" + namespaceName + " = 'http://dummy.dummy'>");
        return (CndNamespace) file.getFirstChild();
    }

    public static CndNodetype createNodetype(Project project, String identifier) {
        final CndFile file = createFile(project, "[" + identifier + "]");
        return (CndNodetype) file.getFirstChild();
    }

    public static CndProperty createProperty(Project project, String propertyName, PropertyTypeEnum propertyType) {
        final CndFile file = createFile(project,
                "[dummyNs:dummyNt]\r\n" +
                        " - " + propertyName + "(" + propertyType + ")");
        return (CndProperty) file.getFirstChild().getLastChild();
    }

    public static CndProperty createProperty(Project project, String propertyName) {
        return createProperty(project, propertyName, PropertyTypeEnum.UNDEFINED);
    }

    public static CndSubnode createSubnode(Project project, String subnodeName) {
        final CndFile file = createFile(project,
                "[dummyNs:dummyNt]\r\n" +
                        " + " + subnodeName + " (dummyNs:dummyNt)");
        return (CndSubnode) file.getFirstChild().getLastChild();
    }

    public static ASTNode createSubnodeDefaultEqual(Project project) {
        final CndFile file = createFile(project,
                "[dummyNs:dummyNt]\r\n" +
                        " + subnode (dummyNs:dummyNt) = dummyNs:dummyNt");
        return file.getFirstChild().getLastChild().getNode().findChildByType(CndTypes.SUB_DEFAULT_EQUAL);
    }

    public static CndSubnodeDefault createSubnodeDefault(Project project, String subnodeDefaultValue) {
        final CndFile file = createFile(project,
                "[dummyNs:dummyNt]\r\n" +
                        " + subnode (dummyNs:dummyNt) = " + subnodeDefaultValue);
        return (CndSubnodeDefault) file.getFirstChild().getLastChild().getLastChild();
    }
}

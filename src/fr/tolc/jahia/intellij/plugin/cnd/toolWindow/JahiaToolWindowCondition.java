package fr.tolc.jahia.intellij.plugin.cnd.toolWindow;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Condition;
import fr.tolc.jahia.intellij.plugin.cnd.utils.CndProjectFilesUtil;

public class JahiaToolWindowCondition implements Condition<Project> {

    @Override
    public boolean value(Project project) {
        return CndProjectFilesUtil.getProjectCndFiles(project).size() > 0;
    }
}

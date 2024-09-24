package fr.tolc.jahia.plugin.config;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurableProvider;
import com.intellij.openapi.project.Project;
import fr.tolc.jahia.plugin.JahiaUtil;
import org.jetbrains.annotations.NotNull;

public class JahiaProjectSettingsConfigurableProvider extends ConfigurableProvider {
    private final @NotNull Project project;

    public JahiaProjectSettingsConfigurableProvider(@NotNull Project project) {
        this.project = project;
    }

    @Override
    public @NotNull Configurable createConfigurable() {
        return new JahiaProjectSettingsConfigurable(project);
    }

    @Override
    public boolean canCreateConfigurable() {
        return JahiaUtil.isJahiaProject(project);
    }
}

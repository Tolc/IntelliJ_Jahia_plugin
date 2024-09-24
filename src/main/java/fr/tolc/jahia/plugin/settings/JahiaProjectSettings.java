package fr.tolc.jahia.plugin.settings;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.components.StoragePathMacros;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service(Service.Level.PROJECT)
@State(name = "fr.tolc.jahia.plugin.settings.JahiaProjectSettings", storages = @Storage(StoragePathMacros.PRODUCT_WORKSPACE_FILE))
public final class JahiaProjectSettings implements PersistentStateComponent<JahiaProjectSettings.JahiaProjectState> {
    private static final Logger logger = LoggerFactory.getLogger(JahiaProjectSettings.class);

    private Project project;
    private JahiaProjectState state = new JahiaProjectState();

    public JahiaProjectSettings(@NotNull Project project) {
        this.project = project;
    }

    public static JahiaProjectSettings getInstance(@NotNull final Project project) {
        return project.getService(JahiaProjectSettings.class);
    }

    public String getJahiaServerPath() {
        return getState().jahiaServerPath;
    }

    public void setJahiaServerPath(String jahiaServerPath) {
        getState().jahiaServerPath = jahiaServerPath;
    }

    @Override
    public @Nullable JahiaProjectSettings.JahiaProjectState getState() {
        return state;
    }

    @Override
    public void loadState(@NotNull JahiaProjectSettings.JahiaProjectState state) {
        this.state = state;
    }


    /**
     * Settings state class
     */
    public static class JahiaProjectState {
        public String jahiaServerPath;
    }
}

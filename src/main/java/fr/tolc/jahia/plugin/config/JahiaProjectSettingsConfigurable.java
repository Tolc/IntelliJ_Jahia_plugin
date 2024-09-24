package fr.tolc.jahia.plugin.config;

import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.options.BoundConfigurable;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogPanel;
import com.intellij.openapi.ui.LabeledComponent;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import fr.tolc.jahia.plugin.messages.CndBundle;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.util.Objects;

public class JahiaProjectSettingsConfigurable extends BoundConfigurable {
    private static final Logger logger = LoggerFactory.getLogger(JahiaProjectSettingsConfigurable.class);

    private final @NotNull Project project;

    private DialogPanel dialogPanel;
    private JTextField serverPathTextField;

    public JahiaProjectSettingsConfigurable(@NotNull Project project) {
        super(CndBundle.message("jahia.settings.project.configurable.name"), null);
        this.project = project;
    }


    @Override
    public @NotNull DialogPanel createPanel() {
        if (dialogPanel == null) {
            dialogPanel = new DialogPanel(new BorderLayout());

            LabeledComponent<TextFieldWithBrowseButton> labeledComponent = LabeledComponent.create(new TextFieldWithBrowseButton(), CndBundle.message("jahia.settings.project.configurable.jahiaServerPath"), BorderLayout.WEST);

            FileChooserDescriptor descriptor = FileChooserDescriptorFactory.createSingleFolderDescriptor()
                    .withTitle(CndBundle.message("jahia.settings.project.configurable.jahiaServerPath.browser"))
                    .withDescription(CndBundle.message("jahia.settings.project.configurable.jahiaServerPath.browser.description"));
            labeledComponent.getComponent().addBrowseFolderListener(null, null, null, descriptor);
            serverPathTextField = labeledComponent.getComponent().getTextField();

            dialogPanel.add(labeledComponent, BorderLayout.NORTH);
        }

        return dialogPanel;
    }

    @Override
    public boolean isModified() {
        JahiaProjectSettings jahiaProjectSettings = JahiaProjectSettings.getInstance(project);
        return !Objects.equals(jahiaProjectSettings.getJahiaServerPath(), serverPathTextField.getText());
    }

    @Override
    public void apply() {
        JahiaProjectSettings jahiaProjectSettings = JahiaProjectSettings.getInstance(project);
        jahiaProjectSettings.setJahiaServerPath(serverPathTextField.getText());
    }

    @Override
    public void reset() {
        JahiaProjectSettings jahiaProjectSettings = JahiaProjectSettings.getInstance(project);
        serverPathTextField.setText(jahiaProjectSettings.getJahiaServerPath());
    }

    @Override
    public void disposeUIResources() {
        dialogPanel = null;
        serverPathTextField = null;
    }
}

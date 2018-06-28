package fr.tolc.jahia.intellij.plugin.cnd.dialogs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import javax.swing.*;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.WindowManager;
import fr.tolc.jahia.intellij.plugin.cnd.utils.CndPluginUtil;

public class CreateNodeTypeViewDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField viewName;
    private JCheckBox hiddenViewCheckBox;
    private JComboBox viewType;
    private JComboBox viewLanguage;
    private JComboBox moduleSelect;
    private JLabel moduleLabel;

    private Module module;
    private boolean okClicked = false;

    public CreateNodeTypeViewDialog(Project project, Module module, String nodeType) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        this.module = module;
        Module[] projectModules = CndPluginUtil.getProjectModules(project);
        if (projectModules.length >= 2) {
            List<Module> modulesSorted = Arrays.asList(projectModules);
            modulesSorted.sort(Comparator.comparing(Module::getName));
            for (Module projectModule : modulesSorted) {
                moduleSelect.addItem(new ModuleWrapper(projectModule));
            }
            if (this.module != null) {
                moduleSelect.setSelectedItem(new ModuleWrapper(this.module));
            }
        } else {
            this.module = projectModules[0];
            //hide
            moduleSelect.setVisible(false);
            moduleLabel.setVisible(false);
        }


        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        setTitle("Create new view for " + nodeType);
        pack();
        setSize(350, 300);
        setLocationRelativeTo(WindowManager.getInstance().getFrame(project));
    }

    private void onOK() {
        okClicked = true;
        dispose();
    }

    private void onCancel() {
        dispose();
    }

    public String getViewName() {
        return viewName.getText();
    }

    public boolean isHiddenView() {
        return hiddenViewCheckBox.isSelected();
    }

    public String getViewType() {
        return (String) viewType.getSelectedItem();
    }

    public String getViewLanguage() {
        return (String) viewLanguage.getSelectedItem();
    }

    public Module getModule() {
        if (module != null) {
            return module;
        }
        return ((ModuleWrapper) moduleSelect.getSelectedItem()).getModule();
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    public class ModuleWrapper {
        private Module module;

        public ModuleWrapper(Module module) {
            this.module = module;
        }

        public Module getModule() {
            return module;
        }

        @Override
        public String toString() {
            return module.getName();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof  ModuleWrapper)
                return this.module.equals(((ModuleWrapper) obj).getModule());
            return false;
        }
    }
}

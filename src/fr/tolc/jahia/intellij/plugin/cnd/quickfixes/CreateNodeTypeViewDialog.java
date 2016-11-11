package fr.tolc.jahia.intellij.plugin.cnd.quickfixes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.WindowManager;

public class CreateNodeTypeViewDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField viewName;
    private JCheckBox hiddenViewCheckBox;
    private JComboBox viewType;
    private JComboBox viewLanguage;

    private boolean okClicked = false;

    public CreateNodeTypeViewDialog(Project project, String nodeType) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

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
        setSize(275, 275);
        setLocationRelativeTo(WindowManager.getInstance().getFrame(project));
    }

    private void onOK() {
        // add your code here
        okClicked = true;
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
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

    public boolean isOkClicked() {
        return okClicked;
    }
}

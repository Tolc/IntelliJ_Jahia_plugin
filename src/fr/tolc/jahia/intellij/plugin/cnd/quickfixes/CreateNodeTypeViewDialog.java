package fr.tolc.jahia.intellij.plugin.cnd.quickfixes;

import javax.swing.*;
import java.awt.event.*;

public class CreateNodeTypeViewDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField viewName;
    private JCheckBox hiddenViewCheckBox;
    private JComboBox viewType;
    private JComboBox viewLanguage;

    private boolean okClicked = false;

    public CreateNodeTypeViewDialog() {
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

    public static void main(String[] args) {
        CreateNodeTypeViewDialog dialog = new CreateNodeTypeViewDialog();
        dialog.pack();
        dialog.setVisible(true);
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

package com.dg.mdsrose.view;

import com.dg.mdsrose.util.DataDataset;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class DataSelectColumn extends JFrame implements ActionListener {

    private JButton confirmButton;
    private JPanel dataSelectColumnPanel;
    private JTextField numColumnField;
    private JLabel columnLabel;
    private JButton generateRowButton;
    private JPanel rowPanel;

    private Map<Integer,String> selectedColumns = new HashMap<>();
    private final String path;
    private final String prefixIndexColumnInputField = "numberColumn";
    private final String prefixNameColumnInputField = "nameColumn";
    private final Integer numColumnsDataset;
    private boolean rowGenerated;

    public DataSelectColumn(String path) {
        this.setTitle("Select Column");
        this.setContentPane(dataSelectColumnPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(490, 300));
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        generateRowButton.addActionListener(this);
        confirmButton.addActionListener(this);

        this.path = path;
        DataDataset dataDataset = new DataDataset(path);
        numColumnsDataset = dataDataset.getNumberOfColumns();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(generateRowButton)) {
            generateRow();
        } else if (e.getSource().equals(confirmButton)) {
            confirmRow();
        }
    }

    private void confirmRow() {
        checkInputField();
        if (!rowGenerated) {
            JOptionPane.showMessageDialog(
                    this,
                    "First generate rows.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }
        selectedColumns.clear();

        Component[] components = rowPanel.getComponents();
        Integer numColumn = null;
        String nameColumn = null;
        for (Component component : components) {
            if (component instanceof JTextField textField) {
                if(textField.getName().startsWith(prefixIndexColumnInputField)) {
                    numColumn = Integer.parseInt(textField.getText());
                }
                if(textField.getName().startsWith(prefixNameColumnInputField)) {
                    nameColumn = textField.getText();
                }
                if(Objects.nonNull(numColumn) && Objects.nonNull(nameColumn)) {
                    selectedColumns.put(numColumn, nameColumn);
                    numColumn = null;
                    nameColumn = null;
                }
            }
        }

        new SelectShapeAndColor(this.path,selectedColumns);
        this.dispose();
    }

    private void checkInputField() {
        Component[] components = rowPanel.getComponents();
        for (Component component : components) {
            if (component instanceof JTextField textField) {
                if(checkInputAreEmpty(textField)){
                    JOptionPane.showMessageDialog(
                            this,
                            "Fill all rows.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }
                if (checkNumericField(textField)) {
                    if(!checkDigitInputFieldNumericColumn(textField)){
                        JOptionPane.showMessageDialog(
                                this,
                                "You must enter a numeric value.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE
                        );
                        return;
                    }
                    if(checkInputFieldMaxColumn(textField)){
                        JOptionPane.showMessageDialog(
                                this,
                                "Overflow max column.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE
                        );
                        return;
                    }
                }
            }
        }
    }

    private boolean checkInputFieldMaxColumn(JTextField textField) {
        return Integer.parseInt(textField.getText()) > numColumnsDataset;
    }

    private boolean checkNumericField(JTextField textField) {
        return textField.getName().startsWith(prefixIndexColumnInputField);
    }

    private boolean checkInputAreEmpty(JTextField textField) {
        return textField.getText().isEmpty();
    }

    private boolean checkDigitInputFieldNumericColumn(JTextField textField) {
        return StringUtils.isNumeric(textField.getText());
    }

    private void generateRow() {
        if (!validateNumberColumns()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Invalid number.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }
        if(!checkMaxNumColumn()){
            JOptionPane.showMessageDialog(
                    this,
                    "Invalid number.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }
        this.rowGenerated = true;
        rowPanel.removeAll();
        rowPanel.setLayout(new GridLayout(Integer.parseInt(numColumnField.getText()), 0));
        for (int i = 0; i < Integer.parseInt(numColumnField.getText()); i++) {
            JTextField numColField = new JTextField(0);
            numColField.setName(prefixIndexColumnInputField+i);
            JLabel numColLabel = new JLabel(".");
            JTextField nameColField = new JTextField(0);
            nameColField.setName(prefixNameColumnInputField+i);
            rowPanel.add(numColField);
            rowPanel.add(numColLabel);
            rowPanel.add(nameColField);
        }
        rowPanel.revalidate();
        rowPanel.repaint();
    }

    private boolean checkMaxNumColumn() {
        int numColumn = Integer.parseInt(numColumnField.getText());
        return numColumn <= numColumnsDataset - 1;
    }

    private boolean validateNumberColumns() {
        String string = numColumnField.getText();
        if (string.isEmpty()) {
            return false;
        }
        return StringUtils.isNumeric(string);
    }


    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        dataSelectColumnPanel = new JPanel();
        dataSelectColumnPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(5, 2, new Insets(10, 10, 10, 10), -1, -1));
        confirmButton = new JButton();
        confirmButton.setText("Confirm");
        dataSelectColumnPanel.add(confirmButton, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        numColumnField = new JTextField();
        dataSelectColumnPanel.add(numColumnField, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        columnLabel = new JLabel();
        columnLabel.setText("Define number of column to visualize.");
        dataSelectColumnPanel.add(columnLabel, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        generateRowButton = new JButton();
        generateRowButton.setText("Generate");
        dataSelectColumnPanel.add(generateRowButton, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JSeparator separator1 = new JSeparator();
        dataSelectColumnPanel.add(separator1, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(-1, 20), null, 0, false));
        rowPanel = new JPanel();
        rowPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        dataSelectColumnPanel.add(rowPanel, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return dataSelectColumnPanel;
    }

}

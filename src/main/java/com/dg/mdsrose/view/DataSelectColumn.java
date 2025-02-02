package com.dg.mdsrose.view;

import com.dg.mdsrose.project.processor.DataFileProcessor;
import com.dg.mdsrose.project.extractor.DataDatasetColumnExtractor;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DataSelectColumn extends SelectColumnBaseFrame implements ActionListener {

    private JButton confirmButton;
    private JPanel dataSelectColumnPanel;
    private JTextField numColumnField;
    private JLabel columnLabel;
    private JButton generateRowButton;
    private JPanel rowPanel;

    private static final String PREFIX_INDEX_COLUMN_INPUT_FIELD = "numberColumn";
    private static final String PREFIX_NAME_COLUMN_INPUT_FIELD = "nameColumn";
    private final Map<Integer, String> selectedColumns = new HashMap<>();
    private final String path;
    private final Integer numColumnsDataset;
    private boolean rowGenerated;

    public DataSelectColumn(String path) {
        createAndShowGUI();
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                new Homepage();
            }
        });
        generateRowButton.addActionListener(this);
        confirmButton.addActionListener(this);

        this.path = path;
        DataDatasetColumnExtractor dataDatasetColumnExtractor = new DataDatasetColumnExtractor(path);
        numColumnsDataset = dataDatasetColumnExtractor.extractColumnsMetadata();
    }

    @Override
    protected String setTitle() {
        return "Data file column selection";
    }

    @Override
    protected JPanel setContentPanel() {
        return dataSelectColumnPanel;
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
                if (textField.getName().startsWith(PREFIX_INDEX_COLUMN_INPUT_FIELD)) {
                    numColumn = Integer.parseInt(textField.getText());
                }
                if (textField.getName().startsWith(PREFIX_NAME_COLUMN_INPUT_FIELD)) {
                    nameColumn = textField.getText();
                }
                if (Objects.nonNull(numColumn) && Objects.nonNull(nameColumn)) {
                    selectedColumns.put(numColumn, nameColumn);
                    numColumn = null;
                    nameColumn = null;
                }
            }
        }

        partialSave(new DataFileProcessor(path, selectedColumns), selectedColumns);
        new SelectShapeAndColor();
        this.dispose();
    }

    private void checkInputField() {
        Component[] components = rowPanel.getComponents();
        for (Component component : components) {
            if (component instanceof JTextField textField) {
                if (checkInputAreEmpty(textField)) {
                    JOptionPane.showMessageDialog(
                        this,
                        "Fill all rows.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }
                if (checkNumericField(textField)) {
                    if (!checkDigitInputFieldNumericColumn(textField)) {
                        JOptionPane.showMessageDialog(
                            this,
                            "You must enter a numeric value.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                        );
                        return;
                    }
                    if (checkInputFieldMaxColumn(textField)) {
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
        return textField.getName().startsWith(PREFIX_INDEX_COLUMN_INPUT_FIELD);
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
        if (!checkMaxNumColumn()) {
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
            numColField.setToolTipText("Insert column number");
            numColField.setName(PREFIX_INDEX_COLUMN_INPUT_FIELD + i);
            JTextField nameColField = new JTextField(0);
            nameColField.setToolTipText("Insert column label");
            nameColField.setName(PREFIX_NAME_COLUMN_INPUT_FIELD + i);
            rowPanel.add(numColField);
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
        dataSelectColumnPanel.setLayout(new GridLayoutManager(4, 2, new Insets(10, 10, 10, 10), -1, -1));
        confirmButton = new JButton();
        confirmButton.setText("Confirm");
        dataSelectColumnPanel.add(confirmButton, new GridConstraints(3, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        numColumnField = new JTextField();
        dataSelectColumnPanel.add(numColumnField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        columnLabel = new JLabel();
        columnLabel.setText("Define number of column to visualize.");
        dataSelectColumnPanel.add(columnLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        generateRowButton = new JButton();
        generateRowButton.setText("Generate");
        dataSelectColumnPanel.add(generateRowButton, new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        rowPanel = new JPanel();
        rowPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        dataSelectColumnPanel.add(rowPanel, new GridConstraints(2, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return dataSelectColumnPanel;
    }

}

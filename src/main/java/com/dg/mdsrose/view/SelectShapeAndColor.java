package com.dg.mdsrose.view;

import com.dg.mdsrose.enums.Colors;
import com.dg.mdsrose.enums.Shapes;
import com.dg.mdsrose.project.ConcreteShapeBuilder;
import com.dg.mdsrose.project.Shape;
import com.dg.mdsrose.project.ShapeClass;
import com.dg.mdsrose.project.processor.DataFileProcessor;
import com.dg.mdsrose.project.processor.FileProcessorResult;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class SelectShapeAndColor extends JFrame implements ActionListener {
    private JPanel selectShapeAndColorPanel;
    private JButton confirmButton;
    private JPanel classesPanel;


    private final String[] colors = {Colors.BLUE.getValue(), Colors.GREEN.getValue(), Colors.RED.getValue(), Colors.BLACK.getValue(), Colors.YELLOW.getValue()};
    private final String[] markers = {Shapes.CIRCLE.getValue(), Shapes.SQUARE.getValue(), Shapes.TRIANGLE_UP.getValue(), Shapes.TRIANGLE_DOWN.getValue(), Shapes.DIAMOND.getValue()};
    private final String path;
    private Map<Integer, String> selectedColumns = new HashMap<>();
    private FileProcessorResult result;
    private List<JComboBox<String>> colorComponents = new ArrayList<>();
    private List<JComboBox<String>> markerComponents = new ArrayList<>();
    private List<JLabel> labelComponents = new ArrayList<>();
    private int numClasses;

    SelectShapeAndColor(String path, Map<Integer, String> selectedColumns) {
        this.setTitle("Select shape and color");
        this.setContentPane(selectShapeAndColorPanel);
        this.setPreferredSize(new Dimension(500, 300));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.confirmButton.addActionListener(this);

        this.path = path;
        this.selectedColumns = selectedColumns;
        generateDatasetClass();
    }

    private void generateDatasetClass() {
        try {
            DataFileProcessor dataFileProcessor = new DataFileProcessor(path, selectedColumns);
            result = dataFileProcessor.process();
            List<String> datasetClasses = result.classes();
            numClasses = datasetClasses.size();
            classesPanel.setLayout(new GridLayout(numClasses, 3, 10, 10));
            for (String datasetClass : datasetClasses) {
                JComboBox<String> colorClassComboBox = new JComboBox<>(colors);
                JComboBox<String> markerClassComboBox = new JComboBox<>(markers);
                JLabel jLabel = new JLabel(datasetClass);
                classesPanel.add(colorClassComboBox);
                classesPanel.add(markerClassComboBox);
                classesPanel.add(jLabel);
                colorComponents.add(colorClassComboBox);
                markerComponents.add(markerClassComboBox);
                labelComponents.add(jLabel);
            }
            classesPanel.revalidate();
            classesPanel.repaint();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(confirmButton)) {
            confirmShapeAndColor();
        }
    }

    private void confirmShapeAndColor() {
        boolean hasDuplicated = checkDuplicatedInputs();
        if (hasDuplicated) {
            JOptionPane.showMessageDialog(
                this,
                "Duplicate color and marker chosen!",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        List<ShapeClass> shapeClasses = getShapeClasses();
        List<Shape> listShape = shapeClasses.stream()
            .map(SelectShapeAndColor::mapShape)
            .toList();

        new ShowProject(result, listShape);
        this.dispose();
    }

    private boolean checkDuplicatedInputs() {
        for (int i = 0; i < colorComponents.size(); i++) {
            for (int j = 0; j < colorComponents.size(); j++) {
                if (
                    i != j &&
                        Objects.equals(colorComponents.get(i).getSelectedItem(), colorComponents.get(j).getSelectedItem()) &&
                        Objects.equals(markerComponents.get(i).getSelectedItem(), markerComponents.get(j).getSelectedItem())
                ) {
                    return true;
                }
            }
        }
        return false;
    }

    private static Shape mapShape(ShapeClass shapeClass) {
        ConcreteShapeBuilder shapeBuilder = new ConcreteShapeBuilder();
        shapeBuilder.setColor(Objects.requireNonNull(Colors.from(shapeClass.getColor())).getColor());
        shapeBuilder.setMarker(Objects.requireNonNull(Shapes.from(shapeClass.getMarker())).getShape());
        shapeBuilder.setLabel(shapeClass.getLabel());
        return shapeBuilder.getResult();
    }

    private List<ShapeClass> getShapeClasses() {
        List<ShapeClass> shapeClasses = new ArrayList<>();
        for (int i = 0; i < numClasses; i++) {
            ShapeClass shapeClass = new ShapeClass();
            shapeClass.setColor(Objects.requireNonNull(colorComponents.get(i).getSelectedItem()).toString());
            shapeClass.setMarker(Objects.requireNonNull(markerComponents.get(i).getSelectedItem()).toString());
            shapeClass.setLabel(labelComponents.get(i).getText());
            shapeClasses.add(shapeClass);
        }
        return shapeClasses;
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
        selectShapeAndColorPanel = new JPanel();
        selectShapeAndColorPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(10, 10, 10, 10), -1, -1));
        confirmButton = new JButton();
        confirmButton.setText("Confirm");
        selectShapeAndColorPanel.add(confirmButton, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        classesPanel = new JPanel();
        classesPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        selectShapeAndColorPanel.add(classesPanel, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return selectShapeAndColorPanel;
    }

}

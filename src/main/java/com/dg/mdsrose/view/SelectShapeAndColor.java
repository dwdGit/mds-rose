package com.dg.mdsrose.view;

import com.dg.mdsrose.enums.ColorOption;
import com.dg.mdsrose.enums.MarkerOption;
import com.dg.mdsrose.project.InMemoryProjectService;
import com.dg.mdsrose.project.ProjectService;
import com.dg.mdsrose.project.builder.ConcreteShapeBuilder;
import com.dg.mdsrose.project.builder.SelectedShape;
import com.dg.mdsrose.project.model.Shape;
import com.dg.mdsrose.project.processor.CSVFileProcessor;
import com.dg.mdsrose.project.processor.DataFileProcessor;
import com.dg.mdsrose.project.processor.FileProcessor;
import com.dg.mdsrose.project.processor.FileProcessorResult;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.List;
import java.util.*;

import static com.dg.mdsrose.enums.FileMetadata.CSV;
import static com.dg.mdsrose.enums.FileMetadata.DATA;

public class SelectShapeAndColor extends BaseJFrame implements ActionListener {
    private JPanel selectShapeAndColorPanel;
    private JButton confirmButton;
    private JPanel classesPanel;


    private final String path;
    private final List<JComboBox<String>> colorComponents = new ArrayList<>();
    private final List<JComboBox<String>> markerComponents = new ArrayList<>();
    private final List<JLabel> labelComponents = new ArrayList<>();

    private Map<Integer, String> selectedColumns = new HashMap<>();
    private FileProcessorResult result;
    private int numClasses;

    SelectShapeAndColor(String path, Map<Integer, String> selectedColumns) {
        createAndShowGUI();
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                new Homepage();
            }
        });
        this.confirmButton.addActionListener(this);

        this.path = path;
        this.selectedColumns = selectedColumns;
        generateDatasetClass();
    }

    @Override
    protected String setTitle() {
        return "Select shape and color";
    }

    @Override
    protected JPanel setContentPanel() {
        return selectShapeAndColorPanel;
    }

    @Override
    protected Dimension setPreferredSize() {
        return new Dimension(500, 300);
    }

    private void generateDatasetClass() {
        try {
            FileProcessor fileProcessor;
            if (path.endsWith(DATA.getExtension())) {
                fileProcessor = new DataFileProcessor(path, selectedColumns);
            } else if (path.endsWith(CSV.getExtension())) {
                fileProcessor = new CSVFileProcessor(path, selectedColumns);
            } else {
                JOptionPane.showMessageDialog(
                    this,
                    "Cannot generate dataset class!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }
            result = fileProcessor.process();
            List<String> datasetClasses = result.classes();
            numClasses = datasetClasses.size();
            classesPanel.setLayout(new GridLayout(numClasses, 3, 10, 10));
            for (String datasetClass : datasetClasses) {
                JComboBox<String> colorClassComboBox = new JComboBox<>(ColorOption.comboBoxOptions());
                JComboBox<String> markerClassComboBox = new JComboBox<>(MarkerOption.comboBoxOptions());
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

        List<SelectedShape> selectedShapes = getSelectedShapes();
        List<Shape> shapes = selectedShapes.stream()
            .map(selectedShape ->
                ConcreteShapeBuilder.of(
                    selectedShape.getLabel(),
                    ColorOption.from(selectedShape.getColor()),
                    MarkerOption.from(selectedShape.getMarker())
                )
            )
            .map(ConcreteShapeBuilder::getResult)
            .toList();

        ProjectService projectService = new InMemoryProjectService().createProjectService();
        Long projectId = projectService.save(result.completeDatasetRows(), shapes, selectedColumns);
        new ShowProject(projectService, projectId, true);
        this.dispose();
    }

    private boolean checkDuplicatedInputs() {
        for (int i = 0; i < colorComponents.size(); i++) {
            for (int j = 0; j < colorComponents.size(); j++) {
                if (haveSameShapeAndColor(i, j)) return true;
            }
        }
        return false;
    }

    private boolean haveSameShapeAndColor(int i, int j) {
        return i != j &&
            Objects.equals(colorComponents.get(i).getSelectedItem(), colorComponents.get(j).getSelectedItem()) &&
            Objects.equals(markerComponents.get(i).getSelectedItem(), markerComponents.get(j).getSelectedItem());
    }

    private List<SelectedShape> getSelectedShapes() {
        List<SelectedShape> selectedShapes = new ArrayList<>();
        for (int i = 0; i < numClasses; i++) {
            SelectedShape selectedShape = new SelectedShape();
            selectedShape.setColor(Objects.requireNonNull(colorComponents.get(i).getSelectedItem()).toString());
            selectedShape.setMarker(Objects.requireNonNull(markerComponents.get(i).getSelectedItem()).toString());
            selectedShape.setLabel(labelComponents.get(i).getText());
            selectedShapes.add(selectedShape);
        }
        return selectedShapes;
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
        selectShapeAndColorPanel.setLayout(new GridLayoutManager(2, 1, new Insets(10, 10, 10, 10), -1, -1));
        confirmButton = new JButton();
        confirmButton.setText("Confirm");
        selectShapeAndColorPanel.add(confirmButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        classesPanel = new JPanel();
        classesPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        selectShapeAndColorPanel.add(classesPanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return selectShapeAndColorPanel;
    }

}

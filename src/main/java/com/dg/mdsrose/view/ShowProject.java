package com.dg.mdsrose.view;

import com.dg.mdsrose.project.Shape;
import com.dg.mdsrose.project.processor.DatasetRow;
import com.dg.mdsrose.project.processor.FileProcessorResult;
import org.apache.commons.lang3.ObjectUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowProject extends JFrame implements ActionListener {
    private JPanel showProjectPanel;
    private JButton showPointsButton;
    private JPanel chartContainerPanel;

    private final Map<Integer, String> selectedColumns;
    private final FileProcessorResult fileProcessorResult;
    private DefaultTableModel selectedPoints;


    public ShowProject(FileProcessorResult result, List<Shape> shapes, Map<Integer, String> selectedColumns) {
        this.setTitle("Show project");
        this.setContentPane(showProjectPanel);
        this.setPreferredSize(new Dimension(800, 640));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);

        this.fileProcessorResult = result;
        this.selectedColumns = selectedColumns;

        showProjectPanel.setLayout(new BoxLayout(showProjectPanel, BoxLayout.Y_AXIS));
        chartContainerPanel.setLayout(new GridLayout(1, 1));
        showProjectPanel.add(chartContainerPanel);

        generateChart(result, shapes);
        this.setVisible(true);
        showPointsButton.addActionListener(this);
    }

    private void generateChart(FileProcessorResult result, List<Shape> shapes) {
        Map<String, XYSeries> chartDataMap = generateChartData(result, shapes);
        XYSeriesCollection dataset = new XYSeriesCollection();
        chartDataMap.keySet()
                .forEach(key -> dataset.addSeries(chartDataMap.get(key)));

        // Create chart
        JFreeChart chart = ChartFactory.createXYLineChart(
                "MDS Graph",
                "X",
                "Y",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        int currIdx = 0;
        for (Shape shape : shapes) {
            renderer.setSeriesPaint(currIdx, shape.getColor());
            renderer.setSeriesShapesVisible(currIdx, true);
            renderer.setSeriesShape(currIdx, shape.getMarker());
            renderer.setSeriesLinesVisible(currIdx, false);
            currIdx++;
        }
        chart.getXYPlot().setRenderer(renderer);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setFillZoomRectangle(true);

        chartPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                ValueAxis domainAxis = chart.getXYPlot().getDomainAxis();
                ValueAxis rangeAxis = chart.getXYPlot().getRangeAxis();
                getPointSpotlight(domainAxis, rangeAxis);
                showPointsButton.setVisible(true);
                showPointsButton.setEnabled(true);
            }
        });

        chartContainerPanel.add(chartPanel);
    }

    private void getPointSpotlight(ValueAxis domainAxis, ValueAxis rangeAxis) {
        double xLowerBound = domainAxis.getLowerBound();
        double xUpperBound = domainAxis.getUpperBound();
        double yLowerBound = rangeAxis.getLowerBound();
        double yUpperBound = rangeAxis.getUpperBound();

        List<DatasetRow> datasetRows = fileProcessorResult.datasetRows();
        List<DatasetRow> listSelectedPoints = datasetRows.stream()
                .filter(datasetRow -> datasetRow.x() >= xLowerBound && datasetRow.x() <= xUpperBound &&
                        datasetRow.y() >= yLowerBound && datasetRow.y() <= yUpperBound)
                .toList();
        if (ObjectUtils.isNotEmpty(listSelectedPoints)) {

            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("class");
            model.addColumn("mds-x");
            model.addColumn("mds-y");
            selectedColumns.values().forEach(model::addColumn);
            listSelectedPoints.forEach(datasetRow -> {
                int arraySize = datasetRow.features().length;
                Object[] tableRow = new Object[3 + arraySize];
                tableRow[0] = datasetRow.label();
                tableRow[1] = datasetRow.x();
                tableRow[2] = datasetRow.y();
                double[] array = datasetRow.features();
                for (int i = 0; i < arraySize; i++) {
                    tableRow[3 + i] = array[i];
                }
                model.addRow(tableRow);
            });
            this.selectedPoints = model;
        }
    }

    private static Map<String, XYSeries> generateChartData(FileProcessorResult result, List<Shape> shapes) {
        Map<String, XYSeries> dataMap = new HashMap<>();
        shapes.forEach(shape -> dataMap.put(shape.getLabel(), new XYSeries(shape.getLabel())));
        result.datasetRows().forEach(datasetRow ->
                dataMap.get(datasetRow.label())
                        .add(datasetRow.x(), datasetRow.y())
        );
        return dataMap;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(showPointsButton)) {
            makeFrameSelectedPoints();
        }
    }

    private void makeFrameSelectedPoints() {
        JFrame frame = new JFrame();
        frame.setTitle("Selected Points");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setPreferredSize(new Dimension(800, 300));
        JTable table = new JTable(selectedPoints);
        table.setFillsViewportHeight(true);
        JScrollPane jScrollPane = new JScrollPane(table);
        jScrollPane.setLayout(new ScrollPaneLayout());
        frame.add(jScrollPane);
        frame.pack();
        frame.setVisible(true);
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
        showProjectPanel = new JPanel();
        showProjectPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(10, 10, 10, 10), -1, -1));
        chartContainerPanel = new JPanel();
        chartContainerPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        showProjectPanel.add(chartContainerPanel, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        showPointsButton = new JButton();
        showPointsButton.setEnabled(false);
        showPointsButton.setText("Show selected points");
        showPointsButton.setVisible(false);
        showProjectPanel.add(showPointsButton, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, new Dimension(30, 10), new Dimension(30, 10), new Dimension(130, 10), 1, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return showProjectPanel;
    }

}

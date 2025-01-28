package com.dg.mdsrose.view;

import com.dg.mdsrose.project.Shape;
import com.dg.mdsrose.project.processor.FileProcessorResult;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowProject extends JFrame {
    private JPanel showProjectPanel;

    public ShowProject(FileProcessorResult result, List<Shape> shapes) {
        this.setTitle("Show project");
        this.setContentPane(showProjectPanel);
        this.setPreferredSize(new Dimension(500, 300));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        showProjectPanel.setLayout(new GridLayout(1, 1, 10, 10));
        generateChart(result, shapes);
        this.setVisible(true);
    }

    private void generateChart(FileProcessorResult result, List<Shape> shapes) {
        Map<String, XYSeries> chartDataMap = generateChartData(result, shapes);
        XYSeriesCollection dataset = new XYSeriesCollection();
        chartDataMap.keySet()
            .forEach(key -> dataset.addSeries(chartDataMap.get(key)));

        // Create chart
        JFreeChart chart = ChartFactory.createXYLineChart(
            "Sine Wave",
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
                double lowerBound = domainAxis.getLowerBound();
                double upperBound = domainAxis.getUpperBound();
                System.out.println("Current Range x: " + lowerBound + " to " + upperBound);
                System.out.println("Current Range y: " + rangeAxis.getLowerBound() + " to " + rangeAxis.getUpperBound());
            }
        });

        showProjectPanel.add(chartPanel);
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

    /*private void generateChart(FileProcessorResult result, List<Shape> shapes) {
        Map<String, ChartData> chartDataMap = generateChartData(result, shapes);

        // Create Chart
        XYChart chart = new XYChartBuilder()
            .width(800)
            .height(640)
            .title("MDS")
            .xAxisTitle("X")
            .yAxisTitle("Y")
            .build();

        // Customize Chart
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Scatter);
        chart.getStyler().setChartTitleVisible(false);
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideSW);
        chart.getStyler().setMarkerSize(8);
        chart.getStyler().setCursorEnabled(true);
        //chart.getStyler().setToolTipsEnabled(true);
        chart.getStyler().setZoomEnabled(true);

        // Series
        shapes.forEach(shape -> {
            ChartData cd = chartDataMap.get(shape.getLabel());
            chart.addSeries(shape.getLabel(), cd.getXValues(), cd.getYValues())
                .setMarker(shape.getMarker())
                .setMarkerColor(shape.getColor());
        });

        // Create JPanel to display the chart
        //DrawableChartPanel<XYChart> chartPanel = new DrawableChartPanel<>(chart);
        XChartPanel<XYChart> chartPanel = new XChartPanel<>(chart);
        this.add(chartPanel);
    }

    private static Map<String, ChartData> generateChartData(FileProcessorResult result, List<Shape> shapes) {
        Map<String, ChartData> dataMap = new HashMap<>();
        shapes.forEach(shape -> dataMap.put(shape.getLabel(), new ChartData()));
        result.datasetRows().forEach(datasetRow ->
            dataMap.get(datasetRow.label())
                .add(datasetRow.x(), datasetRow.y())
        );
        return dataMap;
    }*/


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
        showProjectPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(10, 10, 10, 10), -1, -1));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return showProjectPanel;
    }

}

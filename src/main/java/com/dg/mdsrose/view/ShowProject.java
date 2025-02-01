package com.dg.mdsrose.view;

import com.dg.mdsrose.enums.ColorOption;
import com.dg.mdsrose.enums.MarkerOption;
import com.dg.mdsrose.project.DBProjectService;
import com.dg.mdsrose.project.ProjectService;
import com.dg.mdsrose.project.model.*;
import com.dg.mdsrose.user.UserSession;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
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
import java.awt.event.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ShowProject extends BaseJFrame implements ActionListener {
    private JPanel showProjectPanel;
    private JButton showPointsButton;
    private JPanel chartContainerPanel;
    private JButton saveButton;

    private final UserSession userSession = UserSession.getInstance();
    private final ProjectService projectService;
    private final List<DatasetClass> datasetClasses;
    private final List<DatasetRow> datasetRows;
    private final List<DatasetFeature> datasetFeatures;
    private DefaultTableModel selectedPoints;


    public ShowProject(ProjectService projectService) {
        createAndShowGUI();

        showProjectPanel.setLayout(new BoxLayout(showProjectPanel, BoxLayout.Y_AXIS));
        chartContainerPanel.setLayout(new GridLayout(1, 1));
        showProjectPanel.add(chartContainerPanel);

        showPointsButton.addActionListener(this);
        saveButton.addActionListener(this);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                new Homepage();
            }
        });

        this.projectService = projectService;
        this.datasetClasses = projectService.findAllDatasetClasses();
        this.datasetRows = projectService.findAllDatasetRows();
        this.datasetFeatures = projectService.findAllDatasetFeatures();
        generateChart();
        this.setVisible(true);
    }

    public ShowProject(ProjectService projectService, Long projectId) {
        createAndShowGUI();

        showProjectPanel.setLayout(new BoxLayout(showProjectPanel, BoxLayout.Y_AXIS));
        chartContainerPanel.setLayout(new GridLayout(1, 1));
        showProjectPanel.add(chartContainerPanel);

        saveButton.setVisible(false);
        saveButton.setEnabled(false);
        showPointsButton.addActionListener(this);
        saveButton.addActionListener(this);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                new Homepage();
            }
        });

        this.projectService = projectService;
        this.datasetClasses = projectService.findDatasetClassesByProjectId(projectId);
        this.datasetRows = projectService.findDatasetRowsByProjectId(projectId);
        this.datasetFeatures = projectService.findDatasetFeaturesByProjectId(projectId);
        generateChart();
        this.setVisible(true);
    }

    @Override
    protected String setTitle() {
        return "Show project";
    }

    @Override
    protected JPanel setContentPanel() {
        return showProjectPanel;
    }

    private void generateChart() {
        Map<Long, XYSeries> chartDataMap = generateChartData();
        XYSeriesCollection dataset = new XYSeriesCollection();
        chartDataMap.keySet()
            .forEach(key -> dataset.addSeries(chartDataMap.get(key)));

        // Create chart
        JFreeChart chart = ChartFactory.createXYLineChart(
            "MDS Chart",
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
        for (DatasetClass datasetClass : datasetClasses) {
            ColorOption colorOption = ColorOption.valueOf(datasetClass.getColor());
            MarkerOption markerOption = MarkerOption.valueOf(datasetClass.getMarker());

            renderer.setSeriesPaint(currIdx, colorOption.getColor());
            renderer.setSeriesShapesVisible(currIdx, true);
            renderer.setSeriesShape(currIdx, markerOption.getShape());
            renderer.setSeriesLinesVisible(currIdx, false);
            currIdx++;
        }
        chart.getXYPlot().setRenderer(renderer);

        chartContainerPanel.add(buildChartPanel(chart));
    }

    private ChartPanel buildChartPanel(JFreeChart chart) {
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
        return chartPanel;
    }

    private void getPointSpotlight(ValueAxis domainAxis, ValueAxis rangeAxis) {
        double xLowerBound = domainAxis.getLowerBound();
        double xUpperBound = domainAxis.getUpperBound();
        double yLowerBound = rangeAxis.getLowerBound();
        double yUpperBound = rangeAxis.getUpperBound();

        List<DatasetRow> listSelectedPoints = datasetRows.stream()
            .filter(datasetRow -> datasetRow.getX() >= xLowerBound && datasetRow.getX() <= xUpperBound &&
                datasetRow.getY() >= yLowerBound && datasetRow.getY() <= yUpperBound)
            .toList();
        if (ObjectUtils.isNotEmpty(listSelectedPoints)) {
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("class");
            model.addColumn("mds-x");
            model.addColumn("mds-y");
            datasetFeatures.forEach(datasetFeature -> model.addColumn(datasetFeature.getName()));
            listSelectedPoints.forEach(datasetRow -> {
                List<DatasetFeatureRow> datasetFeatureRows = projectService.findDatasetFeatureRowsByRowId(datasetRow.getId());
                Object[] tableRow = new Object[3 + datasetFeatureRows.size()];
                Optional<DatasetClass> optionalDatasetClassById = projectService.findDatasetClassById(datasetRow.getClassId());
                if (optionalDatasetClassById.isPresent()) {
                    DatasetClass datasetClass = optionalDatasetClassById.get();
                    tableRow[0] = datasetClass.getName();
                    tableRow[1] = datasetRow.getX();
                    tableRow[2] = datasetRow.getY();
                    for (int i = 0; i < datasetFeatureRows.size(); i++) {
                        tableRow[3 + i] = datasetFeatureRows.get(i).getValue();
                    }
                    model.addRow(tableRow);
                }
            });
            this.selectedPoints = model;
        }
    }

    private Map<Long, XYSeries> generateChartData() {
        Map<Long, XYSeries> dataMap = new HashMap<>();
        datasetClasses.forEach(datasetClass ->
            dataMap.put(datasetClass.getId(), new XYSeries(datasetClass.getName()))
        );
        datasetRows.forEach(datasetRow ->
            dataMap.get(datasetRow.getClassId())
                .add(datasetRow.getX(), datasetRow.getY())
        );
        return dataMap;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(showPointsButton)) {
            makeFrameSelectedPoints();
        } else if (e.getSource().equals(saveButton)) {
            saveProject();
        }
    }

    private void saveProject() {
        ProjectService dbProjectService = DBProjectService.getInstance();
        String projectName = JOptionPane.showInputDialog(this, "Insert project name");
        if (dbProjectService.projectExistsByName(projectName)) {
            JOptionPane.showMessageDialog(
                this,
                String.format("Project with name `%s` already exists!", projectName),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        List<DatasetClass> datasetClassesByProjectId = projectService.findAllDatasetClasses();
        List<DatasetFeature> datasetFeaturesByProjectId = projectService.findAllDatasetFeatures();
        List<DatasetRow> datasetRowsByProjectId = projectService.findAllDatasetRows();
        List<Long> listDatasetRowIds = datasetRowsByProjectId.stream()
            .map(DatasetRow::getId)
            .toList();
        List<DatasetFeatureRow> datasetFeatureRows = projectService.findDatasetFeatureRowsByRowIds(listDatasetRowIds);
        dbProjectService.partialSave(
            datasetClassesByProjectId,
            datasetFeaturesByProjectId,
            datasetRowsByProjectId,
            datasetFeatureRows,
            new Project(projectName, userSession.getUserId())
        );

        projectService.clearTables();

        new Homepage();
        this.dispose();
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
        showProjectPanel.setLayout(new GridLayoutManager(2, 2, new Insets(10, 10, 10, 10), -1, -1));
        chartContainerPanel = new JPanel();
        chartContainerPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        showProjectPanel.add(chartContainerPanel, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        showPointsButton = new JButton();
        showPointsButton.setEnabled(false);
        showPointsButton.setText("Show selected points");
        showPointsButton.setVisible(false);
        showProjectPanel.add(showPointsButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, 1, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 1, false));
        saveButton = new JButton();
        saveButton.setText("Save");
        showProjectPanel.add(saveButton, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return showProjectPanel;
    }

}

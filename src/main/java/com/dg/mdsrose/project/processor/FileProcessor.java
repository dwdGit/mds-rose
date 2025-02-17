package com.dg.mdsrose.project.processor;

import mdsj.MDSJ;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
 * Template pattern implementation to process dataset files
 * */
public abstract class FileProcessor {
    private static final String DEFAULT_DELIMITER = ",";
    protected String filePath;
    protected Map<Integer, String> columnsToPick;

    public FileProcessorResult process() throws IOException {
        List<String> lines = parseFile();
        ExtractedDatasetData extractedDatasetData = extractDatasetContent(lines);
        double[][] dissimilarityMatrix = calculateDissimilarityMatrix(extractedDatasetData.partialDatasetRows());
        double[][] mds = calculateMDS(dissimilarityMatrix);

        return parseResult(extractedDatasetData, mds);
    }

    public List<String> parseFile() throws IOException {
        return Files.readAllLines(Paths.get(filePath));
    }

    public ExtractedDatasetData extractDatasetContent(List<String> lines) {
        List<PartialDatasetRow> pDataRows = new ArrayList<>();
        List<String> classes = new ArrayList<>();

        for(String line : lines) {
            // split the row by comma to get all values
            String[] columns = line.split(getDelimiter());

            // declare a data array with the same size of columns to pick
            double[] data = new double[columnsToPick.size()];
            int dataIdx = 0;

            String className = "";

            for(int i=0; i<columns.length; i++) {
                if(i == columns.length-1) {
                    // get the feature name as it's line's last value
                    className = columns[i];
                    if(!classes.contains(className)) {
                        // add the feature name to classes list if not contained yet
                        classes.add(className);
                    }
                }

                if(columnsToPick.containsKey(i+1)) {
                    // add data if the column is among the ones to pick
                    data[dataIdx++] = Double.parseDouble(columns[i]);
                }
            }
            pDataRows.add(new PartialDatasetRow(className, data));
        }

        return new ExtractedDatasetData(pDataRows, classes);
    }

    private String getDelimiter() {
        return DEFAULT_DELIMITER;
    }

    public double[][] calculateDissimilarityMatrix(List<PartialDatasetRow> partialDatasetRows) {
        double[][] dataset = new double[partialDatasetRows.size()][];
        for(int i = 0; i< partialDatasetRows.size(); i++) {
            dataset[i] = partialDatasetRows.get(i).data();
        }

        int n = dataset.length;
        double[][] distanceMatrix = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                distanceMatrix[i][j] = euclideanDistance(dataset[i], dataset[j]);
            }
        }

        return distanceMatrix;
    }

    public double[][] calculateMDS(double[][] dissimilarityMatrix) {
        return MDSJ.classicalScaling(dissimilarityMatrix);
    }

    public FileProcessorResult parseResult(ExtractedDatasetData extractedDatasetData, double[][] mds) {
        List<CompleteDatasetRow> result = new ArrayList<>();
        List<PartialDatasetRow> partialDatasetRows = extractedDatasetData.partialDatasetRows();
        for(int i = 0; i < partialDatasetRows.size(); i++) {
            PartialDatasetRow partialDatasetRow = partialDatasetRows.get(i);
            result.add(new CompleteDatasetRow(partialDatasetRow.featureName(), partialDatasetRow.data(), mds[0][i], mds[1][i]));
        }

        return new FileProcessorResult(result, extractedDatasetData.classes());
    }


    protected double euclideanDistance(double[] point1, double[] point2) {
        double sum = 0.0;
        for (int i = 0; i < point1.length; i++) {
            double diff = point1[i] - point2[i];
            sum += diff * diff;
        }
        return Math.sqrt(sum);
    }
}

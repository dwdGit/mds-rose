package com.dg.mdsrose.project.processor;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/*
 * Template pattern implementation to process dataset files
 * */
public abstract class FileProcessor {
    String fileName;
    Map<Integer, String> columnsToPick;

    public FileProcessorResult process() throws IOException {
        List<String> lines = parseFile(fileName);
        ExtractedDatasetData extractedDatasetData = extractDatasetContent(lines);
        double[][] dissimilarityMatrix = calculateDissimilarityMatrix(extractedDatasetData.partialDatasetRows());
        double[][] mds = calculateMDS(dissimilarityMatrix);

        return parseResult(extractedDatasetData, mds);
    }

    public abstract List<String> parseFile(String fileName) throws IOException;
    public abstract ExtractedDatasetData extractDatasetContent(List<String> lines);
    public abstract double[][] calculateDissimilarityMatrix(List<PartialDatasetRow> partialDatasetRows);
    public abstract double[][] calculateMDS(double[][] dissimilarityMatrix);
    public abstract FileProcessorResult parseResult(ExtractedDatasetData extractedDatasetData, double[][] mds);


    protected double euclideanDistance(double[] point1, double[] point2) {
        double sum = 0.0;
        for (int i = 0; i < point1.length; i++) {
            double diff = point1[i] - point2[i];
            sum += diff * diff;
        }
        return Math.sqrt(sum);
    }
}

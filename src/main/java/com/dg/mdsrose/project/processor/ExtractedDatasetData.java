package com.dg.mdsrose.project.processor;

import java.util.List;

public record ExtractedDatasetData(List<PartialDatasetRow> partialDatasetRows, List<String> classes, List<List<Double>> dataset) {}

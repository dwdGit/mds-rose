package com.dg.mdsrose.project.processor;

import java.util.List;

/*
* Extracted data from dataset after first iteration, all data needed for next calculus and/or for final result
* */
public record ExtractedDatasetData(List<PartialDatasetRow> partialDatasetRows, List<String> classes) {}

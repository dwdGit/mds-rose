package com.dg.mdsrose.project.processor;

/*
* Dataset class with partial data as mds results are missing
* */
public record PartialDatasetRow(String featureName, double[] data) {}

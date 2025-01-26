package com.dg.mdsrose.project.processor;

/*
* Complete dataset row with all necessary data
* */
public record DatasetRow(String label, double[] features, double x, double y) {}

package com.dg.mdsrose.project.model;

import java.util.ArrayList;
import java.util.List;

public class ChartData {
    List<Double> xValues;
    List<Double> yValues;

    public ChartData() {
        xValues = new ArrayList<>();
        yValues = new ArrayList<>();
    }

    public ChartData(List<Double> xValues, List<Double> yValues) {
        this.xValues = xValues;
        this.yValues = yValues;
    }

    public void add(double x, double y) {
        xValues.add(x);
        yValues.add(y);
    }

    public List<Double> getXValues() {
        return xValues;
    }

    public void setXValues(List<Double> xValues) {
        this.xValues = xValues;
    }

    public List<Double> getYValues() {
        return yValues;
    }

    public void setYValues(List<Double> yValues) {
        this.yValues = yValues;
    }
}

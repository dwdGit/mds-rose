package com.dg.mdsrose.project.model;

import java.util.Objects;

public class DatasetFeatureRow {
    private Long id;
    private Long featureId;
    private Long rowId;
    private double value;

    public DatasetFeatureRow(Long id, Long featureId, Long rowId, double value) {
        this.id = id;
        this.featureId = featureId;
        this.rowId = rowId;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFeatureId() {
        return featureId;
    }

    public void setFeatureId(Long featureId) {
        this.featureId = featureId;
    }

    public Long getRowId() {
        return rowId;
    }

    public void setRowId(Long rowId) {
        this.rowId = rowId;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "DatasetFeatureRow{" +
            "id=" + id +
            ", featureId=" + featureId +
            ", rowId=" + rowId +
            ", value=" + value +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DatasetFeatureRow that = (DatasetFeatureRow) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

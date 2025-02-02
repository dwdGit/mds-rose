package com.dg.mdsrose.project.model;

import java.util.Objects;

public class DatasetRow {
    private Long id;
    private Long classId;
    private double x;
    private double y;
    private Long projectId;

    public DatasetRow() {}

    public DatasetRow(Long classId, double x, double y) {
        this.classId = classId;
        this.x = x;
        this.y = y;
    }

    public DatasetRow(Long classId, double x, double y, Long projectId) {
        this.classId = classId;
        this.x = x;
        this.y = y;
        this.projectId = projectId;
    }

    public DatasetRow(Long id, Long classId, double x, double y, Long projectId) {
        this.id = id;
        this.classId = classId;
        this.x = x;
        this.y = y;
        this.projectId = projectId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    @Override
    public String toString() {
        return "DatasetRow{" +
            "id=" + id +
            ", classId=" + classId +
            ", x=" + x +
            ", y=" + y +
            ", projectId=" + projectId +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DatasetRow that = (DatasetRow) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

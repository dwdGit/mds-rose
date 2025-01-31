package com.dg.mdsrose.project.model;

import java.util.Objects;

public class DatasetClass {
    private Long id;
    private String name;
    private String marker;
    private String color;
    private Long projectId;

    public DatasetClass() {}

    public DatasetClass(String name, String marker, String color, Long projectId) {
        this.name = name;
        this.marker = marker;
        this.color = color;
        this.projectId = projectId;
    }

    public DatasetClass(Long id, String name, String marker, String color, Long projectId) {
        this.id = id;
        this.name = name;
        this.marker = marker;
        this.color = color;
        this.projectId = projectId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMarker() {
        return marker;
    }

    public void setMarker(String marker) {
        this.marker = marker;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    @Override
    public String toString() {
        return "DatasetClass{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", marker='" + marker + '\'' +
            ", color='" + color + '\'' +
            ", projectId=" + projectId +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DatasetClass that = (DatasetClass) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, marker, color, projectId);
    }
}

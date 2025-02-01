package com.dg.mdsrose.project.model;

import java.util.Objects;

public class DatasetFeature {
    private Long id;
    private String name;
    private Long projectId;

    public DatasetFeature() {}

    public DatasetFeature(Long id, String name, Long projectId) {
        this.id = id;
        this.name = name;
        this.projectId = projectId;
    }

    public DatasetFeature(String name, Long projectId) {
        this.name = name;
        this.projectId = projectId;
    }

    public DatasetFeature(String name) {
        this.name = name;
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

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    @Override
    public String toString() {
        return "DatasetFeature{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", projectId=" + projectId +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DatasetFeature that = (DatasetFeature) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

package com.dg.mdsrose.project;

import com.dg.mdsrose.project.model.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryProjectDAO implements ProjectDAO {
    private final Map<Long, Project> projects = new HashMap<>();
    private final Map<Long, DatasetClass> datasetClasses = new HashMap<>();
    private final Map<Long, DatasetFeature> datasetFeatures = new HashMap<>();
    private final Map<Long, DatasetRow> datasetRows = new HashMap<>();
    private final Map<Long, DatasetFeatureRow> datasetFeatureRows = new HashMap<>();
    private final AtomicLong projectSequence = new AtomicLong(0);
    private final AtomicLong datasetClassesSequence = new AtomicLong(0);
    private final AtomicLong datasetFeaturesSequence = new AtomicLong(0);
    private final AtomicLong datasetRowsSequence = new AtomicLong(0);
    private final AtomicLong datasetFeatureRowsSequence = new AtomicLong(0);

    public InMemoryProjectDAO() {}

    @Override
    public Long insertProject(Project project) {
        long id = projectSequence.getAndIncrement();
        project.setId(id);
        projects.put(id, project);
        return id;
    }

    @Override
    public Long insertDatasetClass(DatasetClass datasetClass) {
        long id = datasetClassesSequence.getAndIncrement();
        datasetClass.setId(id);
        datasetClasses.put(id, datasetClass);
        return id;
    }

    @Override
    public Long insertDatasetFeature(DatasetFeature datasetFeature) {
        long id = datasetFeaturesSequence.getAndIncrement();
        datasetFeature.setId(id);
        datasetFeatures.put(id, datasetFeature);
        return id;
    }

    @Override
    public Long insertDatasetRow(DatasetRow datasetRow) {
        long id = datasetRowsSequence.getAndIncrement();
        datasetRow.setId(id);
        datasetRows.put(id, datasetRow);
        return id;
    }

    @Override
    public void insertDatasetFeatureRow(DatasetFeatureRow datasetFeatureRow) {
        long id = datasetFeatureRowsSequence.getAndIncrement();
        datasetFeatureRows.put(id, datasetFeatureRow);
    }

    @Override
    public Optional<DatasetClass> findDatasetClassById(Long id) {
        return Optional.of(datasetClasses.get(id));
    }

    @Override
    public Optional<DatasetFeature> findDatasetFeatureById(Long id) {
        return Optional.of(datasetFeatures.get(id));
    }

    @Override
    public List<DatasetClass> findDatasetClassesByProjectId(Long projectId) {
        return datasetClasses.values()
            .stream()
            .filter(datasetClass -> datasetClass.getProjectId().equals(projectId))
            .toList();
    }

    @Override
    public List<DatasetRow> findDatasetRowsByProjectId(Long projectId) {
        return datasetRows.values()
            .stream()
            .filter(datasetRow -> datasetRow.getProjectId().equals(projectId))
            .toList();
    }

    @Override
    public List<DatasetFeature> findDatasetFeaturesByProjectId(Long projectId) {
        return datasetFeatures.values()
            .stream()
            .filter(datasetFeature -> datasetFeature.getProjectId().equals(projectId))
            .toList();
    }

    @Override
    public List<DatasetFeatureRow> findDatasetFeatureRowsByRowId(Long rowId) {
        return datasetFeatureRows.values()
            .stream()
            .filter(datasetFeatureRow -> datasetFeatureRow.getRowId().equals(rowId))
            .toList();
    }

    @Override
    public Optional<Project> findProject(Long id) {
        return Optional.of(projects.get(id));
    }

    @Override
    public List<DatasetFeatureRow> findDatasetFeatureRowsByRowIds(List<Long> rowIds) {
        return datasetFeatureRows.values()
                .stream()
                .filter(datasetFeatureRow -> rowIds.contains(datasetFeatureRow.getRowId()))
                .toList();
    }

    @Override
    public List<Project> findProjectByUserId(Long id) {
        return projects.values()
                .stream()
                .filter(project -> project.getUserId().equals(id))
                .toList();
    }

    @Override
    public boolean projectExistsByName(String name) {
        return projects.values()
            .stream()
            .anyMatch(project -> project.getName().equals(name));
    }

    @Override
    public List<DatasetClass> findAllDatasetClasses() {
        return datasetClasses.values()
            .stream()
            .toList();
    }

    @Override
    public void updateDatasetClass(DatasetClass datasetClass) {
        Optional<DatasetClass> storedDatasetClass = findDatasetClassById(datasetClass.getId());

        storedDatasetClass.ifPresent(dc -> {
            dc.setName(datasetClass.getName());
            dc.setMarker(datasetClass.getMarker());
            dc.setColor(datasetClass.getColor());
            dc.setProjectId(datasetClass.getProjectId());
            datasetClasses.put(dc.getId(), dc);
        });
    }

    @Override
    public List<DatasetRow> findAllDatasetRows() {
        return datasetRows.values()
            .stream()
            .toList();
    }

    @Override
    public List<DatasetFeature> findAllDatasetFeatures() {
        return datasetFeatures.values()
            .stream()
            .toList();
    }

    @Override
    public void bulkInsertDatasetFeatureRows(List<DatasetFeatureRow> datasetFeatureRows) {
        datasetFeatureRows.forEach(this::insertDatasetFeatureRow);
    }

    @Override
    public void clearTables() {
        datasetClasses.clear();
        datasetFeatures.clear();
        datasetRows.clear();
        datasetFeatureRows.clear();
    }
}

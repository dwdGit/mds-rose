package com.dg.mdsrose.project;

import com.dg.mdsrose.project.model.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryProjectRepository implements ProjectRepository {
    private static final InMemoryProjectRepository instance = new InMemoryProjectRepository();
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

    private InMemoryProjectRepository() {}

    public static InMemoryProjectRepository getInstance() {
        return instance;
    }

    @Override
    public Long insertProject(Project project) {
        long id = projectSequence.getAndIncrement();
        project.setId(id);
        projects.put(id, project);
        return id;
    }

    @Override
    public Long insertDatasetClass(Shape shape, Long projectId) {
        long id = datasetClassesSequence.getAndIncrement();
        DatasetClass datasetClass = new DatasetClass(
            id,
            shape.getLabel(),
            shape.getMarker().name(),
            shape.getColor().name(),
            projectId
        );
        datasetClasses.put(id, datasetClass);
        return id;
    }

    @Override
    public Long insertDatasetFeatures(String name, Long projectId) {
        long id = datasetFeaturesSequence.getAndIncrement();
        DatasetFeature datasetFeature = new DatasetFeature(name, projectId);
        datasetFeatures.put(id, datasetFeature);
        return id;
    }

    @Override
    public Long insertDatasetRow(Long classId, double x, double y, Long projectId) {
        long id = datasetRowsSequence.getAndIncrement();
        DatasetRow datasetRow = new DatasetRow(id, classId, x, y, projectId);
        datasetRows.put(id, datasetRow);
        return id;
    }

    @Override
    public Long insertDatasetFeatureRow(Long featureId, Long rowId, double value) {
        long id = datasetFeatureRowsSequence.getAndIncrement();
        DatasetFeatureRow datasetFeatureRow = new DatasetFeatureRow(id, featureId, rowId, value);
        datasetFeatureRows.put(id, datasetFeatureRow);
        return id;
    }

    @Override
    public DatasetClass findDatasetClassById(Long id) {
        return datasetClasses.get(id);
    }

    @Override
    public DatasetFeature findDatasetFeatureById(Long id) {
        return datasetFeatures.get(id);
    }

    @Override
    public List<DatasetClass> findAllDatasetClasses() {
        return datasetClasses.values()
            .stream()
            .toList();
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
    public List<DatasetFeatureRow> findDatasetFeatureRowsByRowId(Long rowId) {
        return datasetFeatureRows.values()
            .stream()
            .filter(datasetFeatureRow -> datasetFeatureRow.getRowId().equals(rowId))
            .toList();
    }
}

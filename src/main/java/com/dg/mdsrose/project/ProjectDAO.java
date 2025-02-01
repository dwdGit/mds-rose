package com.dg.mdsrose.project;

import com.dg.mdsrose.project.model.*;

import java.util.List;
import java.util.Optional;

public interface ProjectDAO {
    Long insertProject(Project project);

    Long insertDatasetClass(DatasetClass datasetClass);

    Long insertDatasetFeature(DatasetFeature datasetFeature);

    Long insertDatasetRow(DatasetRow datasetRow);

    Long insertDatasetFeatureRow(DatasetFeatureRow datasetFeatureRow);

    Optional<DatasetClass> findDatasetClassById(Long id);

    Optional<DatasetFeature> findDatasetFeatureById(Long id);

    List<DatasetClass> findDatasetClassesByProjectId(Long projectId);

    List<DatasetRow> findDatasetRowsByProjectId(Long projectId);

    List<DatasetFeature> findDatasetFeaturesByProjectId(Long projectId);

    List<DatasetFeatureRow> findDatasetFeatureRowsByRowId(Long rowId);

    Optional<Project> findProject(Long id);

    List<DatasetFeatureRow> findDatasetFeatureRowsByRowIds(List<Long> rowIds);

    List<Project> findProjectByUserId(Long id);

    boolean projectExistsByName(String name);

    List<DatasetClass> findAllDatasetClasses();

    void updateDatasetClass(DatasetClass datasetClass);

    List<DatasetRow> findAllDatasetRows();

    List<DatasetFeature> findAllDatasetFeatures();

    void bulkInsertDatasetFeatureRows(List<DatasetFeatureRow> datasetFeatureRows);

    void clearTables();
}

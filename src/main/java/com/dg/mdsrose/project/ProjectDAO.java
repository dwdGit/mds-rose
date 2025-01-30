package com.dg.mdsrose.project;

import com.dg.mdsrose.project.model.*;

import java.util.List;

public interface ProjectDAO {
    Long insertProject(Project project);

    Long insertDatasetClass(Shape shape, Long projectId);

    Long insertDatasetFeatures(String name, Long projectId);

    Long insertDatasetRow(Long classId, double x, double y, Long projectId);

    Long insertDatasetFeatureRow(Long featureId, Long rowId, double value);

    DatasetClass findDatasetClassById(Long id);

    DatasetFeature findDatasetFeatureById(Long id);

    List<DatasetClass> findDatasetClassesByProjectId(Long projectId);

    List<DatasetRow> findDatasetRowsByProjectId(Long projectId);

    List<DatasetFeature> findDatasetFeaturesByProjectId(Long projectId);

    List<DatasetFeatureRow> findDatasetFeatureRowsByRowId(Long rowId);
}

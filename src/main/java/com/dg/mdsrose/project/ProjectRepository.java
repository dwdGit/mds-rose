package com.dg.mdsrose.project;

import com.dg.mdsrose.project.model.*;

import java.util.List;

public interface ProjectRepository {
    Long insertProject(Project project);

    Long insertDatasetClass(Shape shape, Long projectId);

    Long insertDatasetFeatures(String name, Long projectId);

    Long insertDatasetRow(Long classId, double x, double y, Long projectId);

    Long insertDatasetFeatureRow(Long featureId, Long rowId, double value);

    DatasetClass findDatasetClassById(Long id);

    DatasetFeature findDatasetFeatureById(Long id);

    List<DatasetClass> findAllDatasetClasses();

    List<DatasetRow> findAllDatasetRows();

    List<DatasetFeature> findAllDatasetFeatures();

    List<DatasetFeatureRow> findDatasetFeatureRowsByRowId(Long rowId);
}

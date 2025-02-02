package com.dg.mdsrose.project;

import com.dg.mdsrose.project.model.*;
import com.dg.mdsrose.project.processor.CompleteDatasetRow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ProjectService {
    private final ProjectDAO projectDAO;

    public ProjectService(ProjectDAO projectDAO) {
        this.projectDAO = projectDAO;
    }

    public void partialSave(
        List<CompleteDatasetRow> listCompleteDatasetRows,
        List<String> classes,
        Map<Integer, String> features
    ) {
        Map<String, Long> classIds = new HashMap<>();
        Map<Integer, Long> featureIds = new HashMap<>();

        classes.forEach(clazz -> {
            DatasetClass datasetClass = new DatasetClass(clazz);
            classIds.put(clazz, projectDAO.insertDatasetClass(datasetClass));
        });

        int currIdx = 0;
        for(String name : features.values()) {
            DatasetFeature datasetFeature = new DatasetFeature(name);
            featureIds.put(currIdx++, projectDAO.insertDatasetFeature(datasetFeature));
        }

        listCompleteDatasetRows.forEach(completeDatasetRow -> {
            DatasetRow datasetRow = new DatasetRow(
                classIds.get(completeDatasetRow.label()),
                completeDatasetRow.x(),
                completeDatasetRow.y()
            );
            Long rowId = projectDAO.insertDatasetRow(datasetRow);

            for(int i = 0; i < completeDatasetRow.features().length; i++) {
                DatasetFeatureRow datasetFeatureRow = new DatasetFeatureRow(
                    featureIds.get(i),
                    rowId,
                    completeDatasetRow.features()[i]
                );
                projectDAO.insertDatasetFeatureRow(datasetFeatureRow);
            }
        });
    }

    public List<DatasetClass> findDatasetClassesByProjectId(Long projectId) {
        return projectDAO.findDatasetClassesByProjectId(projectId);
    }

    public List<DatasetRow> findDatasetRowsByProjectId(Long projectId) {
        return projectDAO.findDatasetRowsByProjectId(projectId);
    }

    public List<DatasetFeature> findDatasetFeaturesByProjectId(Long projectId) {
        return projectDAO.findDatasetFeaturesByProjectId(projectId);
    }

    public List<DatasetFeatureRow> findDatasetFeatureRowsByRowId(Long rowId) {
        return projectDAO.findDatasetFeatureRowsByRowId(rowId);
    }

    public Optional<DatasetClass> findDatasetClassById(Long id) {
        return projectDAO.findDatasetClassById(id);
    }

    public List<Project> findProjectByUserId(Long id) {
        return projectDAO.findProjectByUserId(id);
    }

    public void partialSave(List<DatasetClass> datasetClasses,
                            List<DatasetFeature> datasetFeatures,
                            List<DatasetRow> datasetRows,
                            List<DatasetFeatureRow> datasetFeatureRows,
                            Project project) {
        Map<Long, Long> classIds = new HashMap<>();
        Map<Long, Long> featureIds = new HashMap<>();
        Map<Long, Long> rowsIds = new HashMap<>();

        Long projectId = projectDAO.insertProject(project);
        datasetClasses.forEach(datasetClass -> {
            datasetClass.setProjectId(projectId);
            Long idDatasetClass = projectDAO.insertDatasetClass(datasetClass);
            classIds.put(datasetClass.getId(),idDatasetClass);
        });

        datasetFeatures.forEach(datasetFeature -> {
            datasetFeature.setProjectId(projectId);
            Long idDatasetFeature = projectDAO.insertDatasetFeature(datasetFeature);
            featureIds.put(datasetFeature.getId(),idDatasetFeature);
        });

        datasetRows.forEach(datasetRow -> {
            datasetRow.setProjectId(projectId);
            datasetRow.setClassId(classIds.get(datasetRow.getClassId()));
            Long idDatasetRow = projectDAO.insertDatasetRow(datasetRow);
            rowsIds.put(datasetRow.getId(),idDatasetRow);
        });

        bulkInsertDatasetFeatureRows(
            datasetFeatureRows.stream()
                .peek(datasetFeatureRow -> {
                    datasetFeatureRow.setRowId(rowsIds.get(datasetFeatureRow.getRowId()));
                    datasetFeatureRow.setFeatureId(featureIds.get(datasetFeatureRow.getFeatureId()));
                })
                .toList()
        );
    }

    public List<DatasetFeatureRow> findDatasetFeatureRowsByRowIds(List<Long> rowIds) {
        return projectDAO.findDatasetFeatureRowsByRowIds(rowIds);
    }

    public boolean projectExistsByName(String name) {
        return projectDAO.projectExistsByName(name);
    }

    public List<DatasetClass> findAllDatasetClasses() {
        return projectDAO.findAllDatasetClasses();
    }

    public List<DatasetRow> findAllDatasetRows() {
        return projectDAO.findAllDatasetRows();
    }

    public List<DatasetFeature> findAllDatasetFeatures() {
        return projectDAO.findAllDatasetFeatures();
    }

    public void updateDatasetClass(DatasetClass datasetClass) {
        projectDAO.updateDatasetClass(datasetClass);
    }

    public void clearTables() {
        projectDAO.clearTables();
    }

    public void bulkInsertDatasetFeatureRows(List<DatasetFeatureRow> datasetFeatureRows) {
        projectDAO.bulkInsertDatasetFeatureRows(datasetFeatureRows);
    }
}

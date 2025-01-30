package com.dg.mdsrose.project;

import com.dg.mdsrose.project.model.*;
import com.dg.mdsrose.project.processor.CompleteDatasetRow;
import com.dg.mdsrose.user.UserSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProjectService {
    private final ProjectDAO projectDAO;
    private final UserSession userSession;

    public ProjectService(ProjectDAO projectDAO) {
        this.projectDAO = projectDAO;
        this.userSession = UserSession.getInstance();
    }

    public Long save(
        List<CompleteDatasetRow> datasetRows,
        List<Shape> shapes,
        Map<Integer, String> features
    ) {
        Map<String, Long> classIds = new HashMap<>();
        Map<Integer, Long> featureIds = new HashMap<>();
        Project project = new Project(userSession.getUserId());
        Long projectId = projectDAO.insertProject(project);

        shapes.forEach(shape ->
            classIds.put(shape.getLabel(), projectDAO.insertDatasetClass(shape, projectId))
        );

        int currIdx = 0;
        for(String name : features.values()) {
            featureIds.put(currIdx++, projectDAO.insertDatasetFeatures(name, projectId));
        }

        datasetRows.forEach(datasetRow -> {
            Long rowId = projectDAO.insertDatasetRow(
                classIds.get(datasetRow.label()),
                datasetRow.x(),
                datasetRow.y(),
                projectId
            );

            for(int i = 0; i < datasetRow.features().length; i++) {
                projectDAO.insertDatasetFeatureRow(featureIds.get(i), rowId, datasetRow.features()[i]);
            }
        });

        return projectId;
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

    public DatasetClass findDatasetClassById(Long id) {
        return projectDAO.findDatasetClassById(id);
    }
}

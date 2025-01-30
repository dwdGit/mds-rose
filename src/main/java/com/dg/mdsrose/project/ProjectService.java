package com.dg.mdsrose.project;

import com.dg.mdsrose.project.model.*;
import com.dg.mdsrose.project.processor.CompleteDatasetRow;
import com.dg.mdsrose.user.UserSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProjectService {
    private static final ProjectService instance = new ProjectService();
    private final InMemoryProjectRepository projectRepository;
    private final UserSession userSession;

    private ProjectService() {
        this.projectRepository = InMemoryProjectRepository.getInstance();
        this.userSession = UserSession.getInstance();
    }

    public static ProjectService getInstance() {
        return instance;
    }

    public Long create(
        List<CompleteDatasetRow> datasetRows,
        List<Shape> shapes,
        Map<Integer, String> features
    ) {
        Map<String, Long> classIds = new HashMap<>();
        Map<Integer, Long> featureIds = new HashMap<>();
        Project project = new Project(userSession.getUserId());
        Long projectId = projectRepository.insertProject(project);

        shapes.forEach(shape ->
            classIds.put(shape.getLabel(), projectRepository.insertDatasetClass(shape, projectId))
        );

        int currIdx = 0;
        for(String name : features.values()) {
            featureIds.put(currIdx++, projectRepository.insertDatasetFeatures(name, projectId));
        }

        datasetRows.forEach(datasetRow -> {
            Long rowId = projectRepository.insertDatasetRow(
                classIds.get(datasetRow.label()),
                datasetRow.x(),
                datasetRow.y(),
                projectId
            );

            for(int i = 0; i < datasetRow.features().length; i++) {
                projectRepository.insertDatasetFeatureRow(featureIds.get(i), rowId, datasetRow.features()[i]);
            }
        });

        return projectId;
    }

    public List<DatasetClass> findAllDatasetClasses() {
        return projectRepository.findAllDatasetClasses();
    }

    public List<DatasetRow> findAllDatasetRows() {
        return projectRepository.findAllDatasetRows();
    }

    public List<DatasetFeature> findAllDatasetFeatures() {
        return projectRepository.findAllDatasetFeatures();
    }

    public List<DatasetFeatureRow> findDatasetFeatureRowsByRowId(Long rowId) {
        return projectRepository.findDatasetFeatureRowsByRowId(rowId);
    }

    public DatasetClass findDatasetClassById(Long id) {
        return projectRepository.findDatasetClassById(id);
    }
}

package com.dg.mdsrose.project;

import com.dg.mdsrose.db.DB;
import com.dg.mdsrose.enums.DBTable;
import com.dg.mdsrose.project.model.DatasetClass;
import com.dg.mdsrose.project.model.DatasetFeature;
import com.dg.mdsrose.project.model.DatasetFeatureRow;
import com.dg.mdsrose.project.model.DatasetRow;
import com.dg.mdsrose.project.model.Project;
import com.dg.mdsrose.project.model.Shape;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.StringJoiner;

public class DBProjectDAO implements ProjectDAO {
    @Override
    public Long insertProject(Project project) {
        String sql = new StringJoiner(" ")
            .add("INSERT INTO")
            .add(DBTable.PROJECT.getValue())
            .add("(name, user_id) VALUES (?, ?, ?, ?)")
            .toString();
        ResultSet resultSet = null;

        try (
            Connection connection = DB.createConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            preparedStatement.setString(1, project.getName());
            preparedStatement.setLong(2, project.getUserId());
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                return resultSet.getLong(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if(resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException _) {}
            }
        }
        return -1L;
    }

    @Override
    public Long insertDatasetClass(Shape shape, Long projectId) {
        return 0L;
    }

    @Override
    public Long insertDatasetFeatures(String name, Long projectId) {
        return 0L;
    }

    @Override
    public Long insertDatasetRow(Long classId, double x, double y, Long projectId) {
        return 0L;
    }

    @Override
    public Long insertDatasetFeatureRow(Long featureId, Long rowId, double value) {
        return 0L;
    }

    @Override
    public DatasetClass findDatasetClassById(Long id) {
        return null;
    }

    @Override
    public DatasetFeature findDatasetFeatureById(Long id) {
        return null;
    }

    @Override
    public List<DatasetClass> findDatasetClassesByProjectId(Long projectId) {
        return List.of();
    }

    @Override
    public List<DatasetRow> findDatasetRowsByProjectId(Long projectId) {
        return List.of();
    }

    @Override
    public List<DatasetFeature> findDatasetFeaturesByProjectId(Long projectId) {
        return List.of();
    }

    @Override
    public List<DatasetFeatureRow> findDatasetFeatureRowsByRowId(Long rowId) {
        return List.of();
    }
}

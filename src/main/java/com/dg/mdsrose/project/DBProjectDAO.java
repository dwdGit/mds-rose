package com.dg.mdsrose.project;

import com.dg.mdsrose.db.DB;
import com.dg.mdsrose.enums.DBTable;
import com.dg.mdsrose.project.model.DatasetClass;
import com.dg.mdsrose.project.model.DatasetFeature;
import com.dg.mdsrose.project.model.DatasetFeatureRow;
import com.dg.mdsrose.project.model.DatasetRow;
import com.dg.mdsrose.project.model.Project;
import com.dg.mdsrose.project.model.Shape;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

public class DBProjectDAO implements ProjectDAO {
    @Override
    public Long insertProject(Project project) {
        String sql = new StringJoiner(" ")
            .add("INSERT INTO")
            .add(DBTable.PROJECT.getValue())
            .add("(name, user_id) VALUES (?, ?)")
            .toString();
        ResultSet resultSet = null;

        try (
            Connection connection = DB.createConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            preparedStatement.setString(1, project.getName());
            preparedStatement.setLong(2, project.getUserId());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
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
    public Long insertDatasetClass(DatasetClass datasetClass) {
        String sql = new StringJoiner(" ")
                .add("INSERT INTO")
                .add(DBTable.DATASET_CLASS.getValue())
                .add("(name, marker, color, project_id) VALUES (?, ?, ?, ?)")
                .toString();

        ResultSet resultSet = null;
        try(
                Connection connection = DB.createConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
        ){
            preparedStatement.setString(1,datasetClass.getName());
            preparedStatement.setString(2,datasetClass.getMarker());
            preparedStatement.setString(3,datasetClass.getColor());
            preparedStatement.setLong(4,datasetClass.getProjectId());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getLong(1);
            }
        } catch (SQLException e){
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
    public Long insertDatasetFeatures(DatasetFeature datasetFeature) {
        String sql = new StringJoiner(" ")
                .add("INSERT INTO")
                .add(DBTable.DATASET_FEATURE.getValue())
                .add("(name, project_id) VALUES (?, ?)")
                .toString();

        ResultSet resultSet = null;
        try(
                Connection connection = DB.createConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
        ){
            preparedStatement.setString(1, datasetFeature.getName());
            preparedStatement.setLong(2, datasetFeature.getProjectId());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getLong(1);
            }
        } catch (SQLException e){
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
    public Long insertDatasetRow(DatasetRow datasetRow) {
        String sql = new StringJoiner(" ")
                .add("INSERT INTO")
                .add(DBTable.DATASET_ROW.getValue())
                .add("(class_id, x, y, project_id) VALUES (?, ?, ?, ?)")
                .toString();

        ResultSet resultSet = null;
        try(
                Connection connection = DB.createConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
        ){
            preparedStatement.setLong(1, datasetRow.getClassId());
            preparedStatement.setDouble(2, datasetRow.getX());
            preparedStatement.setDouble(3, datasetRow.getY());
            preparedStatement.setLong(4, datasetRow.getProjectId());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getLong(1);
            }
        } catch (SQLException e){
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
    public Long insertDatasetFeatureRow(DatasetFeatureRow datasetFeatureRow) {
        String sql = new StringJoiner(" ")
                .add("INSERT INTO")
                .add(DBTable.DATASET_FEATURE_ROW.getValue())
                .add("(dataset_row_id, dataset_feature_id, value) VALUES (?, ?, ?)")
                .toString();

        ResultSet resultSet = null;
        try(
                Connection connection = DB.createConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
        ){
            preparedStatement.setLong(1, datasetFeatureRow.getRowId());
            preparedStatement.setLong(2, datasetFeatureRow.getFeatureId());
            preparedStatement.setDouble(3, datasetFeatureRow.getValue());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getLong(1);
            }
        } catch (SQLException e){
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
    public Optional<DatasetClass> findDatasetClassById(Long id) {
        String sql = new StringJoiner(" ")
                .add("SELECT * FROM")
                .add(DBTable.DATASET_CLASS.getValue())
                .add("WHERE id = ?")
                .toString();

        ResultSet resultSet = null;
        try(
                Connection connection = DB.createConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ){
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                return Optional.ofNullable(resultSet.getObject(DBTable.DATASET_CLASS.getValue(), DatasetClass.class));
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        } finally {
            if(resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException _) {}
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<DatasetFeature> findDatasetFeatureById(Long id) {
        String sql = new StringJoiner(" ")
                .add("SELECT * FROM")
                .add(DBTable.DATASET_FEATURE.getValue())
                .add("WHERE id = ?")
                .toString();

        ResultSet resultSet = null;
        try(
                Connection connection = DB.createConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ){
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                return Optional.ofNullable(resultSet.getObject(DBTable.DATASET_FEATURE.getValue(), DatasetFeature.class));
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        } finally {
            if(resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException _) {}
            }
        }
        return Optional.empty();
    }

    @Override
    public List<DatasetClass> findDatasetClassesByProjectId(Long projectId) {
        String sql = new StringJoiner(" ")
                .add("SELECT * FROM")
                .add(DBTable.DATASET_CLASS.getValue())
                .add("WHERE project_id = ?")
                .toString();

        ResultSet resultSet = null;
        try(
            Connection connection = DB.createConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ){
            preparedStatement.setLong(1, projectId);
            resultSet = preparedStatement.executeQuery();
            List<DatasetClass> resultList = new ArrayList<>();
            while(resultSet.next()) {
                resultList.add(resultSet.getObject(DBTable.DATASET_CLASS.getValue(), DatasetClass.class));
            }
            return resultList;
        } catch (SQLException e){
            throw new RuntimeException(e);
        } finally {
            if(resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException _) {}
            }
        }
    }

    @Override
    public List<DatasetRow> findDatasetRowsByProjectId(Long projectId) {
        String sql = new StringJoiner(" ")
                .add("SELECT * FROM")
                .add(DBTable.DATASET_ROW.getValue())
                .add("WHERE project_id = ?")
                .toString();

        ResultSet resultSet = null;
        try(
                Connection connection = DB.createConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ){
            preparedStatement.setLong(1, projectId);
            resultSet = preparedStatement.executeQuery();
            List<DatasetRow> resultList = new ArrayList<>();
            while(resultSet.next()) {
                resultList.add(resultSet.getObject(DBTable.DATASET_ROW.getValue(), DatasetRow.class));
            }
            return resultList;
        } catch (SQLException e){
            throw new RuntimeException(e);
        } finally {
            if(resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException _) {}
            }
        }
    }

    @Override
    public List<DatasetFeature> findDatasetFeaturesByProjectId(Long projectId) {
        String sql = new StringJoiner(" ")
                .add("SELECT * FROM")
                .add(DBTable.DATASET_FEATURE.getValue())
                .add("WHERE project_id = ?")
                .toString();

        ResultSet resultSet = null;
        try(
                Connection connection = DB.createConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ){
            preparedStatement.setLong(1, projectId);
            resultSet = preparedStatement.executeQuery();
            List<DatasetFeature> resultList = new ArrayList<>();
            while(resultSet.next()) {
                resultList.add(resultSet.getObject(DBTable.DATASET_FEATURE.getValue(), DatasetFeature.class));
            }
            return resultList;
        } catch (SQLException e){
            throw new RuntimeException(e);
        } finally {
            if(resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException _) {}
            }
        }
    }

    @Override
    public List<DatasetFeatureRow> findDatasetFeatureRowsByRowId(Long rowId) {
        String sql = new StringJoiner(" ")
                .add("SELECT * FROM")
                .add(DBTable.DATASET_FEATURE_ROW.getValue())
                .add("WHERE dataset_row_id = ?")
                .toString();

        ResultSet resultSet = null;
        try(
                Connection connection = DB.createConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ){
            preparedStatement.setLong(1, rowId);
            resultSet = preparedStatement.executeQuery();
            List<DatasetFeatureRow> resultList = new ArrayList<>();
            while(resultSet.next()) {
                resultList.add(resultSet.getObject(DBTable.DATASET_FEATURE_ROW.getValue(), DatasetFeatureRow.class));
            }
            return resultList;
        } catch (SQLException e){
            throw new RuntimeException(e);
        } finally {
            if(resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException _) {}
            }
        }
    }

    @Override
    public Optional<Project> findProject(Long id) {
        String sql = new StringJoiner(" ")
                .add("SELECT * FROM")
                .add(DBTable.PROJECT.getValue())
                .add("WHERE id = ?")
                .toString();

        ResultSet resultSet = null;
        try(
                Connection connection = DB.createConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ){
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                return Optional.ofNullable(resultSet.getObject(DBTable.PROJECT.getValue(), Project.class));
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        } finally {
            if(resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException _) {}
            }
        }
        return Optional.empty();
    }

    @Override
    public List<DatasetFeatureRow> findDatasetFeatureRowsByRowIds(List<Long> rowIds) {
        String placeholders = String.join(", ", rowIds.stream().map(id -> "?").toArray(String[]::new));

        String sql = new StringJoiner(" ")
                .add("SELECT * FROM")
                .add(DBTable.DATASET_FEATURE_ROW.getValue())
                .add("WHERE dataset_row_id IN (" + placeholders + ")")
                .toString();

        ResultSet resultSet = null;
        try(
                Connection connection = DB.createConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ){
            for (int i = 0; i < rowIds.size(); i++) {
                preparedStatement.setLong(i + 1, rowIds.get(i));
            }
            resultSet = preparedStatement.executeQuery();
            List<DatasetFeatureRow> resultList = new ArrayList<>();
            while(resultSet.next()) {
                resultList.add(resultSet.getObject(DBTable.DATASET_FEATURE_ROW.getValue(), DatasetFeatureRow.class));
            }
            return resultList;
        } catch (SQLException e){
            throw new RuntimeException(e);
        } finally {
            if(resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException _) {}
            }
        }
    }

    @Override
    public List<Project> findProjectByUserId(Long id) {
        String sql = new StringJoiner(" ")
                .add("SELECT * FROM")
                .add(DBTable.PROJECT.getValue())
                .add("WHERE user_id = ?")
                .toString();

        ResultSet resultSet = null;
        try(
                Connection connection = DB.createConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ){
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            List<Project> resultList = new ArrayList<>();
            while(resultSet.next()) {
                Project project = new Project();
                project.setId(resultSet.getLong("id"));
                project.setName(resultSet.getString("name"));
                project.setUserId(resultSet.getLong("user_id"));
                resultList.add(project);
            }
            return resultList;
        } catch (SQLException e){
            throw new RuntimeException(e);
        } finally {
            if(resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException _) {}
            }
        }
    }
}

package com.dg.mdsrose.project;

import com.dg.mdsrose.db.DB;
import com.dg.mdsrose.enums.DBTable;
import com.dg.mdsrose.project.model.*;

import java.sql.*;
import java.util.*;

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
                PreparedStatement preparedStatement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)
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
    public Long insertDatasetFeature(DatasetFeature datasetFeature) {
        String sql = new StringJoiner(" ")
                .add("INSERT INTO")
                .add(DBTable.DATASET_FEATURE.getValue())
                .add("(name, project_id) VALUES (?, ?)")
                .toString();

        ResultSet resultSet = null;
        try(
                Connection connection = DB.createConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)
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
                PreparedStatement preparedStatement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)
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
                PreparedStatement preparedStatement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)
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
                .add("SELECT id, name, marker, color, project_id FROM")
                .add(DBTable.DATASET_CLASS.getValue())
                .add("WHERE id = ?")
                .toString();

        ResultSet resultSet = null;
        try(
                Connection connection = DB.createConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ){
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                DatasetClass datasetClass = new DatasetClass();
                datasetClass.setId(resultSet.getLong(1));
                datasetClass.setName(resultSet.getString(2));
                datasetClass.setMarker(resultSet.getString(3));
                datasetClass.setColor(resultSet.getString(4));
                datasetClass.setProjectId(resultSet.getLong(5));
                return Optional.of(datasetClass);
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
                .add("SELECT id, name, project_id FROM")
                .add(DBTable.DATASET_FEATURE.getValue())
                .add("WHERE id = ?")
                .toString();

        ResultSet resultSet = null;
        try(
                Connection connection = DB.createConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ){
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                DatasetFeature datasetFeature = new DatasetFeature();
                datasetFeature.setId(resultSet.getLong(1));
                datasetFeature.setName(resultSet.getString(2));
                datasetFeature.setProjectId(resultSet.getLong(3));
                return Optional.of(datasetFeature);
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
                .add("SELECT id, name, marker, color, project_id FROM")
                .add(DBTable.DATASET_CLASS.getValue())
                .add("WHERE project_id = ?")
                .toString();

        ResultSet resultSet = null;
        try(
            Connection connection = DB.createConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ){
            preparedStatement.setLong(1, projectId);
            resultSet = preparedStatement.executeQuery();
            List<DatasetClass> resultList = new ArrayList<>();
            while(resultSet.next()) {
                DatasetClass datasetClass = new DatasetClass();
                datasetClass.setId(resultSet.getLong(1));
                datasetClass.setName(resultSet.getString(2));
                datasetClass.setMarker(resultSet.getString(3));
                datasetClass.setColor(resultSet.getString(4));
                datasetClass.setProjectId(resultSet.getLong(5));
                resultList.add(datasetClass);
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
                .add("SELECT id, x, y, class_id, project_id FROM")
                .add(DBTable.DATASET_ROW.getValue())
                .add("WHERE project_id = ?")
                .toString();

        ResultSet resultSet = null;
        try(
                Connection connection = DB.createConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ){
            preparedStatement.setLong(1, projectId);
            resultSet = preparedStatement.executeQuery();
            List<DatasetRow> resultList = new ArrayList<>();
            while(resultSet.next()) {
                DatasetRow datasetRow = new DatasetRow();
                datasetRow.setId(resultSet.getLong(1));
                datasetRow.setX(resultSet.getDouble(2));
                datasetRow.setY(resultSet.getDouble(3));
                datasetRow.setClassId(resultSet.getLong(4));
                datasetRow.setProjectId(resultSet.getLong(5));
                resultList.add(datasetRow);
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
                .add("SELECT id, name, project_id FROM")
                .add(DBTable.DATASET_FEATURE.getValue())
                .add("WHERE project_id = ?")
                .toString();

        ResultSet resultSet = null;
        try(
                Connection connection = DB.createConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ){
            preparedStatement.setLong(1, projectId);
            resultSet = preparedStatement.executeQuery();
            List<DatasetFeature> resultList = new ArrayList<>();
            while(resultSet.next()) {
                DatasetFeature datasetFeature = new DatasetFeature();
                datasetFeature.setId(resultSet.getLong(1));
                datasetFeature.setName(resultSet.getString(2));
                datasetFeature.setProjectId(resultSet.getLong(3));
                resultList.add(datasetFeature);
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
                .add("SELECT dataset_row_id, dataset_feature_id, value  FROM")
                .add(DBTable.DATASET_FEATURE_ROW.getValue())
                .add("WHERE dataset_row_id = ?")
                .toString();

        ResultSet resultSet = null;
        try(
                Connection connection = DB.createConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ){
            preparedStatement.setLong(1, rowId);
            resultSet = preparedStatement.executeQuery();
            List<DatasetFeatureRow> resultList = new ArrayList<>();
            while(resultSet.next()) {
                DatasetFeatureRow datasetFeatureRow = new DatasetFeatureRow();
                datasetFeatureRow.setRowId(resultSet.getLong(1));
                datasetFeatureRow.setFeatureId(resultSet.getLong(2));
                datasetFeatureRow.setValue(resultSet.getDouble(3));
                resultList.add(datasetFeatureRow);
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
                .add("SELECT id, name, user_id FROM")
                .add(DBTable.PROJECT.getValue())
                .add("WHERE id = ?")
                .toString();

        ResultSet resultSet = null;
        try(
                Connection connection = DB.createConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ){
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                Project project = new Project();
                project.setId(resultSet.getLong(1));
                project.setName(resultSet.getString(2));
                project.setUserId(resultSet.getLong(3));
                return Optional.of(project);
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
        String placeholders = String.join(", ", rowIds.stream().map(_ -> "?").toArray(String[]::new));

        String sql = new StringJoiner(" ")
                .add("SELECT dataset_row_id, dataset_feature_id, value FROM")
                .add(DBTable.DATASET_FEATURE_ROW.getValue())
                .add("WHERE dataset_row_id IN (" + placeholders + ")")
                .toString();

        ResultSet resultSet = null;
        try(
                Connection connection = DB.createConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ){
            for (int i = 0; i < rowIds.size(); i++) {
                preparedStatement.setLong(i + 1, rowIds.get(i));
            }
            resultSet = preparedStatement.executeQuery();
            List<DatasetFeatureRow> resultList = new ArrayList<>();
            while(resultSet.next()) {
                DatasetFeatureRow datasetFeatureRow = new DatasetFeatureRow();
                datasetFeatureRow.setRowId(resultSet.getLong(1));
                datasetFeatureRow.setFeatureId(resultSet.getLong(2));
                datasetFeatureRow.setValue(resultSet.getDouble(3));
                resultList.add(datasetFeatureRow);
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
                .add("SELECT id, name, user_id FROM")
                .add(DBTable.PROJECT.getValue())
                .add("WHERE user_id = ?")
                .toString();

        ResultSet resultSet = null;
        try(
                Connection connection = DB.createConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
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

    @Override
    public boolean projectExistsByName(String name) {
        String sql = new StringJoiner(" ")
            .add("SELECT 1 FROM")
            .add(DBTable.PROJECT.getValue())
            .add("WHERE name = ?")
            .toString();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DB.createConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if(resultSet != null) resultSet.close();
                if(preparedStatement != null) preparedStatement.close();
                if(connection != null) connection.close();
            } catch (SQLException _) {}
        }

        return false;
    }

    @Override
    public List<DatasetClass> findAllDatasetClasses() {
        return List.of();
    }

    @Override
    public void updateDatasetClass(DatasetClass datasetClass) {}

    @Override
    public List<DatasetRow> findAllDatasetRows() {
        return List.of();
    }

    @Override
    public List<DatasetFeature> findAllDatasetFeatures() {
        return List.of();
    }

    @Override
    public void bulkInsertDatasetFeatureRows(List<DatasetFeatureRow> datasetFeatureRows) {
        StringJoiner sqlJoiner = new StringJoiner(" ")
            .add("INSERT INTO")
            .add(DBTable.DATASET_FEATURE_ROW.getValue())
            .add("(dataset_row_id, dataset_feature_id, value) VALUES");

        for(int i=0;i<datasetFeatureRows.size();i++) {
            String row = " (?, ?, ?)";
            if(i < datasetFeatureRows.size() - 1) row += ",";
            sqlJoiner.add(row);
        }
        String sql = sqlJoiner.toString();

        try(
            Connection connection = DB.createConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ){
            int currIdx = 0;
            for(DatasetFeatureRow datasetFeatureRow : datasetFeatureRows) {
                preparedStatement.setLong(++currIdx, datasetFeatureRow.getRowId());
                preparedStatement.setLong(++currIdx, datasetFeatureRow.getFeatureId());
                preparedStatement.setDouble(++currIdx, datasetFeatureRow.getValue());
            }
            preparedStatement.execute();
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void clearTables() {

    }
}

package com.dg.mdsrose.project;

import com.dg.mdsrose.db.DB;
import com.dg.mdsrose.enums.DBTable;
import com.dg.mdsrose.project.model.Project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringJoiner;

public class ProjectDAO {
    private static ProjectDAO instance;

    public static ProjectDAO getInstance() {
        if (instance == null) {
            instance = new ProjectDAO();
        }
        return instance;
    }

    public Long save(Project project) {
        String sql = new StringJoiner(" ")
            .add("INSERT INTO")
            .add(DBTable.PROJECT.getValue())
            .add("(name, user_id) VALUES (?, ?)")
            .toString();
        ResultSet resultSet = null;

        try (
            Connection connection = DB.createConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)
        ){
            preparedStatement.setString(1, project.getName());
            preparedStatement.setLong(2, project.getUserId());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getLong(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException _) {}
            }
        }
        return -1L;
    }
}

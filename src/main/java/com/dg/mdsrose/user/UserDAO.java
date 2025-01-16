package com.dg.mdsrose.user;

import com.dg.mdsrose.db.DB;
import com.dg.mdsrose.enums.DBTable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.StringJoiner;

public class UserDAO {
    private static UserDAO instance;

    public static UserDAO getInstance() {
        if (instance == null) {
            instance = new UserDAO();
        }

        return instance;
    }

    public boolean existsByUsername(String username) {
        String sql = new StringJoiner(" ")
            .add("SELECT 1 FROM")
            .add(DBTable.USER.getValue())
            .add("WHERE username = ?")
            .toString();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DB.createConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
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

    public Optional<User> findByUsername(String username) {
        String sql = new StringJoiner(" ")
            .add("SELECT * FROM")
            .add(DBTable.USER.getValue())
            .add("WHERE username = ?")
            .toString();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DB.createConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                User user = new User(
                    resultSet.getLong("id"),
                    resultSet.getString("username"),
                    resultSet.getString("password"),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"));
                return Optional.of(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException _) {}
        }
        return Optional.empty();
    }

    public void insert(User user) {
        String sql = new StringJoiner(" ")
            .add("INSERT INTO")
            .add(DBTable.USER.getValue())
            .add("(username, password, first_name, last_name) VALUES (?, ?, ?, ?)")
            .toString();

        try (
            Connection connection = DB.createConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getFirstName());
            preparedStatement.setString(4, user.getLastName());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

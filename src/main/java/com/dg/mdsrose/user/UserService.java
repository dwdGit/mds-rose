package com.dg.mdsrose.user;

import com.dg.mdsrose.db.DB;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.Optional;

public class UserService {

    private static UserService instance;



    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public Optional<User> login(String username, String password) {
        Optional<User> userOptional = getUser(username);
        if(userOptional.isPresent()) {
            User user = userOptional.get();
            if(BCrypt.checkpw(password,user.getPassword())) {
                UserSession userSession = UserSession.getInstance();
                userSession.setUserId(user.getId());
                userSession.setUsername(user.getUsername());
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    private Optional<User> getUser(String username) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "SELECT * FROM user WHERE username = ?";

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
                if (resultSet != null)
                    resultSet.close();
                if (preparedStatement != null)
                    preparedStatement.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return Optional.empty();
    }

    public void signup(String username, String password, String firstname, String lastname) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            if(checkIfUserAlreadyExist(username)){
                throw new RuntimeException("User already exists");
            }

            String sql = "INSERT INTO user (username, password, first_name, last_name) VALUES (?, ?, ?, ?)";
            connection = DB.createConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,BCrypt.hashpw(password, BCrypt.gensalt(10)));
            preparedStatement.setString(3,firstname);
            preparedStatement.setString(4,lastname);
            preparedStatement.execute();
        } catch (SQLException e){
            throw new RuntimeException(e);
        } finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static Boolean checkIfUserAlreadyExist(String username) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String sql = "SELECT * FROM user WHERE username = ?";

        connection = DB.createConnection();
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, username);
        boolean result = preparedStatement.execute();

        preparedStatement.close();
        connection.close();
        return result;
    }
}

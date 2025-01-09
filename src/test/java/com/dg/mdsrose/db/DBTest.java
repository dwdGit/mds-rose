package com.dg.mdsrose.db;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DBTest {
    @Test
    public void testConnection() {
        int result = -1;
        try(
            Connection connection = DB.createConnection();
            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT 1+1;")
        ) {
            if(resultSet.next()) {
                result = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        assertEquals(2, result);
    }
}

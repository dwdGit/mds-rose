package com.dg.mdsrose.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {
    private DB() {}

    public static Connection createConnection() throws SQLException {
        return DriverManager.getConnection(System.getenv("MDSROSE_URL"));
    }
}

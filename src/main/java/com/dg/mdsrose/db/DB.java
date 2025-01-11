package com.dg.mdsrose.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {
    private static final String DB_URL = "jdbc:sqlite:src/main/resources/db/mds-rose.db";

    private DB() {}

    public static Connection createConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }
}

package com.palesd.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private static Connection connection;

    // Close connection to open database
    public static void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Connection to SQLite has been closed.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //Create a new database (should only be called once)
    public static void createDatabase() {
        String url = "jdbc:sqlite:database/database.db";
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    // Create new table in the database
    public static void createTable(String tableName) {
        //SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS '" + tableName + "' (\n"
                + " id integer PRIMARY KEY,\n"
                + " name text NOT NULL,\n"
                + " titanCard text,"
                + " UNIQUE(name, titanCard)"
                + ");";
        try {
            Statement statement = connection.createStatement();
            statement.execute(sql);
            System.out.println("Table creation successful.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    // Delete a row in a table
    public static void deleteRow(String tableName, String name) {
        //SQL statement for deleting a row
        String sql = "DELETE FROM '" + tableName + "' WHERE name LIKE '%" + name + "%'";
        try {
            Statement statement = connection.createStatement();
            statement.executeQuery(sql);
        } catch (SQLException e) {
            //System.out.println(e.getMessage());
        }
        System.out.println("Row deletion successful.\n");
    }

    // Delete a table in the database
    public static void deleteTable(String tableName) {
        //SQL statement for dropping a table
        String sql = "DROP TABLE IF EXISTS '" + tableName + "'";
        try {
            Statement statement = connection.createStatement();
            statement.executeQuery(sql);
        } catch (SQLException e) {
            //System.out.println(e.getMessage());
        }
        System.out.println("Table deletion successful.\n");
    }

    // Insert into table
    public static void insert(String tableName, String name, String id) {
        String sql = "INSERT INTO '" + tableName + "' (name, titanCard) VALUES (?,?)";

        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, id);
            pstmt.executeUpdate();
            System.out.println("Insert successful.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    // Connect to specified database
    public static void openConnection() {
        try {
            String url = "jdbc:sqlite:database/database.db";
            connection = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();
    }

    // Select all guests from table
    public static ResultSet selectAllGuests(String tableName) {
        String sql = "SELECT name, titanCard FROM '" + tableName + "'";
        ResultSet resultSet = null;

        try {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return resultSet;
    }
    
    // Select all guests from table, but sorted
    public static ResultSet selectAllGuestsSorted(String tableName) {
        String sql = "SELECT name, titanCard FROM '" + tableName + "' ORDER BY name";
        ResultSet resultSet = null;

        try {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return resultSet;
    }
    
    // Select all tables from database
    public static ResultSet selectAllTables(String identifier) {
        String sql = "SELECT name FROM sqlite_master WHERE type='table' AND name LIKE '%" + identifier + "'";
        ResultSet resultSet = null;

        try {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return resultSet;
    }
}

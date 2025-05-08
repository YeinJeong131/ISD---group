package uts.isd.model.dao;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
    private Connection connection;

    public DBConnector() {
        System.setProperty("org.sqlite.lib.verbose", "true");
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        String url = "jdbc:sqlite:C:/Users/inhoo/.SmartTomcat/ISD---group/ISD---group/AssignmentDB.db";
        try {
            connection = DriverManager.getConnection(url);
            connection.setAutoCommit(true);
            System.out.println("DBConnector: Connected to database");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("DBConnector: Connection closed");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
package uts.isd.model.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAO {
    ArrayList<UserDBManager> tables;
    private DBConnector connector;
    private Connection connection;

    public DAO() throws SQLException {
        tables = new ArrayList<>();
        connector = new DBConnector();
        connection = connector.getConnection();
        try {
            tables.add(new UserDBManager(connection));
        }
        catch (SQLException ex) {
            System.out.println("Error initializing DBManagers");
        }
    }

    public UserDBManager Users() {
        return (UserDBManager) tables.get(0);
    }

    public void close() {
        connector.closeConnection();
    }

}
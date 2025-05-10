package uts.isd.model.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAO {
    ArrayList<DBManager<?>> tables;
    private DBConnector connector;
    private Connection connection;

    public DAO() throws SQLException {
        tables = new ArrayList<>();
        connector = new DBConnector();
        connection = connector.getConnection();
        try {
            tables.add(new UserDBManager(connection));
            tables.add(new LogDBManager(connection));
            tables.add(new DeviceDBManager(connection));
            tables.add(new OrderDBManager(connection));
        }
        catch (SQLException ex) {
            System.out.println("Error initializing DBManagers");
        }
    }

    public UserDBManager Users() {
        return (UserDBManager) tables.get(0);
    }

    public LogDBManager Logs() { return (LogDBManager) tables.get(1);}

    public DeviceDBManager Devices() { return (DeviceDBManager) tables.get(2);}

    public OrderDBManager Orders() { return (OrderDBManager) tables.get(3);}

    public Connection getConnection() { return this.connection;}

    public void close() {
        connector.closeConnection();
    }

}

package uts.isd.model.dao;

import uts.isd.model.dao.User;

import java.sql.*;

public abstract class DBManager<T> {
    protected final Statement statement;
    protected final Connection connection;

    public DBManager(Connection connection) throws SQLException {
        this.connection = connection;
        statement = connection.createStatement();
    }

}

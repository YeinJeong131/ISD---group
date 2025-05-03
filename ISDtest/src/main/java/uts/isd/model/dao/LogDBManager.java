package uts.isd.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LogDBManager extends DBManager<AccessLog>{

    public LogDBManager(Connection connection) throws SQLException{
        super(connection);
    }

    @Override
    protected AccessLog add(AccessLog object) throws SQLException {
        return null;
    }

    @Override
    protected AccessLog get(AccessLog object) throws SQLException {
        return null;

    }

    public List<AccessLog> getLogsByID(int userId) throws SQLException{
        List<AccessLog> oneUserLogs = new ArrayList<>();
        String query = "SELECT * FROM AccessLog WHERE userID = ? ORDER BY loginTime DESC";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, userId);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            AccessLog findedLog = new AccessLog();
            findedLog.setLogId(resultSet.getInt("logId"));
            findedLog.setUserId(resultSet.getInt("userId"));
            findedLog.setLoginTime(resultSet.getString("loginTime"));
            findedLog.setLogoutTime(resultSet.getString("logoutTime"));
            oneUserLogs.add(findedLog);
        }
        return oneUserLogs;
    }


    @Override
    protected void update(AccessLog oldObject, AccessLog newObject) throws SQLException {

    }

    public void insertLoginLog(int userId) throws SQLException {
        String query = "INSERT INTO AccessLog (userId, loginTime, logoutTime) VALUES (?, datetime('now'), NULL)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, userId);
        statement.executeUpdate();
    }

    @Override
    protected void delete(AccessLog object) throws SQLException {

    }

    public void updateLogoutTime(int userId) throws SQLException {
        String query = "UPDATE AccessLog SET logoutTime = datetime('now') WHERE userId = ? AND logoutTime IS NULL";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, userId);
        statement.executeUpdate();
    }

}

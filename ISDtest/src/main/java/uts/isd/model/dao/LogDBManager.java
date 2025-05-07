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



    public void insertLoginLog(int userId) throws SQLException {
        String query = "INSERT INTO AccessLog (userId, loginTime, logoutTime) VALUES (?, datetime('now'), NULL)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, userId);
        statement.executeUpdate();
    }


    public void updateLogoutTime(int userId) throws SQLException {
        String query = "UPDATE AccessLog SET logoutTime = datetime('now') WHERE userId = ? AND logoutTime IS NULL";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, userId);
        statement.executeUpdate();
    }

    public List<AccessLog> getLogsPaginated(int userId, int startingLog, int logsToFetch) throws SQLException {
        List<AccessLog> oneUserLogs = new ArrayList<>();
        String query = "SELECT * FROM AccessLog WHERE userID = ? ORDER BY loginTime DESC LIMIT ? OFFSET ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, userId);
        statement.setInt(2, logsToFetch);
        statement.setInt(3, startingLog);
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

    public List<AccessLog> getLogsByDatePaginated(int userId, String date, int startingLog, int logsToFetch) throws SQLException {
        List<AccessLog> oneUserLogs = new ArrayList<>();
        String query = "SELECT * FROM AccessLog WHERE userID = ? AND DATE(loginTime) = ? ORDER BY loginTime DESC LIMIT ? OFFSET ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, userId);
        statement.setString(2, date);
        statement.setInt(3, logsToFetch);
        statement.setInt(4, startingLog);
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



}

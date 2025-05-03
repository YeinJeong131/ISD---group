package uts.isd.model.dao;

import java.sql.*;

public class UserDBManager extends DBManager<User> {

    public UserDBManager(Connection connection) throws SQLException {
        super(connection);
    }

    @Override
    protected User add(User object) throws SQLException {
        addUser(object);
        return object;
    }

    @Override
    protected User get(User object) throws SQLException {
        return null;

    }

    @Override
    protected void update(User oldObject, User newObject) throws SQLException {
        updateUser(oldObject, newObject);
    }

    @Override
    protected void delete(User object) throws SQLException {
        removeUser(object);
    }

    public int getUserCount() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM User");
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getInt(1);
    }

    //CREATE
    public void addUser(User user) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO User (email, password, firstName, lastName, address, dob) VALUES (?, ?, ?, ?, ?, ?)");
        preparedStatement.setString(1, user.getEmail());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setString(3, user.getFirstName());
        preparedStatement.setString(4, user.getLastName());
        preparedStatement.setString(5, user.getAddress());
        preparedStatement.setString(6, user.getDateOfBirth());

        preparedStatement.executeUpdate();

        ResultSet keys = preparedStatement.getGeneratedKeys();
        if (keys.next()) {
            int id = keys.getInt(1);
            user.setId(id);
        } else {
            System.out.println("failed to add user");
        }

    }

    //READ
    //...

    //UPDATE
    public void updateUser(User user, User newUser) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE USER SET email = ?, password = ?, firstName = ?, lastName = ?, address = ?, dob = ? WHERE UserId = ?");
        preparedStatement.setString(1, user.getEmail());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setString(3, user.getFirstName());
        preparedStatement.setString(4, user.getLastName());
        preparedStatement.setString(5, user.getAddress());
        preparedStatement.setString(6, user.getDateOfBirth());
        preparedStatement.setInt(7, user.getId());
        preparedStatement.executeUpdate();
    }

    //DELETE
    public void removeUser(User user) throws SQLException {
        System.out.println("ID: " + user.getId());
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM USER WHERE userId = ?");
        preparedStatement.setInt(1, user.getId());
        preparedStatement.executeUpdate();
    }

    //find user
    public User findUser(String email, String password) throws SQLException {
        String findingQuery = "SELECT * FROM User WHERE email = ? AND password = ?";
        PreparedStatement stmt = connection.prepareStatement(findingQuery);
        stmt.setString(1, email);
        stmt.setString(2, password);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return new User(
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("firstName"),
                    rs.getString("lastName"),
                    rs.getString("address"),
                    rs.getString("dob")
            );
        }
        else { return null;}
    }

    public boolean existingEmail(String email) throws SQLException {
        String findingQuery = "SELECT * FROM User WHERE email = ?";
        PreparedStatement stmt = connection.prepareStatement(findingQuery);
        stmt.setString(1, email);
        ResultSet rs = stmt.executeQuery();
        return rs.next();
    }


}

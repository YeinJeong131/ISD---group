package uts.isd.model.dao;

import uts.isd.model.Device;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DeviceDBManager extends DBManager<Device> {

    public DeviceDBManager(Connection connection) throws SQLException {
        super(connection);
    }

    @Override
    protected Device add(Device object) throws SQLException {
        addDevice(object);
        return object;
    }

    @Override
    protected Device get(Device object) throws SQLException {
        return getDeviceById(object.getId());
    }

    @Override
    protected void update(Device oldObject, Device newObject) throws SQLException {
        updateDevice(newObject);
    }

    @Override
    protected void delete(Device object) throws SQLException {
        removeDevice(object);
    }

    public int getDeviceCount() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM devices");
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getInt(1);
    }

    //CREATE
    public int addDevice(Device device) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO devices (name, type, price, quantity) VALUES (?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS
        );
        preparedStatement.setString(1, device.getName());
        preparedStatement.setString(2, device.getType());
        preparedStatement.setBigDecimal(3, device.getPrice());
        preparedStatement.setInt(4, device.getQuantity());
        preparedStatement.executeUpdate();

        ResultSet keys = preparedStatement.getGeneratedKeys();
        if (keys.next()) {
            int id = keys.getInt(1);
            device.setId(id);
            return id;
        } else {
            System.out.println("Failed to add device");
        }
        return 0;
    }

    //READ
    public List<Device> getAllDevices() throws SQLException {
        List<Device> devices = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM devices ORDER BY created_at DESC");
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            Device device = new Device(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("type"),
                    resultSet.getBigDecimal("price"),
                    resultSet.getInt("quantity"),
                    resultSet.getTimestamp("created_at")
            );
            devices.add(device);
        }
        return devices;
    }

    public Device getDeviceById(int id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM devices WHERE id = ?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return new Device(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("type"),
                    resultSet.getBigDecimal("price"),
                    resultSet.getInt("quantity"),
                    resultSet.getTimestamp("created_at")
            );
        }
        return null;
    }

    //UPDATE
    public Boolean updateDevice(Device device) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE devices SET name = ?, type = ?, price = ?, quantity = ? WHERE id = ?"
        );
        preparedStatement.setString(1, device.getName());
        preparedStatement.setString(2, device.getType());
        preparedStatement.setBigDecimal(3, device.getPrice());
        preparedStatement.setInt(4, device.getQuantity());
        preparedStatement.setInt(5, device.getId());
        return preparedStatement.executeUpdate() > 0;
    }

    public void updateDeviceQuantity(int deviceId, int quantityChange) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE devices SET quantity = quantity + ? WHERE id = ?"
        );
        preparedStatement.setInt(1, quantityChange);
        preparedStatement.setInt(2, deviceId);
        preparedStatement.executeUpdate();
    }

    //DELETE
    public Boolean removeDevice(Device device) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM devices WHERE id = ?");
        preparedStatement.setInt(1, device.getId());
        return preparedStatement.executeUpdate()>0;
    }

    //SEARCH
    public List<Device> searchDevices(String name, String type) throws SQLException {
        List<Device> devices = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM devices WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (name != null && !name.trim().isEmpty()) {
            sql.append(" AND name LIKE ?");
            params.add("%" + name + "%");
        }
        if (type != null && !type.trim().isEmpty()) {
            sql.append(" AND type LIKE ?");
            params.add("%" + type + "%");
        }
        sql.append(" ORDER BY created_at DESC");

        PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());
        for (int i = 0; i < params.size(); i++) {
            preparedStatement.setObject(i + 1, params.get(i));
        }
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            Device device = new Device(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("type"),
                    resultSet.getBigDecimal("price"),
                    resultSet.getInt("quantity"),
                    resultSet.getTimestamp("created_at")
            );
            devices.add(device);
        }
        return devices;
    }
}
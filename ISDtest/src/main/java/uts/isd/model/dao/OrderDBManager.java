package uts.isd.model.dao;

import uts.isd.model.CartItem;
import uts.isd.model.User;

import java.sql.*;
import java.util.List;

public class OrderDBManager extends DBManager<Object> {

    public OrderDBManager(Connection connection) throws SQLException {
        super(connection);
    }

    public int saveOrder(User user, List<CartItem> cart) throws SQLException {
        int orderId = -1;

        PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO orders (user_id, order_date, status) VALUES (?, datetime('now'), 'submitted')",
                Statement.RETURN_GENERATED_KEYS
        );
        ps.setInt(1, user.getId());
        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            orderId = rs.getInt(1);
        }

        PreparedStatement itemPS = connection.prepareStatement(
                "INSERT INTO order_items (order_id, device_id, quantity, price) VALUES (?, ?, ?, ?)"
        );
        for (CartItem item : cart) {
            itemPS.setInt(1, orderId);
            itemPS.setInt(2, item.getDeviceId());
            itemPS.setInt(3, item.getQuantity());
            itemPS.setBigDecimal(4, item.getPrice());
            itemPS.executeUpdate();
        }

        return orderId;
    }

    public void cancelOrder(int orderId, DeviceDBManager deviceManager) throws SQLException {
        // change staus
        PreparedStatement update = connection.prepareStatement(
                "UPDATE orders SET status = 'cancelled' WHERE id = ?"
        );
        update.setInt(1, orderId);
        update.executeUpdate();

        // reset inventory
        PreparedStatement select = connection.prepareStatement(
                "SELECT device_id, quantity FROM order_items WHERE order_id = ?"
        );
        select.setInt(1, orderId);
        ResultSet rs = select.executeQuery();

        while (rs.next()) {
            int deviceId = rs.getInt("device_id");
            int quantity = rs.getInt("quantity");
            deviceManager.updateDeviceQuantity(deviceId, quantity);
        }
    }

    @Override
    protected Object add(Object object) throws SQLException {
        return null;
    }

    @Override
    protected Object get(Object object) throws SQLException {
        return null;
    }

    @Override
    protected void update(Object oldObject, Object newObject) throws SQLException {
    }

    @Override
    protected void delete(Object object) throws SQLException {
    }
}


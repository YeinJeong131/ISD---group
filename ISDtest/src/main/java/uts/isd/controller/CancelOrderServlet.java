package uts.isd.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import uts.isd.model.dao.DAO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/order/cancel")
public class CancelOrderServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        DAO dao = (DAO) session.getAttribute("db");
        String orderIdStr = request.getParameter("orderId");

        if (orderIdStr == null) {
            response.sendRedirect("/cart.jsp");
            return;
        }

        try {
            int orderId = Integer.parseInt(orderIdStr);
            Connection conn = dao.getConnection();

            // status = cancelled
            PreparedStatement updateOrder = conn.prepareStatement(
                    "UPDATE orders SET status = 'cancelled' WHERE id = ?"
            );
            updateOrder.setInt(1, orderId);
            updateOrder.executeUpdate();

            // based on order, reset inventory
            PreparedStatement selectItems = conn.prepareStatement(
                    "SELECT device_id, quantity FROM order_items WHERE order_id = ?"
            );
            selectItems.setInt(1, orderId);
            ResultSet rs = selectItems.executeQuery();

            while (rs.next()) {
                int deviceId = rs.getInt("device_id");
                int quantity = rs.getInt("quantity");
                dao.Devices().updateDeviceQuantity(deviceId, quantity);
            }

            // reset cart items and order
            session.removeAttribute("cart");
            session.removeAttribute("latestOrderId");

            // head to cart page
            response.sendRedirect("/cart.jsp");

        } catch (Exception e) {
            session.setAttribute("error", "Failed to cancel order: " + e.getMessage());
            response.sendRedirect("/confirm_order.jsp");
        }
    }
}

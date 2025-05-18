package uts.isd.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import uts.isd.model.CartItem;
import uts.isd.model.User;
import uts.isd.model.dao.DAO;

import java.io.IOException;
import java.sql.*;
import java.util.List;

@WebServlet("/order/place")
public class PlaceOrderServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        DAO dao = (DAO) session.getAttribute("db");
        User user = (User) session.getAttribute("loggedInUser");
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");


        if (cart == null || cart.isEmpty()) {
            session.setAttribute("error", "Place order failed: Your cart is empty."); // error message in server side
            response.sendRedirect(request.getContextPath() + "/cart.jsp");
            return;
        }

        try {
            Connection conn = dao.getConnection();
            PreparedStatement ps;

            // login or not
            if (user != null) {
                ps = conn.prepareStatement(
                        "INSERT INTO orders (user_id, order_date, status) VALUES (?, datetime('now'), 'submitted')",
                        Statement.RETURN_GENERATED_KEYS
                );
                ps.setInt(1, user.getId());
            } else {
                ps = conn.prepareStatement(
                        "INSERT INTO orders (order_date, status) VALUES (datetime('now'), 'submitted')",
                        Statement.RETURN_GENERATED_KEYS
                );
            }

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int orderId = rs.getInt(1); // created order ID

            // save items
            PreparedStatement itemPS = conn.prepareStatement(
                    "INSERT INTO order_items (order_id, device_id, quantity, price) VALUES (?, ?, ?, ?)"
            );

            for (CartItem item : cart) {
                // validate the inventory in server side
                int stock = dao.Devices().getDeviceById(item.getDeviceId()).getQuantity();
                if (item.getQuantity() > stock) {
                    session.setAttribute("error", "Product \"" + item.getName() + "\" is out of stock or not enough quantity.");
                    response.sendRedirect(request.getContextPath() + "/cart.jsp");
                    return;
                }
                itemPS.setInt(1, orderId);
                itemPS.setInt(2, item.getDeviceId());
                itemPS.setInt(3, item.getQuantity());
                itemPS.setBigDecimal(4, item.getPrice());
                itemPS.executeUpdate();

            }

            // save order id in session
            session.setAttribute("latestOrderId", orderId);

            // head to confirm page
            response.sendRedirect(request.getContextPath() + "/confirm.jsp");

        } catch (Exception e) {
            session.setAttribute("error", "Order creation failed: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/cart.jsp");
        }
    }
}

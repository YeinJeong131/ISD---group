package uts.isd.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import uts.isd.model.CartItem;
import uts.isd.model.User;
import uts.isd.model.dao.DAO;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.List;

@WebServlet("/order/submit")
public class SubmittedServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        DAO dao = (DAO) session.getAttribute("db");
        User user = (User) session.getAttribute("loggedInUser");
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        if (cart == null || cart.isEmpty()) {
            session.setAttribute("error", "Your cart is empty.");
            response.sendRedirect(request.getContextPath() + "/cart.jsp");
            return;
        }

        try {
            Connection conn = dao.getConnection();
            PreparedStatement ps;

            // calculate total price
            BigDecimal totalAmount = BigDecimal.ZERO;
            for (CartItem item : cart) {
                totalAmount = totalAmount.add(item.getTotal());
            }

            // save order in table
            if (user != null) {
                ps = conn.prepareStatement(
                        "INSERT INTO orders (user_id, order_date, status, total_amount) VALUES (?, datetime('now'), 'submitted', ?)",
                        Statement.RETURN_GENERATED_KEYS
                );
                ps.setInt(1, user.getId());
                ps.setBigDecimal(2, totalAmount);
            } else {
                ps = conn.prepareStatement(
                        "INSERT INTO orders (order_date, status, total_amount) VALUES (datetime('now'), 'submitted', ?)",
                        Statement.RETURN_GENERATED_KEYS
                );
                ps.setBigDecimal(1, totalAmount);
            }

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int orderId = rs.getInt(1);

            // save items
            PreparedStatement itemPS = conn.prepareStatement(
                    "INSERT INTO order_items (order_id, device_id, quantity, price) VALUES (?, ?, ?, ?)"
            );

            for (CartItem item : cart) {
                itemPS.setInt(1, orderId);
                itemPS.setInt(2, item.getDeviceId());
                itemPS.setInt(3, item.getQuantity());
                itemPS.setBigDecimal(4, item.getPrice());
                itemPS.executeUpdate();
            }

            // save in session
            session.setAttribute("latestOrderId", orderId);

            // direct to payment page
            response.sendRedirect(request.getContextPath() + "/payment.jsp");

        } catch (Exception e) {
            session.setAttribute("error", "Failed to submit order: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/cart.jsp");
        }
    }
}

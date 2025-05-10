package uts.isd.controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import uts.isd.model.CartItem;
import uts.isd.model.Device;
import uts.isd.model.dao.DAO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/cart/add")
public class AddToCartServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int deviceId = Integer.parseInt(request.getParameter("deviceId"));
        HttpSession session = request.getSession();
        DAO db = (DAO) session.getAttribute("db");

        try {
            Device device = db.Devices().getDeviceById(deviceId);
            if (device == null || device.getQuantity() <= 0) {
                session.setAttribute("error", "Device unavailable or out of stock.");
                response.sendRedirect("device/");
                return;
            }

            List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
            if (cart == null) cart = new ArrayList<>();

            boolean found = false;
            for (CartItem item : cart) {
                if (item.getDeviceId() == deviceId) {
                    item.incrementQuantity();
                    found = true;
                    break;
                }
            }

            if (!found) {
                cart.add(new CartItem(device.getId(), device.getName(), device.getPrice()));
            }

            session.setAttribute("cart", cart);
            response.sendRedirect(request.getContextPath() + "/cart.jsp");
        } catch (Exception e) {
            session.setAttribute("error", "Error adding device to cart.");
            response.sendRedirect("device/");
        }
    }
}
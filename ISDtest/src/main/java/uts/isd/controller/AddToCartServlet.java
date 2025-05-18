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

    // post request method
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int deviceId = Integer.parseInt(request.getParameter("deviceId"));
        HttpSession session = request.getSession();
        DAO db = (DAO) session.getAttribute("db");

        try {
            Device device = db.Devices().getDeviceById(deviceId);

            // out of stock or diff ID
            if (device == null || device.getQuantity() <= 0) {
                session.setAttribute("error", "This product is out of stock.");
                response.sendRedirect(request.getHeader("referer")); // redirect the page
                return;
            }

            // call cart from session as a list if there's no cart
            List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
            if (cart == null) cart = new ArrayList<>();

            boolean found = false;

            // compare to cart items to product
            for (CartItem item : cart) {
                if (item.getDeviceId() == deviceId) {
                    // validate quantity
                    if (item.getQuantity() + 1 > device.getQuantity()) {
                        session.setAttribute("error", "Not enough stock available.");
                        response.sendRedirect(request.getHeader("referer"));
                        return;
                    }
                    item.incrementQuantity();
                    found = true;
                    break;
                }
            }

            // if product not in cart, get new cart item and add product
            if (!found) {
                cart.add(new CartItem(device.getId(), device.getName(), device.getPrice()));
            }

            session.setAttribute("cart", cart);
            response.sendRedirect(request.getHeader("referer"));

        } catch (Exception e) {
            session.setAttribute("error", "An error occurred while adding the product to the cart.");
            response.sendRedirect(request.getHeader("referer"));
        }
    }
}

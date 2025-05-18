package uts.isd.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import uts.isd.model.CartItem;

import java.io.IOException;
import java.util.List;

@WebServlet("/cart/remove")
public class RemoveFromCartServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int deviceId = Integer.parseInt(request.getParameter("deviceId"));
        HttpSession session = request.getSession();
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        if (cart != null) {
            for (int i = 0; i < cart.size(); i++) {
                CartItem item = cart.get(i);
                if (item.getDeviceId() == deviceId) {
                    if (item.getQuantity() > 1) {
                        item.decrementQuantity(); // decrease by 1 in quantity
                    } else {
                        cart.remove(i); // if quantity is 1, remove
                    }
                    break;
                }
            }
            session.setAttribute("cart", cart);
        }

        response.sendRedirect(request.getContextPath() + "/cart.jsp");
    }
}

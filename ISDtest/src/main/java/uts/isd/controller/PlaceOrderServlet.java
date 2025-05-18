package uts.isd.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import uts.isd.model.CartItem;

import java.io.IOException;
import java.util.List;

@WebServlet("/order/place")
public class PlaceOrderServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        if (cart == null || cart.isEmpty()) {
            session.setAttribute("error", "Place order failed: Your cart is empty.");
            response.sendRedirect(request.getContextPath() + "/cart.jsp");
            return;
        }

        // if cart is not empty, direct to confirm page
        response.sendRedirect(request.getContextPath() + "/confirm.jsp");
    }
}


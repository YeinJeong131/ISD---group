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
            cart.removeIf(item -> item.getDeviceId() == deviceId);
            session.setAttribute("cart", cart); // 업데이트 반영
        }

        response.sendRedirect(request.getContextPath() + "/cart.jsp");
    }
}
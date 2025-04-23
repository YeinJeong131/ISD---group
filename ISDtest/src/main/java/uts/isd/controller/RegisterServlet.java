package uts.isd.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import uts.isd.model.dao.DBManager;
import uts.isd.model.dao.User;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DBManager db = (DBManager) session.getAttribute("db");
        HttpSession session = req.getSession();

        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String first = req.getParameter("firstName");
        String last = req.getParameter("lastName");
        String address = req.getParameter("address");
        String date = req.getParameter("dob");
        String name = first + " " + last;

        User user = new User(email, password, first, last, address, date, name);

        session.setAttribute("loggedInUser", user);

        try {
            user = db.addUser(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        resp.sendRedirect("fixWelcome.jsp");

    }
}

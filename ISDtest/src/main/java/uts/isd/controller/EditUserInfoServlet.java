package uts.isd.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import uts.isd.model.dao.DBManager;
import uts.isd.model.dao.User;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/EditUserInfoServlet")
public class EditUserInfoServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        DBManager db = (DBManager)session.getAttribute("db");

        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String dob = req.getParameter("dob");
        String address = req.getParameter("address");


        User existingUser = (User)session.getAttribute("loggedInUser");
        User newUser = new User(email, password, firstName, lastName, dob, address);

        try {
            db.updateUser(existingUser, newUser);
            session.setAttribute("loggedInUser", newUser);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        resp.sendRedirect("fixIndex.jsp");
    }
}

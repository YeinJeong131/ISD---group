package uts.isd.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import uts.isd.model.dao.DAO;
import uts.isd.model.dao.UserDBManager;
import uts.isd.model.dao.User;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

@WebServlet("/EditUserInfoServlet")
public class EditUserInfoServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session == null) {
            System.out.println("No session found");
            return;
        }

        User existingUser = (User)session.getAttribute("loggedInUser");
        System.out.println("Existing user's ID: " + existingUser.getId());

        DAO db = (DAO)session.getAttribute("db");

        String email = req.getParameter("email");
        String password = req.getParameter("password");
            if (password ==  null || password.trim().isEmpty()) {
                password = existingUser.getPassword();
            }
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String address = req.getParameter("address");
        String dob = req.getParameter("dob");
            if (dob == null || dob.trim().isEmpty()) {
                dob = existingUser.getDateOfBirth();
            }

        User newUser = new User(email, password, firstName, lastName, address, dob, existingUser.getId());

        try {
            db.Users().updateUser(newUser);
            session.setAttribute("loggedInUser", newUser);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        resp.sendRedirect("fixIndex.jsp");
    }
}

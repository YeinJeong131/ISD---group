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
import java.sql.SQLException;

@WebServlet("/DeleteServlet")
public class DeleteServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User)session.getAttribute("loggedInUser");
        DAO db = (DAO)session.getAttribute("db");


        try {
            db.Users().removeUser(user);
        } catch (SQLException e) {
            System.out.format("Failed to delete user %s from the database", user.getEmail());
        }
        session.removeAttribute("loggedInUser");
        resp.sendRedirect("fixIndex.jsp");
    }
}
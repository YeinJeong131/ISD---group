package uts.isd.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import uts.isd.model.dao.DAO;
import uts.isd.model.dao.LogDBManager;
import uts.isd.model.User;


import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        HttpSession session = req.getSession();
        DAO db = (DAO)session.getAttribute("db");


        try {
            User user = db.Users().findUser(email, password);

            if (user != null) {
                session.setAttribute("loggedInUser", user);
                if (user.getRole() == 1) {
                    session.setAttribute("userRole", "staff");
                } else {
                    session.setAttribute("userRole", "customer");
                }
                LogDBManager logDB = db.Logs();
                logDB.insertLoginLog(user.getId());

                resp.sendRedirect("fixIndex.jsp");
            } else { resp.sendRedirect("login.jsp?error=true");}
        }
        catch (SQLException e) {
            e.printStackTrace();
            resp.sendRedirect("login.jsp?error=true");
        }

    }
}
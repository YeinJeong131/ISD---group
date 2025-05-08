package uts.isd.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import uts.isd.model.dao.DAO;
import uts.isd.model.dao.LogDBManager;
import uts.isd.model.dao.User;

import java.io.IOException;

@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            DAO db = (DAO) session.getAttribute("db");
            LogDBManager logDB = db.Logs();
            User user = (User)session.getAttribute("loggedInUser");

            if (user != null && logDB != null) {
                try {
                    logDB.updateLogoutTime(user.getId());
                    System.out.println("Logout time is now recorded -> user ID: " + user.getId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            session.removeAttribute("loggedInUser");
            session.invalidate();
            System.out.println("Logout successful - session invalidated");
        }
        else {System.out.println("No active session to invalidate");}
        resp.sendRedirect("fixIndex.jsp");

    }
}
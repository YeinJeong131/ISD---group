package uts.isd.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import uts.isd.model.dao.AccessLog;
import uts.isd.model.dao.DAO;
import uts.isd.model.dao.LogDBManager;
import uts.isd.model.dao.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/AccessLogServlet")
public class AccessLogServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        User loggedInUser = (User)session.getAttribute("loggedInUser");
        DAO db = (DAO)session.getAttribute("db");
        if (db == null) {
            System.out.println("⚠️ db was null in AccessLogServlet, creating manually");
            try {
                db = new DAO(); // 예외적으로 만들게요.
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            session.setAttribute("db", db);
        }
        if (loggedInUser == null || db == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            LogDBManager logDB = db.Logs();
            List<AccessLog> logs = logDB.getLogsByID(loggedInUser.getId());
            request.setAttribute("logs", logs);
            System.out.println("✅ AccessLog size: " + logs.size());
            RequestDispatcher dispatcher = request.getRequestDispatcher("viewLog.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}

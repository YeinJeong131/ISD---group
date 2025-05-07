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
    private static final int PAGE_SIZE = 3;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("loggedInUser");
        DAO db = (DAO) session.getAttribute("db");
        if (user == null || db == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String searchingDate = request.getParameter("searchingDate");
        int page = 1;
        String pageParam = request.getParameter("page");
        if (pageParam != null && !pageParam.isEmpty()) {
            try {
                page = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                System.err.println("Invalid page number format: " + pageParam);
            }
        }

        int offset = (page -1) * PAGE_SIZE;
        int fetchSize = PAGE_SIZE + 1;

        List<AccessLog> logs;
        try {
            if (searchingDate != null && !searchingDate.isEmpty()) {
                logs = db.Logs().getLogsByDatePaginated(user.getId(), searchingDate, offset, fetchSize);
                request.setAttribute("searchingDate", searchingDate);
            } else {
                logs= db.Logs().getLogsPaginated(user.getId(), offset, fetchSize);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }

        boolean hasNextPage = logs.size() > PAGE_SIZE;
        if (hasNextPage) logs.remove(PAGE_SIZE);

        request.setAttribute("logs", logs);
        request.setAttribute("page", page);
        request.setAttribute("hasNextPage", hasNextPage);
        request.getRequestDispatcher("viewLog.jsp").forward(request, response);




    }
}
//public class AccessLogServlet extends HttpServlet {
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        HttpSession session = request.getSession();
//
//        User loggedInUser = (User)session.getAttribute("loggedInUser");
//        DAO db = (DAO)session.getAttribute("db");
//        if (db == null) {
//            System.out.println("⚠️ db was null in AccessLogServlet, creating manually");
//            try {
//                db = new DAO(); // 예외적으로 만들게요.
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//            session.setAttribute("db", db);
//        }
//        if (loggedInUser == null || db == null) {
//            response.sendRedirect("login.jsp");
//            return;
//        }
//
//        try {
//            LogDBManager logDB = db.Logs();
//            List<AccessLog> logs = logDB.getLogsByID(loggedInUser.getId());
//            request.setAttribute("logs", logs);
//            System.out.println("✅ AccessLog size: " + logs.size());
//            RequestDispatcher dispatcher = request.getRequestDispatcher("viewLog.jsp");
//            dispatcher.forward(request, response);
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//}
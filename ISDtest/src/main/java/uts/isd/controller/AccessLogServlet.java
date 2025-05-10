package uts.isd.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import uts.isd.model.AccessLog;
import uts.isd.model.dao.DAO;
import uts.isd.model.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/AccessLogServlet")
public class AccessLogServlet extends HttpServlet {
    private static final int LOGS_PER_PAGE = 3;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            System.out.println("AccessLogServlet: No session found");
            return;
        }
        User user = (User) session.getAttribute("loggedInUser");

        DAO db = (DAO) session.getAttribute("db");
        if (db == null) {
            System.out.println("AccessLogServlet: No database session found");
            return;
        }

        String selectedDate = request.getParameter("selectedDate");
        int currentPage = 1;
        String requestedPage = request.getParameter("requestedPage");
        if (requestedPage != null && !requestedPage.isEmpty()) {
            try {
                currentPage = Integer.parseInt(requestedPage);
            } catch (NumberFormatException e) {
                System.err.println("Invalid page number format: " + requestedPage);
            }
        }

        int startingLog = (currentPage -1) * LOGS_PER_PAGE ;
        int logsToFetch = LOGS_PER_PAGE  + 1;

        List<AccessLog> logs;
        try {
            if (selectedDate != null && !selectedDate.isEmpty()) {
                logs = db.Logs().getLogsByDatePaginated(user.getId(), selectedDate, startingLog, logsToFetch);
                request.setAttribute("selectedDate", selectedDate);
            } else {
                logs= db.Logs().getLogsPaginated(user.getId(), startingLog, logsToFetch);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }

        boolean hasNextPage = logs.size() > LOGS_PER_PAGE ;
        if (hasNextPage) logs.remove(LOGS_PER_PAGE); // Remove the extra last log used to check for the existence of the next page.

        request.setAttribute("logs", logs);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("hasNextPage", hasNextPage);
        request.getRequestDispatcher("viewLog.jsp").forward(request, response);




    }
}
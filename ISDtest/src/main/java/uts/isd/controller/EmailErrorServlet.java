package uts.isd.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import uts.isd.model.dao.DAO;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/EmailErrorServlet")
public class EmailErrorServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String checkingEmail = request.getParameter("email");
        System.out.println("Checking email: " + checkingEmail);

        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        if (session == null) {
            System.out.println("EmailErrorServlet: No session found");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("error");
            return;
        }

        DAO db = (DAO) session.getAttribute("db");
        if (db == null) {
            System.out.println("EmailErrorServlet: No database session found");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("error");
            return;
        }

        try {
            boolean doesExist = db.Users().doesEmailExist(checkingEmail);
            System.out.println("Does email exist: " + doesExist);
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(doesExist ? "existing email" : "can use this email");

        } catch (SQLException e) {
            System.out.println("Failed to check whether email exists");
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("error");
        }

    }
}

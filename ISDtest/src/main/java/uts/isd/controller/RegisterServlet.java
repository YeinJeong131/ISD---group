package uts.isd.controller;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import uts.isd.model.dao.DAO;
import uts.isd.model.dao.UserDBManager;
import uts.isd.model.dao.User;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("RegisterServlet: doPost() triggered");
        HttpSession session = req.getSession();

        DAO db = ((DAO)session.getAttribute("db"));
        UserDBManager userDB = db.Users();


        if (db == null) {
            System.out.println("DAO was null — creating new DAO just in case.");
            try {
                db = new DAO();
                session.setAttribute("db", db);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        System.out.println("DBManager is null? " + (db == null));

        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String first = req.getParameter("firstName");
        String last = req.getParameter("lastName");
        String address = req.getParameter("address");
        String date = req.getParameter("dob");
        String name = first + " " + last;

            User user = new User(email, password, first, last, address, date);
            session.setAttribute("RegisteredUser", user);
            try {
                db.Users().addUser(user);
                System.out.println("✅  Successfully inserted user!");
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("❌ Failed to insert new user.");
            }
            resp.sendRedirect(req.getContextPath() + "/fixWelcome.jsp");

        File dbFile = new File("AssignmentDB.db");
        System.out.println("📁 The absolute path of the database currently in use: " + dbFile.getAbsolutePath());





    }
}

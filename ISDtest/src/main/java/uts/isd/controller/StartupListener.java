package uts.isd.controller;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import uts.isd.model.dao.DAO;
import uts.isd.model.dao.DBConnector;
import uts.isd.model.dao.UserDBManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebListener
public class StartupListener implements ServletContextListener, HttpSessionListener {
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("contextInitialized");
    }

    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        try {
            DAO dao = new DAO();
            session.setAttribute("db", dao);
        }
        catch (SQLException e) {
            System.out.println("Could not connect to database");
        }
    }
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("contextDestroyed");
    }

    public void sessionDestroyed(HttpSessionEvent se) {
        System.out.println("sessionDestroyed");
        HttpSession session = se.getSession();
        DAO db = (DAO) session.getAttribute("db");

        if (db != null) {
            db.close();
        }
        System.out.println("DB connection closed on session destroy.");
    }



}

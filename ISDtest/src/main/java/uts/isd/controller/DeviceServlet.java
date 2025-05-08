package uts.isd.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import uts.isd.model.Device;
import uts.isd.model.dao.DAO;
import uts.isd.model.dao.DeviceDBManager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/device/*")
public class DeviceServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();
        HttpSession session = request.getSession();
        String userRole = (String) session.getAttribute("userRole");
        DAO db = (DAO)session.getAttribute("db");
        DeviceDBManager deviceDBManager = db.Devices();
        try {
            if (action == null || action.equals("/")) {
                listDevices(request, response, deviceDBManager);
            } else if (action.equals("/new") && "staff".equals(userRole)) {
                showNewForm(request, response);
            } else if (action.equals("/edit") && "staff".equals(userRole)) {
                showEditForm(request, response, deviceDBManager);
            } else if (action.equals("/delete") && "staff".equals(userRole)) {
                deleteDevice(request, response, deviceDBManager);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (SQLException e) {
            session.setAttribute("error", "Database error: " + e.getMessage());
            session.setAttribute("debug", e.toString());
            response.sendRedirect(request.getContextPath() + "/device/");
        } catch (Exception e) {
            session.setAttribute("error", "An unexpected error occurred: " + e.getMessage());
            session.setAttribute("debug", e.toString());
            response.sendRedirect(request.getContextPath() + "/device/");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();
        HttpSession session = request.getSession();
        String userRole = (String) session.getAttribute("userRole");
        DAO db = (DAO)session.getAttribute("db");
        DeviceDBManager deviceDBManager = db.Devices();
        if (!"staff".equals(userRole)) {
            session.setAttribute("error", "Only staff members can perform this operation");
            response.sendRedirect(request.getContextPath() + "/device/");
            return;
        }

        try {
            if (action == null || action.equals("/")) {
                insertDevice(request, response, deviceDBManager);
            } else if (action.equals("/update")) {
                updateDevice(request, response, deviceDBManager);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (SQLException e) {
            session.setAttribute("error", "Database error: " + e.getMessage());
            session.setAttribute("debug", e.toString());
            response.sendRedirect(request.getContextPath() + "/device/");
        } catch (Exception e) {
            session.setAttribute("error", "An unexpected error occurred: " + e.getMessage());
            session.setAttribute("debug", e.toString());
            response.sendRedirect(request.getContextPath() + "/device/");
        }
    }

    private void listDevices(HttpServletRequest request, HttpServletResponse response, DeviceDBManager deviceDBManager)
            throws SQLException, ServletException, IOException {
        try {
            String searchName = request.getParameter("searchName");
            String searchType = request.getParameter("searchType");
            List<Device> devices;
            if ((searchName != null && !searchName.trim().isEmpty()) || (searchType != null && !searchType.trim().isEmpty())) {
                devices = deviceDBManager.searchDevices(searchName, searchType);
            } else {
                devices = deviceDBManager.getAllDevices();
            }
            request.setAttribute("devices", devices);
            request.getRequestDispatcher("/list.jsp").forward(request, response);
        } catch (Exception e) {
            HttpSession session = request.getSession();
            session.setAttribute("error", "Failed to load devices: " + e.getMessage());
            session.setAttribute("debug", e.toString());
            response.sendRedirect(request.getContextPath() + "/device/");
        }
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/form.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response, DeviceDBManager deviceDBManager)
            throws SQLException, ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Device device = deviceDBManager.getDeviceById(id);
            if (device == null) {
                HttpSession session = request.getSession();
                session.setAttribute("error", "Device not found");
                response.sendRedirect(request.getContextPath() + "/device/");
                return;
            }
            request.setAttribute("device", device);
            request.getRequestDispatcher("/form.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            HttpSession session = request.getSession();
            session.setAttribute("error", "Invalid device ID format");
            response.sendRedirect(request.getContextPath() + "/device/");
        }
    }

    private void insertDevice(HttpServletRequest request, HttpServletResponse response, DeviceDBManager deviceDBManager)
            throws SQLException, IOException {
        try {
            Device device = new Device();
            device.setName(request.getParameter("name"));
            device.setType(request.getParameter("type"));
            device.setPrice(new java.math.BigDecimal(request.getParameter("price")));
            device.setQuantity(Integer.parseInt(request.getParameter("quantity")));
            deviceDBManager.addDevice(device);
            HttpSession session = request.getSession();
            session.setAttribute("message", "Device added successfully");
            response.sendRedirect(request.getContextPath() + "/device/");
        } catch (NumberFormatException e) {
            HttpSession session = request.getSession();
            session.setAttribute("error", "Invalid number format in price or quantity");
            response.sendRedirect(request.getContextPath() + "/device/new");
        }
    }

    private void updateDevice(HttpServletRequest request, HttpServletResponse response, DeviceDBManager deviceDBManager)
            throws SQLException, IOException {
        try {
            Device device = new Device();
            device.setId(Integer.parseInt(request.getParameter("id")));
            device.setName(request.getParameter("name"));
            device.setType(request.getParameter("type"));
            device.setPrice(new java.math.BigDecimal(request.getParameter("price")));
            device.setQuantity(Integer.parseInt(request.getParameter("quantity")));
            deviceDBManager.updateDevice(device);
            HttpSession session = request.getSession();
            session.setAttribute("message", "Device updated successfully");
            response.sendRedirect(request.getContextPath() + "/device/");
        } catch (NumberFormatException e) {
            HttpSession session = request.getSession();
            session.setAttribute("error", "Invalid number format in price or quantity");
            response.sendRedirect(request.getContextPath() + "/device/edit?id=" + request.getParameter("id"));
        }
    }

    private void deleteDevice(HttpServletRequest request, HttpServletResponse response, DeviceDBManager deviceDBManager)
            throws SQLException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Device device = deviceDBManager.getDeviceById(id);
            if (device != null) {
                deviceDBManager.removeDevice(device);
                HttpSession session = request.getSession();
                session.setAttribute("message", "Device deleted successfully");
            } else {
                HttpSession session = request.getSession();
                session.setAttribute("error", "Device not found");
            }
            response.sendRedirect(request.getContextPath() + "/device/");
        } catch (NumberFormatException e) {
            HttpSession session = request.getSession();
            session.setAttribute("error", "Invalid device ID format");
            response.sendRedirect(request.getContextPath() + "/device/");
        }
    }
}
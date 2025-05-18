<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="uts.isd.model.User, java.util.*, java.sql.*, uts.isd.model.dao.DAO" %>

<%
  User user = (User) session.getAttribute("loggedInUser");
  DAO dao = (DAO) session.getAttribute("db");

  if (user == null) {
    response.sendRedirect("login.jsp");
    return;
  }

  String startDate = request.getParameter("startDate");
  String endDate = request.getParameter("endDate");
  String orderIdParam = request.getParameter("orderId");

  List<Map<String, String>> orders = new ArrayList<>();

  try {
    Connection conn = dao.getConnection();
    PreparedStatement ps;

    if (orderIdParam != null && !orderIdParam.trim().isEmpty()) {
      ps = conn.prepareStatement(
              "SELECT o.id, o.order_date, o.status, " +
                      "IFNULL(SUM(oi.price * oi.quantity), 0) AS total_price " +
                      "FROM orders o " +
                      "LEFT JOIN order_items oi ON o.id = oi.order_id " +
                      "WHERE o.user_id = ? AND o.id = ? " +
                      "GROUP BY o.id ORDER BY o.order_date DESC"
      );
      ps.setInt(1, user.getId());
      ps.setInt(2, Integer.parseInt(orderIdParam));
    } else if (startDate != null && endDate != null && !startDate.isEmpty() && !endDate.isEmpty()) {
      ps = conn.prepareStatement(
              "SELECT o.id, o.order_date, o.status, " +
                      "IFNULL(SUM(oi.price * oi.quantity), 0) AS total_price " +
                      "FROM orders o " +
                      "LEFT JOIN order_items oi ON o.id = oi.order_id " +
                      "WHERE o.user_id = ? AND DATE(o.order_date) BETWEEN ? AND ? " +
                      "GROUP BY o.id ORDER BY o.order_date DESC"
      );
      ps.setInt(1, user.getId());
      ps.setString(2, startDate);
      ps.setString(3, endDate);
    } else {
      ps = conn.prepareStatement(
              "SELECT o.id, o.order_date, o.status, " +
                      "IFNULL(SUM(oi.price * oi.quantity), 0) AS total_price " +
                      "FROM orders o " +
                      "LEFT JOIN order_items oi ON o.id = oi.order_id " +
                      "WHERE o.user_id = ? GROUP BY o.id ORDER BY o.order_date DESC"
      );
      ps.setInt(1, user.getId());
    }

    ResultSet rs = ps.executeQuery();
    while (rs.next()) {
      Map<String, String> order = new HashMap<>();
      order.put("id", rs.getString("id"));
      order.put("order_date", rs.getString("order_date"));
      order.put("status", rs.getString("status"));
      order.put("total_price", rs.getString("total_price"));
      orders.add(order);
    }

  } catch (Exception e) {
    out.println("<p style='color: red;'>Failed to load order history: " + e.getMessage() + "</p>");
  }
%>

<!DOCTYPE html>
<html>
<head>
  <title>Your Order History</title>
  <link rel="stylesheet" href="style.css">
</head>
<body>
<h2>Your Order History</h2>

<form method="get" class="filter-form">
  <label>From: <input type="date" name="startDate" value="<%= startDate != null ? startDate : "" %>"/></label>
  <label>To: <input type="date" name="endDate" value="<%= endDate != null ? endDate : "" %>"/></label>
  <label>Order ID: <input type="text" name="orderId" value="<%= orderIdParam != null ? orderIdParam : "" %>" /></label>
  <button type="submit">Search</button>
</form>

<table>
  <tr>
    <th>Order ID</th>
    <th>Date</th>
    <th>Status</th>
    <th>Total Price</th>
  </tr>
  <%
    if (orders.isEmpty()) {
  %>
  <tr><td colspan="4" style="text-align:center;">No orders found.</td></tr>
  <%
  } else {
    for (Map<String, String> od : orders) {
  %>
  <tr>
    <td><%= od.get("id") %></td>
    <td><%= od.get("order_date") %></td>
    <td><%= od.get("status") %></td>
    <td>$<%= od.get("total_price") %></td>
  </tr>
  <%
      }
    }
  %>
</table>

<form action="fixIndex.jsp">
  <button class="back-button">Back to Home</button>
</form>
</body>
</html>

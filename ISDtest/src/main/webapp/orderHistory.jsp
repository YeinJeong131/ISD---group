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

  List<Map<String, String>> orders = new ArrayList<>();
  try {
    Connection conn = dao.getConnection();
    PreparedStatement ps;

    if (startDate != null && endDate != null && !startDate.isEmpty() && !endDate.isEmpty()) {
      ps = conn.prepareStatement("SELECT * FROM orders WHERE user_id = ? AND DATE(order_date) BETWEEN ? AND ? ORDER BY order_date DESC");
      ps.setInt(1, user.getId());
      ps.setString(2, startDate);
      ps.setString(3, endDate);
    } else {
      ps = conn.prepareStatement("SELECT * FROM orders WHERE user_id = ? ORDER BY order_date DESC");
      ps.setInt(1, user.getId());
    }

    ResultSet rs = ps.executeQuery();
    while (rs.next()) {
      Map<String, String> order = new HashMap<>();
      order.put("id", rs.getString("id"));
      order.put("order_date", rs.getString("order_date"));
      order.put("status", rs.getString("status"));
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
  <button type="submit">Search</button>
</form>

<table>
  <tr>
    <th>Order ID</th>
    <th>Date</th>
    <th>Status</th>
  </tr>
  <%
    if (orders.isEmpty()) {
  %>
  <tr><td colspan="3" style="text-align:center;">No orders found.</td></tr>
  <%
  } else {
    for (Map<String, String> o : orders) {
  %>
  <tr>
    <td><%= o.get("id") %></td>
    <td><%= o.get("order_date") %></td>
    <td><%= o.get("status") %></td>
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


<%@ page import="uts.isd.model.CartItem" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Your Cart</title>
  <link rel="stylesheet" href="style.css">
</head>
<body>
<h2>Your Cart</h2>
<ul>
  <%
    List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
    if (cart != null && !cart.isEmpty()) {
      for (CartItem item : cart) {
  %>
  <li><%= item.getName() %> - Quantity: <%= item.getQuantity() %></li>
  <%
    }
  } else {
  %>
  <li>Your cart is empty.</li>
  <%
    }
  %>
</ul>
<a href="main.jsp">Back to Main</a>
</body>
</html>
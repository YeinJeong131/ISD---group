<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="uts.isd.model.CartItem" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
  <title>Your Cart</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <link rel="stylesheet" href="style.css">
</head>
<body>
<div class="cart-container">

  <%-- display error message from server --%>
  <%
    String error = (String) session.getAttribute("error");
    if (error != null) {
  %>
  <div class="alert alert-danger"><%= error %></div>
  <%
      session.removeAttribute("error");
    }
  %>

  <h2>Your Shopping Cart</h2>

  <%
    List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
    double total = 0;
    boolean cartIsEmpty = (cart == null || cart.isEmpty());
  %>

  <% if (cartIsEmpty) { %>
  <p class="empty-cart">Your cart is empty.</p>
  <% } else { %>
  <table class="cart-table">
    <thead>
    <tr>
      <th>Device</th>
      <th>Price</th>
      <th>Quantity</th>
      <th>Total</th>
      <th>Action</th>
    </tr>
    </thead>
    <tbody>
    <%
      for (CartItem item : cart) {
        total += item.getTotal().doubleValue();
    %>
    <tr>
      <td><%= item.getName() %></td>
      <td>$<%= item.getPrice() %></td>
      <td><%= item.getQuantity() %></td>
      <td>$<%= item.getTotal() %></td>
      <td>
        <form method="post" action="cart/remove">
          <input type="hidden" name="deviceId" value="<%= item.getDeviceId() %>">
          <button type="submit" class="btn-remove">Remove</button>
        </form>
      </td>
    </tr>
    <% } %>
    </tbody>
  </table>

  <h3 class="total-price">Total: $<%= String.format("%.2f", total) %></h3>
  <% } %>


  <form method="post" action="order/place">
    <button type="submit" class="btn-order">Place Order</button>
  </form>

  <br><br>
  <a href="device/" class="btn btn-secondary">Back to Device List</a>
</div>
</body>
</html>

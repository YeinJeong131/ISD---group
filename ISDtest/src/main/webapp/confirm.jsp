<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@ page import="java.util.*" %>
<%
    Integer orderId = (Integer) session.getAttribute("latestOrderId");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Order Confirmation</title>
    <link rel="stylesheet" href="style.css">

</head>
<body>
<div class="confirmation-container">
    <h1>Order #<%= orderId %> has been created.</h1>
    <p>Please choose an action:</p>

    <div class="button-group">
        <form action="payment.jsp" method="post">
            <button class="button-proceed">Proceed to Payment</button>
        </form>
        <form action="order/cancel" method="post">
            <input type="hidden" name="orderId" value="${sessionScope.latestOrderId}" />
            <button type="submit" class="button-cancel">Cancel Order</button>
        </form>
        <form action="cart.jsp" method="get">
            <button class="button-back">Back to Cart</button>
        </form>
    </div>
</div>
</body>
</html>

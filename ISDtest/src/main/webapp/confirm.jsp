<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Order Confirmation</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
<div class="confirmation-container">
    <h1>Ready to Place Your Order</h1>
    <p>Please choose an action below:</p>

    <%-- list possible actions--%>
    <div class="button-group">
        <form action="order/submit" method="post">
            <button class="button-proceed">Proceed to Payment</button>
        </form>
        <form action="order/cancel" method="post">
            <button type="submit" class="button-cancel">Cancel Order</button>
        </form>
        <form action="cart.jsp" method="get">
            <button class="button-back">Back to Cart</button>
        </form>
    </div>
</div>
</body>
</html>

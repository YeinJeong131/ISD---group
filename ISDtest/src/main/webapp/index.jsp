<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<link rel="stylesheet" href="style.css">
<head>
    <meta charset="UTF-8">
    <title>IoT Bay</title>

</head>
<body>

<div class="navbar">
    <div class="logo"><span>IoT</span><span class="bay"> BaY</span></div>

    <div class="search-bar">
        <input type="text" placeholder="Search...">
    </div>

    <div class="nav-icons">
        <a href="login.jsp">Login</a>
        <a href="cart.jsp" class="cart-text">Cart</a>
    </div>
</div>

<div class="main-content">
    <p>Have ID?</p>
    <button onclick="location.href='login.jsp'">Login</button>
    <p>New customer?</p>
    <button onclick="location.href='register.jsp'">Register</button>
</div>

</body>
</html>
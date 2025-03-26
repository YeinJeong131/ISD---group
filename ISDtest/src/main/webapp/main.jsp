<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.User" %>
<!DOCTYPE html>
<html lang="en">
<link rel="stylesheet" href="style.css">
<head>
    <meta charset="UTF-8">
    <title>IoT Bay</title>
    <style>
        .user-info {
            display: flex;
            align-items: center;
            gap: 10px;
            /*//david*/
        }
        .profile-icon {
            width: 30px;
            height: 30px;
            border-radius: 50%;
        }
    </style>
</head>
<body>
<div class="navbar">
    <div class="logo"><span>IoT</span><span class="bay"> BaY</span></div>
    <div class="search-bar">
        <input type="text" placeholder="Search...">
    </div>
    <div class="nav-icons">
        <%
            User loggedInUser = (User) session.getAttribute("user");
            if (loggedInUser != null) {
        %>
        <div class="user-info">
            <img src="black-icon.png" alt="Profile" class="profile-icon">
            <span><%= loggedInUser.getFirstName() %> <%= loggedInUser.getLastName() %></span>
        </div>
        <a href="logout.jsp">Logout</a>
        <% } else { %>
        <a href="login.jsp">Login</a>
        <% } %>
        <a class="cart-text">Cart</a>
    </div>
</div>
</body>
</html>

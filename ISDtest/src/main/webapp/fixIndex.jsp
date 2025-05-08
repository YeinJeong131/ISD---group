<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="uts.isd.model.dao.User" %>
<%@ page import="uts.isd.model.dao.DBConnector, uts.isd.model.dao.UserDBManager, java.sql.SQLException" %>
<%@ page import="uts.isd.model.dao.DAO" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>IoT Bay</title>
    <link rel="stylesheet" href="indexStyle2.css">
</head>
<body>
<%
    User validUser = (User) session.getAttribute("loggedInUser");
    DAO db = (DAO)session.getAttribute("db");


    if (validUser != null) {

%>
<div class="navbar">
    <div class="logo"><span>IoT</span><span class="bay"> BaY</span></div>
    <div class="search-bar">
        <input type="text" placeholder="Hi <%= validUser.getFirstName() %> <%= validUser.getLastName() %>">
    </div>
    <div class="nav-icons">
        <div class="user-info-box">
            <a href="userInfo.jsp"><img src="userImage2.jpg" alt="USER" style="width: 60px; height: 60px; border-radius: 50%;"/></a>
        </div>


        <form action="/LogoutServlet" method="post" style="margin: 0;">
            <button type="submit" class="logout-button">Logout</button>
        </form>
        <a href="cart.jsp" class="cart-icon">
            <img src = "cart.png" alt="Cart">
        </a>
        <a href="/AccessLogServlet">View Login Record</a>

    </div>
</div>
<div class="main-content">Welcome back <%=validUser.getFirstName()%> <%=validUser.getLastName()%> !</div>
<%
} else {

%>
<div class="navbar">
    <div class="logo"><span>IoT</span><span class="bay"> BaY</span></div>
    <div class="search-bar">
        <input type="text" placeholder="Search...">
    </div>
    <div class="nav-icons">
        <a href="fixRegister.jsp">Register</a>
        <a href="login.jsp">Login</a>
        <a href="cart.jsp" class="cart-icon">
            <img src = "cart.png" alt="Cart">
        </a>
    </div>
</div>
<div class="main-content">
    <div class="main-content-section">
        <p>Have ID?</p>
        <button onclick="location.href='login.jsp'">Login</button>
    </div>
    <div class="main-content-section">
        <p>New customer?</p>
        <button onclick="location.href='fixRegister.jsp'">Register</button>
    </div>


</div>
<%
    }
%>

<%
    String message = request.getParameter("message");
    if ("logout".equals(message)) {
%>
<h2 class="log_out">successfully log out</h2>
<%
    }
%>
</body>
</html>
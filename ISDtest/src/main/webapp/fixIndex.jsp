<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="uts.isd.model.dao.User" %>
<%@ page import="uts.isd.model.dao.DBConnector, uts.isd.model.dao.UserDBManager, java.sql.SQLException" %>
<%@ page import="uts.isd.model.dao.DAO" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>IoT Bay</title>
    <link rel="stylesheet" href="style.css">
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
        <input type="text" placeholder="Search...">
    </div>
    <div class="nav-icons">
        <h4>User name: <%=validUser.getFirstName()%> <%=validUser.getLastName()%></h4>
        <a href="userInfo.jsp"><img src="userimage.jpg" alt="USER" style="width: 60px; height: 60px; border-radius: 50%;"/></a>
        <form action="/LogoutServlet" method="post">
            <button>Logout</button>
        </form>
        <a href="cart.jsp" class="cart-text">Cart</a>
    </div>
</div>
<div class="main-content">Welcome back <%=validUser.getFirstName()%> <%=validUser.getLastName()%> !</div>
<%
} else {
    // 用户未登录，显示默认导航栏
%>
<div class="navbar">
    <div class="logo"><span>IoT</span><span class="bay"> BaY</span></div>
    <div class="search-bar">
        <input type="text" placeholder="Search...">
    </div>
    <div class="nav-icons">
        <a href="fixRegister.jsp">Register</a>
        <a href="login.jsp">Login</a>
        <a href="cart.jsp" class="cart-text">Cart</a>
    </div>
</div>
<div class="main-content">
    <p>Have ID?</p>
    <button onclick="location.href='login.jsp'">Login</button>
    <p>New customer?</p>
    <button onclick="location.href='fixRegister.jsp'">Register</button>
</div>
<%
    }
%>
<!-- 保留原有消息处理逻辑 -->
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

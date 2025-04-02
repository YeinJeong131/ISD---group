<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.User" %>
<!DOCTYPE html>
<html lang="en">
<link rel="stylesheet" href="style.css">
<head>
    <meta charset="UTF-8">
    <title>IoT Bay</title>

</head>

<body>
<%
    String email = request.getParameter("email");
    String password = request.getParameter("password");

    User registeredUser = (User) session.getAttribute("user");
    if (registeredUser != null && registeredUser.getEmail().equals(email) && registeredUser.getPassword().equals(password)) {
%>
<div class="navbar">
    <div class="logo"><span>IoT</span><span class="bay"> BaY</span></div>

    <div class="search-bar">
        <input type="text" placeholder="Search...">
    </div>

    <div class="nav-icons">
        <h4>User name: <%=registeredUser.getFirstName()%> <%=registeredUser.getLastName()%></h4>
        <a href="userInfo.jsp"><image src="userimage.jpg" alt="USER" style="width: 60px; height: 60px; border-radius: 50%;"/></a>
        <a href="logout.jsp">log out</a>
    </div>
</div>
<div class="main-content">Welcome back <%=registeredUser.getFirstName()%> <%=registeredUser.getLastName()%> !</div>
<%
} else {

%>
<div class="navbar">
    <div class="logo"><span>IoT</span><span class="bay"> BaY</span></div>

    <div class="search-bar">
        <input type="text" placeholder="Search...">
    </div>

    <div class="nav-icons">
        <a href="fixRegister.jsp">fixRegister</a>
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


<br>
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

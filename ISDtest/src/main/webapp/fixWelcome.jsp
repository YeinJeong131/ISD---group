<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="uts.isd.model.User" %>

<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="style.css">
    <meta charset="UTF-8">
    <title>Welcome to IoT Bay</title>
</head>

<body>
<header>
    <div class="navbar">
        <div class="logo"><span>IoT</span><span class="bay"> BaY</span></div>
    </div>
</header>


<%
    User user = (User) session.getAttribute("RegisteredUser");
    if (user != null) {
%>
<div class="center-box">
    <div class="card">
        <h2>You successfully registered in IoTBay <%=user.getFullName()%> !</h2>
        <p>Let's login to explore more about our products</p>
        <form action="login.jsp" method="get">
            <button class="login-btn" type="submit">Login</button>
        </form>
    </div>
</div>

<div class="center-box">
    <h2>You failed to register in IoTBay</h2>
    <button><a href="fixRegister.jsp">Register again</a></button>
</div>
<%
    }
%>

</body>
</html>

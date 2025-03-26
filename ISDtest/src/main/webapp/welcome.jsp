<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="model.User" %>
<%
    String email = request.getParameter("email");
    String password = request.getParameter("password");
    String first = request.getParameter("firstName");
    String last = request.getParameter("lastName");
    String address = request.getParameter("address");
    String date = request.getParameter("date of birth");
    String name = first + " " + last;

    if (first != null && last != null) {
        User loggedInUser = new User();
        loggedInUser.setEmail(email);
        loggedInUser.setPassword(password);
        loggedInUser.setName(first, last);
        loggedInUser.setAddress(address);
        loggedInUser.setDateOfBirth(date);

        session.setAttribute("user", loggedInUser);
        response.sendRedirect("main.jsp");
    } else {
        response.sendRedirect("login.jsp");
    }
%>
<!DOCTYPE html>
<html lang="ko">
<link rel="stylesheet" href="style.css">
<head>
    <meta charset="UTF-8">
    <title>Welcome to IoT Bay</title>
</head>



<body>
<header>
    <div class="navbar">
        <div class="logo"><span>IoT</span><span class="bay"> BaY</span></div>
    </div>
</header>

<div class="center-box">
    <div class="card">
        <h2>You successfully registered in IoTBay <%=name%> !</h2>
        <p>Let's login to explore more about our products</p>
        <form action="login.jsp" method="get">
            <button class="login-btn" type="submit">Login</button>
        </form>
    </div>
</div>

</body>
</html>


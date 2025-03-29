<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="model.User" %>
<%
    String email = request.getParameter("email");
    String password = request.getParameter("password");
    String first = request.getParameter("firstName");
    String last = request.getParameter("lastName");
    String address = request.getParameter("address");
    String date = request.getParameter("dob");
    String name = first + " " + last;
%>

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
    if (first != null && last != null) {
        User loggedInUser = new User();
        loggedInUser.setEmail(email);
        loggedInUser.setPassword(password);
        loggedInUser.setFirstName(first);
        loggedInUser.setLastName(last);
        loggedInUser.setAddress(address);
        loggedInUser.setDateOfBirth(date);

        session.setAttribute("user", loggedInUser);

%>
<div class="center-box">
    <div class="card">
        <h2>You successfully registered in IoTBay <%=name%> !</h2>
        <p>Let's login to explore more about our products</p>
        <form action="login.jsp" method="get">
            <button class="login-btn" type="submit">Login</button>
        </form>
    </div>
</div>
<%
    } else {

%>
<div class="center-box">
    <h2>You failed to register in IoTBay</h2>
    <button><a href="fixRegister.jsp">Register again</a></button>
</div>
<%
    }
%>

</body>
</html>


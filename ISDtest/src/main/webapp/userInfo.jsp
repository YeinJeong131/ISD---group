<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="model.User" %>

<%
    User user = (User) session.getAttribute("user");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Information</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
<%
    if (user != null) {
%>
<h2 class="info_h2">User Information</h2>
<ul class="info_ul">
    <li class="info_li"><strong>Full Name:</strong> <%= user.getFirstName() %> <%= user.getLastName() %></li>
    <li class="info_li"><strong>Email:</strong> <%= user.getEmail() %></li>
    <li class="info_li"><strong>Address:</strong> <%= user.getAddress() %></li>
    <li class="info_li"><strong>Date of Birth:</strong> <%= user.getDateOfBirth() %></li>
</ul>

<form action="fixIndex.jsp">
    <button class="info_button" method="get">Back to Home</button>
</form>


<%
} else {
%>
<p>User is not logged in. Please <a href="login.jsp">login</a>.</p>
<%
    }
%>
</body>
</html>

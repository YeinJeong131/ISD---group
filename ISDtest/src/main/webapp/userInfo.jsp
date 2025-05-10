<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="uts.isd.model.User" %>

<%
    User user = (User) session.getAttribute("loggedInUser");
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

<div class="button-container">
    <form action="fixIndex.jsp" class="inline-form">
        <button class="info_button" type="submit">Back to Home</button>
    </form>

    <form action="orderHistory.jsp" class="inline-form">
        <button class="info_button" type="submit">View Order History</button>
    </form>
</div>


<%
} else {
%>
<p>User is not logged in. Please <a href="login.jsp">login</a>.</p>
<%
    }
%>

<div class="content">
    <form action="/EditUserInfoServlet" method="post">

        <label for="firstName">First name:</label>
        <input  id="firstName" name="firstName" type="text" maxlength="10" pattern="[A-Za-z]+" title="Only alphabetic characters are allowed" value="<%=user.getFirstName()%>" required>
        <br>

        <label for="lastName">Last name:</label>
        <input  id="lastName" name="lastName" type="text" maxlength="10" pattern="[A-Za-z]+" title="Only alphabetic characters are allowed" value="<%=user.getLastName()%>" required>
        <br>

        <label for="email">Email:</label>
        <input  id="email" name="email" type="email" value="<%=user.getEmail()%>" required>
        <br>

        <label for="password">Password (Leave blank to keep current):</label>
        <input id="password" name="password" type="password"
               pattern="(?=.*\d)(?=.*[A-Z])(?=.*[\W]).{11,}"
               title="Must be at least 12 characters long, including at least 1 uppercase letter, 1 special character, and 1 number" placeholder="new password">
        <br>

        <label for="address">Address:</label>
        <input  id="address" name="address" type="text" placeholder="Post code, Street, City, State, Province" value="<%=user.getAddress()%>" required>
        <br>

        <label for="dob">Date of Birth:</label>
        <input  id="dob" name="dob" type="date" >
        <br>
        <button type="submit">Update your Information</button>
    </form>

    <div>
        <form method="POST" action="/DeleteServlet">
            <button>Delete my Account</button>
        </form>
    </div>

</div>



</body>
</html>

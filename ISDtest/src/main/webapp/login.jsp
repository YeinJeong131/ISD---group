<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="uts.isd.model.User" %>

<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
<div class="login_parent_container">
    <h2>Login</h2>
    <% if (request.getParameter("error") != null) { %>
    <H3>Invalid email or password.</H3>
    <% } %>

    <form action="/LoginServlet" method="post">
        <label class="login_label" for="email">Email:</label>
        <input id="email" name="email" type="email" required>
        <br><br>

        <label class="login_label" for="password">Password:</label>
        <input id="password" name="password" type="password" required
               pattern="(?=.*\d)(?=.*[A-Z])(?=.*[\W]).{11,}"
               title="Must be at least 12 characters long, including at least 1 uppercase letter, 1 special character, and 1 number">
        <br><br>

        <button class="login_button" type="submit">Login</button>
    </form>

    <p>Don't have an account?</p>
    <button onclick="location.href='fixRegister.jsp'">Register</button>


</div>
</body>
</html>

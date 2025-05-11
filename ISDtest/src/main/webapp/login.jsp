<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="uts.isd.model.User" %>

<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="loginStyle2.css">
</head>
<body>
<div class="login_parent_container">
    <div class="login_title"> Login</div>
    <% if (request.getParameter("error") != null) { %>
    <div class="error-message">Invalid email or password.</div>
    <% } %>

    <form action="/LoginServlet" method="post">
        <div class="login_form_group">
            <label class="login_label" for="email">Email:</label>
            <input class="login_input" id="email" name="email" type="email" required>
        </div>

        <div class="login_form_group">
            <label class="login_label" for="password">Password:</label>
            <input class="login_input" id="password" name="password" type="password" required
                   pattern="(?=.*\d)(?=.*[A-Z])(?=.*[\W]).{11,}"
                   title="Must be at least 12 characters long, including at least 1 uppercase letter, 1 special character, and 1 number">
        </div>



        <button class="login_button" type="submit">Login</button>
    </form>

    <div class="login_link_text">Don't have an account?</div>
    <button class="login_register_button" onclick="location.href='fixRegister.jsp'">Register</button>


</div>
</body>
</html>

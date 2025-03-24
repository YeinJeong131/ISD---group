<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <style>
        body {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }
        .content {
            text-align: center;
            background: #f8f8f8;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
        }
    </style>
</head>
<body>
<div class="content">
    <h2>Login</h2>

    <form action="welcome.jsp" method="post">
        <label for="email">Email:</label>
        <input id="email" name="email" type="email" required>
        <br><br>

        <label for="password">Password:</label>
        <input id="password" name="password" type="password" required
               pattern="(?=.*\d)(?=.*[A-Z])(?=.*[\W]).{11,}"
               title="Must be at least 12 characters long, including at least 1 uppercase letter, 1 special character, and 1 number">
        <br><br>

        <button type="submit">Login</button>
    </form>
</div>
</body>
</html>

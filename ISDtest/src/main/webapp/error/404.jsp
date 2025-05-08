<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>can not find this page</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .container {
            margin-top: 100px;
            text-align: center;
        }
        .error-code {
            font-size: 72px;
            font-weight: bold;
            color: #dc3545;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="error-code">404</div>
    <h2>can not find this page</h2>
    <p class="lead">Sorry, the page you are finding does not exist.</p>
    <a href="${pageContext.request.contextPath}/device/" class="btn btn-primary">Back to device</a>
</div>
</body>
</html>
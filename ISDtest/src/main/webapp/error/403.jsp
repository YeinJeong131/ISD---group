<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Refused</title>
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
    <div class="error-code">403</div>
    <h2>Refused</h2>
    <p class="lead">Sorry, you can not view this page.</p>
    <p>Only staff can</p>
    <a href="${pageContext.request.contextPath}/device/" class="btn btn-primary">Back to device</a>
</div>
</body>
</html>
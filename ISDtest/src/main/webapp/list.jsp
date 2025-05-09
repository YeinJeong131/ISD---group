<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>IoT Products</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="style.css">
    <style>
        .container {
            margin-top: 30px;
            background-color: #777;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
        }
        .table {
            margin-top: 20px;
            background-color: white;
            color: #333;
        }
        .search-section {
            margin-bottom: 20px;
            display: flex;
            gap: 10px;
        }
        .search-section input {
            padding: 8px;
            border-radius: 4px;
            border: 1px solid #ddd;
        }
        .search-section button {
            background-color: lightgreen;
            color: #333;
            border: none;
            padding: 8px 16px;
            border-radius: 4px;
            cursor: pointer;
        }
        .search-section button:hover {
            background-color: #90EE90;
        }
        h2 {
            color: lightgreen;
            margin-bottom: 20px;
        }
        .error-message {
            color: #ff4444;
            background-color: #ffeeee;
            padding: 10px;
            border-radius: 4px;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>IoT Products</h2>

    <%-- 错误信息显示 --%>
    <c:if test="${not empty error}">
        <div class="error-message">
            <strong>Error:</strong> ${error}
        </div>
    </c:if>

    <%-- 调试信息 --%>
    <c:if test="${not empty debug}">
        <div class="error-message">
            <strong>Debug Info:</strong> ${debug}
        </div>
    </c:if>

    <%-- 判空 --%>
    <c:if test="${empty devices}">
        <div style="color:orange;">failed or the list is empty</div>
    </c:if>

    <div class="search-section">
        <form action="${pageContext.request.contextPath}/device/" method="get" class="d-flex gap-2">
            <input type="text" name="searchName" placeholder="Search by name..." value="${param.searchName}">
            <input type="text" name="searchType" placeholder="Search by type..." value="${param.searchType}">
            <button type="submit">Search</button>
        </form>
    </div>

    <c:if test="${sessionScope.userRole == 'staff'}">
        <a href="${pageContext.request.contextPath}/device/new" class="btn btn-primary">Add New Product</a>
    </c:if>

    <table class="table table-striped table-bordered">
        <thead>
        <tr>
            <th>ID</th>
            <th>Product Name</th>
            <th>Type</th>
            <th>Price</th>
            <th>Stock</th>
            <th>Created At</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${devices}" var="device">
            <tr>
                <td>${device.id}</td>
                <td>${device.name}</td>
                <td>${device.type}</td>
                <td>￥<fmt:formatNumber value="${device.price}" pattern="#,##0.00"/></td>
                <td>${device.quantity}</td>
                <td><fmt:formatDate value="${device.createdAt}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                <td>
                    <c:if test="${sessionScope.userRole == 'staff'}">
                        <a href="${pageContext.request.contextPath}/device/edit?id=${device.id}" class="btn btn-sm btn-warning">Edit</a>
                        <a href="${pageContext.request.contextPath}/device/delete?id=${device.id}" class="btn btn-sm btn-danger" onclick="return confirm('Are you sure you want to delete this product?')">Delete</a>
                    </c:if>
                    <a href="${pageContext.request.contextPath}/cart.jsp?deviceId=${device.id}" class="btn btn-sm btn-success">Add to Cart</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <form action="fixIndex.jsp">
        <button class="info_button" method="get">Back to Home</button>
    </form>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

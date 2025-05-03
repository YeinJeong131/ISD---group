<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head><title>My login record</title></head>
<body>
<h2>My login record</h2>
<table border="1">
    <tr>
        <th>Log ID</th>
        <th>Login Time</th>
        <th>Logout Time</th>
    </tr>

    <c:forEach items="${logs}" var="log">
        <tr>
            <td>${log.logId}</td>
            <td>${log.loginTime}</td>
            <td>
                <c:choose>
                    <c:when test="${empty log.logoutTime}">
                        Not Logged Out
                    </c:when>
                    <c:otherwise>
                        ${log.logoutTime}
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head><title>My login record</title></head>
<body>
    <h2>My login record</h2>

    <form action="AccessLogServlet" method="get">
        <label>Search by date: </label>
        <input type="date" name="selectedDate" value="${selectedDate}"/>
        <button type="submit">Search</button>
    </form>

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
        <c:if test="${empty logs}">
            <tr>
                <td colspan="3" style="text-align: center">No log found</td>
            </tr>
        </c:if>
    </table>

    <div>
        <c:if test="${currentPage > 1}">
            <a href="AccessLogServlet?requestedPage=${currentPage-1}&selectedDate=${selectedDate}">Previous</a>
        </c:if>
        &nbsp;&nbsp;Page ${currentPage}&nbsp;&nbsp;
        <c:if test="${hasNextPage}">
            <a href="AccessLogServlet?requestedPage=${currentPage+1}&selectedDate=${selectedDate}">Next</a>
        </c:if>
    </div>
    <a href="fixIndex.jsp">Back to Home</a>
</body>
</html>

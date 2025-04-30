<%
    session.invalidate();
    response.sendRedirect("fixIndex.jsp?message=logout");
%>
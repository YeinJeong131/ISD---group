<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="model.User" %>
<%
    String email = request.getParameter("email");
    String password = request.getParameter("password");
    String first = request.getParameter("firstName");
    String last = request.getParameter("lastName");
    String address = request.getParameter("address");
    String date = request.getParameter("date of birth");
    String name = first + " " + last;

    boolean firstLast = first != null || last != null;

    if (firstLast){
        User newCustomer = new User();
        newCustomer.setEmail(email);
        newCustomer.setPassword(password);
        newCustomer.setName(first, last);
        newCustomer.setAddress(address);
        newCustomer.setDateOfBirth(date);

%>
<!DOCTYPE html>
<html lang="ko">
<link rel="stylesheet" href="style.css">
<head>
    <meta charset="UTF-8">
    <title>Welcome to IoT Bay</title>
</head>



<body>
<header>
    <div class="navbar">
        <div class="logo"><span>IoT</span><span class="bay"> BaY</span></div>
    </div>
</header>

<div class="center-box">
    <div class="card">
        <h2>You successfully registered in IoTBay <%=name%> !</h2>
        <p>Let's login to explore more about our products</p>
            <form action="login.jsp" method="get">
                <button class="login-btn" type="submit">Login</button>
            </form>
    </div>
</div>

</body>
</html>
<%
} else {
        User customer = new User();
        customer.setEmail(email);
        customer.setPassword(password);

        session.setAttribute("Customer", customer);
        response.sendRedirect("main.jsp");
}
    %>

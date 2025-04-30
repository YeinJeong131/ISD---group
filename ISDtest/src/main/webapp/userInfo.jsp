<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="uts.isd.model.dao.User" %>

<%
    User user = (User) session.getAttribute("loggedInUser");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Information</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
<%
    if (user != null) {
%>
<h2 class="info_h2">User Information</h2>
<ul class="info_ul">
    <li class="info_li"><strong>Full Name:</strong> <%= user.getFirstName() %> <%= user.getLastName() %></li>
    <li class="info_li"><strong>Email:</strong> <%= user.getEmail() %></li>
    <li class="info_li"><strong>Address:</strong> <%= user.getAddress() %></li>
    <li class="info_li"><strong>Date of Birth:</strong> <%= user.getDateOfBirth() %></li>
</ul>

<form action="fixIndex.jsp">
    <button class="info_button" method="get">Back to Home</button>
</form>


<%
} else {
%>
<p>User is not logged in. Please <a href="login.jsp">login</a>.</p>
<%
    }
%>

<div class="content">
    <form action="/EditUserInfoServlet" method="post">

        <label for="firstName">First name:</label>
        <input  id="firstName" name="firstName" type="text" maxlength="10" pattern="[A-Za-z]+" title="Only alphabetic characters are allowed" value=<%=user.getFirstName()%> required>
        <br>

        <label for="lastName">Last name:</label>
        <input  id="lastName" name="lastName" type="text" maxlength="10" pattern="[A-Za-z]+" title="Only alphabetic characters are allowed" value=<%=user.getLastName()%> required>
        <br>

        <label for="email">Email:</label>
        <input  id="email" name="email" type="email" value=<%=user.getEmail()%> required>
        <br>

        <label for="password">Password:</label>
        <input id="password" name="password" type="password" required
               pattern="(?=.*\d)(?=.*[A-Z])(?=.*[\W]).{11,}"
               title="Must be at least 12 characters long, including at least 1 uppercase letter, 1 special character, and 1 number" value=<%=user.getPassword()%>>
        <br>

        <label for="address">Address:</label>
        <input  id="address" name="address" type="text" placeholder="Post code, Street, City, State, Province" value=<%=user.getAddress()%> required>
        <br>

        <label for="dob">Date of Birth:</label>
        <input  id="dob" name="dob" type="text" maxlength="10" pattern="\d{2}/\d{2}/\d{4}" placeholder="DDMMYYYY" required
                title="Enter your date of birth in DDMMYYYY format">
        <br>
        <script>
            document.getElementById("dob").addEventListener("input", function(e) {
                e.target.value=e.target.value
                    .replace(/\D/g, "")
                    .replace(/^(\d{2})(\d)/, "$1/$2")
                    .replace(/^(\d{2})\/(\d{2})(\d)/, "$1/$2/$3")
                    .slice(0,10);
            });
        </script>

        <label for="paymentMethod">Changing Payment Method:</label>
        <select id="paymentMethod" name="paymentMethod" required onchange="togglePaymentDetails()">
            <option value="">Select a method</option>
            <option value="card">Card</option>
            <option value="bank">Bank</option>
        </select>
        <br>


        <script>
            function togglePaymentDetails() {
                const paymentMethod = document.getElementById("paymentMethod").value;
                const showCardDiv = document.getElementById("cardDetails");
                const showBankDiv = document.getElementById("bankDetails");

                showCardDiv.style.display = (paymentMethod === "card") ? "block" : "none";
                showBankDiv.style.display =  (paymentMethod === "bank") ? "block" : "none";
            }
        </script>


        <div id="cardDetails" style="display:none">
            <label for="cardNumber">Card Number:</label>
            <input  id="cardNumber" name="cardNumber" type="text" maxlength="16" placeholder="Enter card number">
            <br>

            <label for="expiryDate">Expiry Date:</label>
            <input type="text" id="expiryDate" name="expiryDate" maxlength="5" placeholder="MM/YY">
            <br>

            <script>
                document.getElementById("expiryDate").addEventListener("input", function(e) {
                    e.target.value=e.target.value
                        .replace(/\D/g, "")
                        .replace(/^(\d{2})(\d)/, "$1/$2")
                        .slice(0,5);
                });
            </script>

            <label for="cvv">CVV:</label>
            <input type="text" id="cvv" name="cvv" maxlength="3" placeholder="Enter CVV">
            <br>
        </div>


        <div id="bankDetails" style="display:none">
            <label for="bsb">BSB Number:</label>
            <input  id="bsb" name="bsb" type="text" maxlength="6" placeholder="Enter BSB Number">
            <br>

            <label for="accountNumber">Account Number:</label>
            <input  id="accountNumber" name="accountNumber" type="text" maxlength="10" placeholder="Enter Account Number">
            <br>
        </div>

        <br>
        <button class="register_button" type="submit">Register</button>
    </form>

    <div>
        <form method="POST" action="/DeleteServlet">
            <button>Delete my Account</button>
        </form>
    </div>

</div>



</body>
</html>

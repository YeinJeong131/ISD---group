<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
  <title>Register</title>
  <link rel="stylesheet" href="style.css">
</head>
<body>
<div class="register_parent_container">
  <div class="title">
    <h2>Registration form</h2>
  </div>
  <div class="content">
    <form action="/RegisterServlet" method="post">

      <label for="firstName">First name:</label>
      <input  id="firstName" name="firstName" type="text" maxlength="10" pattern="[A-Za-z]+" title="Only alphabetic characters are allowed" required>
      <br>

      <label for="lastName">Last name:</label>
      <input  id="lastName" name="lastName" type="text" maxlength="10" pattern="[A-Za-z]+" title="Only alphabetic characters are allowed" required>
      <br>

      <label for="email">Email:</label>
      <input  id="email" name="email" type="email" required oninput="checkExistingEmail()"/>
      <div id="error-message" class="error-message"></div>
      <br>
          <script>
            function checkExistingEmail() {
              console.log("checkExistingEmail called.");
              const email = document.getElementById("email").value;
              const errorMessage = document.getElementById("error-message");
              const checkAvailabilityButton = document.getElementById("checkAvailability");

              if (email.length < 5) {
                errorMessage.innerText = "";
                errorMessage.style.display = "none";
                checkAvailabilityButton.disabled = true;
                return;
              }

              fetch("/EmailErrorServlet?email=" + encodeURIComponent(email))
                      .then(response => {
                        console.log("Server response: " + response.status);
                        return response.text();
                      })
                      .then(data => {
                        console.log("Server response: " + data);
                        data = data.trim();

                        if (data === "existing email") {
                          errorMessage.innerText = "This email is already registered. Use a different email.";
                          errorMessage.style.display = "block";
                          checkAvailabilityButton.disabled = true;
                        } else if (data === "can use this email") {
                          errorMessage.innerText = "Valid email address.";
                          errorMessage.style.display = "block";
                          checkAvailabilityButton.disabled = false;
                        } else {
                          errorMessage.innerText = "It is unavailable to check email now. Please try again later.";
                          errorMessage.style.display = "block";
                          checkAvailabilityButton.disabled = true;
                        }
                      })
                      .catch(error => {
                        console.log("Fail to check email: " + error);
                        errorMessage.innerText = "network error.";
                        errorMessage.style.display = "block";
                        checkAvailabilityButton.disabled = true;
                      })

            }
          </script>

      <label for="password">Password:</label>
      <input id="password" name="password" type="password" required
             pattern="(?=.*\d)(?=.*[A-Z])(?=.*[\W]).{11,}"
             title="Must be at least 12 characters long, including at least 1 uppercase letter, 1 special character, and 1 number">
      <br>

      <label for="address">Address:</label>
      <input  id="address" name="address" type="text" placeholder="Post code, Street, City, State, Province" required>
      <br>

      <label for="dob">Date of Birth:</label>
      <input  id="dob" name="dob" type="date"  required>
      <br>

      <label for="paymentMethod">Payment Method:</label>
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
      <button class="register_button" type="submit" id="checkAvailability" disabled>Register</button>


    </form>
  </div>
</div>

</body>
</html>

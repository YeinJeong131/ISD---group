## Online User Access Management (MVC)

This section describes the **user authentication and access management module that I implemented** as part of the IoTBay Web System team project.

The module manages user registration, authentication, profile management, and access logging using **Java Servlets, JSP, and the MVC architecture**.

### Features
- User registration with name, email (username), password, and phone number
- User login and logout functionality
- View and update user profile information
- Access log tracking (login and logout timestamps stored in database)
- View and search access logs by date
- User account cancellation

### Technologies Used
- Java (JSP / Servlet)
- MVC Architecture
- JDBC
- SQLite Database
- Apache Tomcat

### Key Components Implemented

**Controllers**
- `RegisterServlet`
- `LoginServlet`
- `LogoutServlet`
- `EditUserInfoServlet`

**Models / DAO**
- `User`
- `UserDBManager`
- `DBConnector`
- `DBManager`

**Views**
- `login.jsp`
- `logout.jsp`
- `userInfo.jsp`
- `viewLog.jsp`

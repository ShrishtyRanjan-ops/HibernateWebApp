
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    if (session.getAttribute("userId") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Home Page</title>
</head>
<body>
    <h1>Welcome, <%= session.getAttribute("userName") %>!</h1>
    <p>You have logged in successfully.</p>

    <h2>Employee Management System</h2>
    <p>Choose an option below:</p>

    <a href="index.jsp">Home Page</a>
    <br><br>
    <a href="employees">Manage Employees</a>
    <br><br>
    <a href="logout">Logout</a>
</body>
</html>

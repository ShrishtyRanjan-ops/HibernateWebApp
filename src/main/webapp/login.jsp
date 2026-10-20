
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Login</title>
</head>
<body>
    <h2>User Login</h2>

    <form action="login" method="post">
        <label>Email:</label>
        <input type="email" name="email" required>
        <br><br>

        <label>Password:</label>
        <input type="password" name="password" required>
        <br><br>

        <button type="submit">Login</button>
    </form>

    <p>New user? <a href="register.jsp">Register here</a></p>
    <p><a href="index.jsp">Back to Home</a></p>
</body>
</html>

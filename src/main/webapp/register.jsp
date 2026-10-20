
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Registration</title>
</head>
<body>
    <h2>User Registration</h2>

    <form action="register" method="post">
        <label>Full Name:</label>
        <input type="text" name="name" required>
        <br><br>

        <label>Email:</label>
        <input type="email" name="email" required>
        <br><br>

        <label>Password:</label>
        <input type="password" name="password" required>
        <br><br>

        <button type="submit">Register</button>
    </form>

    <p>Already registered? <a href="login.jsp">Login here</a></p>
    <p><a href="index.jsp">Back to Home</a></p>
</body>
</html>

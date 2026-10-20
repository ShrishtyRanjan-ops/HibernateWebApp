
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.hibernateapp.Employee" %>
<%
    if (session.getAttribute("userId") == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    List<Employee> employees =
        (List<Employee>) request.getAttribute("employees");

    Employee editEmployee =
        (Employee) request.getAttribute("editEmployee");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Employee Management</title>
</head>
<body>
    <h1>Employee Management System</h1>

    <% if (editEmployee != null) { %>
        <h2>Update Employee</h2>
        <form action="employees" method="post">
            <input type="hidden" name="action" value="update">
            <input type="hidden" name="id"
                   value="<%= editEmployee.getId() %>">

            <label>Name:</label>
            <input type="text" name="name"
                   value="<%= editEmployee.getName() %>" required><br><br>

            <label>Email:</label>
            <input type="email" name="email"
                   value="<%= editEmployee.getEmail() %>" required><br><br>

            <label>Department:</label>
            <input type="text" name="department"
                   value="<%= editEmployee.getDepartment() %>" required><br><br>

            <button type="submit">Update Employee</button>
            <a href="employees">Cancel</a>
        </form>
    <% } else { %>
        <h2>Add Employee</h2>
        <form action="employees" method="post">
            <label>Name:</label>
            <input type="text" name="name" required><br><br>

            <label>Email:</label>
            <input type="email" name="email" required><br><br>

            <label>Department:</label>
            <input type="text" name="department" required><br><br>

            <button type="submit">Add Employee</button>
        </form>
    <% } %>

    <hr>
    <h2>Employee List</h2>

    <table border="1" cellpadding="8">
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Email</th>
            <th>Department</th>
            <th>Actions</th>
        </tr>

        <% if (employees != null && !employees.isEmpty()) {
            for (Employee employee : employees) { %>
                <tr>
                    <td><%= employee.getId() %></td>
                    <td><%= employee.getName() %></td>
                    <td><%= employee.getEmail() %></td>
                    <td><%= employee.getDepartment() %></td>
                    <td>
                        <a href="employees?edit=<%= employee.getId() %>">
                            Edit
                        </a>

                        <form action="employees" method="post"
                              style="display:inline;"
                              onsubmit="return confirm('Delete this employee?');">
                            <input type="hidden" name="action" value="delete">
                            <input type="hidden" name="id"
                                   value="<%= employee.getId() %>">
                            <button type="submit">Delete</button>
                        </form>
                    </td>
                </tr>
        <%  }
           } else { %>
                <tr>
                    <td colspan="5">No employees found.</td>
                </tr>
        <% } %>
    </table>

    <br>
    <a href="home.jsp">Back to Home</a>
</body>
</html>

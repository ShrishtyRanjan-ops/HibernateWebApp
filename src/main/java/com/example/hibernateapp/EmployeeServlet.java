
package com.example.hibernateapp;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;
import java.util.List;

@WebServlet("/employees")
public class EmployeeServlet extends HttpServlet {

    private boolean isLoggedIn(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null &&
                session.getAttribute("userId") != null;
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        if (!isLoggedIn(request)) {
            response.sendRedirect("login.jsp");
            return;
        }

        String editId = request.getParameter("edit");

        try (Session session =
                     HibernateUtil.getSessionFactory().openSession()) {

            List<Employee> employees =
                    session.createQuery(
                            "FROM Employee", Employee.class).list();

            request.setAttribute("employees", employees);

            if (editId != null) {
                try {
                    Long id = Long.parseLong(editId);
                    Employee employee = session.get(Employee.class, id);

                    if (employee != null) {
                        request.setAttribute("editEmployee", employee);
                    }
                } catch (NumberFormatException e) {
                    response.sendError(400, "Invalid employee ID.");
                    return;
                }
            }

            request.getRequestDispatcher("employees.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            throw new ServletException("Unable to load employees.", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        if (!isLoggedIn(request)) {
            response.sendRedirect("login.jsp");
            return;
        }

        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        Transaction transaction = null;

        try (Session session =
                     HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            if ("delete".equals(action)) {
                Long id = Long.parseLong(request.getParameter("id"));
                Employee employee = session.get(Employee.class, id);

                if (employee != null) {
                    session.remove(employee);
                }

            } else if ("update".equals(action)) {
                Long id = Long.parseLong(request.getParameter("id"));
                String name = request.getParameter("name");
                String email = request.getParameter("email");
                String department = request.getParameter("department");

                if (name == null || name.isBlank() ||
                        email == null || email.isBlank() ||
                        department == null || department.isBlank()) {
                    response.sendError(400, "All fields are required.");
                    return;
                }

                Employee employee = session.get(Employee.class, id);

                if (employee == null) {
                    response.sendError(404, "Employee not found.");
                    return;
                }

                employee.setName(name.trim());
                employee.setEmail(email.trim());
                employee.setDepartment(department.trim());

            } else {
                String name = request.getParameter("name");
                String email = request.getParameter("email");
                String department = request.getParameter("department");

                if (name == null || name.isBlank() ||
                        email == null || email.isBlank() ||
                        department == null || department.isBlank()) {
                    response.sendError(400, "All fields are required.");
                    return;
                }

                Employee employee = new Employee(
                        name.trim(), email.trim(), department.trim());

                session.persist(employee);
            }

            transaction.commit();
            response.sendRedirect("employees");

        } catch (NumberFormatException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            response.sendError(400, "Invalid employee ID.");
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new ServletException(
                    "Unable to process employee request.", e);
        }
    }
}

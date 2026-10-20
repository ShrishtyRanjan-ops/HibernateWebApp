package com.example.hibernateapp;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.hibernate.Session;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (email == null || email.isBlank()
                || password == null || password.isBlank()) {
            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Email and password are required."
            );
            return;
        }

        try (Session session =
                     HibernateUtil.getSessionFactory().openSession()) {

            User user = session.createQuery(
                            "FROM User u WHERE u.email = :email",
                            User.class)
                    .setParameter("email", email.trim())
                    .uniqueResult();

            if (user != null
                    && user.getPasswordHash()
                    .equals(hashPassword(password))) {

                HttpSession httpSession = request.getSession(true);
                httpSession.setAttribute("userId", user.getId());
                httpSession.setAttribute("userName", user.getName());

                response.sendRedirect("home.jsp");

            } else {
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().println(
                        "Invalid email or password. "
                                + "<a href='login.jsp'>Try again</a>"
                );
            }

        } catch (Exception e) {
            throw new ServletException("Login failed.", e);
        }
    }

    private String hashPassword(String password)
            throws ServletException {

        try {
            MessageDigest digest =
                    MessageDigest.getInstance("SHA-256");

            byte[] hash = digest.digest(
                    password.getBytes(StandardCharsets.UTF_8));

            return HexFormat.of().formatHex(hash);

        } catch (NoSuchAlgorithmException e) {
            throw new ServletException(
                    "Password verification failed.", e);
        }
    }
}

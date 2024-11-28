package org.example.hotelbookingapp;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.util.DataBaseUtil;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String fullName = request.getParameter("fullName");
        String role = request.getParameter("role");

        if (!password.equals(confirmPassword)) {
            request.setAttribute("errorMessage", "Пароли не совпадают!");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }

        try (Connection connection = DataBaseUtil.getConnection()) {
            String checkQuery = "SELECT * FROM users WHERE username = ? OR email = ?";
            try (PreparedStatement checkStmt = connection.prepareStatement(checkQuery)) {
                checkStmt.setString(1, username);
                checkStmt.setString(2, email);
                ResultSet resultSet = checkStmt.executeQuery();
                if (resultSet.next()) {
                    request.setAttribute("errorMessage", "Пользователь с таким именем или email уже существует!");
                    request.getRequestDispatcher("/register.jsp").forward(request, response);
                    return;
                }
            }


            String insertQuery = "INSERT INTO users (username, email, password, fullName, role) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
                insertStmt.setString(1, username);
                insertStmt.setString(2, email);
                insertStmt.setString(3, password);
                insertStmt.setString(4, fullName);
                insertStmt.setString(5, role);
                int rowsInserted = insertStmt.executeUpdate();
                if (rowsInserted > 0) {
                    if (role == "admin"){
                        response.sendRedirect("adminDashboard");
                    } else {
                        response.sendRedirect("bookRoom");
                    }

                } else {
                    request.setAttribute("errorMessage", "Ошибка регистрации! Попробуйте снова.");
                    request.getRequestDispatcher("/register.jsp").forward(request, response);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Произошла ошибка сервера!");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        }
    }
}

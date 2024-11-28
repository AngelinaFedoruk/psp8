package org.example.hotelbookingapp;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.models.User;
import org.example.util.DataBaseUtil;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/login")  // Добавляем аннотацию для указания URL-адреса сервлета
public class LoginServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        System.out.println("LoginServlet инициализирован.");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("fvgjhkn");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = validateUser(username, password);

        if (user != null) {
            request.getSession().setAttribute("user", user);
            if ("admin".equals(user.getRole())) {
                response.sendRedirect("adminDashboard");
            } else {
                response.sendRedirect("bookRoom");
            }
        } else {
            response.getWriter().println("Неверное имя пользователя или пароль.");
        }
    }

    private User validateUser(String username, String password) {
        String query = "SELECT password, role, id FROM users WHERE username = ? AND password = ?";

        // Используем try-with-resources для автоматического закрытия соединений
        try (Connection connection = DataBaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Устанавливаем параметры запроса
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            // Выполняем запрос и получаем результат
            ResultSet resultSet = preparedStatement.executeQuery();

            // Проверяем, если пользователь найден в базе
            if (resultSet.next()) {
                String storedPassword = resultSet.getString("password");
                String role = resultSet.getString("role");
                int id = resultSet.getInt("id");

                if (password.equals(storedPassword)) {
                    return new User(username, password, role, id);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Логирование ошибки
        }

        return null;
    }

    @Override
    public void destroy() {
        System.out.println("LoginServlet уничтожен.");
    }
}

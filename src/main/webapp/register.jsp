<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Регистрация</title>
    <link rel="stylesheet" href="register.css">
</head>
<body>
<h1>Регистрация</h1>
<form action="<%= request.getContextPath() %>/register" method="POST">
    <label for="fullName">ФИО:</label>
    <input type="text" id="fullName" name="fullName" required>

    <label for="username">Имя пользователя:</label>
    <input type="text" id="username" name="username" required>

    <label for="email">Email:</label>
    <input type="email" id="email" name="email" required>

    <label for="password">Пароль:</label>
    <input type="password" id="password" name="password" required>

    <label for="confirmPassword">Подтвердите пароль:</label>
    <input type="password" id="confirmPassword" name="confirmPassword" required>

    <label for="role">Роль:</label>
    <select id="role" name="role" required>
        <option value="user">Пользователь</option>
        <option value="admin">Администратор</option>
    </select>


    <button type="submit">Зарегистрироваться</button>
</form>

<% if (request.getAttribute("errorMessage") != null) { %>
<p style="color: red;"><%= request.getAttribute("errorMessage") %></p>
<% } %>

</body>
</html>

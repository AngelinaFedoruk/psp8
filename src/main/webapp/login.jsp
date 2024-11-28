<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Вход в систему</title>
   <link rel="stylesheet" href="login.css">
</head>
<body>

<div class="login-container">
    <h1>Авторизация</h1>
    <form action="login" method="POST">
        <label for="username">Имя пользователя:</label>
        <input type="text" id="username" name="username" required><br>

        <label for="password">Пароль:</label>
        <input type="password" id="password" name="password" required><br>

        <button type="submit">Войти</button>
    </form>

    <div class="footer">
        <p>&copy; 2024 Гостиница "Уют". Все права защищены.</p>
    </div>
</div>

</body>
</html>

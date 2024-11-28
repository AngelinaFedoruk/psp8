<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Отель "Glamour"</title>
    <link rel="stylesheet" href="index.css">
</head>
<body>

<header>
    <img src="<%= request.getContextPath() + "/images/girls.jpg" %>" alt="Гостиница Уют" class="logo">
    <h1>Добро пожаловать в отель "Glamour</h1>
</header>

<div class="container">
    <div class="intro-text">
        <p>Мы рады приветствовать вас в нашей уютной гостинице, где комфорт и качество на первом месте!</p>
        <p>Забронируйте номер для вашего отдыха или деловой поездки прямо сейчас!</p>
        <div class="button-container">
            <a href="login.jsp" class="btn">Войти в личный кабинет</a>
            <a href="register.jsp" class="btn btn-secondary">Зарегистрироваться</a>
        </div>
    </div>
</div>

<div class="footer">
    <p>&copy; 2024 Отель "Glamour. Все права защищены.</p>
</div>

</body>
</html>

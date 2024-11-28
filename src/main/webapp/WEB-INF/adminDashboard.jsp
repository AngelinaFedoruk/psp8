<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="org.example.models.Booking" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Панель администратора</title>
    <link rel="stylesheet" href="adminStyle.css">

    <style>
        body {
            font-family: 'Lora', serif; /* Используем более изысканный шрифт */
            margin: 0;
            padding: 0;
            background: linear-gradient(to bottom right, #f7a8b8, #f8d7e2); /* Градиентный фон */
        }

        h1 {
            text-align: center;
            margin-top: 30px;
            color: #444;
            font-family: 'Playfair Display', serif;
            font-size: 36px;
            text-shadow: 2px 2px 5px rgba(0, 0, 0, 0.2); /* Тень для текста */
        }

        .reservation-list {
            width: 80%;
            margin: 30px auto;
            background: #fff;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
            border-radius: 15px;
            padding: 40px;
            border: 1px solid #f2a2c9; /* Розовый бордер */
            background: #fff;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 10px;
        }

        th, td {
            padding: 18px;
            border: 1px solid #f2a2c9;
            text-align: left;
            font-size: 16px;
            font-family: 'Arial', sans-serif;
        }

        th {
            background-color: #f8e5f2;
            font-weight: bold;
            color: #9c4b7b; /* Темно-розовый цвет для заголовков */
        }

        td {
            background-color: #fff;
            color: #9c4b7b; /* Темно-розовый цвет для текста */
        }

        td:hover {
            background-color: #f1d4e6; /* Цвет при наведении на ячейку */
        }

        p {
            text-align: center;
            font-size: 20px;
            color: #9c4b7b;
            font-weight: 600;
            margin-top: 20px;
        }

        /* Стиль для кнопки назад */
        .back-button {
            display: inline-block;
            padding: 12px 25px;
            background-color: #9c4b7b;
            color: white;
            text-decoration: none;
            border-radius: 30px; /* Закругленные края */
            font-size: 18px;
            font-weight: 600;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.15);
            transition: background-color 0.3s ease, transform 0.3s ease;
        }

        .back-button:hover {
            background-color: #d38ab1; /* Легкий розовый оттенок */
            transform: scale(1.05); /* Легкое увеличение кнопки при наведении */
        }

        .back-button:active {
            transform: scale(1); /* При нажатии кнопка возвращается в обычное состояние */
        }

    </style>
</head>
<body>


<a href="index.jsp" class="back-button" onclick="clearSession()">Назад</a>

<h1>Панель администратора</h1>

<% List<Booking> reservations = (List<Booking>) request.getAttribute("reservations"); %>

<% if (reservations != null && !reservations.isEmpty()) { %>
<div class="reservation-list">
    <table>
        <thead>
        <tr>
            <th>ID бронирования</th>
            <th>Имя пользователя</th>
            <th>Номер комнаты</th>
            <th>Дата бронирования</th>
        </tr>
        </thead>
        <tbody>
        <% for (Booking reservation : reservations) { %>
        <tr>
            <td><%= reservation.getId() %></td>
            <td><%= reservation.getUserName() %></td>
            <td><%= reservation.getRoomNumber() %></td>
            <td><%= reservation.getBookingDate() %></td>
        </tr>
        <% } %>
        </tbody>
    </table>
</div>
<% } else { %>
<p>На данный момент нет забронированных номеров.</p>
<% } %>

</body>
<script>
    function clearSession() {
        fetch('/logout')
            .then(response => {
                if (response.ok) {
                    window.location.href = 'index.jsp'; // Перенаправление после успешного логута
                } else {
                    console.log('Ошибка при выходе:', response.status);
                }
            })
            .catch(error => console.log('Ошибка при очистке сессии:', error));

    }
</script>
</html>

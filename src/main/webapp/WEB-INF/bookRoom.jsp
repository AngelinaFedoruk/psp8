<%@ page import="org.example.models.Room" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ru">
<head>init
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Бронирование номера</title>
    <link rel="stylesheet" href="bookRoom.css">

</head>
<body>
<h1>Доступные номера для бронирования</h1>

<!-- Кнопка назад -->
<a href="index.jsp" class="back-button" onclick="clearSession()">Назад</a>

<% List<Room> availableRooms = (List<Room>) request.getAttribute("availableRooms"); %>

<% if (availableRooms != null && !availableRooms.isEmpty()) { %>
<div class="room-list">
    <% int count = 0; %>
    <% for (Room room : availableRooms) { %>
    <% if (count % 3 == 0) { %>
    <div class="room-row">
        <% } %>

        <div class="room-item" style="background-image: url('<%= request.getContextPath() + "/images/" + room.getImage_path() %>'); background-size: cover; background-position: center;">
            <h2><%= room.getDescription() %></h2>
            <p>Номер:<%= room.getRoom_number() %></p>
            <p>Доступность: <%= room.isAvailable() ? "Да" : "Нет" %></p>
            <form action="bookRoom" method="POST">
                <input type="hidden" name="roomId" value="<%= room.getId() %>">
                <button type="submit">Забронировать</button>
            </form>
        </div>

        <% count++; %>
        <% if (count % 3 == 0 || count == availableRooms.size()) { %>
    </div>
    <% } %>
    <% } %>
</div>
<% } else { %>
<p>Извините, в данный момент нет доступных номеров.</p>
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

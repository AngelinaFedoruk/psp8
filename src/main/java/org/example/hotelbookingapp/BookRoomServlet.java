package org.example.hotelbookingapp;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.models.Room;
import org.example.models.User;
import org.example.util.DataBaseUtil;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class BookRoomServlet extends HttpServlet {
    public List<Room> rooms = new ArrayList<>();

    @Override
    public void init() throws ServletException {
        System.out.println("BookRoomServlet init");
        reloadRooms();
        System.out.println("BookRoomServlet init " + rooms.size() + " rooms");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        reloadRooms();
        request.setAttribute("availableRooms", rooms);
        request.getRequestDispatcher("/WEB-INF/bookRoom.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int roomId = Integer.parseInt(request.getParameter("roomId"));
        Room room = findRoomById(roomId);

        if (room != null && room.isAvailable()) {
            bookRoomInDatabase(roomId, request);
            reloadRooms();

            response.getWriter().println("<h1>Номер забронирован!</h1>");
            request.setAttribute("availableRooms", rooms);
            response.sendRedirect("bookRoom");
        } else {
            response.getWriter().println("<h1>Номер не доступен!</h1>");
        }
    }

    private Room findRoomById(int id) {
        for (Room room : rooms) {
            if (room.getId() == id) {
                return room;
            }
        }
        return null;
    }

    private void reloadRooms() {
        rooms.clear();
        String query = "SELECT * FROM rooms where available = true";

        try (Connection connection = DataBaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int roomId = resultSet.getInt("id");
                int roomNumber = resultSet.getInt("room_number");
                boolean available = resultSet.getBoolean("available");
                int maxCapacity = resultSet.getInt("max_capacity");
                String description = resultSet.getString("description");
                String imagePath = resultSet.getString("image_path");
                System.out.println("Room fetched: " + roomId + ", " + roomNumber + ", " + available);
                rooms.add(new Room(roomId, roomNumber, available, maxCapacity, description, imagePath));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void bookRoomInDatabase(int roomId, HttpServletRequest request) {
        String updateRoomQuery = "UPDATE rooms SET available = false WHERE id = ?";
        String insertResidentQuery = "INSERT INTO residents (user_id, room_id, booking_date) VALUES (?, ?, NOW())";

        try (Connection connection = DataBaseUtil.getConnection();
             PreparedStatement updateRoomStatement = connection.prepareStatement(updateRoomQuery);
             PreparedStatement insertResidentStatement = connection.prepareStatement(insertResidentQuery)) {

            // Обновляем статус номера
            updateRoomStatement.setInt(1, roomId);
            int rowsAffected = updateRoomStatement.executeUpdate();

            if (rowsAffected > 0) {
                // Получаем ID текущего пользователя из сессии
                User user = (User) request.getSession().getAttribute("user");
                if (user == null) {
                    throw new IllegalStateException("Пользователь не найден в сессии.");
                }

                // Добавляем запись в таблицу residents
                insertResidentStatement.setInt(1, user.getId());
                insertResidentStatement.setInt(2, roomId);
                insertResidentStatement.executeUpdate();

                // Обновляем локальный список комнат
                for (Room room : rooms) {
                    if (room.getId() == roomId) {
                        room.setAvailable(false);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

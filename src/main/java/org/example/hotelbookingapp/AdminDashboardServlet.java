package org.example.hotelbookingapp;



import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.models.Booking;
import org.example.util.DataBaseUtil;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/adminDashboard")
public class AdminDashboardServlet extends HttpServlet {
    private List<Booking> reservations = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Booking> reservations = fetchReservations();
        request.setAttribute("reservations", reservations);
        request.getRequestDispatcher("/WEB-INF/adminDashboard.jsp").forward(request, response);
    }

    private List<Booking> fetchReservations() {
        String query = """
                SELECT r.resident_id AS reservation_id, u.fullName AS user_name, rm.room_number, r.booking_date
                FROM residents r
                JOIN users u ON r.user_id = u.id
                JOIN rooms rm ON r.room_id = rm.id
                ORDER BY r.booking_date DESC
                """;


        try (Connection connection = DataBaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int reservationId = resultSet.getInt("reservation_id");
                String userName = resultSet.getString("user_name");
                int roomNumber = resultSet.getInt("room_number");
                String bookingDate = resultSet.getString("booking_date");

                reservations.add(new Booking(reservationId, userName, roomNumber, bookingDate));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reservations;
    }
}

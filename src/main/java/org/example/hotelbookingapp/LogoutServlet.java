package org.example.hotelbookingapp;



import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(true); // false означает, что сессия не будет создана, если её нет
        if (session != null) {
            session.removeAttribute("user");
            session.removeAttribute("reservations");
            session.removeAttribute("availableRooms");
            session.invalidate();
        }

        response.sendRedirect("index");
    }
}

package org.example.models;

public class Booking {

    private int id;
    private String userName;
    private int roomNumber;
    private String bookingDate;

    public Booking(int id, String userName, int roomNumber, String bookingDate) {
        this.id = id;
        this.userName = userName;
        this.roomNumber = roomNumber;
        this.bookingDate = bookingDate;
    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getBookingDate() {
        return bookingDate;
    }

}
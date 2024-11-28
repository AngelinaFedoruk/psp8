package org.example.models;

public class Room {
    private int id;
    private int  room_number;
    private boolean available;
    private int max_capacity;
    private String description;
    private String image_path;

    public Room(int id, int room_number, boolean isAvailable, int max_capacity, String description, String image_path) {
        this.id = id;
        this.room_number = room_number;
        this.available = isAvailable;
        this.max_capacity = max_capacity;
        this.description = description;
        this.image_path = image_path;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getRoom_number() {
        return room_number;
    }
    public void setRoom_number(int room_number) {
        this.room_number = room_number;
    }
    public boolean isAvailable() {
        return available;
    }
    public void setAvailable(boolean available) {
        this.available = available;
    }
    public int getMax_capacity() {
        return max_capacity;
    }
    public void setMax_capacity(int max_capacity) {
        this.max_capacity = max_capacity;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getImage_path() {
        return image_path;
    }
    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }


}

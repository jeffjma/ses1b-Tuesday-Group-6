package com.example.erest;

public class Staff {
    String Name;
    String Email;
    String Position;

    public Staff() {

    }

    public Staff(String name, String email, String position) {
        Name = name;
        Email = email;
        Position = position;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setPrice(String email) {
        this.Email = email;
    }

    public String getPosition() {
        return Position;
    }

    public void setPosition(String position) {
        this.Position = position;
    }
}

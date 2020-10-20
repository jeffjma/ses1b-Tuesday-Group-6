package com.example.erest;

public class AdminViewReservationItem {
    private String name;
    private String pax;
    private String time;

    public AdminViewReservationItem() {
    }

    public AdminViewReservationItem(String name, String pax, String time, String date) {
        this.name = name;
        this.pax = pax;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPax() {
        return pax;
    }

    public void setPax(String pax) {
        this.pax = "Pax: " + pax;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
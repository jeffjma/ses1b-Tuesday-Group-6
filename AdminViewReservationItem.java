package com.example.erest;

public class AdminViewReservationItem {
    private String date;
    private String pax;
    private String time;

    public AdminViewReservationItem() {
    }

    public AdminViewReservationItem(String date, String pax, String time) {
        this.date = date;
        this.pax = pax;
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPax() {
        return pax;
    }

    public void setPax(String pax) {
        this.pax = pax;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
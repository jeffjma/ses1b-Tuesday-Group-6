package com.example.erest;

public class Reservation {
    String Pax;
    String Date;
    String Time;

    public Reservation(){

    }

    public Reservation(String pax, String date, String time) {
        Pax = pax;
        Date = date;
        Time = time;
    }

    public String getPax() {
        return Pax;
    }

    public void setPax(String pax) {
        Pax = pax;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }
}

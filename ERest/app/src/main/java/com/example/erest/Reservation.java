package com.example.erest;

public class Reservation {
    int Pax;

    public Reservation(){

    }

    public Reservation(String pax) {
        Pax = Integer.parseInt(pax);
    }

    public int getPax() {
        return Pax;
    }

    public void setPax(String pax) {
        Integer.parseInt(pax);
    }

}

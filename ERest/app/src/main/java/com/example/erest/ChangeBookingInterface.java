package com.example.erest;

public interface ChangeBookingInterface {
    void onBookingButtonClick(int position, String date, String time, int pax);
    void onOrderButtonClick(int position, String date, String time);
}

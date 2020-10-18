package com.example.erest;

import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;

import androidx.constraintlayout.widget.Placeholder;

import com.bumptech.glide.provider.ResourceEncoderRegistry;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Order implements Serializable {

    String User, Discount, Reservation;
    Double Price, discountAmount;
    ArrayList<MenuItem> Food = new ArrayList<>();

    public Order() {
        Price = 0.00;
        User = "PlaceHolder";
        Discount = "";
        discountAmount = 0.00;
        Reservation = "";
    }

    public void addToCart(MenuItem itemName){
        Food.add(itemName);
        String tmpStr = itemName.getPrice().substring(1);
        double price = Double.parseDouble(tmpStr);
        Price += price;
    }

    public void clearCart(){
        Food.clear();
        Price = 0.00;
    }

    public ArrayList<MenuItem> getFood() {
        return Food;
    }

    public void setFood(ArrayList<MenuItem> food) { Food = food; }

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }

    public Double getPrice() {
        return Price;
    }

    public void setPrice(Double price) {
        Price = price;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }

    public Double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getReservation() {
        return Reservation;
    }

    public void setReservation(String reservation) {
        Reservation = reservation;
    }
}

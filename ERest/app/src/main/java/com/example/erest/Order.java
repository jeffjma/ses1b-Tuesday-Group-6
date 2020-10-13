package com.example.erest;

import androidx.constraintlayout.widget.Placeholder;

import com.bumptech.glide.provider.ResourceEncoderRegistry;

import java.util.ArrayList;

public class Order {

    String Food, User, Discount, Reservation;
    Double Price;

    public Order() {
        Food = "";
        Price = 0.00;
        User = "PlaceHolder";
        Discount = "";
        Reservation = "";
    }

    public void addToCart(String name, String priceString){
        if(Food.isEmpty()){
            Food = name;
        }
        else {
            Food += " " + name;
        }
        String tmpStr = priceString.substring(1);
        double price = Double.parseDouble(tmpStr);
        Price += price;
    }

    public void clearCart(){
        Food = "";
        Price = 0.00;
    }

    public String getFood() {
        return Food;
    }

    public void setFood(String food) {
        Food = food;
    }

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
}

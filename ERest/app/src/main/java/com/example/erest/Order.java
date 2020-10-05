package com.example.erest;

import androidx.constraintlayout.widget.Placeholder;

import java.util.ArrayList;

public class Order {

    String Food, User;
    Double Price;

    public Order() {
        Food = "";
        Price = 0.00;
        User = "PlaceHolder";
    }

    public void addToCart(String name, String priceString){
        Food += ", " + name;
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
}

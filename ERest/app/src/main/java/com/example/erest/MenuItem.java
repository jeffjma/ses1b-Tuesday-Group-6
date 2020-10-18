package com.example.erest;

import java.io.Serializable;

public class MenuItem implements Serializable {
    String Name;
    String Price;

    public MenuItem() {

    }

    public MenuItem(String name, String price) {
        Name = name;
        Price = price;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        this.Price = price;
    }
}

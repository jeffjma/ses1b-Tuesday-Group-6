package com.example.erest;

public class Discount {
    String Name;
    String Amount;

    public Discount() {

    }

    public Discount(String name, String amount, String position) {
        Name = name;
        Amount = amount;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        this.Amount = amount;
    }
}

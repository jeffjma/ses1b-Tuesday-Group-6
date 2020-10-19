package com.example.erest;

import android.app.Application;

public class eRestaurantApplication extends Application {
    private String userEmail;

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail.replace(".", "_dot_");
    }
}

package com.example.erest;

public class User {
    String firstName, lastName, usertype;

    public User() {

    }

    public User(String fName, String lName, String usertype) {
        this.firstName = fName;
        this.lastName = lName;
        this.usertype=usertype;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }
}

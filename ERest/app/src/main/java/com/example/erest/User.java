package com.example.erest;

public class User {
    String firstName, lastName, name, usertype;

    public User() {

    }

    public User(String fName, String lName, String name, String usertype) {
        this.firstName = fName;
        this.lastName = lName;
        this.name=name;
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

    public String getName() {
        return name;
    }

    public void setName(String newemail) {
        this.name = newemail;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }
}

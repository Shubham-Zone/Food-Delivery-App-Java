package com.example.cafeteria;

public class User {
    String phoneNo,userstatuss;

    public User(String phoneNo, String userstatuss) {
        this.phoneNo = phoneNo;
        this.userstatuss = userstatuss;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getUserstatuss() {
        return userstatuss;
    }

    public void setUserstatuss(String userstatuss) {
        this.userstatuss = userstatuss;
    }
}

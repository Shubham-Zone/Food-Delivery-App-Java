package com.example.cafeteria;

public class orderholder {
    String Foodname,Location,Phone_No,Quantity,Token,Roll;


    public orderholder(){}

    public orderholder(String foodname, String location, String phone_No, String quantity,String token,String roll) {
        Foodname = foodname;
        Location = location;
        Phone_No = phone_No;
        Quantity = quantity;
        Token=token;
        Roll=roll;
    }

    public String getRoll() {
        return Roll;
    }

    public void setRoll(String roll) {
        Roll = roll;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public String getFoodname() {
        return Foodname;
    }

    public void setFoodname(String foodname) {
        Foodname = foodname;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getPhone_No() {
        return Phone_No;
    }

    public void setPhone_No(String phone_No) {
        Phone_No = phone_No;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }
}

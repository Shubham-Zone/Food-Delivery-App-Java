package com.example.cafeteria;

public class PlaceOrderModel {
    String Foodlist,Location,phoneNo,token,roll,userstatus,subtotal;



    public
    PlaceOrderModel(String foodlist, String location, String phoneNo, String token, String roll,String userstatus,String subtotal) {
        Foodlist = foodlist;
        Location = location;
        this.phoneNo = phoneNo;
        this.token = token;
        this.roll = roll;
        this.userstatus=userstatus;
        this.subtotal=subtotal;
    }

    public String getUserstatus() {
        return userstatus;
    }

    public String getsubtotal() {
        return subtotal;
    }

    public void setsubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public void setUserstatus(String userstatus) {
        this.userstatus = userstatus;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    public String getFoodlist() {
        return Foodlist;
    }

    public void setFoodlist(String foodlist) {
        Foodlist = foodlist;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
}

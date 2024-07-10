package com.example.cafeteria.Models;

public class Usercart {
    String orderImage;
    String price,quantity,soldItemName,Roll,token;

    public Usercart(){

    }

    public Usercart(String orderImage, String price, String quantity, String soldItemName,String Roll,String token) {
        this.orderImage = orderImage;
        this.soldItemName = soldItemName;
        this.price = price;
        this.quantity = quantity;
        this.Roll=Roll;
        this.token=token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRoll() {
        return Roll;
    }

    public void setRoll(String roll) {
        Roll = roll;
    }

    public String getOrderImage() {
        return orderImage;
    }

    public void setOrderImage(String orderImage) {
        this.orderImage = orderImage;
    }

    public String getSoldItemName() {
        return soldItemName;
    }

    public void setSoldItemName(String soldItemName) {
        this.soldItemName = soldItemName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}

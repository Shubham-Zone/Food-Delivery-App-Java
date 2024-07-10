package com.example.cafeteria;

public class Cartmodel {

    int orderImage;
    String soldItemName,Price,quantity,orderNumber;

    public Cartmodel(int orderImage, String soldItemName, String price, String quantity,String orderNumber) {
        this.orderImage = orderImage;
        this.soldItemName = soldItemName;
        this.Price = price;
        this.quantity = quantity;
        this.orderNumber = orderNumber;
    }

    public Cartmodel(){

    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getOrderImage() {
        return orderImage;
    }

    public void setOrderImage(int orderImage) {
        this.orderImage = orderImage;
    }

    public String getSoldItemName() {
        return soldItemName;
    }

    public void setSoldItemName(String soldItemName) {
        this.soldItemName = soldItemName;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}

package com.example.cafeteria.Models;

public class OrdersModel {

    int orderImage;
    String soldItemName,Price,orderNumber;

    public OrdersModel(){}

    public OrdersModel(int orderImage, String soldItemName, String price, String orderNumber) {
        this.orderImage = orderImage;
        this.soldItemName = soldItemName;
        this.Price = price;
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

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }
}

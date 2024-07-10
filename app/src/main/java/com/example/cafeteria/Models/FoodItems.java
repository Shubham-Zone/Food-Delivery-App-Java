package com.example.cafeteria.Models;

public class FoodItems {
    String desc,imageurl,price,foodname;

    public  FoodItems(){

    }

    public FoodItems(String desc, String imageurl, String price,String foodname) {
        this.desc = desc;
        this.imageurl = imageurl;
        this.price = price;
        this.foodname=foodname;
    }

    public String getFoodname() {
        return foodname;
    }

    public String getDesc() {
        return desc;
    }

    public String getImageurl() {
        return imageurl;
    }

    public String getPrice() {
        return price;
    }
}

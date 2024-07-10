package com.example.cafeteria.Models;

import android.media.Image;

public class CategoryDomain
{
    private String title;
    private Image pic;

    public CategoryDomain(String title, Image pic) {
        this.title = title;
        this.pic = pic;
    }

    public String getTitle() {
        return title;
    }

    public Image getPic() {
        return pic;
    }
}

package com.utkarshlamba.edibit;

import java.util.ArrayList;

/**
 * Created by utk on 16-03-12.
 */
public class FoodItem {
    String foodName;
    String userName;
    String description;
    String location;
    String contactInfo;
    String price;
    String timeCooked;
    String imagePath;
    String tags;

    public FoodItem(String foodName, String userName, String description, String location, String contactInfo,
                    String price, String timeCooked, String imagePath, String tags){
        this.foodName = foodName;
        this.userName = userName;
        this.description = description;
        this.location = location;
        this.contactInfo = contactInfo;
        this.price = price;
        this.timeCooked = timeCooked;
        this.imagePath = imagePath;
        this.tags = tags;
    }

public String getFoodName() {
        return foodName;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getTimeCooked() {
        return timeCooked;
    }

    public void setTimeCooked(String timeCooked) {
        this.timeCooked = timeCooked;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


}

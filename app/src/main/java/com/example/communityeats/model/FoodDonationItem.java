package com.example.communityeats.model;

public class FoodDonationItem {
    public String date, foodName,foodQuantity,foodDescription,foodImageUrl;

    public FoodDonationItem() {

    }

    public FoodDonationItem(String date, String foodName, String foodQuantity,
                            String foodDescription, String foodImageUrl)
    {

        this.date = date;
        this.foodName = foodName;
        this.foodQuantity = foodQuantity;
        this.foodDescription = foodDescription;
        this.foodImageUrl = foodImageUrl;
    }

}

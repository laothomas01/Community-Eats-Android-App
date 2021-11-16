package com.example.communityeats;

public class FoodDonationItem {
    public String date, foodName,foodQuantity,foodDescription,foodImageUrl, donorID, recipientID;

    public FoodDonationItem() {

    }

    public FoodDonationItem(String date, String foodName, String foodQuantity,
                            String foodDescription, String foodImageUrl, String donorID, String recipientID)
    {

        this.date = date;
        this.foodName = foodName;
        this.foodQuantity = foodQuantity;
        this.foodDescription = foodDescription;
        this.foodImageUrl = foodImageUrl;
        this.donorID = donorID;
        this.recipientID = recipientID;


    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodQuantity() {
        return foodQuantity;
    }

    public void setFoodQuantity(String foodQuantity) {
        this.foodQuantity = foodQuantity;
    }

    public String getFoodDescription() {
        return foodDescription;
    }

    public void setFoodDescription(String foodDescription) {
        this.foodDescription = foodDescription;
    }
}

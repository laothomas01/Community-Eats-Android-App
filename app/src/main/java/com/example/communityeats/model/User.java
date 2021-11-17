package com.example.communityeats.model;




/*
User -> user clicks on profile image
    ->profile image -> activity that looks like the donation activity but you can upload image and click update.
        ->image updates


 */

public class User {
    public String email, password, username, address, imageURL;

    public User() {

    }

    public User(String email, String password, String username, String address, String imageURL) {
        this.email = email;
        this.password = password;
        this.address = address;
        this.username = username;
        this.imageURL = imageURL;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public String getAddress() {
        return this.address;
    }

    public String getUsername() {
        return this.username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

}

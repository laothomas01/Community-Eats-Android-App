package com.example.communityeats;

public class User {
    public String email, password, username, address;

    public User() {

    }

    public User(String email, String password, String username, String address) {
        this.email = email;
        this.password = password;
        this.address = address;
        this.username = username;

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


}

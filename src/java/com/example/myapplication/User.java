package com.example.myapplication;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {
    private String name;
    private String status;
    private String items;
    private String userId;

    public User() {
    }

    public User(String name, String status, String items) {
        this.name = name;
        this.status = status;
        this.items = items;



    }




    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public String getItems() {
        return items;
    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}

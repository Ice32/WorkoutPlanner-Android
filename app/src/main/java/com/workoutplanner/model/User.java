package com.workoutplanner.model;

public class User {
    public Integer id;
    public String fullName;
    public String email;
    public String password;

    public User(String email, String fullName, String password) {
        this.email = email;
        this.fullName = fullName;
        this.password = password;
    }
}

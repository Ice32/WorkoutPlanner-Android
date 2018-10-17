package com.workoutplanner.model;

import java.io.Serializable;

public class User implements Serializable {
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

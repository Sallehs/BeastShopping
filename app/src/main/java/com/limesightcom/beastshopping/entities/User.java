package com.limesightcom.beastshopping.entities;

import java.util.HashMap;
import java.util.Objects;

/**
 * Created by Syahrizal1 on 16/11/2016.
 */

public class User {
    private String email;
    private String name;
    private HashMap<String, Objects> dateJoined;
    private boolean hasLoggedInWithPassword;

    public User() {
    }

    public User(String email, String name, HashMap<String, Objects> dateJoined, boolean hasLoggedInWithPassword) {
        this.email = email;
        this.name = name;
        this.dateJoined = dateJoined;
        this.hasLoggedInWithPassword = hasLoggedInWithPassword;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public HashMap<String, Objects> getDateJoined() {
        return dateJoined;
    }

    public boolean isHasLoggedInWithPassword() {
        return hasLoggedInWithPassword;
    }
}

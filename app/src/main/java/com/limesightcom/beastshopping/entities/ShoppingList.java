package com.limesightcom.beastshopping.entities;

import com.firebase.client.ServerValue;

import java.util.HashMap;

/**
 * Created by Syahrizal1 on 17/11/2016.
 */

// Model

public class ShoppingList {
    private String id;
    private String listName;
    private String ownerEmail;
    private String ownerName;
    private HashMap<String, Object> dateCreated;
    private HashMap<String, Object> dateLastChanged;

    public ShoppingList() {
    }

    public ShoppingList(String id, String listName, String ownerEmail, String ownerName, HashMap<String, Object> dateCreated) {
        this.id = id;
        this.listName = listName;
        this.ownerEmail = ownerEmail;
        this.ownerName = ownerName;
        this.dateCreated = dateCreated;

        // date is auto created using ServerValue
        HashMap<String , Object> dateLastChangedObject = new HashMap<>();
        dateLastChangedObject.put("date", ServerValue.TIMESTAMP);

        this.dateLastChanged = dateLastChangedObject;
    }

    public String getId() {
        return id;
    }

    public String getListName() {
        return listName;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public HashMap<String, Object> getDateCreated() {
        if (dateLastChanged != null) {
            return dateCreated;
        }

        HashMap<String, Object> dateCreatedObject = new HashMap<>();
        dateCreatedObject.put("date", ServerValue.TIMESTAMP);
        return dateCreatedObject;
    }

    public HashMap<String, Object> getDateLastChanged() {
        return dateLastChanged;
    }
}

package com.limesightcom.beastshopping;

import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.ServerValue;
import com.google.firebase.auth.FirebaseAuth;
import com.limesightcom.beastshopping.entities.ShoppingList;
import com.limesightcom.beastshopping.infrastructure.BeastShoppingApplication;
import com.limesightcom.beastshopping.infrastructure.Utils;
import com.limesightcom.beastshopping.services.ShoppingListService;
import com.squareup.otto.Subscribe;

import java.util.HashMap;

/**
 * Created by Syahrizal1 on 17/11/2016.
 */

// Handles Shopping list creation and lifecycle

public class LiveShoppingListService extends BaseLiveService {
    public LiveShoppingListService(BeastShoppingApplication application) {
        super(application);
    }

    @Subscribe
    public void AddShoppingList(ShoppingListService.AddShoppingListRequest request) {
        ShoppingListService.AddShoppingListResponse response = new ShoppingListService.AddShoppingListResponse();

        if (request.shoppingListName.isEmpty()) {
            response.setPropertyError("listName", "Shopping List must have a name");
        }

        if (response.didSucceed()) {
            Firebase reference = new Firebase(Utils.FIREBASE_SHOPPING_LIST_REFERENCE + request.ownerEmail).push();
            HashMap<String, Object> timestampedCreated = new HashMap<>();
            timestampedCreated.put("timestamp", ServerValue.TIMESTAMP);
            ShoppingList shoppingList = new ShoppingList(reference.getKey(), request.shoppingListName,
                   Utils.decodeEmail(request.ownerEmail), request.ownerName, timestampedCreated);
            reference.child("id").setValue(shoppingList.getId());
            reference.child("listName").setValue(shoppingList.getListName());
            reference.child("ownerEmail").setValue(shoppingList.getOwnerEmail());
            reference.child("ownerName").setValue(shoppingList.getOwnerName());
            reference.child("dateCreated").setValue(shoppingList.getDateCreated());
            reference.child("dateLastChanged").setValue(shoppingList.getDateLastChanged());

            Toast.makeText(application.getApplicationContext(), "List created", Toast.LENGTH_LONG).show();
        }

        bus.post(response);
    }
}

package com.limesightcom.beastshopping.services;

import com.limesightcom.beastshopping.infrastructure.ServiceResponse;

/**
 * Created by Syahrizal1 on 17/11/2016.
 */

// Model

public class ShoppingListService {

    private ShoppingListService() {
    }

    public static class AddShoppingListRequest {
        public String shoppingListName;
        public String ownerName;
        public String ownerEmail;

        public AddShoppingListRequest(String shoppingListName, String ownerName, String ownerEmail) {
            this.shoppingListName = shoppingListName;
            this.ownerName = ownerName;
            this.ownerEmail = ownerEmail;
        }
    }

    public static class AddShoppingListResponse extends ServiceResponse {

    }
}

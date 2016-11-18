package com.limesightcom.beastshopping.infrastructure;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.google.firebase.auth.FirebaseAuth;
import com.limesightcom.beastshopping.activities.LoginActivity;

import static com.google.android.gms.common.api.Status.st;

/**
 * Created by Syahrizal1 on 16/11/2016.
 */

public class Utils {
    public static final String FIREBASE_BASE_URL = "https://beastshopping-1a03c.firebaseio.com/";
    public static final String FIREBASE_USER_REFERENCE = FIREBASE_BASE_URL + "users/";
    public static final String FIREBASE_SHOPPING_LIST_REFERENCE = FIREBASE_BASE_URL + "userShoppingList/";

    public static final String MY_PREFERENCE = "MY_PREFERENCE";
    public static final String EMAIL = "EMAIL";
    public static final String USERNAME = "USERNAME";

    public static String encodeEmail(String userEmail) {
        return userEmail.replace(".",",");
    }

    public static String decodeEmail(String userEmail) {
        return userEmail.replace(",",".");
    }

//    public static void logUserOut(Context context, SharedPreferences sharedPreferences, FirebaseAuth auth) {
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString(Utils.EMAIL, null).apply();
//        editor.putString(Utils.USERNAME, null).apply();
//        auth.signOut();
//        Intent intent = new Intent(context, LoginActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(intent);
//    }
}

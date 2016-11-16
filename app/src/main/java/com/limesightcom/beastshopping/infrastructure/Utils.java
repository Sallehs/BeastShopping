package com.limesightcom.beastshopping.infrastructure;

import static com.google.android.gms.common.api.Status.st;

/**
 * Created by Syahrizal1 on 16/11/2016.
 */

public class Utils {
    public static final String FIREBASE_BASE_URL = "https://beastshopping-1a03c.firebaseio.com/";
    public static final String FIREBASE_USER_REFERENCE = FIREBASE_BASE_URL + "users/";

    public static String encodeEmail(String userEmail) {
        return userEmail.replace(".",",");
    }
}

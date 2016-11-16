package com.limesightcom.beastshopping.infrastructure;

import java.util.HashMap;

/**
 * Created by Syahrizal1 on 16/11/2016.
 */

// Catch user errors in filling up EditText
public class ServiceResponse {
    // HashMap is an array or <key, value>
    private HashMap<String, String> propertyErrors;

    public ServiceResponse() {
        propertyErrors = new HashMap<>();
    }

    public void setPropertyError(String property, String error) {
        propertyErrors.put(property, error);
    }

    public String getPropertyError(String property) {
        return propertyErrors.get(property);
    }

    public boolean didSucceed() {
        return (propertyErrors.size() == 0);      // return true if propertyErrors.size is zero
    }
}

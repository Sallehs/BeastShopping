package com.limesightcom.beastshopping;

import com.limesightcom.beastshopping.infrastructure.BeastShoppingApplication;

/**
 * Created by Syahrizal1 on 16/11/2016.
 */

public class Module {

    public static void Register(BeastShoppingApplication application) {
        new LiveAccountServices(application);
    }
}

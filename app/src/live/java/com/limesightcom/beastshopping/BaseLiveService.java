package com.limesightcom.beastshopping;

import com.google.firebase.auth.FirebaseAuth;
import com.limesightcom.beastshopping.infrastructure.BeastShoppingApplication;
import com.squareup.otto.Bus;

/**
 * Created by Syahrizal1 on 16/11/2016.
 */

public class BaseLiveService {
    protected Bus bus;
    protected BeastShoppingApplication application;
    protected FirebaseAuth auth;

    public BaseLiveService(BeastShoppingApplication application) {
        this.application = application;
        bus = application.getBus();
        bus.register(this);
        auth = FirebaseAuth.getInstance();
    }
}

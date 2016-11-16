package com.limesightcom.beastshopping.infrastructure;

import android.app.Application;

import com.firebase.client.Firebase;
import com.limesightcom.beastshopping.Module;
import com.squareup.otto.Bus;

/**
 * Created by Syahrizal1 on 16/11/2016.
 */

public class BeastShoppingApplication extends Application {
    private Bus bus;                        // all our activities will have access to this bus

    public BeastShoppingApplication() {
        bus = new Bus();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //The Firebase library must be initialized once with an Android context. This must happen
        // before any Firebase app reference is created or used. You can add the setup code to your
        // Android Application's or Activity's onCreate method.
        Firebase.setAndroidContext(this);
        Module.Register(this);
    }

    public Bus getBus() {
        return bus;
    }
}

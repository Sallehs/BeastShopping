package com.limesightcom.beastshopping.dialog;

import android.app.DialogFragment;
import android.os.Bundle;

import com.limesightcom.beastshopping.infrastructure.BeastShoppingApplication;
import com.squareup.otto.Bus;

/**
 * Created by Syahrizal1 on 16/11/2016.
 */

public class BaseDialog extends DialogFragment {
    protected BeastShoppingApplication application;
    protected Bus bus;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application = (BeastShoppingApplication) getActivity().getApplication();
        bus = application.getBus();
        bus.register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bus.unregister(this);
    }
}

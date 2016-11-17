package com.limesightcom.beastshopping.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.limesightcom.beastshopping.infrastructure.BeastShoppingApplication;
import com.limesightcom.beastshopping.infrastructure.Utils;
import com.squareup.otto.Bus;

/**
 * Created by Syahrizal1 on 16/11/2016.
 */

public class BaseActivity extends AppCompatActivity {
    protected BeastShoppingApplication application;
    protected Bus bus;
    protected FirebaseAuth mAuth;
    protected FirebaseAuth.AuthStateListener mAuthStateListener;
    protected String userEmail, userName;
    protected SharedPreferences sharedPreferences;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        application = (BeastShoppingApplication) getApplication();
        bus = application.getBus();
        bus.register(this);

        sharedPreferences = getSharedPreferences(Utils.MY_PREFERENCE, Context.MODE_PRIVATE);
        userName = sharedPreferences.getString(Utils.USERNAME, "");
        userEmail = sharedPreferences.getString(Utils.EMAIL, "");

        mAuth = FirebaseAuth.getInstance();

        if (!((this instanceof LoginActivity) ||
                (this instanceof RegisterActivity) ||
                (this instanceof SplashScreenActivity))) {
            mAuthStateListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    if (user == null) {
                        // sends user to LoginActivity if user has not signed in
//                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
//                        finish();
//                        Utils.logUserOut(getApplicationContext(), sharedPreferences, mAuth);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(Utils.EMAIL, null).apply();
                        editor.putString(Utils.USERNAME, null).apply();
                        mAuth.signOut();
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        finish();
                    } else {
                        if (userEmail.equals("")) {
//                            Utils.logUserOut(getApplicationContext(), sharedPreferences, mAuth);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(Utils.EMAIL, null).apply();
                            editor.putString(Utils.USERNAME, null).apply();
                            mAuth.signOut();
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            finish();
                        }
                    }
                }
            };
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!((this instanceof LoginActivity) ||
                (this instanceof RegisterActivity) ||
                (this instanceof SplashScreenActivity))) {
            mAuth.addAuthStateListener(mAuthStateListener);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bus.unregister(this);
        if (!((this instanceof LoginActivity) ||
                (this instanceof RegisterActivity) ||
                (this instanceof SplashScreenActivity))) {
            mAuth.removeAuthStateListener(mAuthStateListener);
        }
    }
}

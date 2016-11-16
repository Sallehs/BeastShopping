package com.limesightcom.beastshopping;

import android.support.annotation.NonNull;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.ServerValue;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.limesightcom.beastshopping.infrastructure.BeastShoppingApplication;
import com.limesightcom.beastshopping.infrastructure.Utils;
import com.limesightcom.beastshopping.services.AccountServices;
import com.squareup.otto.Subscribe;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Objects;

/**
 * Created by Syahrizal1 on 16/11/2016.
 */

public class LiveAccountServices extends BaseLiveService {
    public LiveAccountServices(BeastShoppingApplication application) {
        super(application);
    }

    @Subscribe
    public void RegisterUser(final AccountServices.RegisterUserRequest request) {
        AccountServices.RegisterUserResponse response = new AccountServices.RegisterUserResponse();

        if (request.userEmail.isEmpty()) {
            response.setPropertyError("email", "Please enter your email address");
        }

        if (request.userName.isEmpty()) {
            response.setPropertyError("userName", "Please enter your user name");
        }

        if (response.didSucceed()) {
            request.mProgressDialog.show();

            SecureRandom random = new SecureRandom();
            final String randomPassword = new BigInteger(36, random).toString();

            auth.createUserWithEmailAndPassword(request.userEmail, randomPassword)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                request.mProgressDialog.dismiss();
                                Toast.makeText(application.getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            } else {
                                auth.sendPasswordResetEmail(request.userEmail)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (!task.isSuccessful()) {
                                                    request.mProgressDialog.dismiss();
                                                    Toast.makeText(application.getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                                } else {
                                                    Firebase reference = new Firebase(Utils.FIREBASE_USER_REFERENCE + Utils.encodeEmail(request.userEmail));

                                                    HashMap<String, Object> timeJoined = new HashMap<>();
                                                    timeJoined.put("dateJoined", ServerValue.TIMESTAMP);

                                                    // Write to Firebase database
                                                    reference.child("email").setValue(request.userEmail);
                                                    reference.child("name").setValue(request.userName);
                                                    reference.child("hasLoggedInWithPassword").setValue(false);
                                                    reference.child("timeJoined").setValue(timeJoined);

                                                    Toast.makeText(application.getApplicationContext(), "Please check your email", Toast.LENGTH_LONG).show();
                                                    request.mProgressDialog.dismiss();
                                                }
                                            }
                                        });
                            }
                        }
                    });
        }

        bus.post(response);
    }
}

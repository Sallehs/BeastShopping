package com.limesightcom.beastshopping;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ServerValue;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.limesightcom.beastshopping.activities.LoginActivity;
import com.limesightcom.beastshopping.activities.MainActivity;
import com.limesightcom.beastshopping.activities.RegisterActivity;
import com.limesightcom.beastshopping.entities.User;
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

                                                    Intent intent = new Intent(application.getApplicationContext(), LoginActivity.class);
                                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);  //almost like calling finish();
                                                    application.startActivity(intent);
                                                }
                                            }
                                        });
                            }
                        }
                    });
        }

        bus.post(response);
    }



    @Subscribe
    public void LogInUser(final AccountServices.LogUserInRequest request) {
        final AccountServices.LogUserInResponse response = new AccountServices.LogUserInResponse();

        if (request.userEmail.isEmpty()) {
            response.setPropertyError("email", "Please enter your email address");
        }

        if (request.userPassword.isEmpty()) {
            response.setPropertyError("password", "Please enter your password");
        }

        if (response.didSucceed()) {
            request.mProgressDialog.show();
            auth.signInWithEmailAndPassword(request.userEmail, request.userPassword)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                request.mProgressDialog.dismiss();
                                Toast.makeText(application.getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            } else {
                                final Firebase userLocation = new Firebase(Utils.FIREBASE_USER_REFERENCE + Utils.encodeEmail(request.userEmail));
                                userLocation.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        User user = dataSnapshot.getValue(User.class);
                                        if (user != null) {
                                            userLocation.child("hasLoggedInWithPassword").setValue(true);
                                            // Shared preferences is to keep track of current user's data (email and username)
                                            SharedPreferences sharedPreferences = request.mSharedPreferences;
                                            sharedPreferences.edit().putString(Utils.EMAIL, Utils.encodeEmail(user.getEmail())).apply();
                                            sharedPreferences.edit().putString(Utils.USERNAME, user.getName()).apply();

                                            request.mProgressDialog.dismiss();

                                            Intent intent = new Intent(application.getApplicationContext(), MainActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);  //almost like calling finish();
                                            application.startActivity(intent);
                                        } else {
                                            request.mProgressDialog.dismiss();
                                            Toast.makeText(application.getApplicationContext(), "Failed to connect to Server", Toast.LENGTH_LONG).show();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(FirebaseError firebaseError) {
                                        request.mProgressDialog.dismiss();
                                        Toast.makeText(application.getApplicationContext(), firebaseError.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                });


                                userLocation.child("hasLoggedInWithPassword").setValue(true);

                                request.mProgressDialog.dismiss();
//                                Toast.makeText(application.getApplicationContext(), "User has logged in", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
            bus.post(response);
        }
    }
}

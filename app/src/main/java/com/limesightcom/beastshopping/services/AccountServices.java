package com.limesightcom.beastshopping.services;

import android.app.ProgressDialog;

import com.limesightcom.beastshopping.infrastructure.ServiceResponse;

import java.security.Policy;

/**
 * Created by Syahrizal1 on 16/11/2016.
 */

public class AccountServices {

    private AccountServices() {
    }

    public static class RegisterUserRequest {
        public String userName;
        public String userEmail;
        public ProgressDialog mProgressDialog;

        public RegisterUserRequest(String userName, String userEmail, ProgressDialog progressDialog) {
            this.userName = userName;
            this.userEmail = userEmail;
            mProgressDialog = progressDialog;
        }
    }

    public static class RegisterUserResponse extends ServiceResponse{

    }
}

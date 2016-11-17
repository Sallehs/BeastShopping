package com.limesightcom.beastshopping.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.limesightcom.beastshopping.R;
import com.limesightcom.beastshopping.infrastructure.Utils;
import com.limesightcom.beastshopping.services.AccountServices;
import com.squareup.otto.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Syahrizal1 on 16/11/2016.
 */

public class LoginActivity extends BaseActivity {

    @BindView(R.id.activity_login_linearlayout)
    LinearLayout mLinearLayout;

    @BindView(R.id.activity_login_registerButton)
    Button registerButton;

    @BindView(R.id.activity_login_loginButton)
    Button loginButton;

    @BindView(R.id.activity_login_userEmail)
    EditText userEmail;

    @BindView(R.id.activity_login_userPassword)
    EditText userPassword;

    private ProgressDialog mProgressDialog;

    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mLinearLayout.setBackgroundResource(R.drawable.background_screen_two);

        FirebaseAuth.getInstance().signOut();

        mLinearLayout.setBackgroundResource(R.drawable.background_screen_two);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Loading...");
        mProgressDialog.setMessage("Registering account");
        mProgressDialog.setCancelable(false);

        mSharedPreferences = getSharedPreferences(Utils.MY_PREFERENCE, Context.MODE_PRIVATE);
    }

    @OnClick(R.id.activity_login_registerButton)
    public void setRegisterButton() {
        startActivity(new Intent(this, RegisterActivity.class));
        finish();
    }

    @OnClick(R.id.activity_login_loginButton)
    public void setLoginButton() {
        bus.post(new AccountServices.LogUserInRequest(
                userEmail.getText().toString(), userPassword.getText().toString(), mProgressDialog, mSharedPreferences));
    }

    @Subscribe
    public void LogUserIn(AccountServices.LogUserInResponse response) {
        if (!response.didSucceed()) {
            userEmail.setError(response.getPropertyError("email"));
            userPassword.setError(response.getPropertyError("password"));
        }
    }
}

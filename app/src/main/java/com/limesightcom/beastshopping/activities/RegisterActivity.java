package com.limesightcom.beastshopping.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.limesightcom.beastshopping.R;
import com.limesightcom.beastshopping.services.AccountServices;
import com.squareup.otto.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Syahrizal1 on 16/11/2016.
 */
public class RegisterActivity extends BaseActivity{

    @BindView(R.id.activity_register_loginButton)
    Button loginButton;

    @BindView(R.id.activity_register_linearlayout)
    LinearLayout mLinearLayout;

    @BindView(R.id.activity_register_userEmail)
    EditText userEmail;

    @BindView(R.id.activity_register_userName)
    EditText userName;

    @BindView(R.id.activity_register_registerButton)
    Button registerButton;

    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        mLinearLayout.setBackgroundResource(R.drawable.background_screen_two);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Loading...");
        mProgressDialog.setMessage("Registering account");
        mProgressDialog.setCancelable(false);
    }

    @OnClick(R.id.activity_register_loginButton)
    public void setLoginButton() {
        startActivity(new Intent(this,LoginActivity.class));
        finish();
    }

    @OnClick(R.id.activity_register_registerButton)
    public void setRegisterButton() {
        bus.post(new AccountServices.RegisterUserRequest(userName.getText().toString(), userEmail.getText().toString(), mProgressDialog));
    }

    @Subscribe
    public void RegisterUser(AccountServices.RegisterUserResponse response) {
        if (!response.didSucceed()) {
            userEmail.setError(response.getPropertyError("email"));
            userName.setError(response.getPropertyError("userName"));
        }
    }
}

package com.limesightcom.beastshopping.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.LinearLayout;

import com.limesightcom.beastshopping.R;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mLinearLayout.setBackgroundResource(R.drawable.background_screen_two);
    }

    @OnClick(R.id.activity_login_registerButton)
    public void setRegisterButton() {
        startActivity(new Intent(this, RegisterActivity.class));
        finish();
    }
}

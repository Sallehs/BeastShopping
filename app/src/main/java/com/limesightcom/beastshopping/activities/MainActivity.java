package com.limesightcom.beastshopping.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.limesightcom.beastshopping.R;
import com.limesightcom.beastshopping.infrastructure.Utils;

public class MainActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String toolBarName;

        if (userName.contains(" ")) {
            toolBarName = userName.substring(0, userName.indexOf(" ")) + "'s Shopping List";
        } else {
            toolBarName = userName + "'s Shopping List";
        }

        getSupportActionBar().setTitle(toolBarName);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_logout:
                SharedPreferences sharedPreferences2 = getSharedPreferences(Utils.MY_PREFERENCE, Context.MODE_PRIVATE);

                ProgressDialog mProgressDialog = new ProgressDialog(this);
                mProgressDialog.setTitle("Loading...");
                mProgressDialog.setMessage("Logging out");
                mProgressDialog.setCancelable(false);

                SharedPreferences.Editor editor = sharedPreferences2.edit();
                editor.putString(Utils.EMAIL, null).apply();
                editor.putString(Utils.USERNAME, null).apply();
                mAuth.signOut();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();

                mProgressDialog.dismiss();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

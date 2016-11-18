package com.limesightcom.beastshopping.activities;

import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseRecyclerAdapter;
import com.limesightcom.beastshopping.R;
import com.limesightcom.beastshopping.dialog.AddListDialogFragment;
import com.limesightcom.beastshopping.entities.ShoppingList;
import com.limesightcom.beastshopping.infrastructure.Utils;
import com.limesightcom.beastshopping.viewss.shoppingListViews.ShoppingListViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.activity_main_FAB)
    FloatingActionButton mFloatingActionButton;

//    RecyclerView mRecyclerView;
//
//    FirebaseRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

//        mRecyclerView = (RecyclerView) findViewById(R.id.activity_main_list_recyclerview);

        String toolBarName;

        if (userName.contains(" ")) {
            toolBarName = userName.substring(0, userName.indexOf(" ")) + "'s Shopping List";
        } else {
            toolBarName = userName + "'s Shopping List";
        }

        getSupportActionBar().setTitle(toolBarName);
    }


//    @Override
//    protected void onResume() {
//        super.onResume();
//        Firebase shoppingListReference = new Firebase(Utils.FIREBASE_SHOPPING_LIST_REFERENCE + userEmail);
//
//        adapter = new FirebaseRecyclerAdapter<ShoppingList, ShoppingListViewHolder>(ShoppingList.class,
//                R.layout.list_shopping_list,
//                ShoppingListViewHolder.class,
//                shoppingListReference) {
//            @Override
//            protected void populateViewHolder(ShoppingListViewHolder shoppingListViewHolder,
//                                              final ShoppingList shoppingList,
//                                              int i) {
//                shoppingListViewHolder.populate(shoppingList);
//                shoppingListViewHolder.layout.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Toast.makeText(getApplicationContext(), shoppingList.getListName() + "was clicked", Toast.LENGTH_LONG).show();
//                    }
//                });
//            }
//        };
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mRecyclerView.setAdapter(adapter);
//    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        adapter.cleanup();
//    }

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


    @OnClick(R.id.activity_main_FAB)
    public void setFloatingActionButton() {
        DialogFragment dialogFragment = AddListDialogFragment.newInstance();
        dialogFragment.show(getFragmentManager(), AddListDialogFragment.class.getSimpleName());
    }

}

package com.limesightcom.beastshopping.viewss.shoppingListViews;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.limesightcom.beastshopping.R;
import com.limesightcom.beastshopping.entities.ShoppingList;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Syahrizal1 on 17/11/2016.
 */

public class ShoppingListViewHolder extends RecyclerView.ViewHolder {

//    @BindView(R.id.list_shopping_list_ownername)
//    TextView ownerName;
//
//    @BindView(R.id.list_shopping_list_listname)
//    TextView listName;
//
//    @BindView(R.id.list_shopping_list_datecreated)
//    TextView dateCreated;
//
//    @BindView(R.id.list_shopping_list_relativelayout)
//    public View layout;

    protected TextView ownerName;
    protected TextView listName;
    protected TextView dateCreated;
    protected View layout;

    public ShoppingListViewHolder(View itemView) {
        super(itemView);
//        ButterKnife.bind(this.itemView);
        this.ownerName = (TextView) itemView.findViewById(R.id.list_shopping_list_ownername);
        this.listName = (TextView) itemView.findViewById(R.id.list_shopping_list_listname);
        this.dateCreated = (TextView) itemView.findViewById(R.id.list_shopping_list_datecreated);
        this.layout = (View) itemView.findViewById(R.id.list_shopping_list_relativelayout);
    }

    public void populate(ShoppingList shoppingList) {
        ownerName.setText(shoppingList.getOwnerName());
        listName.setText(shoppingList.getListName());

        ownerName.setText(shoppingList.getOwnerName());
        listName.setText(shoppingList.getListName());

        if (shoppingList.getDateCreated().get("timestamp") != null) {
            dateCreated.setText(convertTime((long)shoppingList.getDateCreated().get("timestamp")));
        }
    }

    private String convertTime(Long unixTime) {
        Date dateObject = new Date(unixTime);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yy kk:mm");
        return simpleDateFormat.format(dateObject);
    }
}

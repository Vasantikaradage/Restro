package com.restrosmart.restro.User;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.restrosmart.restro.R;
import com.restrosmart.restro.Utils.Sessionmanager;

public class ActivityCartBill extends AppCompatActivity {

    private Toolbar mToolbar;
    private FrameLayout flContainer;
    private Fragment fragment;
    private String title;

    private Sessionmanager sessionmanager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_bill);

        init();
        setupToolbar();

        LoadFragment(1);


        /*ArrayList<FoodCartModel> cartList = sessionmanager.getAddToFoodCartList(this);
        for (int i = 0; i < cartList.size(); i++) {
            *//*Log.v("data", "----------------START----------------");
            Log.v("data", String.valueOf(cartList.get(i).getMenuId()));
            Log.v("data", cartList.get(i).getMenuName());
            Log.v("data", String.valueOf(cartList.get(i).getMenuQty()));
            Log.v("data", "----------------END----------------");*//*

            Toast.makeText(this, String.valueOf(cartList.get(i).getMenuId()) + cartList.get(i).getMenuName()
                    + String.valueOf(cartList.get(i).getMenuQty()), Toast.LENGTH_SHORT).show();
        }*/
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
            if (fragment instanceof FragmentOrderStatus) {
                getSupportActionBar().setTitle("Cart");
            }
        } else {
            this.finish();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        /*if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();

            if (fragment instanceof FragmentOrderStatus) {
                getSupportActionBar().setTitle("Cart");
            }
        } else {
            this.finish();
        }*/

        finish();
        return true;
    }

    public void LoadFragment(int i) {

        switch (i) {
            case 1:
                fragment = new FragmentCart();
                title = "Cart";
                break;
            case 2:
                fragment = new FragmentOrderStatus();
                title = "Order Status";
                break;
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.flContainer, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.addToBackStack(null);
        ft.commit();

        getSupportActionBar().setTitle(title);
    }

    /*public void goToSecondFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment secondFragment = new FragmentOrderStatus();
        ft.add(R.id.flContainer, secondFragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.addToBackStack(null);
        ft.commit();
    }*/

    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
    }

    private void init() {
        sessionmanager = new Sessionmanager(this);
        mToolbar = findViewById(R.id.toolbar);
        flContainer = findViewById(R.id.flContainer);
    }
}

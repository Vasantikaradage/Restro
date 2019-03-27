package com.restrosmart.restro.User;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.restrosmart.restro.Model.FoodCartModel;
import com.restrosmart.restro.Model.UserCategory;
import com.restrosmart.restro.R;
import com.restrosmart.restro.Utils.BadgeCount;
import com.restrosmart.restro.Utils.Sessionmanager;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;

public class ActivityHotelMenu extends AppCompatActivity {

    private Toolbar mToolbar;
    private MenuItem menuItemCart, menuItemVeg, menuItemNonVeg, menuItemLiquors;

    private int categoryPos, categoryId;
    private String categoryName;
    private ArrayList<UserCategory> arrayList;
    private Menu hotelMenu;
    private int menuPosition;

    private Sessionmanager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_menu);

        init();
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            categoryPos = bundle.getInt("categoryPos");
            categoryId = bundle.getInt("categoryId");
            categoryName = bundle.getString("categoryName");
            arrayList = bundle.getParcelableArrayList("arrayList");
        }

        setupToolbar();
       // sessionManager.deleteFoodCartList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setMenuFragment(categoryId);
        registerReceiver(mAnimationReceiver, new IntentFilter("com.restrosmart.restro.addmenu"));
        invalidateOptionsMenu();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mAnimationReceiver);
    }

    public BroadcastReceiver mAnimationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            String menuName = bundle.getString("menuname");
            //Toast.makeText(ActivityHotelMenu.this, menuName, Toast.LENGTH_SHORT).show();

            updateCartMenu();
        }
    };

    public void updateCartMenu() {
        // invalidateOptionsMenu();
        //menuItemCart = hotelMenu.findItem(R.id.menuCart);
        LayerDrawable icon = (LayerDrawable) menuItemCart.getIcon();
        BadgeCount badgeCount = new BadgeCount();

        ArrayList<FoodCartModel> cartRVModelArrayList = sessionManager.getAddToFoodCartList(this);

        if (cartRVModelArrayList != null) {
            if (cartRVModelArrayList.size() == 1) {
                invalidateOptionsMenu();
            }

            badgeCount.setBadgeCount(this, icon, String.valueOf(cartRVModelArrayList.size()));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.hotel_menu, menu);
        menuItemCart = menu.findItem(R.id.menuCart);
        LayerDrawable icon = (LayerDrawable) menuItemCart.getIcon();
        BadgeCount badgeCount = new BadgeCount();

        ArrayList<FoodCartModel> cartRVModelArrayList = sessionManager.getAddToFoodCartList(this);

        if (cartRVModelArrayList != null) {
            badgeCount.setBadgeCount(this, icon, String.valueOf(cartRVModelArrayList.size()));
        }

        /*menuItemVeg = menu.findItem(R.id.menuVeg);
        menuItemNonVeg = menu.findItem(R.id.menuNonVeg);
        menuItemLiquors = menu.findItem(R.id.menuLiquors);*/

        this.hotelMenu = menu;

        //Dynamically added all menus
        /*for (int i = 0; i <= arrayList.size(); i++) {

            if (i == 0) {
                hotelMenu.add(0, i, 0, "Cart").setIcon(R.drawable.ic_menu_cart)
                        .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

                LayerDrawable icon = (LayerDrawable) hotelMenu.getItem(0).getIcon();
                BadgeCount badgeCount = new BadgeCount();
                badgeCount.setBadgeCount(this, icon, String.valueOf(2));
            } else {

                hotelMenu.add(0, i, 0, arrayList.get(i - 1).getCategoryName())
                        .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

                menuPosition = i;
                final Target mTarget = new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
                        //Log.d("DEBUG", "onBitmapLoaded");
                        BitmapDrawable mBitmapDrawable = new BitmapDrawable(getResources(), bitmap);
                        //                                mBitmapDrawable.setBounds(0,0,24,24);
                        // setting icon of Menu Item or Navigation View's Menu Item
                        hotelMenu.getItem(menuPosition).setIcon(mBitmapDrawable);
                    }

                    @Override
                    public void onBitmapFailed(Drawable drawable) {
                        Log.d("DEBUG", "onBitmapFailed");
                    }

                    @Override
                    public void onPrepareLoad(Drawable drawable) {
                        Log.d("DEBUG", "onPrepareLoad");
                    }
                };

                Picasso.with(this).load(arrayList.get(i - 1).getCategoryImage()).into(mTarget);
            }
        }*/

        //Dynamically added all menus except cart
        for (int i = 0; i < arrayList.size(); i++) {
            {
                hotelMenu.add(1, i + 1, i + 1, arrayList.get(i).getCategoryName())
                        .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

                menuPosition = i + 1;
                final Target mTarget = new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
                        //Log.d("DEBUG", "onBitmapLoaded");
                        BitmapDrawable mBitmapDrawable = new BitmapDrawable(getResources(), bitmap);
                        //                                mBitmapDrawable.setBounds(0,0,24,24);
                        // setting icon of Menu Item or Navigation View's Menu Item
                        hotelMenu.getItem(menuPosition).setIcon(mBitmapDrawable);
                    }

                    @Override
                    public void onBitmapFailed(Drawable drawable) {
                        Log.v("Bitmap Failed", "onBitmapFailed");
                    }

                    @Override
                    public void onPrepareLoad(Drawable drawable) {
                        Log.v("Prepare Bitmap", "onPrepareLoad");
                    }
                };

                Picasso.with(this).load(arrayList.get(i).getCategoryImage()).into(mTarget);
            }
        }

        MenuItem menu1 = hotelMenu.findItem(categoryPos + 1);
        menu1.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != R.id.menuCart) {
            for (int j = 1; j <= arrayList.size(); j++) {
                if (item.getItemId() == j) {
                    MenuItem menu2 = hotelMenu.findItem(item.getItemId());
                    menu2.setVisible(false);
                    mToolbar.setTitle(arrayList.get(j - 1).getCategoryName());
                } else {
                    MenuItem menu2 = hotelMenu.findItem(j);
                    menu2.setVisible(true);
                }
            }
        }

        if (item.getItemId() == R.id.menuCart) {
            Intent intent = new Intent(this, ActivityCartBill.class);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == arrayList.get(item.getItemId() - 1).getCategoryId()) {
            setMenuFragment(arrayList.get(item.getItemId() - 1).getCategoryId());
            return true;
        } else {

            setMenuFragment(arrayList.get(item.getItemId() - 1).getCategoryId());
            return true;
        }
    }

    private void setMenuFragment(int fragmentId) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putInt("categoryId", fragmentId);

        switch (fragmentId) {
            /*case 1:
                ft.replace(R.id.flMenu, new FragmentFoodMenu());
                ft.commit();
                break;

            case 2:
                ft.replace(R.id.flMenu, new FragmentFoodMenu());
                ft.commit();
                break;*/

            case 3:
                FragmentLiquorsMenu fragmentLiquorsMenu = new FragmentLiquorsMenu();
                fragmentLiquorsMenu.setArguments(bundle);

                ft.replace(R.id.flMenu, fragmentLiquorsMenu);
                ft.commit();
                break;

            default:
                FragmentFoodMenu fragmentFoodMenu = new FragmentFoodMenu();
                fragmentFoodMenu.setArguments(bundle);

                ft.replace(R.id.flMenu, fragmentFoodMenu);
                ft.commit();
                break;
        }
    }

    private void setupToolbar() {
        mToolbar.setTitle(categoryName);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void init() {
        sessionManager = new Sessionmanager(this);
        mToolbar = findViewById(R.id.toolbar);
    }
}

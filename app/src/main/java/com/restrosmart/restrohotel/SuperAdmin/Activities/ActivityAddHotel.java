package com.restrosmart.restrohotel.SuperAdmin.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;


import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.SuperAdmin.Fragments.HotelBasicDetailsFragment;

public class ActivityAddHotel extends AppCompatActivity {
    private Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hotel);


        init();
        setupToolbar();

        FragmentManager fragmentManager = this.getSupportFragmentManager();

        // Begin Fragment transaction.
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Replace the layout holder with the required Fragment object.
        fragmentTransaction.replace(R.id.flContainer, new HotelBasicDetailsFragment());

        // Commit the Fragment replace action.
        fragmentTransaction.commit();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        popFragment();
        return true;
    }

    private void popFragment() {
    }

    private void setupToolbar() {
        //mToolbar.setTitle("Add Client Form");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_back);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
    }

    private void init() {
        mToolbar = findViewById(R.id.toolbar);
    }


}



package com.restrosmart.restrohotel.SuperAdmin.Activities;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;


import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.SuperAdmin.Fragments.HotelBasicDetailsFragment;
import com.restrosmart.restrohotel.SuperAdmin.Models.CuisineForm;
import com.restrosmart.restrohotel.SuperAdmin.Models.HotelForm;
import com.restrosmart.restrohotel.SuperAdmin.Models.HotelImageForm;
import com.restrosmart.restrohotel.SuperAdmin.Models.TagsForm;

import java.util.ArrayList;

public class ActivityAddHotel extends AppCompatActivity {
    private Toolbar mToolbar;
    // private    FragmentManager fragmentManager;
    private ArrayList<CuisineForm> cuisineFormArrayList;
    private ArrayList<TagsForm> tagsFormArrayList;
    private ArrayList<HotelImageForm> hotelImageFormArrayList;
    private HotelForm hotelForm;
    private  FragmentManager fragmentManager;
    private      Bundle bundle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hotel);


        init();
        setupToolbar();



        HotelBasicDetailsFragment hotelBasicDetailsFragment = new HotelBasicDetailsFragment();
        bundle = getIntent().getExtras();
        if (bundle != null) {

            hotelForm = bundle.getParcelable("hotelInfo");
            hotelImageFormArrayList = bundle.getParcelableArrayList("hotelImags");
            cuisineFormArrayList = bundle.getParcelableArrayList("CuisineList");
            tagsFormArrayList = bundle.getParcelableArrayList("TagsList");

            Bundle bundle1 = new Bundle();
            bundle1.putParcelableArrayList("hotelImags",hotelImageFormArrayList);
            bundle1.putParcelableArrayList("CuisineList",cuisineFormArrayList);
            bundle1.putParcelableArrayList("TagsList",tagsFormArrayList);
            bundle1.putParcelable("hotelInfo", hotelForm);

            hotelBasicDetailsFragment.setArguments(bundle1);
        }


        fragmentManager = this.getSupportFragmentManager();

        // Begin Fragment transaction.
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Replace the layout holder with the required Fragment object.
        fragmentTransaction.replace(R.id.flContainer, hotelBasicDetailsFragment);

        // Commit the Fragment replace action.
        fragmentTransaction.commit();
    }

    @Override
    public boolean onSupportNavigateUp() {
       /* if (getFragmentManager().getBackStackEntryCount() != 0) {
            getFragmentManager().popBackStack();
        }*/
        onBackPressed();
        return true;
    }


    private void setupToolbar() {
        //mToolbar.setTitle("Add Client Form");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fragmentManager.getBackStackEntryCount() != 0) {
                    fragmentManager.popBackStack();

                } else {
                    onBackPressed();
                }
                //   Toast.makeText(ActivityAddHotel.this, "123"+fragmentManager.getBackStackEntryCount(), Toast.LENGTH_SHORT).show();
            }
        });
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
    }

    private void init() {
        mToolbar = findViewById(R.id.toolbar);
    }


}



package com.restrosmart.restrohotel.SuperAdmin.Activities;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;


import com.github.ybq.android.spinkit.SpinKitView;
import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.SuperAdmin.Adapters.RVCuisineDisplay;
import com.restrosmart.restrohotel.SuperAdmin.Adapters.RVHotelImageDetailsAdapter;
import com.restrosmart.restrohotel.SuperAdmin.Adapters.RVTagsDisplay;
import com.restrosmart.restrohotel.SuperAdmin.Interfaces.ImageSelectedListerner;
import com.restrosmart.restrohotel.SuperAdmin.Models.CuisineForm;
import com.restrosmart.restrohotel.SuperAdmin.Models.HotelForm;
import com.restrosmart.restrohotel.SuperAdmin.Models.HotelImageForm;
import com.restrosmart.restrohotel.SuperAdmin.Models.TagsForm;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ActivityViewHotelDetails extends AppCompatActivity {
    private HotelForm hotelForm;
    private RecyclerView rvCuisine, rvTags, rvHotelImage;
    private ArrayList<CuisineForm> cuisineFormArrayList;
    private ArrayList<TagsForm> tagsFormArrayList;
    private ArrayList<HotelImageForm> hotelImageFormArrayList;
    private ImageView ivHotelFullImage;
    private RVHotelImageDetailsAdapter rvHotelImageDetailsAdapter;

    private RVCuisineDisplay rvCuisineDisplay;
    private RVTagsDisplay rvTagsDisplay;
    private RatingBar ratingBar;
    private Toolbar toolbar;
    private TextView toolBarText;
    private SpinKitView skLoading;
    private TextView tvName, tvEmail, tvNoImage, tvPhone, tvRatingBar, tvTiming, tvMob, tvAddress, tvCountry, tvState, tvCity, tvHotelType, tvGstn, tvCgst, tvSgst, tvTableCount;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activtiy_view_hotel_details);
        init();
        setUpToolBar();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            skLoading.setVisibility(View.GONE);
            hotelForm = bundle.getParcelable("hotelInfo");

            tvName.setText(hotelForm.getHotelName());
            tvEmail.setText(hotelForm.getHotelEmail());
            tvMob.setText(hotelForm.getHotelMob() + " / " + hotelForm.getHotelPhone());
            tvAddress.setText(hotelForm.getHotelAddress());
            tvGstn.setText(hotelForm.getGstnNo());
            tvSgst.setText(hotelForm.getHotelSgst());
            tvCgst.setText(hotelForm.getHotelCgst());
            tvCountry.setText(hotelForm.getHotelCountry());
            tvState.setText(hotelForm.getHotelState());
            tvCity.setText(hotelForm.getHotelCity());
            tvTiming.setText(hotelForm.getHotelStartTime() + " : " + hotelForm.getHotelEndTime());

            tvHotelType.setText("(" + hotelForm.getHoteltype() + ")");

            Drawable drawable = ratingBar.getProgressDrawable();
            drawable.setColorFilter(Color.parseColor("#FFC107"), PorterDuff.Mode.SRC_ATOP);
            ratingBar.setRating(Integer.parseInt(hotelForm.getHotelRating()));


            hotelImageFormArrayList = bundle.getParcelableArrayList("hotelImags");
            if (hotelImageFormArrayList.size() != 0) {
                tvNoImage.setVisibility(View.GONE);
                Picasso.with(this).load(hotelImageFormArrayList.get(0).getHotelImageName()).into(ivHotelFullImage);

                //Image Adapter
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
                rvHotelImage.setLayoutManager(linearLayoutManager);
                rvHotelImage.setHasFixedSize(true);
                rvHotelImage.getLayoutManager().setMeasurementCacheEnabled(false);
                rvHotelImageDetailsAdapter = new RVHotelImageDetailsAdapter(this, hotelImageFormArrayList, new ImageSelectedListerner() {
                    @Override
                    public void SelectedImage(int adapterPosition) {
                        Picasso.with(ActivityViewHotelDetails.this).load(hotelImageFormArrayList.get(adapterPosition).getHotelImageName()).into(ivHotelFullImage);
                    }
                });

                rvHotelImage.setAdapter(rvHotelImageDetailsAdapter);

            } else {
                tvNoImage.setVisibility(View.VISIBLE);
            }
            cuisineFormArrayList = bundle.getParcelableArrayList("CuisineList");


            //cuisineAdapter
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
            rvCuisine.setLayoutManager(gridLayoutManager);
            rvCuisine.setHasFixedSize(true);
            rvCuisine.getLayoutManager().setMeasurementCacheEnabled(false);
            rvCuisineDisplay = new RVCuisineDisplay(this, cuisineFormArrayList);
            rvCuisine.setAdapter(rvCuisineDisplay);

            //Tag adapter
            tagsFormArrayList = bundle.getParcelableArrayList("TagsList");
            GridLayoutManager gridLayoutManager1 = new GridLayoutManager(this, 2);
            rvTags.setLayoutManager(gridLayoutManager1);
            rvTags.setHasFixedSize(true);
            rvTags.getLayoutManager().setMeasurementCacheEnabled(false);
            rvTagsDisplay = new RVTagsDisplay(getApplicationContext(), tagsFormArrayList);
            rvTags.setAdapter(rvTagsDisplay);


        }
    }

    private void setUpToolBar() {
        toolBarText.setText("Hotel Details");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }


    private void init() {
        toolbar = findViewById(R.id.toolbar);
        toolBarText = toolbar.findViewById(R.id.tx_title);
        tvName = findViewById(R.id.tv_hotel_name);
        tvEmail = findViewById(R.id.tv_hotel_email);
        tvMob = findViewById(R.id.tv_mob);
        tvAddress = findViewById(R.id.tv_address);
        tvGstn = findViewById(R.id.tv_gstn_no);
        tvSgst = findViewById(R.id.tv_sgstn_no);
        tvCgst = findViewById(R.id.tv_cgstn_no);
        tvTableCount = findViewById(R.id.tv_table_count);

        rvCuisine = findViewById(R.id.rv_cuisine);
        rvTags = findViewById(R.id.rv_tags_details);

        ivHotelFullImage = findViewById(R.id.ivFullImage);
        rvHotelImage = findViewById(R.id.rvHotelImages);

        ratingBar = findViewById(R.id.ratingBar);
        tvHotelType = findViewById(R.id.tv_hotel_type);
        tvTiming = findViewById(R.id.tv_timing);
        tvNoImage = findViewById(R.id.tv_no_image);
        tvCountry = findViewById(R.id.tv_country);
        tvState = findViewById(R.id.tv_state);
        tvCity = findViewById(R.id.tv_city);
        skLoading=findViewById(R.id.skLoading);
    }
}

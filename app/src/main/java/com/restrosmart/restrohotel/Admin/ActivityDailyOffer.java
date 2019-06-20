package com.restrosmart.restrohotel.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.restrosmart.restrohotel.R;

public class ActivityDailyOffer  extends AppCompatActivity {
    private Toolbar mToolbar;
    private TextView tvTitle;
    private FrameLayout addOffer;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);

        init();
        setupTooloBar();
        addOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dailyOfferIntent=new Intent(ActivityDailyOffer.this,ActivityDailyOfferAdd.class);
                startActivity(dailyOfferIntent);

            }
        });
    }

    private void init() {
        mToolbar=findViewById(R.id.toolbar);
        tvTitle=mToolbar.findViewById(R.id.tx_title);
        addOffer=findViewById(R.id.ivAddOffer);
    }

    private void setupTooloBar() {
        setSupportActionBar(mToolbar);
        tvTitle.setText("Daily Offer");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}

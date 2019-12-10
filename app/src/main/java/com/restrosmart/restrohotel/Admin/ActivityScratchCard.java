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

public class ActivityScratchCard extends AppCompatActivity {
    private TextView tvTitle;
    private Toolbar mToolbar;
    private FrameLayout addButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);

        init();
        setupTooloBar();
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent scratchCardIntent=new Intent(ActivityScratchCard.this, ActivityDisplayScratchCard.class);
                startActivity(scratchCardIntent);
            }
        });
    }

    private void init() {
        mToolbar=findViewById(R.id.toolbar);
        tvTitle=mToolbar.findViewById(R.id.tx_title);
        addButton=findViewById(R.id.ivAddOffer);
    }

    private void setupTooloBar() {
        setSupportActionBar(mToolbar);
        tvTitle.setText("Scratch Card");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}


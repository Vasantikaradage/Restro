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

public class ActivityPromoCode  extends AppCompatActivity {

    private TextView tvTitle;
    private Toolbar mToolbar;
    private FrameLayout  addOffer;

       @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);

        init();
        setupTooloBar();

        addOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent promocodeIntent=new Intent(ActivityPromoCode.this, ActivityDisplayPromocode.class);
                startActivity(promocodeIntent);

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
        tvTitle.setText("PromoCode");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}

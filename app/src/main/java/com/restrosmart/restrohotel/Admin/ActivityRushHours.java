package com.restrosmart.restrohotel.Admin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;


import com.restrosmart.restrohotel.R;

public class ActivityRushHours  extends AppCompatActivity {
    private Toolbar mToolbar;
    private TextView tvTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);

        init();
        setupTooloBar();
    }

    private void init() {
        mToolbar=findViewById(R.id.toolbar);
        tvTitle=mToolbar.findViewById(R.id.tx_title);
    }

    private void setupTooloBar() {
        setSupportActionBar(mToolbar);
        tvTitle.setText("Rush Hours");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}


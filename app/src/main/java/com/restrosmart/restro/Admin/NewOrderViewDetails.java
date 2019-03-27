package com.restrosmart.restro.Admin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.restrosmart.restro.R;

/**
 * Created by SHREE on 05/10/2018.
 */

public class NewOrderViewDetails extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order_view);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);





    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}

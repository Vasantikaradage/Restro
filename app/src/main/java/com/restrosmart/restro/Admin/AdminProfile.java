package com.restrosmart.restro.Admin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.restrosmart.restro.R;

/**
 * Created by SHREE on 03/10/2018.
 */

public class AdminProfile extends AppCompatActivity {

    TextView mOld_pass, mNew_pass,mCon_pass;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mOld_pass = findViewById(R.id.et_old_pass);
        mNew_pass = findViewById(R.id.et_pass);
        mCon_pass = findViewById(R.id.et_cpass);

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}

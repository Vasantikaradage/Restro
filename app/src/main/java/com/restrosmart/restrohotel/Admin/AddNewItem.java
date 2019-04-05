package com.restrosmart.restrohotel.Admin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.restrosmart.restrohotel.R;

public class AddNewItem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //MultiStateToggleButton button = (MultiStateToggleButton) this.findViewById(R.id.mstb_multi_id);
//        button.setOnValueChangedListener(new ToggleButton.OnValueChangedListener() {
//            @Override
//            public void onValueChanged(int position) {
//                Log.d("", "Position__1: " + position);
//            }
//        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}

package com.restrosmart.restrohotel.Admin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.restrosmart.restrohotel.R;

public class ActivityMenuOrderDidplay  extends AppCompatActivity {
    private RecyclerView rvOrderMenuCount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_order);

        init();
    }

    private void init() {
        rvOrderMenuCount=findViewById(R.id.rv_count_order);
    }
}

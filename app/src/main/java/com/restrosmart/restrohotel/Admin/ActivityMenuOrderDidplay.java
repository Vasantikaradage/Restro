package com.restrosmart.restrohotel.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.restrosmart.restrohotel.Adapter.RVOrderDetailsAdapter;
import com.restrosmart.restrohotel.Model.MenuForm;
import com.restrosmart.restrohotel.R;

import java.util.ArrayList;

public class ActivityMenuOrderDidplay extends AppCompatActivity {
    private RecyclerView rvOrderMenuCount;
    private Toolbar mToolBar;
    private TextView tvToolBarTitle;

    private ArrayList<MenuForm> arrayList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_order);

        init();
        setUpToolBar();
        Intent intent=getIntent();
        arrayList=intent.getParcelableArrayListExtra("menuArray");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvOrderMenuCount.setHasFixedSize(true);
        rvOrderMenuCount.setLayoutManager(linearLayoutManager);
        RVOrderDetailsAdapter rvOrderDetailsAdapter=new RVOrderDetailsAdapter(getBaseContext(),arrayList);
        rvOrderMenuCount.setAdapter(rvOrderDetailsAdapter);
    }

    private void setUpToolBar() {
        tvToolBarTitle.setText("Order Details");
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
    }

    private void init() {
        rvOrderMenuCount = findViewById(R.id.rv_count_order);
        mToolBar = findViewById(R.id.toolbar);
        tvToolBarTitle = mToolBar.findViewById(R.id.tx_title);
    }
}

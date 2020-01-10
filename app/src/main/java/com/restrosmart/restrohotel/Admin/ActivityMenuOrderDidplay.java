package com.restrosmart.restrohotel.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.ybq.android.spinkit.SpinKitView;
import com.restrosmart.restrohotel.Adapter.RVOrderDetailsAdapter;
import com.restrosmart.restrohotel.Model.AdminOrderModel;
import com.restrosmart.restrohotel.Model.MenuForm;
import com.restrosmart.restrohotel.Model.OrderModel;
import com.restrosmart.restrohotel.R;

import java.util.ArrayList;

public class ActivityMenuOrderDidplay extends AppCompatActivity {
    private RecyclerView rvOrderMenuCount;
    private Toolbar mToolBar;
    private TextView tvToolBarTitle;

    private ArrayList<AdminOrderModel> arrayList;
    private  ArrayList<MenuForm> menuFormArrayList;
    private  ArrayList<String> arrayListIds;
    private LinearLayout llNodataAvailable;
    private SpinKitView spinKitView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_order);

        init();
        setUpToolBar();
        Intent intent=getIntent();
        spinKitView.setVisibility(View.VISIBLE);
        arrayList=intent.getParcelableArrayListExtra("orderArray");
       // ArrayList<String> arrayListIds= intent.getStringArrayListExtra("arrayListIds");

        if(arrayList != null && arrayList.size() > 0) {
            llNodataAvailable.setVisibility(View.GONE);

            for (int i = 0; i < arrayList.size(); i++) {
                arrayListIds.add("Order " + (i + 1));
            }

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            rvOrderMenuCount.setHasFixedSize(true);
            rvOrderMenuCount.setLayoutManager(linearLayoutManager);
            RVOrderDetailsAdapter rvOrderDetailsAdapter = new RVOrderDetailsAdapter(getBaseContext(), arrayListIds, arrayList);
            rvOrderMenuCount.setAdapter(rvOrderDetailsAdapter);
        }
        else
        {
            llNodataAvailable.setVisibility(View.VISIBLE);
        }
        spinKitView.setVisibility(View.GONE);
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
        arrayListIds=new ArrayList<>();
        llNodataAvailable=findViewById(R.id.llNoOrderMenuData);
        spinKitView=findViewById(R.id.skLoading);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }


}

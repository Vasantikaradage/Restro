package com.restrosmart.restrohotel.Captain.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.github.ybq.android.spinkit.SpinKitView;
import com.restrosmart.restrohotel.Captain.Adapters.CapSpecificOrderRVAdapter;
import com.restrosmart.restrohotel.Captain.Models.CapMenuModel;
import com.restrosmart.restrohotel.Captain.Models.CapOrderModel;
import com.restrosmart.restrohotel.R;

import java.util.ArrayList;

public class ActivityCapSpecificOrders extends AppCompatActivity {

    private Toolbar mToolbar;
    private RecyclerView rvOrders;
    private SpinKitView skLoading;
    private LinearLayout llNoOrders;

    private ArrayList<CapOrderModel> capOrderModelArrayList;
    private ArrayList<String> orderIdsArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cap_specific_orders);

        init();
        setupToolBar();

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            capOrderModelArrayList = bundle.getParcelableArrayList("arraylist");
        }

        if (capOrderModelArrayList != null && capOrderModelArrayList.size() > 0) {

            for (int i = 0; i < capOrderModelArrayList.size(); i++) {
                orderIdsArrayList.add("Order " + (i + 1));
            }

            CapSpecificOrderRVAdapter capSpecificOrderRVAdapter = new CapSpecificOrderRVAdapter(this, orderIdsArrayList, capOrderModelArrayList);
            rvOrders.setHasFixedSize(true);
            rvOrders.setNestedScrollingEnabled(false);
            rvOrders.setLayoutManager(new GridLayoutManager(this, 1));
            rvOrders.setItemAnimator(new DefaultItemAnimator());
            rvOrders.setAdapter(capSpecificOrderRVAdapter);

            rvOrders.setVisibility(View.VISIBLE);
            llNoOrders.setVisibility(View.GONE);
        } else {
            rvOrders.setVisibility(View.GONE);
            llNoOrders.setVisibility(View.VISIBLE);
        }


        skLoading.setVisibility(View.GONE);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void setupToolBar() {
        mToolbar.setTitle("Parcel Order");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
    }

    private void init() {
        orderIdsArrayList = new ArrayList<>();

        mToolbar = findViewById(R.id.toolbar);
        rvOrders = findViewById(R.id.rvOrders);
        skLoading = findViewById(R.id.skLoading);
        llNoOrders = findViewById(R.id.llNoOrders);
    }
}

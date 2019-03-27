package com.restrosmart.restro.Bar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.restrosmart.restro.Adapter.RVBarOrderAdapter;
import com.restrosmart.restro.Model.BarOrderModel;
import com.restrosmart.restro.Model.BarOrderSpecificModel;
import com.restrosmart.restro.R;

import java.util.ArrayList;

public class ActivityBarHome extends AppCompatActivity {

    private RecyclerView rvBarOrders;
    private RVBarOrderAdapter rvBarOrderAdapter;
    private ArrayList<BarOrderModel> barOrderModelArrayList = new ArrayList<>();
    private ArrayList<BarOrderSpecificModel> barOrderSpecificModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_home);

        init();

        getDummyData();

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL);
        rvBarOrders.setLayoutManager(staggeredGridLayoutManager);
        //rvKitchenOrders.setLayoutManager(new LinearLayoutManager(this));

        rvBarOrderAdapter = new RVBarOrderAdapter(this, barOrderModelArrayList);
        rvBarOrders.setAdapter(rvBarOrderAdapter);
    }

    int[] qty = {3, 2, 6, 3, 5, 4, 8, 2, 7};

    private void getDummyData() {
        for (int i = 0; i < qty.length; i++) {
            barOrderSpecificModelArrayList = new ArrayList<>();

            for (int j = 0; j < qty[i]; j++) {
                BarOrderSpecificModel barOrderSpecificModel = new BarOrderSpecificModel();
                barOrderSpecificModel.setBarOrderName("Menu " + j);
                barOrderSpecificModel.setBarOrderQty(qty[j]);
                barOrderSpecificModelArrayList.add(barOrderSpecificModel);
            }

            BarOrderModel barOrderModel = new BarOrderModel();
            barOrderModel.setBarOrderNo(i + 1);
            barOrderModel.setBarTableNo(i + 1);
            barOrderModel.setArrayList(barOrderSpecificModelArrayList);
            barOrderModelArrayList.add(barOrderModel);
        }
    }

    private void init() {
        rvBarOrders = findViewById(R.id.rvBarOrders);
    }
}

package com.restrosmart.restro.Kitchen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.restrosmart.restro.Adapter.RVKitchenOrderAdapter;
import com.restrosmart.restro.Model.KitchenOrderMenuModel;
import com.restrosmart.restro.Model.KitchenOrderModel;
import com.restrosmart.restro.R;

import java.util.ArrayList;

public class ActivityKitchenHome extends AppCompatActivity {

    private RecyclerView rvKitchenOrders;
    private RVKitchenOrderAdapter rvKitchenOrderAdapter;
    private ArrayList<KitchenOrderModel> kitchenOrderModelArrayList = new ArrayList<>();
    private ArrayList<KitchenOrderMenuModel> kitchenOrderMenuModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen_home);

        init();

        getDummyData();


        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL);
        rvKitchenOrders.setLayoutManager(staggeredGridLayoutManager);
        //rvKitchenOrders.setLayoutManager(new LinearLayoutManager(this));

        rvKitchenOrderAdapter = new RVKitchenOrderAdapter(this, kitchenOrderModelArrayList);
        rvKitchenOrders.setAdapter(rvKitchenOrderAdapter);
    }

    int[] qty = {3, 2, 6, 3, 5, 4};

    private void getDummyData() {
        for (int i = 0; i < qty.length; i++) {
            kitchenOrderMenuModelArrayList = new ArrayList<>();

            for (int j = 0; j < qty[i]; j++) {
                KitchenOrderMenuModel kitchenOrderMenuModel = new KitchenOrderMenuModel();
                kitchenOrderMenuModel.setOrderMenuName("Menu " + j);
                kitchenOrderMenuModel.setOrderMenuQty(qty[j]);
                kitchenOrderMenuModelArrayList.add(kitchenOrderMenuModel);
            }

            KitchenOrderModel kitchenOrderModel = new KitchenOrderModel();
            kitchenOrderModel.setmOrderNo(i + 1);
            kitchenOrderModel.setmTableNo(i + 1);
            kitchenOrderModel.setArrayList(kitchenOrderMenuModelArrayList);

            kitchenOrderModelArrayList.add(kitchenOrderModel);
        }
    }

    private void init() {
        rvKitchenOrders = findViewById(R.id.rvKitchenOrders);
    }
}

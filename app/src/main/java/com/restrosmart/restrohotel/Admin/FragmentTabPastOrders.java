package com.restrosmart.restrohotel.Admin;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.github.ybq.android.spinkit.SpinKitView;
import com.restrosmart.restrohotel.Adapter.AdapterPastOrder;
import com.restrosmart.restrohotel.Model.OrderModel;
import com.restrosmart.restrohotel.R;

import java.util.ArrayList;

public class FragmentTabPastOrders extends Fragment {
    private ArrayList<OrderModel> arraylistOrderCompleted;
    private ArrayList<String> arrayList;
   private  RecyclerView recyclerView;
    private LinearLayout llNodata;
    private SpinKitView skloding;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        arraylistOrderCompleted = getArguments().getParcelableArrayList("OrderArrayListCompleted");
        //   arrayList=getArguments().getStringArrayList("arrayListId");
        View view = inflater.inflate(R.layout.tab_past_orders, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init();


        if (arraylistOrderCompleted != null && arraylistOrderCompleted.size() != 0) {
           // skloding.setVisibility(View.GONE);
            //llNodata.setVisibility(View.GONE);

            for (int i = 0; i < arraylistOrderCompleted.size(); i++) {
                arrayList.add("Order " + (i + 1));
            }

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setHasFixedSize(true);
            recyclerView.getLayoutManager().setMeasurementCacheEnabled(false);
            AdapterPastOrder adapterPastOrder = new AdapterPastOrder(getActivity(), arraylistOrderCompleted);
            recyclerView.setAdapter(adapterPastOrder);
        } else {
            skloding.setVisibility(View.GONE);
            llNodata.setVisibility(View.VISIBLE);
        }

    }

    private void init() {
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_past_order);
        arrayList = new ArrayList<>();
       // skloding = getActivity().findViewById(R.id.skLoading);
       // llNodata = getActivity().findViewById(R.id.llNoOrderData);
    }
}

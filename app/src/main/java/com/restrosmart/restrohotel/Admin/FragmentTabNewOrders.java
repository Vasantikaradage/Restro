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
import com.restrosmart.restrohotel.Adapter.AdapterNewOrder;
import com.restrosmart.restrohotel.Model.OrderModel;
import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.Utils.Sessionmanager;
import java.util.ArrayList;
import java.util.HashMap;


import static com.restrosmart.restrohotel.Utils.Sessionmanager.HOTEL_ID;


public class FragmentTabNewOrders extends Fragment {
    private String userType;
    private Sessionmanager sessionmanager;
    private int hotelId;
    private RecyclerView recyclerView;
    ArrayList<OrderModel> arraylistOrder;
    private ArrayList<String> arrayList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // userType = getArguments().getString("userType");
        arraylistOrder = getArguments().getParcelableArrayList("orderArrayList");
        arrayList=getArguments().getStringArrayList("arrayListId");
        View view = inflater.inflate(R.layout.tab_orders, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();

        sessionmanager = new Sessionmanager(getActivity());
        HashMap<String, String> name_info = sessionmanager.getHotelDetails();
        hotelId = Integer.parseInt(name_info.get(HOTEL_ID));
        callViewAdapter();
    }


    private void callViewAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.getLayoutManager().setMeasurementCacheEnabled(false);
        AdapterNewOrder adapterNewOrder = new AdapterNewOrder(getActivity(), arraylistOrder,arrayList);
        recyclerView.setAdapter(adapterNewOrder);
    }

    private void init() {
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_new_order);
    }
}

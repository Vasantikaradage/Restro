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

import java.util.ArrayList;

public class FragmentTabPCancelledOrders extends Fragment {


    ArrayList<OrderModel> arraylistOrderCancel;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        arraylistOrderCancel  = getArguments().getParcelableArrayList("OrderArrayListCancel");

        View view = inflater.inflate(R.layout.tab_cancel_orders,container,false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        RecyclerView recyclerView = (RecyclerView)getActivity().findViewById(R.id.recycler_cancel_order);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
       /* recyclerView.setLayoutManager(linearLayoutManager);
        AdapterNewOrder adapterNewOrder = new AdapterNewOrder(getActivity(), arraylistOrderCancel);
        recyclerView.setAdapter(adapterNewOrder);*/


    }
}

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
import com.restrosmart.restrohotel.Adapter.Adapter_Ongoing_Order;
import com.restrosmart.restrohotel.Adapter.Kitchen_Ongoing_Order;
import com.restrosmart.restrohotel.Model.MenuDisplayForm;
import com.restrosmart.restrohotel.Model.OrderModel;
import com.restrosmart.restrohotel.R;

import java.util.ArrayList;

public class FragmentTabOngoingOrders extends Fragment {

    ArrayList<OrderModel> arrayList = new ArrayList<OrderModel>();

    ArrayList<MenuDisplayForm> menu_arrayList = new ArrayList<MenuDisplayForm>();

    String userType;
    private  ArrayList<OrderModel> arraylistOrderAccepted;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        arraylistOrderAccepted  = getArguments().getParcelableArrayList("OrderArrayListAccepted");

        userType=getArguments().getString("userType");
        View view = inflater.inflate(R.layout.tab_ongoing_orders,container,false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

       /* for(int i=0;i<4;i++) {

            MenuDisplayForm menu = new MenuDisplayForm();
            menu.setMenu_Name("Dosa");
           // menu.setMenu_qty("2");
            menu.setBranch_Id(50);
            menu_arrayList.add(menu);
        }
*/





/*  for(int i=0;i<menu_arrayList.size();i++) {

            String qty="Qty - "+"2";

            OrderModel orderModel =new OrderModel();
            orderModel.setCust_mob_no("9845246171");
            orderModel.setOrder_id("2");
            orderModel.setMenu_name("Dosa");
            orderModel.setMenu_qty(qty);
            orderModel.setMenu_price("40");
            orderModel.setTot_bill("80/-");
            orderModel.setTime("11:00 AM");
            orderModel.setMsg("Sweet");
            arrayList.add(orderModel);
        }*/








        RecyclerView recyclerView = (RecyclerView)getActivity().findViewById(R.id.recycler_ongoing_order);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);

        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setHasFixedSize(true);
        recyclerView.getLayoutManager().setMeasurementCacheEnabled(false);
        AdapterNewOrder adapterNewOrder = new AdapterNewOrder(getActivity(), arraylistOrderAccepted);
        recyclerView.setAdapter(adapterNewOrder);

       /* if(userType.equals("Admin")) {
            Adapter_Ongoing_Order adapterNewOrder = new Adapter_Ongoing_Order(getActivity(), arrayList);
            recyclerView.setAdapter(adapterNewOrder);
        }
        else
        {
            Kitchen_Ongoing_Order kitchen_ongoing_order=new Kitchen_Ongoing_Order(getActivity(),arrayList);
            recyclerView.setAdapter(kitchen_ongoing_order);
        }*/
    }
    }


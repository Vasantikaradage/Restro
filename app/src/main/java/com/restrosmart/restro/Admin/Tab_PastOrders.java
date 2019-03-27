package com.restrosmart.restro.Admin;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.restrosmart.restro.Adapter.AdapterPastOrder;
import com.restrosmart.restro.Model.MenuDisplayForm;
import com.restrosmart.restro.Model.Orders;
import com.restrosmart.restro.R;

import java.util.ArrayList;

public class Tab_PastOrders extends Fragment {

    ArrayList<Orders> arrayList = new ArrayList<Orders>();

    ArrayList<MenuDisplayForm> menu_arrayList = new ArrayList<MenuDisplayForm>();



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.tab_past_orders,container,false);

       return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        for(int i=0;i<4;i++) {

            MenuDisplayForm menu = new MenuDisplayForm();
            menu.setMenu_Name("Dosa");
           // menu.s("2");
            menu.setBranch_Id(50);
            menu_arrayList.add(menu);
        }





        for(int i=0;i<menu_arrayList.size();i++) {

            String qty="Qty - "+"2";

            Orders orders=new Orders();
            orders.setCust_mob_no("9845246171");
            orders.setOrder_id("2");
            orders.setMenu_name("Dosa");
            orders.setMenu_qty(qty);
            orders.setMenu_price("40");
            orders.setTot_bill("80/-");
            orders.setTime("11:00 AM");
            orders.setMsg("Sweet");
            arrayList.add(orders);
        }









        RecyclerView recyclerView = (RecyclerView)getActivity().findViewById(R.id.recycler_past_order);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);

        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setHasFixedSize(true);
        recyclerView.getLayoutManager().setMeasurementCacheEnabled(false);


            AdapterPastOrder adapterPastOrder = new AdapterPastOrder(getActivity(), arrayList);
            recyclerView.setAdapter(adapterPastOrder);
       /* }else
        {

            Kitchen_Past_Details kitchenPastOrder = new Kitchen_Past_Details(getActivity(), arrayList);
            recyclerView.setAdapter(kitchenPastOrder);

        }*/
    }

}

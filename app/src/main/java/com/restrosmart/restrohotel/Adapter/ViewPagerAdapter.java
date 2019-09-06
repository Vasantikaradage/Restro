package com.restrosmart.restrohotel.Adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.restrosmart.restrohotel.Admin.FragmentTabPastOrders;
import com.restrosmart.restrohotel.Admin.FragmentTabNewOrders;
import com.restrosmart.restrohotel.Model.OrderModel;

import java.util.ArrayList;


public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private int tabCount;
    private String userType;
    private ArrayList<OrderModel> orderModelArrayList;
    private ArrayList<OrderModel> orderModelArrayListAccepted;


    public ViewPagerAdapter(FragmentManager fm, int tabCount, String user_type, ArrayList<OrderModel> arrayListOder, ArrayList<OrderModel> arrayListOderAccepetd) {
        super(fm);
        this.tabCount = tabCount;
        this.userType = user_type;
        this.orderModelArrayList = arrayListOder;
        this.orderModelArrayListAccepted = arrayListOderAccepetd;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                FragmentTabNewOrders tabNewOrders = new FragmentTabNewOrders();
                Bundle bundle = new Bundle();
                bundle.putString("userType", userType);
                bundle.putParcelableArrayList("orderArrayList", orderModelArrayList);
                tabNewOrders.setArguments(bundle);
                return tabNewOrders;


            case 1:
                FragmentTabPastOrders tabPastOrders = new FragmentTabPastOrders();
                Bundle bundle2 = new Bundle();
                bundle2.putString("userType", userType);
                bundle2.putParcelableArrayList("OrderArrayListCompleted", orderModelArrayListAccepted);
                tabPastOrders.setArguments(bundle2);
                return tabPastOrders;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}

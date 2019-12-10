package com.restrosmart.restrohotel.Adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.restrosmart.restrohotel.Admin.FragmentTabPastOrders;
import com.restrosmart.restrohotel.Admin.FragmentTabNewOrders;
import com.restrosmart.restrohotel.Admin.FragmentTabTakeAwayOrders;
import com.restrosmart.restrohotel.Model.OrderModel;

import java.util.ArrayList;


public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private int tabCount;
    private String userType;

    private ArrayList<OrderModel> orderModelArrayListTakeAway;
    private ArrayList<OrderModel> orderModelArrayListNew;
    private ArrayList<OrderModel> orderModelArrayListAccepted;

    private   ArrayList<String> arrayListIdNew;
    private   ArrayList<String> arrayListIdParcel;
    private   ArrayList<String> arrayListIdAccept;




    public ViewPagerAdapter(FragmentManager fm, int tabCount, String user_type, ArrayList<OrderModel> arrayListOrderTakeAway, ArrayList<OrderModel> arrayListNewOder, ArrayList<OrderModel> arrayListOderAccepetd, ArrayList<String> arrayListIdNew, ArrayList<String> arrayListIdParcel, ArrayList<String> arrayListIdAccept) {
        super(fm);
        this.tabCount = tabCount;
        this.userType = user_type;
        this.orderModelArrayListTakeAway=arrayListOrderTakeAway;
        this.orderModelArrayListNew = arrayListNewOder;
        this.orderModelArrayListAccepted = arrayListOderAccepetd;



        this.arrayListIdNew=arrayListIdNew;
        this.arrayListIdParcel=arrayListIdParcel;
        this.arrayListIdAccept=arrayListIdAccept;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {

           case 0:
                FragmentTabTakeAwayOrders fragmentTabTakeAwayOrders = new FragmentTabTakeAwayOrders();
                Bundle bundle3 = new Bundle();
                bundle3.putString("userType", userType);
                bundle3.putParcelableArrayList("OrderArrayListTakeAway", orderModelArrayListTakeAway);
                bundle3.putStringArrayList("arrayListId",arrayListIdParcel);
                fragmentTabTakeAwayOrders.setArguments(bundle3);
                return fragmentTabTakeAwayOrders;

            case 1:
                FragmentTabNewOrders tabNewOrders = new FragmentTabNewOrders();
                Bundle bundle = new Bundle();
                bundle.putString("userType", userType);
                bundle.putParcelableArrayList("orderArrayList", orderModelArrayListNew);
                bundle.putStringArrayList("arrayListId",arrayListIdNew);
                tabNewOrders.setArguments(bundle);
                return tabNewOrders;


            case 2:
                FragmentTabPastOrders tabPastOrders = new FragmentTabPastOrders();
                Bundle bundle2 = new Bundle();
                bundle2.putString("userType", userType);
                bundle2.putParcelableArrayList("OrderArrayListCompleted", orderModelArrayListAccepted);
                bundle2.putStringArrayList("arrayListId",arrayListIdAccept);
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

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




    public ViewPagerAdapter(FragmentManager fm, int tabCount, String user_type, ArrayList<OrderModel> arrayListOrderTakeAway, ArrayList<OrderModel> arrayListNewOder, ArrayList<OrderModel> arrayListOderAccepetd) {
        super(fm);
        this.tabCount = tabCount;
        this.userType = user_type;
        this.orderModelArrayListTakeAway=arrayListOrderTakeAway;
        this.orderModelArrayListNew = arrayListNewOder;
        this.orderModelArrayListAccepted = arrayListOderAccepetd;



       /* this.arrayListIdNew=arrayListIdNew;
        this.arrayListIdParcel=arrayListIdParcel;
        this.arrayListIdAccept=arrayListIdAccept;*/
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {

           case 0:
                FragmentTabTakeAwayOrders fragmentTabTakeAwayOrders = new FragmentTabTakeAwayOrders();
                Bundle bundle = new Bundle();
                bundle.putString("userType", userType);
                bundle.putParcelableArrayList("OrderArrayListTakeAway", orderModelArrayListTakeAway);
              //  bundle3.putStringArrayList("arrayListId",arrayListIdParcel);
                fragmentTabTakeAwayOrders.setArguments(bundle);
                return fragmentTabTakeAwayOrders;

            case 1:
                FragmentTabNewOrders tabNewOrders = new FragmentTabNewOrders();
                Bundle bundleNew = new Bundle();
                bundleNew.putString("userType", userType);
                bundleNew.putParcelableArrayList("orderArrayList", orderModelArrayListNew);
             //   bundle.putStringArrayList("arrayListId",arrayListIdNew);
                tabNewOrders.setArguments(bundleNew);
                return tabNewOrders;


            case 2:
                FragmentTabPastOrders tabPastOrders = new FragmentTabPastOrders();
                Bundle bundlePast = new Bundle();
                bundlePast.putString("userType", userType);
                bundlePast.putParcelableArrayList("OrderArrayListCompleted", orderModelArrayListAccepted);
              //  bundle2.putStringArrayList("arrayListId",arrayListIdAccept);
                tabPastOrders.setArguments(bundlePast);
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

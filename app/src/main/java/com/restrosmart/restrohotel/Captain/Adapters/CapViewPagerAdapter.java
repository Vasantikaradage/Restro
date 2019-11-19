package com.restrosmart.restrohotel.Captain.Adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.restrosmart.restrohotel.Captain.Fragments.FragmentParcelOrders;
import com.restrosmart.restrohotel.Captain.Fragments.FragmentTableOrders;
import com.restrosmart.restrohotel.Captain.Models.OrderModel;
import com.restrosmart.restrohotel.Captain.Models.UserCategory;
import com.restrosmart.restrohotel.Model.FreeTables;

import java.util.ArrayList;

public class CapViewPagerAdapter extends FragmentStatePagerAdapter {

    private int tabCount;
    private ArrayList<OrderModel> orderModelArrayList;
    private ArrayList<OrderModel> orderModelArrayListAccepted;
    private ArrayList<UserCategory> userCategoryArrayList;

    public CapViewPagerAdapter(FragmentManager fm, int tabCount, ArrayList<OrderModel> arrayListOder, ArrayList<OrderModel> arrayListOderAccepetd, ArrayList<UserCategory> arrayListUserCategory) {
        super(fm);
        this.tabCount = tabCount;
        this.orderModelArrayList = arrayListOder;
        this.orderModelArrayListAccepted = arrayListOderAccepetd;
        this.userCategoryArrayList = arrayListUserCategory;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                FragmentParcelOrders fragmentParcelOrders = new FragmentParcelOrders();
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("arrayListParcelOrder", orderModelArrayList);
                bundle.putParcelableArrayList("arrayListUserCategory", userCategoryArrayList);
                fragmentParcelOrders.setArguments(bundle);
                return fragmentParcelOrders;
            case 1:
                FragmentTableOrders fragmentTableOrders = new FragmentTableOrders();
                Bundle bundle2 = new Bundle();
                bundle2.putParcelableArrayList("arrayListTableOrder", orderModelArrayListAccepted);
                bundle2.putParcelableArrayList("arrayListUserCategory", userCategoryArrayList);
                fragmentTableOrders.setArguments(bundle2);
                return fragmentTableOrders;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}

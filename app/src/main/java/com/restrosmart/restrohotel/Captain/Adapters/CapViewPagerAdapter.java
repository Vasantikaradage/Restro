package com.restrosmart.restrohotel.Captain.Adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.restrosmart.restrohotel.Captain.Fragments.FragmentParcelOrders;
import com.restrosmart.restrohotel.Captain.Fragments.FragmentTableOrders;
import com.restrosmart.restrohotel.Captain.Models.AllOrderModel;
import com.restrosmart.restrohotel.Captain.Models.OrderModel;
import com.restrosmart.restrohotel.Captain.Models.UserCategory;
import com.restrosmart.restrohotel.Model.FreeTables;

import java.util.ArrayList;

public class CapViewPagerAdapter extends FragmentStatePagerAdapter {

    private int tabCount;
    private ArrayList<AllOrderModel> parcelArrayList;
    private ArrayList<AllOrderModel> tableArrayList;
    private ArrayList<UserCategory> userCategoryArrayList;

    public CapViewPagerAdapter(FragmentManager fm, int tabCount, ArrayList<AllOrderModel> parcelAllOrderModelArrayList, ArrayList<AllOrderModel> TableAllOrderModelArrayList, ArrayList<UserCategory> arrayListUserCategory) {
        super(fm);
        this.tabCount = tabCount;
        this.parcelArrayList = parcelAllOrderModelArrayList;
        this.tableArrayList = TableAllOrderModelArrayList;
        this.userCategoryArrayList = arrayListUserCategory;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                FragmentParcelOrders fragmentParcelOrders = new FragmentParcelOrders();
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("arrayListParcelOrder", parcelArrayList);
                bundle.putParcelableArrayList("arrayListUserCategory", userCategoryArrayList);
                fragmentParcelOrders.setArguments(bundle);
                return fragmentParcelOrders;
            case 1:
                FragmentTableOrders fragmentTableOrders = new FragmentTableOrders();
                Bundle bundle2 = new Bundle();
                bundle2.putParcelableArrayList("arrayListTableOrder", tableArrayList);
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

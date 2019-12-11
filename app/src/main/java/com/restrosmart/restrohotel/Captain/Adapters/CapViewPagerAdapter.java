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

    private ArrayList<AllOrderModel> parcelArrayList;
    private ArrayList<AllOrderModel> tableArrayList;
    private ArrayList<UserCategory> userCategoryArrayList;

    private final ArrayList<Fragment> mFragmentList = new ArrayList<>();
    private final ArrayList<String> mFragmentTitleList = new ArrayList<>();

    public CapViewPagerAdapter(FragmentManager fm, ArrayList<AllOrderModel> parcelAllOrderModelArrayList, ArrayList<AllOrderModel> TableAllOrderModelArrayList, ArrayList<UserCategory> arrayListUserCategory) {
        super(fm);
        this.parcelArrayList = parcelAllOrderModelArrayList;
        this.tableArrayList = TableAllOrderModelArrayList;
        this.userCategoryArrayList = arrayListUserCategory;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Fragment fragment = mFragmentList.get(position);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("arrayListParcelOrder", parcelArrayList);
                bundle.putParcelableArrayList("arrayListUserCategory", userCategoryArrayList);
                fragment.setArguments(bundle);
                return fragment;
            case 1:
                Fragment fragment1 = mFragmentList.get(position);
                Bundle bundle2 = new Bundle();
                bundle2.putParcelableArrayList("arrayListTableOrder", tableArrayList);
                bundle2.putParcelableArrayList("arrayListUserCategory", userCategoryArrayList);
                fragment1.setArguments(bundle2);
                return fragment1;
            default:
                return null;
        }
    }

    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}

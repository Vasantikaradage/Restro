package com.restrosmart.restrohotel.Adapter;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.restrosmart.restrohotel.Admin.FragmentTabOngoingOrders;
import com.restrosmart.restrohotel.Admin.FragmentTabPCancelledOrders;
import com.restrosmart.restrohotel.Admin.FragmentTabPastOrders;
import com.restrosmart.restrohotel.Admin.FragmentTabNewOrders;


public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private int tabCount;
    private String userType;

    public ViewPagerAdapter(FragmentManager fm, int tabCount, String user_type) {
        super(fm);
        this.tabCount = tabCount;
        this.userType = user_type;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                FragmentTabNewOrders tabNewOrders = new FragmentTabNewOrders();
                Bundle bundle = new Bundle();
                bundle.putString("userType", userType);
                tabNewOrders.setArguments(bundle);
                return tabNewOrders;

            case 1:
                FragmentTabOngoingOrders tabOngoingOrders = new FragmentTabOngoingOrders();
                Bundle bundle1 = new Bundle();
                bundle1.putString("userType", userType);
                tabOngoingOrders.setArguments(bundle1);
                return tabOngoingOrders;

            case 2:
                FragmentTabPastOrders tabPastOrders = new FragmentTabPastOrders();
                Bundle bundle2 = new Bundle();
                bundle2.putString("userType", userType);
                tabPastOrders.setArguments(bundle2);
                return tabPastOrders;

            case 3:
                FragmentTabPCancelledOrders tabCancelOrders = new FragmentTabPCancelledOrders();
                Bundle bundle3 = new Bundle();
                bundle3.putString("userType", userType);
                tabCancelOrders.setArguments(bundle3);
                return tabCancelOrders;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}

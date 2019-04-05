package com.restrosmart.restrohotel.Adapter;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.restrosmart.restrohotel.Admin.Tab_OngoingOrders;
import com.restrosmart.restrohotel.Admin.Tab_PCancelledOrders;
import com.restrosmart.restrohotel.Admin.Tab_PastOrders;
import com.restrosmart.restrohotel.Admin.Tab_newOrders;


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
                Tab_newOrders tabNewOrders = new Tab_newOrders();
                Bundle bundle = new Bundle();
                bundle.putString("userType", userType);
                tabNewOrders.setArguments(bundle);
                return tabNewOrders;

            case 1:
                Tab_OngoingOrders tabOngoingOrders = new Tab_OngoingOrders();
                Bundle bundle1 = new Bundle();
                bundle1.putString("userType", userType);
                tabOngoingOrders.setArguments(bundle1);
                return tabOngoingOrders;

            case 2:
                Tab_PastOrders tabPastOrders = new Tab_PastOrders();
                Bundle bundle2 = new Bundle();
                bundle2.putString("userType", userType);
                tabPastOrders.setArguments(bundle2);
                return tabPastOrders;

            case 3:
                Tab_PCancelledOrders tabCancelOrders = new Tab_PCancelledOrders();
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

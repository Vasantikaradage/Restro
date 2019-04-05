package com.restrosmart.restrohotel.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by SHREE on 15/10/2018.
 */

public class ViewPagerAdapterMenu extends FragmentStatePagerAdapter {
    public ViewPagerAdapterMenu(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }
/*

    //integer to count number of tabs
    int tabCount;
    String userType;
    Tab_PastOrders tabPastOrders;


    public ViewPagerAdapterMenu(FragmentManager fm, int tabCount, String user_type) {
        super(fm);
        this.tabCount = tabCount;
        this.userType=user_type;

    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                TabVeg tabNewOrders = new TabVeg();

                Bundle bundle=new Bundle();
                bundle.putString("userType",userType);
                tabNewOrders.setArguments(bundle);
                return Tab_Veg;


            case 1: Tab tabOngoingOrders = new Tab_OngoingOrders();
                Bundle bundle1=new Bundle();
                bundle1.putString("userType",userType);
                tabOngoingOrders.setArguments(bundle1);
                return tabOngoingOrders;

            case 2:
                tabPastOrders = new Tab_PastOrders();
                Bundle bundle2=new Bundle();
                bundle2.putString("userType",userType);
                tabPastOrders.setArguments(bundle2);
                return tabPastOrders;









            default:return null;



        }


    }

    @Override
    public int getCount() {
        return tabCount;
    }

*/

}


package com.restrosmart.restrohotel.Admin;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.restrosmart.restrohotel.Adapter.ViewPagerAdapter;
import com.restrosmart.restrohotel.R;


public class FragmentAllOrders extends Fragment {

    TabLayout tabLayout;
    Toolbar toolbar;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    String user_type;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        user_type = getArguments().getString("role");
        View view = inflater.inflate(R.layout.fragment_all_orders, null);

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init();


    }

    private void init() {

        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        tabLayout = (TabLayout) getActivity().findViewById(R.id.tablayout);

        tabLayout.addTab(tabLayout.newTab().setText("New Orders").setIcon(R.drawable.ic_order_accepted));
        tabLayout.addTab(tabLayout.newTab().setText("Ongoing Orders").setIcon(R.drawable.ic_order_preparing));

        if (user_type.equals("2")) {
            tabLayout.addTab(tabLayout.newTab().setText("Past Orders").setIcon(R.drawable.ic_order_served));
        }
        tabLayout.addTab(tabLayout.newTab().setText("Cancelled Orders").setIcon(R.drawable.ic_meat));


        tabLayout.setTabGravity(TabLayout.MODE_SCROLLABLE);
        tabLayout.setBackground(getContext().getResources().getDrawable(R.drawable.login_bg));
        viewPager = (ViewPager) getActivity().findViewById(R.id.pager);
        viewPagerAdapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager(), tabLayout.getTabCount(), user_type);


        viewPager.setAdapter(viewPagerAdapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {


                viewPager.setCurrentItem(tab.getPosition());

                if (tab.getPosition() == 1) {
                    toolbar.setBackground(getContext().getResources().getDrawable(R.drawable.login_bg));
                    tabLayout.setBackground(getContext().getResources().getDrawable(R.drawable.login_bg));
                    //tabLayout.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorAccent));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        getActivity().getWindow().setStatusBarColor(ContextCompat.getColor(getActivity(),
                                R.color.colorPrimaryDark));
                    }

                } else if (tab.getPosition() == 2) {

                    toolbar.setBackground(getContext().getResources().getDrawable(R.drawable.login_bg));
                    tabLayout.setBackground(getContext().getResources().getDrawable(R.drawable.login_bg));
                    //tabLayout.setBackgroundColor(ContextCompat.getColor(getActivity(), android.R.color.holo_purple));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        getActivity().getWindow().setStatusBarColor((ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark)));
                    }

                } else {
                    toolbar.setBackground(getContext().getResources().getDrawable(R.drawable.login_bg));
                    tabLayout.setBackground(getContext().getResources().getDrawable(R.drawable.login_bg));
                    //tabLayout.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        getActivity().getWindow().setStatusBarColor(ContextCompat.getColor(getActivity(),
                                R.color.colorPrimaryDark));
                    }
                }


                // int position =  tabLayout.getSelectedTabPosition();

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

                toolbar.setBackgroundColor(ContextCompat.getColor(getActivity(),
                        R.color.colorPrimary));

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }


}

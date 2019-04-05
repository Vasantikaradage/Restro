package com.restrosmart.restrohotel.Admin;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.restrosmart.restrohotel.R;

public  class FragmentToppings extends Fragment {

    private  View view;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private  Toolbar toolbar;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_toppings, null);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();

        tabLayout.addTab(tabLayout.newTab().setText("Veg"));
        tabLayout.addTab(tabLayout.newTab().setText("Non Veg"));
       // ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        tabLayout.setTabGravity(TabLayout.MODE_SCROLLABLE);

        Pager adapter = new Pager(getActivity().getSupportFragmentManager(), tabLayout.getTabCount());

        //Adding adapter to pager
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {


                viewPager.setCurrentItem(tab.getPosition());

                if (tab.getPosition() == 1) {
                   // toolbar.setBackground(getContext().getResources().getDrawable(R.drawable.login_bg));
                    tabLayout.setBackground(getContext().getResources().getDrawable(R.drawable.login_bg));
                    //tabLayout.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorAccent));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        getActivity().getWindow().setStatusBarColor(ContextCompat.getColor(getActivity(),
                                R.color.colorPrimaryDark));
                    }

                } else if (tab.getPosition() == 2) {

                 //   toolbar.setBackground(getContext().getResources().getDrawable(R.drawable.login_bg));
                    tabLayout.setBackground(getContext().getResources().getDrawable(R.drawable.login_bg));
                    //tabLayout.setBackgroundColor(ContextCompat.getColor(getActivity(), android.R.color.holo_purple));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        getActivity().getWindow().setStatusBarColor((ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark)));
                    }

                } else {
                  //  toolbar.setBackground(getContext().getResources().getDrawable(R.drawable.login_bg));
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

                //toolbar.setBackgroundColor(ContextCompat.getColor(getActivity(),
                   //     R.color.colorPrimary));

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //Adding onTabSelectedListener to swipe views
        //tabLayout.addOnTabSelectedListener((TabLayout.BaseOnTabSelectedListener) getActivity());


    }

    private void init() {
        tabLayout = (TabLayout) view.findViewById(R.id.tablayout);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
    }
}

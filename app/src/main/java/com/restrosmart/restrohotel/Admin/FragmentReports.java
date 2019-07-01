package com.restrosmart.restrohotel.Admin;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.restrosmart.restrohotel.Adapter.TabreportAdapter;
import com.restrosmart.restrohotel.R;

/**
 * Created by SHREE on 30/11/2018.
 */

public  class FragmentReports extends Fragment {
    private View view;

    private TabreportAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.fragment_reports, null);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();

        adapter = new TabreportAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new FragmentTabDailyReport(), "Daily");
        adapter.addFragment(new FragmentTabWeeklyReport(), "Weekly");
        adapter.addFragment(new FragmentTabMonthlyReport(), "Monthly");
        adapter.addFragment(new FragmentTabYearReport(), "Yearly");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);



    }

    private void init() {
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
    }
}

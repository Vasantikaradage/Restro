package com.restrosmart.restrohotel.SuperAdmin.Fragments;

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
import com.restrosmart.restrohotel.Admin.FragmentTabDailyReport;
import com.restrosmart.restrohotel.Admin.FragmentTabMonthlyReport;
import com.restrosmart.restrohotel.Admin.FragmentTabWeeklyReport;
import com.restrosmart.restrohotel.Admin.FragmentTabYearReport;
import com.restrosmart.restrohotel.R;

public class FragmentSAReports extends Fragment {

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
        adapter.addFragment(new FragmentTabSADailyReport(), "Daily");
        adapter.addFragment(new FragmentTabSAWeeklyReport(), "Weekly");
        adapter.addFragment(new FragmentTabSAMonthlyReport(), "Monthly");
        adapter.addFragment(new FragmentTabSAYearReport(), "Yearly");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);



    }

    private void init() {
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
    }
}





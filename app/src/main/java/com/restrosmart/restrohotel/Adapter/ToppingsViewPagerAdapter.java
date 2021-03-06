package com.restrosmart.restrohotel.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.restrosmart.restrohotel.Admin.FragmentTabParentToppings;
import com.restrosmart.restrohotel.Model.ParentCategoryForm;
import com.restrosmart.restrohotel.Model.ParentToppingsInfoForm;
import java.util.ArrayList;
import java.util.List;

public class ToppingsViewPagerAdapter extends FragmentPagerAdapter {

    Context context;
    private ArrayList<ParentCategoryForm> mFragmentTitleList;
    private List<ParentToppingsInfoForm> mAddParentToppnigsinfos;

    public ToppingsViewPagerAdapter(FragmentManager fm, ArrayList<ParentCategoryForm> mFragmentTitleList, List<ParentToppingsInfoForm> mFragmentToppingsTitalList) {
        super(fm);
        this.mFragmentTitleList = mFragmentTitleList;
        this.mAddParentToppnigsinfos = mFragmentToppingsTitalList;
    }

    @Override
    public Fragment getItem(int position) {
        ParentToppingsInfoForm parentToppingsInfoForm = mAddParentToppnigsinfos.get(position);
        return FragmentTabParentToppings.newInstance(parentToppingsInfoForm.getToppingsFoprms(),position);

    }

    @Override
    public int getCount() {
        return mFragmentTitleList == null ? 0 : mFragmentTitleList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return  mFragmentTitleList.get(position).getName();
    }
}

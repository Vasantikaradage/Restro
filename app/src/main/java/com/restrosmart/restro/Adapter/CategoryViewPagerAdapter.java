package com.restrosmart.restro.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.restrosmart.restro.Admin.TabParentCategoryFragment;
import com.restrosmart.restro.Interfaces.Category;
import com.restrosmart.restro.Model.AddParentCategoryinfo;
import com.restrosmart.restro.Model.ParentCategoryForm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SHREE on 16/10/2018.
 */

public class CategoryViewPagerAdapter extends FragmentPagerAdapter {


    Context context;
    private ArrayList<ParentCategoryForm> mFragmentTitleList = new ArrayList<>();
    private List<AddParentCategoryinfo> mAddParentCategoryinfos;
    private Category category;


    public CategoryViewPagerAdapter(FragmentManager fm, ArrayList<ParentCategoryForm> mFragmentTitleList, List<AddParentCategoryinfo> mFragmentCategoryTitalList, Category category) {
        super(fm);
        this.mFragmentTitleList = mFragmentTitleList;
        this.mAddParentCategoryinfos = mFragmentCategoryTitalList;
        this.category=category;
    }

    @Override
    public Fragment getItem(int position) {
        AddParentCategoryinfo addParentCategoryinfo = mAddParentCategoryinfos.get(position);
        category.categoryListern(mFragmentTitleList.get(position).getPc_id());
        return TabParentCategoryFragment.newInstance(addParentCategoryinfo.getCategoryForms(),position);

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

package com.restrosmart.restrohotel.Adapter;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.restrosmart.restrohotel.Admin.FragmentTabParentCategory;
import com.restrosmart.restrohotel.Model.AddParentCategoryinfoModel;
import com.restrosmart.restrohotel.Model.ParentCategoryForm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SHREE on 16/10/2018.
 */

public class CategoryViewPagerAdapter extends FragmentPagerAdapter {
    Context context;
    private ArrayList<ParentCategoryForm> mFragmentTitleList = new ArrayList<>();
    private List<AddParentCategoryinfoModel> mAddParentCategoryinfoModels;



    public CategoryViewPagerAdapter(FragmentManager fm, ArrayList<ParentCategoryForm> mFragmentTitleList, List<AddParentCategoryinfoModel> mFragmentCategoryTitalList) {
        super(fm);
        this.mFragmentTitleList = mFragmentTitleList;
        this.mAddParentCategoryinfoModels = mFragmentCategoryTitalList;

    }

    @Override
    public Fragment getItem(int position) {
        AddParentCategoryinfoModel addParentCategoryinfoModel = mAddParentCategoryinfoModels.get(position);
        return FragmentTabParentCategory.newInstance(addParentCategoryinfoModel.getCategoryForms(),position);

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

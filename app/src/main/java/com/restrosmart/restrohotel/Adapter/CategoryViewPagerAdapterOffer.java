package com.restrosmart.restrohotel.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.restrosmart.restrohotel.Admin.FragmentTabParentCategory;
import com.restrosmart.restrohotel.Admin.FragmentTabParentCategoryOffer;
import com.restrosmart.restrohotel.Captain.Models.UserCategory;
import com.restrosmart.restrohotel.Model.AddParentCategoryinfoModel;
import com.restrosmart.restrohotel.Model.ParentCategoryForm;

import java.util.ArrayList;
import java.util.List;

public class CategoryViewPagerAdapterOffer extends FragmentPagerAdapter {

    Context context;
    private ArrayList<ParentCategoryForm> mFragmentTitleList = new ArrayList<>();
    private List<AddParentCategoryinfoModel> mAddParentCategoryinfoModels;

    public CategoryViewPagerAdapterOffer(FragmentManager supportFragmentManager, ArrayList<ParentCategoryForm> mFragmentTitleList, List<AddParentCategoryinfoModel> addParentCategoryinfoModels) {
        super(supportFragmentManager);
        this.mFragmentTitleList = mFragmentTitleList;
        this.mAddParentCategoryinfoModels = addParentCategoryinfoModels;

    }

    @Override
    public Fragment getItem(int position) {
        AddParentCategoryinfoModel addParentCategoryinfoModel = mAddParentCategoryinfoModels.get(position);
        return FragmentTabParentCategoryOffer.newInstance(addParentCategoryinfoModel.getCategoryForms(),position);

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

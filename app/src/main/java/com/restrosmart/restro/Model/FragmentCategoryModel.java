package com.restrosmart.restro.Model;

import android.support.v4.app.Fragment;

import java.util.ArrayList;

/**
 * Created by SHREE on 16/10/2018.
 */

public class FragmentCategoryModel {

    private Fragment fragment;
    private ArrayList<TabCategoryModel> arrayList;


    public FragmentCategoryModel() {}

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public ArrayList<TabCategoryModel> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<TabCategoryModel> arrayList) {
        this.arrayList = arrayList;
    }
}

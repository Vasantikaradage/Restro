package com.restrosmart.restro.Model;

import android.support.v4.app.Fragment;

import java.util.ArrayList;

/**
 * Created by SHREE on 25/10/2018.
 */

public class AddParentCategoryinfo  {

    private Fragment fragment;
    private ArrayList<CategoryForm> categoryForms;

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public ArrayList<CategoryForm> getCategoryForms() {
        return categoryForms;
    }

    public void setCategoryForms(ArrayList<CategoryForm> categoryForms) {
        this.categoryForms = categoryForms;
    }
}

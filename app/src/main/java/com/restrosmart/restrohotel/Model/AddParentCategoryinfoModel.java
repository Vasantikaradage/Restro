package com.restrosmart.restrohotel.Model;

import android.support.v4.app.Fragment;

import java.util.ArrayList;

/**
 * Created by SHREE on 25/10/2018.
 */

public class AddParentCategoryinfoModel {

    private Fragment fragment;
    private ArrayList<CategoryForm> categoryForms;
    private  int winnerQty;

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

    public int getWinnerQty() {
        return winnerQty;
    }

    public void setWinnerQty(int winnerQty) {
        this.winnerQty = winnerQty;
    }
}

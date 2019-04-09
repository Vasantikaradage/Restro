package com.restrosmart.restrohotel.Model;

import android.support.v4.app.Fragment;

import java.util.ArrayList;

public class ParentToppingsInfo {
    private Fragment fragment;
    private ArrayList<ToppingsForm> ToppingsFoprms;

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public ArrayList<ToppingsForm> getToppingsFoprms() {
        return ToppingsFoprms;
    }

    public void setToppingsFoprms(ArrayList<ToppingsForm> toppingsFoprms) {
        ToppingsFoprms = toppingsFoprms;
    }
}

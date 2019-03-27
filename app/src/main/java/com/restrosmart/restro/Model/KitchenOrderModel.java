package com.restrosmart.restro.Model;

import java.util.ArrayList;

public class KitchenOrderModel {

    private int mTableNo, mOrderNo;
    private ArrayList<KitchenOrderMenuModel> arrayList;

    public KitchenOrderModel() {
    }

    public int getmTableNo() {
        return mTableNo;
    }

    public void setmTableNo(int mTableNo) {
        this.mTableNo = mTableNo;
    }

    public int getmOrderNo() {
        return mOrderNo;
    }

    public void setmOrderNo(int mOrderNo) {
        this.mOrderNo = mOrderNo;
    }

    public ArrayList<KitchenOrderMenuModel> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<KitchenOrderMenuModel> arrayList) {
        this.arrayList = arrayList;
    }
}
